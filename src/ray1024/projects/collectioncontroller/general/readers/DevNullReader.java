package ray1024.projects.collectioncontroller.general.readers;

public class DevNullReader implements IInputSource {
    @Override
    public String nextLine() {
        return null;
    }

    @Override
    public boolean hasNextLine() {
        return false;
    }

    @Override
    public boolean isEOF() {
        return false;
    }
}
