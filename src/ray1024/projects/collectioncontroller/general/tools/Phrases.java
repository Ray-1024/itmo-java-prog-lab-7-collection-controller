package ray1024.projects.collectioncontroller.general.tools;

import ray1024.projects.collectioncontroller.general.data.FormOfEducation;
import ray1024.projects.collectioncontroller.general.data.Semester;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Класс-Инструмент
 * Содержит все фразы выводимые на обозрение пользователя
 * В будущем с помощью функции setLocale можно будет менять язык используемый в приложении
 */
public class Phrases {

    private static final HashMap<String, String> phrases = new HashMap<>();

    static {
        setLocale("Russian");
    }

    private Phrases() {
    }

    public static String getPhrase(String phraseName) {
        return phrases.get(phraseName);
    }

    public static void setLocale(String locale) {
        if (locale.equals("Russian")) {
            phrases.clear();
            phrases.put("ReadySteppedInputObject", "Объект готов.");
            phrases.put("WrongArgument", "Введеное вами значение не соответствует обозначенным условиям.");
            phrases.put("CantParseNumber", "Распознование числа не удалось, возможно вы ввели не число.");
            phrases.put("CoordinatesXDescription", "Пожалуйста введите значение координаты X(вещественное число) не превосходящее 948:");
            phrases.put("CoordinatesYDescription", "Пожалуйста введите значение координаты Y(целое число) не меньшее -543:");
            phrases.put("LocationXDescription", "Пожалуйста введите значение координаты X(вещественное число):");
            phrases.put("LocationYDescription", "Пожалуйста введите значение координаты Y(целое число):");
            phrases.put("LocationZDescription", "Пожалуйста введите значение координаты Z(целое число):");
            phrases.put("PersonNameDescription", "Пожалуйста введите имя человека:");
            phrases.put("PersonWeightDescription", "Пожалуйста введите ширину персоны(вещественное, положительное число):");
            phrases.put("PersonLocationIsNullDescription", "Желаете ли вы указать местоположение человека?(yes/no)");
            phrases.put("StudyGroupNameDescription", "Пожалуйста введите название учебной группы(оно не может быть пустым):");
            phrases.put("StudyGroupCoordinatesDescription", "Пожалуйста введите координаты местоположения группы:");
            phrases.put("StudyGroupStudentsCountDescription", "Пожалуйста введите колличество студентов в группе(положительное число):");
            phrases.put("StudyGroupFormOfEducationIsNullDescription", "Желаете ли вы указать форму обучения группы?(yes/no)");
            phrases.put("StudyGroupFormOfEducationDescription", "Пожалуйста выберите и введите название формы обучения из списка:\n" + Arrays.toString(FormOfEducation.values()));
            phrases.put("StudyGroupSemesterDescription", "Пожалуйста выберите и введите название семестра из списка:\n" + Arrays.toString(Semester.values()));
            phrases.put("StudyGroupGroupAdminIsNullDescription", "Желаете ли вы указать администратора группы?(yes/no)");
            phrases.put("StudyGroupGroupAdminDescription", "Пожалуйста введите данные администратора группы:");
            phrases.put("CantLoadCollectionFromFile", "При загрузке коллекции из файла произошла ошибка.");
            phrases.put("CollectionFileDoesn'tExist", "Файл содержащий коллекцию не существует либо недоступен.");
            phrases.put("CantSaveCollectionToFile", "При сохранении коллекции произошла ошибка. Возможно файл недоступен для сохранения или занят.");
            phrases.put("CantCreateMicroshell", "Для текущего терминала создано слишком много исполнителей создание еще одного невозможно.");
            phrases.put("InputterCan'tBeNullException", "Источник данных для терминала не может быть null.");
            phrases.put("OutputterCan'tBeNullException", "Приемник данных из терминала не может быть null.");
            phrases.put("TerminalWaitNewCommand", "Пожалуйста введите команду(для получения справки по доступным командам используется команда help):");
            phrases.put("WrongCommandInLine", "Введена неверная команда.");
            phrases.put("WrongCommandSyntax", "Неверный синтаксис команды.");
            phrases.put("HelpCommandDescription", "Выводит список доступных команд.");
            phrases.put("Can'tExecuteCommand", "Невозможно выполнить команду.");
            phrases.put("InfoCommandDescription", "Выводит основную информацию о коллекции.");
            phrases.put("AddCommandDescription", "Добавляет новый элемент в коллекцию.");
            phrases.put("ClearCommandDescription", "Очищает коллекцию.");
            phrases.put("EnvironmentVariableDoesn'tExist", "Переменная среды 'CCFilename' не задана.");
            phrases.put("AddIfMinCommandDescription", "Добавляет новый элемент в коллекцию, если он меньше любого элемента коллекции/");
            phrases.put("ExitCommandDescription", "Завершает программу без сохранения коллекции.");
            phrases.put("ShowCommandDescription", "Показывает коллекцию.");
            phrases.put("SaveCommandDescription", "Сохраняет коллекцию в файл(Недоступна пользователю).");
            phrases.put("RemoveFirstCommandDescription", "Удаляет первый элемент коллекции.");
            phrases.put("ShuffleCommandDescription", "Случайным образом меняет местами элементы коллекции.");
            phrases.put("PrintDescendingDescription", "Выводит коллекцию в лексикографическом порядке не меняя ее.");
            phrases.put("WrongCommandArgs", "Введенные вами аргументы команды неверны.");
            phrases.put("RemoveByIdCommandDescription", "Удаляет элемент коллекции с тем же ID что и аргумент команды.");
            phrases.put("UpdateByIdCommandDescription", "Заменяет элемент с указанным ID на новый.");
            phrases.put("FilterLessThanStudentsCountCommandDescription", "Показывает учебные группы в которых студентов меньше указано в аргументе команды.");
            phrases.put("FilterStartsWithNameCommandDescription", "Показывает группы названия которых начинаются с аргумента команды.");
            phrases.put("TooManyMicroshells", "В текущем терминале слишком много исполнителей создание еще одного невозможно.");
            phrases.put("ExecuteScriptCommandDescription", "Выполняет скрипт с названием указанным в аргументе.");
            phrases.put("Can'tFindScript", "Не удалось разобрать скрипт на команды, возможно скрипт вызывает другие скрипты или не существует");
            phrases.put("InputSourceIsNull", "Источник входных данных не может быть null");
            phrases.put("OutputSourceIsNull", "Источник выходных данных не может быть null");
            phrases.put("ServerCan'tStart", "При старте сервера произошла ошибка");
            phrases.put("ServerHasBeenStarted", "Сервер начал работу.");
            phrases.put("ServerHasBeenStopped", "Сервер закончил работу.");
            phrases.put("ClientHasBeenConnected", "Подключен новый клиент.");
            phrases.put("ClientCan'tStart", "Клиент не смог начать работу.");
            phrases.put("PleaseEnterLogin", "Пожалуйста введите логин:");
            phrases.put("PleaseEnterPassword", "Пожалуйста введите пароль:");
            phrases.put("EndOfInputSource", "Источник ввода закрыт, дальнейшее считывание команд невозможно.");
            phrases.put("UnsupportedScriptLevel", "Исполнение вложенных скриптов пока недоступно.");
            phrases.put("TooLongScript", "Для запуска больших скриптов необходима Pro версия лабы.");
            phrases.put("CommandBuilderIsNull", "Нельзя строить команды не существующим CommandBuilder'ом");

        }
    }
}
