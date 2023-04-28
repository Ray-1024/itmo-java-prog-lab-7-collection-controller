package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Выводит справку о доступных командах
 * {element} означает что в интерактивном режиме по каждому полю объекта вы будете опрошены
 * в скрипте все поля должны быть указаны начиная со следующей строки за командой, по 1 аргументу в строке
 */
public class HelpCommand extends BaseCommand {
    public static final HelpCommand command = new HelpCommand();

    private HelpCommand() {
        setName("help").setDescription(Phrases.getPhrase("HelpCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void run() throws RuntimeException {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            final int[] strNumber = {0};
            CommandRegister.getRegisteredCommandsStream().forEach((command) -> {
                stringBuilder.append(String.format("%d. %s: %s\n", ++strNumber[0], command.getName(), command.getDescription()));
            });
            if (getUser().equals(getTerminal().getServer().serverAdmin))
                getTerminal().getWriter().println(stringBuilder.toString());
            else
                getTerminal().getServer().getResponseSender().sendResponse(new Response().setResponseType(ResponseType.ANSWER).setAnswer(stringBuilder.toString()), getUser().getConnector());
            System.out.println("--- HELP COMMAND ---");
        } catch (Throwable throwable) {
            throw new RuntimeException(Phrases.getPhrase("Can'tExecuteCommand"));
        }
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {
    }

    @Override
    public String getStepDescription() {
        return "";
    }

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        if (args == null || args.length != 1) throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
        return this;
    }
}
