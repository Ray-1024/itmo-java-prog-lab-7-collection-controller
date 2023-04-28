package ray1024.projects.collectioncontroller.general.readers;

import java.io.IOException;

public interface IInputSource {
    String nextLine();

    boolean hasNextLine();

    boolean isEOF();
}
