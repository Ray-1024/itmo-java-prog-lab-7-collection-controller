package ray1024.projects.collectioncontroller.general.communication;

import ray1024.projects.collectioncontroller.general.data.MyCollection;
import ray1024.projects.collectioncontroller.general.data.StudyGroup;

import java.io.Serializable;

public interface IResponse extends Serializable {
    ResponseType getResponseType();

    IResponse setResponseType(ResponseType responseType);

    String getAnswer();

    IResponse setAnswer(String answer);

    IResponse setCollection(MyCollection<StudyGroup> collection);

    MyCollection<StudyGroup> getCollection();
}
