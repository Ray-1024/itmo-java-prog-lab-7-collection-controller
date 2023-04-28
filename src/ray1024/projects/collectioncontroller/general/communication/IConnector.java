package ray1024.projects.collectioncontroller.general.communication;

import java.io.Serializable;

public interface IConnector extends Serializable {
    IConnector sendRequest(IRequest request);

    IRequest receiveRequest();

    IResponse receiveResponse();

    IConnector sendResponse(IResponse response);

    boolean isConnected();
    void disconnect();

    boolean isNoise();
}
