package ray1024.projects.collectioncontroller.general.readers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public final class NonBlockingConsoleSourceReader implements IInputSource {

    private static final int BUFFER_SIZE = 128;
    private final Reader reader = new InputStreamReader(System.in);

    private final StringBuilder stringBuilder = new StringBuilder();
    private String line;
    private final char[] buffer = new char[BUFFER_SIZE];
    private int enterIndex = -1, right = 0;
    private boolean eof = false;


    @Override
    public String nextLine() {
        if (enterIndex == -1) return "";
        while (true) {
            try {
                if (!(!eof && reader.ready())) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int cnt = 0;
            try {
                cnt = reader.read(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (cnt > 0) stringBuilder.append(buffer, 0, cnt);
            else if (cnt == -1) eof = true;
            else break;
        }
        line = stringBuilder.substring(0, enterIndex);
        stringBuilder.delete(0, enterIndex + 1);
        right = 0;
        enterIndex = -1;
        return line;
    }

    @Override
    public boolean hasNextLine() {
        while (true) {
            try {
                if (!(!eof && reader.ready())) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int cnt = 0;
            try {
                cnt = reader.read(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (cnt > 0) stringBuilder.append(buffer, 0, cnt);
            else if (cnt == -1) eof = true;
            else break;
        }
        if (enterIndex == -1) {
            for (enterIndex = right; enterIndex < stringBuilder.length(); ++enterIndex) {
                if (stringBuilder.charAt(enterIndex) == '\n') return true;
            }
            enterIndex = -1;
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isEOF() {
        return eof;
    }
}
