import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/** Sends lines typed by the user as UDP datagrams. */
public class UDPClient {

    /** Starts the client with a server address and port. */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java UDPClient <host> <port>");
            return;
        }

        try {
            int port = Integer.parseInt(args[1]);
            sendMessages(args[0], port);
        } catch (NumberFormatException e) {
            System.err.println("The port must be a number.");
        } catch (IOException e) {
            System.err.println("UDP client error: " + e.getMessage());
        }
    }

    /** Reads input and sends one datagram per line. */
    private static void sendMessages(String host, int port) throws IOException {
        InetAddress address = InetAddress.getByName(host);
        BufferedReader input = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));

        try (DatagramSocket socket = new DatagramSocket()) {
            while (true) {
                String message = input.readLine();
                if (message == null) {
                    break;
                }

                byte[] data = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(
                        data, data.length, address, port);
                socket.send(packet);
            }
        }
    }
}
