package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Выводит те элементы коллекции, которые имеют меньшее колличество студентов в группе чем задано в параметре
 */
public class FilterLessThanStudentsCountCommand extends BaseCommand {
    int studentsCount = 0;
    public static final FilterLessThanStudentsCountCommand command = new FilterLessThanStudentsCountCommand();

    private FilterLessThanStudentsCountCommand() {
        setName("filter_less_than_students_count").setDescription(Phrases.getPhrase("FilterLessThanStudentsCountCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void run() {
        try {
            final int[] ind = new int[1];
            StringBuilder stringBuilder = new StringBuilder();
            getTerminal().getCollectionController().getManagedCollection().stream()
                    .filter((elem) -> elem.getStudentsCount() < studentsCount).forEach((elem) -> {
                        stringBuilder.append(String.format("\t%d. %s", ++ind[0], elem));
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
            try {
                studentsCount = Integer.parseInt(args[1]);
                return this;
            } catch (NumberFormatException ignored) {
            }
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
