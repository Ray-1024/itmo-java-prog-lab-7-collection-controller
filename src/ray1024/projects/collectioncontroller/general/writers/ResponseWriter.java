package ray1024.projects.collectioncontroller.general.writers;

import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.communication.IResponse;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

public class ResponseWriter implements IOutputSource {
    private IResponse response;
    private IConnector connector;
    private StringBuilder stringBuilder;

    public ResponseWriter(IConnector connector) {
        this.connector = connector;
        this.response = new Response().setResponseType(ResponseType.ANSWER);
        stringBuilder = new StringBuilder();
    }

    @Override
    public void println(String line) {
        if (Phrases.getPhrase("TerminalWaitNewCommand").equals(line)) return;
        stringBuilder.append(line);
        stringBuilder.append("\n");
    }

    @Override
    public void print(String line) {
        if (Phrases.getPhrase("TerminalWaitNewCommand").equals(line)) return;
        stringBuilder.append(line);
    }

    @Override
    public void flush() {
        try {
            System.out.println("---RESPONSE WRITE---");
            response.setAnswer(stringBuilder.toString());
            connector.sendResponse(response);
            stringBuilder = new StringBuilder();
            System.out.println("--------------------");
        } catch (Throwable ex) {
            System.out.println("---RESPONSE WRITER EXCEPTION");
        }
    }
}
