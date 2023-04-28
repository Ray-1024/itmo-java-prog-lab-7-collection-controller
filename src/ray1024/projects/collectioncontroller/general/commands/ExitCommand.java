package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Завершает программу
 * Не сохраняет коллекцию в файл
 */
public class ExitCommand extends BaseCommand {
    public static final ExitCommand command = new ExitCommand();

    private ExitCommand() {
        setName("exit").setDescription(Phrases.getPhrase("ExitCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void run() {
        System.exit(0);
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
