package ray1024.projects.collectioncontroller.client;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.commands.ExitCommand;
import ray1024.projects.collectioncontroller.general.communication.*;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.data.User;
import ray1024.projects.collectioncontroller.general.readers.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.general.tools.Phrases;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;

import java.net.InetAddress;
import java.util.Scanner;

public class Client implements Tickable {

    private CommandBuilder commandBuilder;
    private IConnector connector;
    private StudyGroupCollectionController collectionController;
    private IUser user;

    Client() {
        try {
            collectionController = new StudyGroupCollectionController(null);
            user = new User();
            Scanner scanner = new Scanner(System.in);
            System.out.println("sign in or sign up?");
            String mode = scanner.nextLine();
            while (!"sign in".equals(mode) && !"sign up".equals(mode)) {
                System.out.println("Sign in or sign up?");
                mode = scanner.nextLine();
            }
            System.out.println(Phrases.getPhrase("PleaseEnterLogin"));
            if (scanner.hasNextLine()) user.setLogin(scanner.nextLine());
            System.out.println(Phrases.getPhrase("PleaseEnterPassword"));
            if (scanner.hasNextLine()) user.setPassword(scanner.nextLine());

            commandBuilder = new CommandBuilder(new NonBlockingConsoleSourceReader(), new ConsoleSourceWriter());
            connector = new ClientConnector(InetAddress.getByName("localhost"), 20661);
            if ("sign up".equals(mode))
                connector.sendRequest(new Request().setUser(user).setRequestType(RequestType.SIGN_UP));
            else connector.sendRequest(new Request().setUser(user).setRequestType(RequestType.SIGN_IN));
        } catch (Throwable e) {
            System.out.println("SERVER DOESN'T EXIST");
        }
    }

    @Override
    public void tick() {
        if (!connector.isConnected()) {
            commandBuilder.getWriter().println("--- CONNECTION TIMED OUT ---");
            System.exit(0);
        }
        commandBuilder.tick();
        BaseCommand currCommand = commandBuilder.getCommand();
        if (currCommand != null) {
            if (currCommand.getName().equals(ExitCommand.command.getName())) {
                connector.sendRequest(new Request().setRequestType(RequestType.DISCONNECTION));
                System.exit(0);
            }
            System.out.println("--- REQUEST WAS SENT ---");
            connector.sendRequest(new Request().setCommand(currCommand).setRequestType(RequestType.EXECUTION_COMMAND).setUser(user));
            commandBuilder.reset();
        }
        IResponse response = connector.receiveResponse();
        if (response != null) {
            System.out.println("--- RESPONSE WAS RECEIVED ---");
            if (response.getResponseType() == ResponseType.ANSWER) {
                commandBuilder.getWriter().println(response.getAnswer());
            } else if (response.getResponseType() == ResponseType.COLLECTION_UPDATE) {
                collectionController.setManagedCollection(response.getCollection());
            } else if (response.getResponseType() == ResponseType.DISCONNECT) {
                commandBuilder.getWriter().println("--- CONNECTION WAS CLOSED ---");
                System.exit(0);
            }
        }
    }
}
