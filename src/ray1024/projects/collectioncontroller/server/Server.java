package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.data.User;
import ray1024.projects.collectioncontroller.general.readers.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;

import java.util.UUID;

public class Server implements Tickable {

    public final IUser serverAdmin = new User().setLogin("admin").setPassword("admin");
    // IMPOSSIBLE TO HACK
    private final IUserManager usersManager;
    private final Terminal terminal;

    private final ConnectionManager connectionManager;
    private final RequestExecutor requestExecutor;
    private final ResponseSender responseSender;
    private final DBController dbController;


    public ResponseSender getResponseSender() {
        return responseSender;
    }

    public Server() {

        dbController = new DBController(this);
        dbController.addUser(serverAdmin);
        usersManager = dbController.getUsers();

        terminal = new Terminal(this, new CommandBuilder(new NonBlockingConsoleSourceReader(), new ConsoleSourceWriter()), new StudyGroupCollectionController(this));
        terminal.getCollectionController().loadCollection();

        connectionManager = new ConnectionManager(this);
        requestExecutor = new RequestExecutor(this);
        responseSender = new ResponseSender(this);
    }

    public DBController getDbController() {
        return dbController;
    }

    public RequestExecutor getRequestExecutor() {
        return requestExecutor;
    }

    public IUserManager getUsersManager() {
        return usersManager;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    @Override
    public void tick() {
        connectionManager.tick();
        terminal.tick();
    }
}
