import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

/** Receives and displays UDP messages. */
public class UDPServer {
    private static final int DEFAULT_PORT = 8080;
    private static final int BUFFER_SIZE = 1024;

    private final int port;
    private int bufferSize = BUFFER_SIZE;
    private boolean running;

    /** Creates a server on the given port. */
    public UDPServer(int port) {
        this.port = port;
    }

    /** Creates a server on the default port. */
    public UDPServer() {
        this(DEFAULT_PORT);
    }

    /** Opens the socket and waits for incoming messages. */
    public void launch() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            running = true;
            System.out.println(this);

            byte[] buffer = new byte[bufferSize];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                // Decode only the bytes received in this datagram.
                String message = new String(packet.getData(), 0,
                        packet.getLength(), StandardCharsets.UTF_8);

                System.out.println(packet.getAddress().getHostAddress()
                        + ":" + packet.getPort() + " - " + message);
            }
        } catch (IOException e) {
            System.err.println("UDP server error: " + e.getMessage());
        } finally {
            running = false;
        }
    }

    /** Returns the listening port and the current server state. */
    @Override
    public String toString() {
        return "UDP server on port " + port + " (running: " + running + ")";
    }

    /** Starts the server with an optional port argument. */
    public static void main(String[] args) {
        UDPServer server;
        if (args.length == 0) {
            server = new UDPServer();
        } else {
            server = new UDPServer(Integer.parseInt(args[0]));
        }
        if (args.length > 1) {
            server.bufferSize = Integer.parseInt(args[1]);
        }
        server.launch();
    }
}