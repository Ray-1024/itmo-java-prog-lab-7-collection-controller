package ray1024.projects.collectioncontroller.general.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Vector;
import java.util.stream.Stream;

/**
 * Класс управляющий коллекцией учебных групп
 */
public class MyCollection<T> implements Serializable {
    private final Vector<T> vec = new Vector<T>();
    private final CollectionInfo collectionInfo = new CollectionInfo();

    public MyCollection() {
        collectionInfo.initializationDateTime = LocalDateTime.now();
        collectionInfo.collectionElementType = StudyGroup.class;
    }

    public Vector<T> getVec() {
        return vec;
    }

    public void clear() {
        vec.clear();
    }

    public CollectionInfo getCollectionInfo() {
        collectionInfo.elementsCount = vec.size();
        return collectionInfo;
    }

    public Stream<T> stream() {
        return vec.stream();
    }

    public <T> T get(int index) {
        return (T) vec.get(index);
    }

    public void add(T elem) {
        vec.add(elem);
    }

    public int size() {
        return vec.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < vec.size(); ++i) {
            stringBuilder.append("\n    ").append(i + 1).append(". ").append(vec.get(i).toString());
        }
        return stringBuilder.toString();
    }

}
