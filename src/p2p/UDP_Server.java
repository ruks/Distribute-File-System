/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author rukshan
 */
public class UDP_Server extends Thread {

    private final static int PACKETSIZE = 1000;
    private int port;
    private Node node;

    public UDP_Server(int port,Node node) {
        this.port = port;
        this.node=node;
    }

    @Override
    public void run() {
        this.start(this.port);
    }

    private void start(int port) {

        try {

            // Construct the socket
            DatagramSocket socket = new DatagramSocket(port);

            System.out.println("Node is ready...");

            for (;;) {
                // Create a packet
                DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);

                // Receive a packet (blocking)
                socket.receive(packet);

                // Print the packet
                System.out.println("recived: >> "+packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()));

                String msg=new String(packet.getData());
                this.node.handleMsg(msg);
//                
            }
        } catch (Exception e) {
//            System.out.println(e);
            e.printStackTrace();
        }

    }
}
