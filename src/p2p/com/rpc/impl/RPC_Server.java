/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.com.rpc.impl;

import org.apache.xmlrpc.WebServer;
import p2p.Node;
import p2p.com.interfaces.node_server;

/**
 *
 * @author rukshan
 */
public class RPC_Server implements node_server {

    private int port;
    private Node node;

    public RPC_Server(int port, Node node) {
        this.port = port;
        this.node = node;
    }

    public void PUT(String msg) {
        System.out.println(msg);
        this.node.console_out(msg);
        try {
            this.node.handleMsg(msg, "address", 9090);
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
        
    }

    @Override
    public void run() {
        this.startListen(this.port);
    }

    @Override
    public void startListen(int port) {
        try {

            System.out.println("Attempting to start XML-RPC Server...");

            WebServer server = new WebServer(port);
            server.addHandler("sample",this);
            server.start();

            System.out.println("Started successfully.");
            System.out.println("Accepting requests. (Halt program to stop.)");

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception);
        }
    }
}
