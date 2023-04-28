package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.readers.IInputSource;
import ray1024.projects.collectioncontroller.general.writers.IOutputSource;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.io.IOException;

public class CommandBuilder implements ICommandBuilder {
    private BaseCommand command;
    private IInputSource reader;
    private IOutputSource writer;
    private Terminal terminal;

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public CommandBuilder(IInputSource reader, IOutputSource writer) {
        this.reader = reader;
        this.writer = writer;
        writer.println(Phrases.getPhrase("TerminalWaitNewCommand"));
    }

    @Override
    public void tick() {
        if (reader.isEOF() || reader == null || writer == null) return;
        if (command == null) {
            try {
                if (!reader.hasNextLine()) return;
                command = CommandRegister.getRegisteredCommandByName(reader.nextLine());
                if (command == null) writer.println(Phrases.getPhrase("TerminalWaitNewCommand"));
                else if (!command.isObjectReady()) writer.println(command.getStepDescription());
            } catch (IllegalStateException illegalStateException) {
                writer.println(illegalStateException.getMessage());
            }
        } else if (!command.isObjectReady()) {
            try {
                if (!reader.hasNextLine()) return;
                command.inputLine(reader.nextLine());
                writer.println(command.getStepDescription());
            } catch (IllegalStateException illegalStateException) {
                writer.println(illegalStateException.getMessage());
            }
        } else writer.println(Phrases.getPhrase("TerminalWaitNewCommand"));
    }

    @Override
    public BaseCommand getCommand() {
        return command == null || !command.isObjectReady() ? null : command;
    }

    @Override
    public ICommandBuilder addCommand(BaseCommand command) {
        this.command = command;
        return this;
    }

    @Override
    public IInputSource getReader() {
        return reader;
    }

    @Override
    public IOutputSource getWriter() {
        return writer;
    }

    @Override
    public boolean isDone() {
        return reader == null || reader.isEOF();
    }

    @Override
    public void reset() {
        try {
            command = null;
            writer.flush();
            writer.println(Phrases.getPhrase("TerminalWaitNewCommand"));
        } catch (Throwable ignored) {
        }
    }
}
