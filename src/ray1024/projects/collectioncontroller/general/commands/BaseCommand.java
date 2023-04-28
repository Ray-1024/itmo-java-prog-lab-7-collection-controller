package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.data.SteppedInputObject;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;

import java.io.Serializable;

/**
 * Базовый класс для реализации команд, исполняемых Microshell
 */
public abstract class BaseCommand extends SteppedInputObject implements Runnable, Serializable, Cloneable {

    private String name = "BaseCommand";
    private String description = "Base command";
    private Terminal terminal = null;
    private IUser user;

    public IUser getUser() {
        return user;
    }

    public BaseCommand setUser(IUser user) {
        this.user = user;
        return this;
    }

    public BaseCommand() {
        stepsCount = 0;
    }

    public String getName() {
        return name;
    }

    public abstract BaseCommand setArgs(String[] args) throws RuntimeException;


    public BaseCommand setName(String name) {
        this.name = name;
        return this;
    }


    public BaseCommand setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public BaseCommand setTerminal(Terminal terminal) {
        this.terminal = terminal;
        return this;
    }

    @Override
    protected BaseCommand clone() throws CloneNotSupportedException {
        return (BaseCommand) super.clone();
    }
}
