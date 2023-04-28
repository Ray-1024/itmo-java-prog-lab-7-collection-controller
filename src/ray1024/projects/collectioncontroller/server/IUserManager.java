package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.data.IUser;

import java.util.stream.Stream;

public interface IUserManager {
    boolean isRegistered(IUser user);

    boolean contains(IUser user);

    IUserManager addUser(IUser user);

    IUser getUser(String login);

    Stream<IUser> stream();
}
