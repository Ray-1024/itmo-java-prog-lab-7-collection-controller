package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Выводит те элементы коллекции, названия групп которых начинаются с аргумента команды
 */
public class FilterStartsWithNameCommand extends BaseCommand {
    String name = null;
    public static final FilterStartsWithNameCommand command = new FilterStartsWithNameCommand();

    private FilterStartsWithNameCommand() {
        setName("filter_starts_with_name").setDescription(Phrases.getPhrase("FilterStartsWithNameCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void run() {
        try {
            final int[] ind = new int[1];
            StringBuilder stringBuilder = new StringBuilder();
            getTerminal().getCollectionController().getManagedCollection().stream().filter((elem) -> elem.getName().startsWith(name)).forEach((elem) -> {
                stringBuilder.append(String.format("    %d. %s", ++ind[0], elem));
                stringBuilder.append("\n");
            });
            if (getUser().equals(getTerminal().getServer().serverAdmin))
                getTerminal().getWriter().println(stringBuilder.toString());
            else
                getTerminal().getServer().getResponseSender().sendResponse(new Response().setResponseType(ResponseType.ANSWER).setAnswer(stringBuilder.toString()), getUser().getConnector());
        } catch (Throwable ignored) {
        }
    }

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        if (args != null && args.length == 2) {
            name = args[1];
            return this;
        }
        throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {

    }

    @Override
    public String getStepDescription() {
        return "";
    }
}
