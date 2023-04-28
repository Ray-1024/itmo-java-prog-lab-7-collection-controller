package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.communication.IRequest;
import ray1024.projects.collectioncontroller.general.tools.Tickable;

import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;

public class ConnectionManager implements Tickable {

    private final ConnectionAcceptor connectionAcceptor;
    private final LinkedList<IConnector> connections;
    private final LinkedList<IConnector> disconnected;
    private final Server server;

    private final ForkJoinPool forkJoinPool;


    public ConnectionManager(Server currentServer) {
        this.connections = new LinkedList<>();
        this.disconnected = new LinkedList<>();
        server = currentServer;
        connectionAcceptor = new ConnectionAcceptor();
        forkJoinPool = new ForkJoinPool();
    }

    private void addConnector(IConnector connector) {
        connections.add(connector);
    }

    @Override
    public void tick() {
        IConnector curr = connectionAcceptor.getNewConnection();
        if (curr != null) {
            addConnector(curr);
            System.out.println("--- NEW CONNECTION ---");
        }
        connections.forEach((connector) -> forkJoinPool.execute(() -> {
            if (!connector.isConnected()) {
                synchronized (disconnected) {
                    disconnected.add(connector);
                }
            }
            IRequest request = connector.receiveRequest();
            if (request != null && request.getUser() != null) {
                if (server.getUsersManager().isRegistered(request.getUser())) {
                    request.getUser().setSalt(server.getUsersManager().getUser(request.getUser().getLogin()).getSalt());
                    server.getDbController().getCryptoController().fixUser(request.getUser());
                    if (server.getUsersManager().contains(request.getUser()))
                        request.getUser().setConnector(connector).setId(server.getUsersManager().getUser(request.getUser().getLogin()).getId());
                }
                server.getRequestExecutor().executeRequest(request, connector);
            }
        }));
        synchronized (disconnected) {
            connections.removeAll(disconnected);
            disconnected.clear();
        }
    }
}
