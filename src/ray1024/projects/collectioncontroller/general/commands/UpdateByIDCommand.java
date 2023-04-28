package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.data.StudyGroup;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Перезаписывает элемент коллекции с ID равным аргументу
 */
public class UpdateByIDCommand extends BaseCommand {
    int updateID = -1;
    StudyGroup elem = new StudyGroup();
    public static final UpdateByIDCommand command = new UpdateByIDCommand();

    private UpdateByIDCommand() {
        setName("update_by_id").setDescription(Phrases.getPhrase("UpdateByIdCommandDescription"));
        CommandRegister.registerCommand(this);
        stepsCount = elem.getStepsCount();
    }

    @Override
    public void run() {
        try {
            getTerminal().getServer().getDbController().updateElementById(getUser(), updateID, elem);
            elem.setId(getTerminal().getCollectionController().getManagedCollection().stream().filter((elem) -> elem.getId() == updateID && elem.getOwnen().equals(getUser())).findFirst().get().getId());
            getTerminal().getCollectionController().getManagedCollection().getVec().replaceAll((i) -> {
                if (i.getId() == elem.getId()) return elem;
                return i;
            });
        } catch (Throwable ex) {
        }
    }

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        elem = new StudyGroup();
        if (args != null && args.length == 2) {
            try {
                updateID = Integer.parseInt(args[1]);
                return this;
            } catch (NumberFormatException ignored) {
            }
        }
        throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {
        elem.inputLine(line);
    }

    @Override
    public String getStepDescription() {
        return elem.getStepDescription();
    }
}
