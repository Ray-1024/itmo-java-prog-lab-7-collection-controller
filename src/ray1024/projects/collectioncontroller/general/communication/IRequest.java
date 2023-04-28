package ray1024.projects.collectioncontroller.general.communication;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.data.IUser;

import java.io.Serializable;

public interface IRequest extends Serializable {
    public RequestType getRequestType();

    public IRequest setRequestType(RequestType requestType);

    public BaseCommand getCommand();

    public IRequest setCommand(BaseCommand command);

    public IUser getUser();

    public IRequest setUser(IUser user);
}
