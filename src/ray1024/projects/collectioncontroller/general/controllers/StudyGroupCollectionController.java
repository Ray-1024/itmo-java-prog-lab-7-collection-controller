package ray1024.projects.collectioncontroller.general.controllers;

import org.xml.sax.InputSource;
import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.data.MyCollection;
import ray1024.projects.collectioncontroller.general.data.StudyGroup;
import ray1024.projects.collectioncontroller.general.tools.Phrases;
import ray1024.projects.collectioncontroller.server.Server;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

public class StudyGroupCollectionController implements Serializable {

    private MyCollection<StudyGroup> managedCollection;
    private Server server;

    private ReentrantReadWriteLock reentrantReadWriteLock;

    public MyCollection<StudyGroup> getManagedCollection() {
        return managedCollection;
    }

    public StudyGroupCollectionController(Server server) {
        this.server = server;
        reentrantReadWriteLock = new ReentrantReadWriteLock();
    }


    public void sortManagedCollection() {
        try {
            reentrantReadWriteLock.writeLock().lock();
            managedCollection.getVec().sort(Comparator.naturalOrder());
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    public void loadCollection() {
        try {
            managedCollection = server.getDbController().getCollection();
        } catch (Throwable ignored) {
            managedCollection = new MyCollection<>();
        }
    }

    public void saveCollection() throws RuntimeException {

    }

    public void setManagedCollection(MyCollection<StudyGroup> managedCollection) {
        try {
            reentrantReadWriteLock.writeLock().lock();
            this.managedCollection = managedCollection;
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    public void removeAll(Collection<StudyGroup> collection) {
        try {
            reentrantReadWriteLock.writeLock().lock();
            managedCollection.getVec().removeAll(collection);
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    public Stream<StudyGroup> stream() {
        try {
            reentrantReadWriteLock.readLock().lock();
            return managedCollection.stream();
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void add(StudyGroup elem) {
        managedCollection.add(elem);
    }

}
