package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Класс-Инструмент
 * 1. Распознование команд и запрос аргументов в интерактивном режиме
 * 2. Создание коллекции команд из списка строк(для парсинга скриптов)
 */

public class CommandRegister {

    private static final HashMap<String, BaseCommand> commands = new HashMap<>();

    public static void registerCommand(BaseCommand command) {
        commands.put(command.getName(), command);
    }

    public static Stream<BaseCommand> getRegisteredCommandsStream() {
        return commands.values().stream();
    }


    public static BaseCommand getRegisteredCommandByName(String line) throws IllegalStateException {
        if ("".equals(line)) return null;
        String[] args = line.split(" ");
        if (!commands.containsKey(args[0])) throw new IllegalStateException(Phrases.getPhrase("WrongCommandInLine"));
        BaseCommand prototype = null;
        try {
            prototype = commands.get(args[0]).clone().setArgs(args);
            prototype.reset();
        } catch (CloneNotSupportedException e) {
            System.out.println("COMMAND_BUILDER_COMMAND_CLONE_NOT_SUPPORTED_EXEPTION");
        }
        return prototype;
    }

}
