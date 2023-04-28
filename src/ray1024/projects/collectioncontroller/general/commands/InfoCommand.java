package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Выводит служебную информация о коллекции
 * Например: дата создания, колличество элементов
 */
public class InfoCommand extends BaseCommand {
    public static final InfoCommand command = new InfoCommand();

    private InfoCommand() {
        this.setName("info").setDescription(Phrases.getPhrase("InfoCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void run() throws RuntimeException {
        try {
            if (getUser().equals(getTerminal().getServer().serverAdmin))
                getTerminal().getWriter().println(getTerminal().getCollectionController().getManagedCollection().getCollectionInfo().toString());
            else
                getTerminal().getServer().getResponseSender().sendResponse(new Response().setResponseType(ResponseType.ANSWER).setAnswer(getTerminal().getCollectionController().getManagedCollection().getCollectionInfo().toString()), getUser().getConnector());
        } catch (Throwable ex) {
            ex.printStackTrace();
            //throw new RuntimeException(Phrases.getPhrase("Can'tExecuteCommand"));
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
