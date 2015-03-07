/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author rukshan
 */
public class UDP_client {

    private final static int PACKETSIZE = 100;

    public void sendData(String serverAddress, int serverPort, String message) {
        // Check the arguments

        DatagramSocket socket = null;
        System.out.println("sending");
        try {
            // Convert the arguments first, to ensure that they are valid
            InetAddress host = InetAddress.getByName(serverAddress);
            int port = serverPort;

            // Construct the socket
            socket = new DatagramSocket();

            // Construct the datagram packet
            byte[] data = message.getBytes();
            
            DatagramPacket packet = new DatagramPacket(data, data.length, host, port);

            // Send it
            socket.send(packet);
            System.out.println("sent");

            // Set a receive timeout, 2000 milliseconds
            socket.setSoTimeout(2000);

            // Prepare the packet for receive
            packet.setData(new byte[PACKETSIZE]);

            // Wait for a response from the server
            socket.receive(packet);

            // Print the response
            System.out.println(new String(packet.getData()));

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
