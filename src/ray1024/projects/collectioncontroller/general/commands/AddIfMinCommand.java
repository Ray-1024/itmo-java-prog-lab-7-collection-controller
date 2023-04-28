package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.data.StudyGroup;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Команда добавляет в коллекция элемент если он меньше всех элементов коллекции
 * Т.к сортировка по умолчанию сравнивает группы по названию, то добавляет элемент если название его группы лексикографически меньше любого другого названия групп из коллекции
 */
public class AddIfMinCommand extends BaseCommand {
    private StudyGroup studyGroup = new StudyGroup();

    public static final AddIfMinCommand command = new AddIfMinCommand();

    private AddIfMinCommand() {
        this.setName("add_if_min").setDescription(Phrases.getPhrase("AddIfMinCommandDescription"));
        CommandRegister.registerCommand(this);
        stepsCount = studyGroup.getStepsCount();
    }


    @Override
    public void run() {
        try {
            studyGroup.setOwnen(getUser());
            if (getTerminal().getCollectionController().getManagedCollection().getVec().stream().allMatch((i) -> i.compareTo(studyGroup) > 0)) {
                if (getTerminal().getServer().getDbController().addCollectionElement(studyGroup))
                    getTerminal().getCollectionController().add(studyGroup);
            }
        } catch (Throwable e) {
            if (getTerminal().getServer().serverAdmin.equals(getUser()))
                getTerminal().getWriter().println(Phrases.getPhrase("Can'tExecuteCommand"));
            else
                getTerminal().getServer().getResponseSender().sendResponse(new Response().setResponseType(ResponseType.ANSWER).setAnswer(Phrases.getPhrase("Can'tExecuteCommand")), getUser().getConnector());
        }
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {
        studyGroup.inputLine(line);
    }

    @Override
    public boolean isObjectReady() {
        return studyGroup.isObjectReady();
    }

    @Override
    public String getStepDescription() {
        return studyGroup.getStepDescription();
    }

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        studyGroup = new StudyGroup();
        if (args == null || args.length != 1) getTerminal().getWriter().println(Phrases.getPhrase("WrongCommandArgs"));
        return this;
    }

    @Override
    public void reset() {
        currentStep = 0;
        studyGroup = new StudyGroup();
    }
}
