package ray1024.projects.collectioncontroller.general.communication;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.data.IUser;

import java.io.Serializable;

public class Request implements Serializable, IRequest {
    private RequestType requestType;
    private BaseCommand command;
    private IUser user;

    public Request() {
    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }

    @Override
    public IRequest setRequestType(RequestType requestType) {
        this.requestType = requestType;
        return this;
    }

    @Override
    public BaseCommand getCommand() {
        return command;
    }

    @Override
    public IRequest setCommand(BaseCommand command) {
        this.command = command;
        return this;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public IRequest setUser(IUser user) {
        this.user = user;
        return this;
    }
}
