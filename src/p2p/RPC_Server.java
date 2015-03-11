/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import org.apache.xmlrpc.WebServer;

/**
 *
 * @author rukshan
 */
public class RPC_Server extends Thread {

    private int port;
    private Node node;

    public RPC_Server(int port, Node node) {
        this.port = port;
        this.node = node;
    }

    public Integer sum(int x, int y) {
        return new Integer(x + y);
    }

    public void PUT(String msg) {
        System.out.println(msg);
//        return 0;
    }
    
    @Override
    public void run() {
        this.start(this.port,this.node);
    }

    private void start(int port, Node node) {
        try {

            System.out.println("Attempting to start XML-RPC Server...");

            WebServer server = new WebServer(8080);
            server.addHandler("sample", new RPC_Server(port, node));
            server.start();

            System.out.println("Started successfully.");
            System.out.println("Accepting requests. (Halt program to stop.)");

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception);
        }
    }

    public static void main(String[] args) {
        RPC_Server s=new RPC_Server(8080, null);
        s.start();
    }
}
