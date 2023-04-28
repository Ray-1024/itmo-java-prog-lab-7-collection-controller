package ray1024.projects.collectioncontroller.general.readers;

import java.util.Scanner;

public class ConsoleSourceReader implements IInputSource {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    @Override
    public boolean isEOF() {
        try {
            scanner.hasNextLine();
            return false;
        } catch (IllegalStateException illegalStateException) {
            return true;
        }
    }
}
