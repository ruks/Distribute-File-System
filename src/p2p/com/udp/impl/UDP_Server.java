/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.com.udp.impl;

import p2p.com.interfaces.node_server;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import p2p.Node;

/**
 *
 * @author rukshan
 */
public class UDP_Server implements node_server {

    private final static int PACKETSIZE = 1000;
    private int port;
    private Node node;

    public UDP_Server(int port, Node node) {
        this.port = port;
        this.node = node;
    }

    @Override
    public void run() {
        this.startListen(this.port);
    }

    @Override
    public void startListen(int port) {

        try {

            // Construct the socket
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println(port);

            System.out.println("Node is ready...");

            for (;;) {
                // Create a packet
                DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);

                // Receive a packet (blocking)
                socket.receive(packet);

                // Print the packet
                String out = "recived: >> " + packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData());
                System.out.println(out);
                this.node.console_out(out);

//                String msg=new String(packet.getData());
                this.node.handleMsg(new String(packet.getData()), packet.getAddress().toString(), packet.getPort());

//                
            }
        } catch (Exception e) {
//            System.out.println(e);
            e.printStackTrace();
        }

    }
}
