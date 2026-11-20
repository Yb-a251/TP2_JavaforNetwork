import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/** Tests four UDP datagram sizes on localhost. */
public class UDPSizeTest {
    public static void main(String[] args) throws IOException {
        InetAddress server = InetAddress.getByName("127.0.0.1");
        int[] sizes = {1000, 10000, 65507, 65508};

        try (DatagramSocket socket = new DatagramSocket()) {
            for (int size : sizes) {
                byte[] data = new byte[size];
                DatagramPacket packet = new DatagramPacket(
                        data, data.length, server, 8080);

                try {
                    socket.send(packet);
                    System.out.println("Sent: " + size + " bytes");
                } catch (IOException e) {
                    System.out.println("Send failed for " + size
                            + " bytes: " + e.getMessage());
                }
            }
        }
    }
}
