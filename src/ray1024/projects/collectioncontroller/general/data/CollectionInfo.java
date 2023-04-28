package ray1024.projects.collectioncontroller.general.data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Вспомогательный класс используемый для хранения информации о коллекции
 */
public class CollectionInfo implements Serializable {
    public LocalDateTime initializationDateTime;
    public Class collectionElementType;
    public int elementsCount;

    public LocalDateTime getInitializationDateTime() {
        return initializationDateTime;
    }

    public void setInitializationDateTime(LocalDateTime initializationDateTime) {
        this.initializationDateTime = initializationDateTime;
    }

    public Class getCollectionElementType() {
        return collectionElementType;
    }

    public void setCollectionElementType(Class collectionElementType) {
        this.collectionElementType = collectionElementType;
    }

    public int getElementsCount() {
        return elementsCount;
    }

    public void setElementsCount(int elementsCount) {
        this.elementsCount = elementsCount;
    }


    @Override
    public String toString() {
        return String.format("CollectionInfo{\n   initializationDateTime : %s\n   collectionElementType : %s\n   elementsCount : %d\n}", initializationDateTime, collectionElementType, elementsCount);
    }
}
