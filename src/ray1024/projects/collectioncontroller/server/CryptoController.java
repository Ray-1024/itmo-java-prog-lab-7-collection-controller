package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.data.IUser;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.stream.IntStream;

public class CryptoController {

    private final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    private final String rndChars = "`1234567890-=~!@#$%^&*()_+qwertyuiop[]asdfghjkl;'zxcvbnm,./QWERTYUIOP{}ASDFGHJKL:ZXCVBNM<>?";
    private final Random rnd = new Random();
    private final String peper = "nbt$^&*b44gHIJ90";

    private String getPasswordHash(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD2");
            byte[] bytes = messageDigest.digest(password.getBytes());
            byte[] hexChars = new byte[bytes.length * 2];
            for (int j = 0; j < bytes.length; j++) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
            }
            return new String(hexChars, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private String getSalt() {
        char[] salt = new char[10];
        IntStream.range(0, salt.length).forEach(i -> salt[i] = rndChars.charAt(rnd.nextInt(0, rndChars.length())));
        return String.valueOf(salt);
    }

    public synchronized void fixUser(IUser user) {
        if (user == null || user.getPassword() == null) return;
        if (user.getSalt() == null) user.setSalt(getSalt());
        user.setPassword(getPasswordHash(user.getSalt() + user.getPassword() + peper));
    }
}
