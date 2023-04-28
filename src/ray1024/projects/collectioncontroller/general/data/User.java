package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.communication.IConnector;

public class User implements IUser {
    private String login;
    private String password;
    private String salt;
    private IConnector connector;
    private long id;

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public IUser setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public IUser setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public IUser setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    @Override
    public String getSalt() {
        return salt;
    }

    @Override
    public IUser setPassword(String passwordHash) {
        this.password = passwordHash;
        return this;
    }

    @Override
    public IUser setConnector(IConnector connector) {
        this.connector = connector;
        return this;
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IUser b)
            return getLogin().equals(b.getLogin()) && getPassword().equals(b.getPassword());
        return false;
    }
}
