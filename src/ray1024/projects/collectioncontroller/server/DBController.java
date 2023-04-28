package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.data.*;

import java.sql.*;
import java.time.LocalDateTime;

public class DBController {
    private Connection connection;
    private Server server;
    private final CryptoController cryptoController = new CryptoController();

    private String login = "login";
    private String password = "password";
    private String url = "JDBC-URL";

    public DBController(Server server) {
        try {
            this.server = server;
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, login, password);
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            // CREATE TABLE USERS
            //statement.execute("drop table users");
            statement.execute("CREATE TABLE IF NOT EXISTS users" + "(" + "    id            BIGSERIAL PRIMARY KEY," + "    login         text UNIQUE NOT NULL," + "    salt          text        NOT NULL," + "    password_hash text        NOT NULL" + ")");

            // CREATE TABLE COLLECTION
            //statement.execute("drop table collection");
            //statement = connection.createStatement();
            statement.execute("create table if not exists collection" + "(" + "    id                  bigserial primary key," + "    name                text      not null check ( name <> '' )," + "    create_time         text      not null," + "    students_count      integer   not null check ( students_count > 0 )," + "    form_of_education   text," + "    semester            text      not null," + "    has_person          boolean   not null," + "    owner_id            bigserial not null," + "    coords_x            float4    not null check ( coords_x <= 948 )," + "    coords_y            integer   not null check ( coords_y > -544 )," + "    person_name         text      not null check ( person_name <> '' )," + "    person_weight       float8    not null check ( person_weight > 0 )," + "    person_has_location boolean   not null," + "    person_location_x   float8    not null," + "    person_location_y   integer   not null," + "    person_location_z   integer   not null" + ")");

        } catch (SQLException e) {
            System.out.println("--- SQL EXCEPTION ---");
        } catch (ClassNotFoundException e) {
            System.out.println("--- CAN'T FIND POSTGRESQL DRIVER ---");
        }
    }

    public UserManager getUsers() {
        UserManager userManager = new UserManager();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id,login,salt,password_hash FROM users");
            while (resultSet.next()) {
                userManager.addUser(new User().setLogin(resultSet.getString("login")).setPassword(resultSet.getString("password_hash")).setSalt(resultSet.getString("salt")).setId(resultSet.getLong("id")));
            }
        } catch (Throwable ex) {
            //ex.printStackTrace();
        }
        return userManager;
    }

    public synchronized boolean addUser(IUser user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users(login, salt, password_hash) VALUES (?,?,?)");
            cryptoController.fixUser(user.setSalt(null));
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getSalt());
            statement.setString(3, user.getPassword());
            statement.execute();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT id from users WHERE password_hash='%s'".formatted(user.getPassword()));
            if (resultSet.next()) user.setId(resultSet.getLong("id"));
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }

    public MyCollection<StudyGroup> getCollection() {
        MyCollection<StudyGroup> collection = new MyCollection<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT collection.*, users.login FROM collection JOIN users ON collection.owner_id = users.id");
            while (resultSet.next()) {
                try {
                    StudyGroup curr = new StudyGroup();
                    curr.setId(resultSet.getLong("id"));
                    curr.setName(resultSet.getString("name"));
                    curr.setCoordinates(new Coordinates());
                    curr.getCoordinates().setX(resultSet.getFloat("coords_x"));
                    curr.getCoordinates().setY(resultSet.getInt("coords_y"));
                    curr.setCreationDate(LocalDateTime.parse(resultSet.getString("create_time")));
                    curr.setStudentsCount(resultSet.getInt("students_count"));
                    curr.setFormOfEducation((resultSet.getString("form_of_education") == null ? null : FormOfEducation.valueOf(resultSet.getString("form_of_education"))));
                    curr.setSemesterEnum(Semester.valueOf(resultSet.getString("semester")));
                    if (resultSet.getBoolean("has_person")) {
                        curr.setGroupAdmin(new Person());
                        curr.getGroupAdmin().setName(resultSet.getString("person_name"));
                        curr.getGroupAdmin().setWeight(resultSet.getDouble("person_weight"));
                        if (resultSet.getBoolean("person_has_location")) {
                            curr.getGroupAdmin().setLocation(new Location());
                            curr.getGroupAdmin().getLocation().setX(resultSet.getDouble("person_location_x"));
                            curr.getGroupAdmin().getLocation().setY(resultSet.getInt("person_location_y"));
                            curr.getGroupAdmin().getLocation().setZ(resultSet.getInt("person_location_z"));
                        } else curr.getGroupAdmin().setLocation(null);

                    } else curr.setGroupAdmin(null);
                    curr.setOwnen(server.getUsersManager().getUser(resultSet.getString("login")));
                    collection.add(curr);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return collection;
    }

    public synchronized boolean addCollectionElement(StudyGroup element) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO collection(name, create_time, students_count, form_of_education, semester, has_person, owner_id, coords_x, coords_y, person_name, person_weight, person_has_location, person_location_x, person_location_y, person_location_z) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            WriteElementToPreparedStatement(element, statement);
            statement.execute();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT id from collection WHERE owner_id=%d".formatted(element.getOwnen().getId()));
            while (resultSet.next()) element.setId(resultSet.getLong("id"));
            return true;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public synchronized void deleteUserElements(IUser user) {
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery("DELETE FROM collection WHERE owner_id=%d".formatted(user.getId()));
        } catch (Throwable ignored) {
        }
    }

    public synchronized void deleteByElementId(IUser user, long elementId) {
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery("DELETE FROM collection WHERE id=%d AND owner_id=%d".formatted(elementId, user.getId()));
        } catch (Throwable ignored) {
        }
    }

    public synchronized void deleteFirst(IUser user) {
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery("DELETE FROM collection WHERE owner_id=%d AND id=(SELECT MIN(id) FROM collection WHERE owner_id=%d)".formatted(user.getId(), user.getId()));
        } catch (Throwable ignored) {
        }
    }

    public synchronized void updateElementById(IUser user, long elementId, StudyGroup element) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE collection SET name=?, create_time=?, students_count=?, form_of_education=?, semester=?, has_person=?, owner_id=?, coords_x=?, coords_y=?, person_name=?, person_weight=?, person_has_location=?, person_location_x=?, person_location_y=?, person_location_z=? WHERE owner_id=%d AND id=%d".formatted(user.getId(), elementId));
            WriteElementToPreparedStatement(element, statement);
            statement.execute();
        } catch (Throwable ignored) {
        }
    }

    private synchronized void WriteElementToPreparedStatement(StudyGroup element, PreparedStatement statement) throws SQLException {
        statement.setString(1, element.getName());
        statement.setString(2, element.getCreationDate().toString());
        statement.setInt(3, element.getStudentsCount());
        if (element.getFormOfEducation() == null) statement.setNull(4, Types.VARCHAR);
        else statement.setString(4, String.valueOf(element.getFormOfEducation()));
        statement.setString(5, String.valueOf(element.getSemesterEnum()));
        if (element.getGroupAdmin() == null) {
            statement.setBoolean(6, false);
            ////////////////////////////////////////////////
            statement.setString(10, "empty_name_because_group_admin_is_null");
            statement.setDouble(11, 0.1d);
            statement.setBoolean(12, false);
            statement.setDouble(13, 0.0d);
            statement.setInt(14, 0);
            statement.setInt(15, 0);
        } else {
            statement.setBoolean(6, true);
            ///////////////////////////////////////////////
            statement.setString(10, element.getGroupAdmin().getName());
            statement.setDouble(11, element.getGroupAdmin().getWeight());
            statement.setBoolean(12, element.getGroupAdmin().getLocation() != null);
            if (element.getGroupAdmin().getLocation() == null) {
                statement.setDouble(13, 0.0d);
                statement.setInt(14, 0);
                statement.setInt(15, 0);
            } else {
                statement.setDouble(13, element.getGroupAdmin().getLocation().getX());
                statement.setInt(14, element.getGroupAdmin().getLocation().getY());
                statement.setInt(15, element.getGroupAdmin().getLocation().getZ());
            }
        }
        statement.setLong(7, element.getOwnen().getId());
        statement.setFloat(8, element.getCoordinates().getX());
        statement.setInt(9, element.getCoordinates().getY());

    }

    public CryptoController getCryptoController() {
        return cryptoController;
    }
}
