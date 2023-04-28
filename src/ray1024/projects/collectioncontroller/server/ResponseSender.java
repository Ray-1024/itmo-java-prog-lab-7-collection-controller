package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.communication.IResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResponseSender {
    private final Server server;
    private final ExecutorService cachedThreadPool;

    public ResponseSender(Server server) {
        this.server = server;
        cachedThreadPool = Executors.newCachedThreadPool();
    }

    public synchronized void sendResponse(IResponse response, IConnector connector) {
        try {
            System.out.println("--- SEND ANSWER ---");
            cachedThreadPool.execute(() -> {
                if (connector != null && response != null) connector.sendResponse(response);
            });
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }


}
