package ray1024.projects.collectioncontroller.general.tools;

public interface ISteppedInput {
    void inputLine(String line) throws IllegalStateException;

    boolean isObjectReady();

    String getStepDescription();

    void reset();


}
