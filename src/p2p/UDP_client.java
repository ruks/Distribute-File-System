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

    public String get_JOIN_cmd(String ip, int port) {
        String cmd = "JOIN " + ip + " " + port;
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
    }

    public String get_LEAVE_cmd(String ip, int port) {
        String cmd = "LEAVE " + ip + " " + port;
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
    }

    public String get_SER_cmd(String IP, int port, String filename, int hops) {
        String cmd = "SER " + IP + " " + port + " " + filename + " " + hops;
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
    }

    public String sendData(String serverAddress, int serverPort, String message) {
        // Check the arguments
        String msg = null;
        DatagramSocket socket = null;
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

        } catch (Exception e) {
            System.out.println(e);
//            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
        return msg;
    }
}
