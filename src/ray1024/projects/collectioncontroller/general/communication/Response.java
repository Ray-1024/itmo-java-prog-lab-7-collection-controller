package ray1024.projects.collectioncontroller.general.communication;

import ray1024.projects.collectioncontroller.general.data.MyCollection;
import ray1024.projects.collectioncontroller.general.data.StudyGroup;

import java.io.Serializable;

public class Response implements Serializable, IResponse {
    private ResponseType responseType;
    private String answer;
    private MyCollection<StudyGroup> collection;

    public Response() {
        collection = null;
    }


    @Override
    public ResponseType getResponseType() {
        return responseType;
    }

    @Override
    public Response setResponseType(ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public Response setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    @Override
    public IResponse setCollection(MyCollection<StudyGroup> collection) {
        this.collection = collection;
        return this;
    }

    @Override
    public MyCollection<StudyGroup> getCollection() {
        return collection;
    }
}
