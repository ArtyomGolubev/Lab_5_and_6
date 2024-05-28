package Other.Network;

import java.io.IOException;

public interface NetworkProvider {
    void openConnection() throws IOException;
    void run() throws IOException;
}
