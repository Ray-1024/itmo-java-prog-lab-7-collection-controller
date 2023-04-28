package ray1024.projects.collectioncontroller.general.tools;

import java.io.*;
import java.util.Arrays;

public class Serializer {
    public static byte[] serialize(Object obj) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream ois = new ObjectOutputStream(baos)) {
            ois.writeObject(obj);
            return baos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Object deserialize(byte[] bytes) {
        try (InputStream is = new ByteArrayInputStream(bytes); ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        } catch (Throwable ex) {
            return null;
        }
    }
}
