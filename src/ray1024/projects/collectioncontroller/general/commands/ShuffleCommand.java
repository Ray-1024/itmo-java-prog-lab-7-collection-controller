package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.util.Collections;

/**
 * Случайным образом перемешивает элементы коллекции
 */
public class ShuffleCommand extends BaseCommand {
    public static final ShuffleCommand command = new ShuffleCommand();

    private ShuffleCommand() {
        setName("shuffle").setDescription(Phrases.getPhrase("ShuffleCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void run() {
        Collections.shuffle(getTerminal().getCollectionController().getManagedCollection().getVec());
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
