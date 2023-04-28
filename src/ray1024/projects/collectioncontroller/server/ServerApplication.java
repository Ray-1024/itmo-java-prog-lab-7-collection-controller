package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.commands.*;

import java.io.IOException;
import java.sql.*;

/**
 * Главный красс создающий и запускающий Терминал
 *
 * @MyTag aloha
 */
public class ServerApplication {

    static {
        AddCommand.command.getName();
        AddIfMinCommand.command.getName();
        ClearCommand.command.getName();
        ExitCommand.command.getName();
        FilterLessThanStudentsCountCommand.command.getName();
        FilterStartsWithNameCommand.command.getName();
        HelpCommand.command.getName();
        InfoCommand.command.getName();
        PrintDescendingCommand.command.getName();
        RemoveByIDCommand.command.getName();
        RemoveFirstCommand.command.getName();
        SaveCommand.command.getName();
        ShowCommand.command.getName();
        ShuffleCommand.command.getName();
        UpdateByIDCommand.command.getName();
        ExecuteScriptCommand.command.getName();
    }


    public static void main(String[] args) {
        /*try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", "postgres", "zh159sm212140");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        Server server = new Server();
        while (true) {
            try {
                server.tick();
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
