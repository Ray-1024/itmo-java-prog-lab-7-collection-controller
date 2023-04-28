package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.readers.IInputSource;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.IOutputSource;

import java.io.Serializable;

public interface ICommandBuilder extends Tickable, Serializable {
    BaseCommand getCommand();

    ICommandBuilder addCommand(BaseCommand command);

    IInputSource getReader();

    IOutputSource getWriter();

    boolean isDone();

    void reset();
}
