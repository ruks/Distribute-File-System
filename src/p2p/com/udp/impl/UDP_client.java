/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.com.udp.impl;

import p2p.com.interfaces.node_client;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author rukshan
 */
public class UDP_client implements node_client {

    private final static int PACKETSIZE = 100;    

    @Override
    public void sendData(String serverAddress, int serverPort, String message) {
        // Check the arguments
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
    }
}
