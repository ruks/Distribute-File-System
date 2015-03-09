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

    private final static int PACKETSIZE = 100;
    private int port;
    private Node node;

    public UDP_Server(int port) {
        this.port = port;
        node=new Node();
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
                System.out.println("req:>> "+packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()));

                String msg=new String(packet.getData());
                String reply=this.node.handleMsg(msg);
                //byte[] resData = "OK ack".getBytes();
                //byte[] resData = this.create_JOINOK_response(status).getBytes();
                byte[] resData = reply.getBytes();
                DatagramPacket resDataPacket = new DatagramPacket(resData, resData.length, packet.getAddress(), packet.getPort());

                // Return the packet to the sender
                socket.send(resDataPacket);
            }
        } catch (Exception e) {
//            System.out.println(e);
            e.printStackTrace();
        }

    }
}
