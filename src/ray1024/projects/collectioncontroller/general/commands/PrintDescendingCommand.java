package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.data.MyCollection;
import ray1024.projects.collectioncontroller.general.data.StudyGroup;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Выводит элементы коллекции в порядке лексикографического не возростания названий их групп
 * Не меняет коллекцию
 */
public class PrintDescendingCommand extends BaseCommand {
    public static final PrintDescendingCommand command = new PrintDescendingCommand();

    private PrintDescendingCommand() {
        setName("print_descending").setDescription(Phrases.getPhrase("PrintDescendingDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void run() {

        try {
            StringBuilder stringBuilder = new StringBuilder();
            MyCollection<StudyGroup> coll = getTerminal().getCollectionController().getManagedCollection();
            ArrayList<Integer> arr = new ArrayList<>(coll.size());
            for (int i = 0; i < coll.size(); ++i) arr.add(i);

            arr.sort(Comparator.comparing(coll::get));
            for (int i = coll.size() - 1; i >= 0; --i) {
                stringBuilder.append("    " + (coll.size() - i) + ". ");
                stringBuilder.append(coll.get(arr.get(i)).toString());
            }
            if (getUser().equals(getTerminal().getServer().serverAdmin))
                getTerminal().getWriter().println(stringBuilder.toString());
            else
                getTerminal().getServer().getResponseSender().sendResponse(new Response().setResponseType(ResponseType.ANSWER).setAnswer(stringBuilder.toString()), getUser().getConnector());
        } catch (Throwable ignored) {
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
