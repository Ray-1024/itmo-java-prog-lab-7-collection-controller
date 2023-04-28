package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.data.IUser;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

public class UserManager implements IUserManager {
    private final HashMap<String, IUser> users;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public UserManager() {
        users = new HashMap<>();
    }

    @Override
    public boolean isRegistered(IUser user) {
        readWriteLock.readLock().lock();
        try {
            return users.containsKey(user.getLogin());
        } finally {
            readWriteLock.readLock().unlock();
        }
    }


    @Override
    public IUserManager addUser(IUser user) {
        readWriteLock.writeLock().lock();
        try {
            if (user == null) return this;
            if (!isRegistered(user)) users.put(user.getLogin(), user);
            return this;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public IUser getUser(String login) {
        readWriteLock.readLock().lock();
        try {
            return users.get(login);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public Stream<IUser> stream() {
        readWriteLock.readLock().lock();
        try {
            return users.values().stream();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    // For check user for request
    @Override
    public boolean contains(IUser user) {
        readWriteLock.readLock().lock();
        try {
            return users.containsKey(user.getLogin()) && users.get(user.getLogin()).getPassword().equals(user.getPassword());
        } catch (Throwable ex) {
            return false;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
