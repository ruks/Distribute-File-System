/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2p.com.rpc.impl;

import java.util.Hashtable;
import java.util.Iterator;
import org.apache.xmlrpc.WebServer;
import p2p.P2P;
import p2p.com.interfaces.node_server;

/**
 *
 * @author malintha
 */
public class RPC_StatServer implements node_server{
    
    private int port;
    private Hashtable<String,Integer> searchHopsHT;
    private Hashtable<String,Integer> latencyHT;

    public RPC_StatServer(int port) {
        this.port = port;
        searchHopsHT=new Hashtable<String,Integer>();
        latencyHT=new Hashtable<String,Integer>();
    }
    
    public int searchFound(String filename,int hops){
        if(!searchHopsHT.containsKey(filename)){
            searchHopsHT.put(filename, p2p.Node.hoplimit-hops);
        }else{
            int num=searchHopsHT.get(filename);
            num+=p2p.Node.hoplimit-hops;
            searchHopsHT.put(filename, num);
        }
        System.out.println("search res : "+filename +" "+hops);
        return 0;
    }
    
    public int latencyRecord(String filename, int timegap){
        latencyHT.put(filename, timegap);
        System.out.println("search latency : "+filename +" "+timegap);
        return 0;
    }
    
    public void saveToFile(){
        Iterator<String> keyit = searchHopsHT.keySet().iterator();
        while (keyit.hasNext()) {
            String filename = keyit.next();
            int hops = searchHopsHT.get(filename);
            P2P.writeToFile("Search "+filename+" : "+hops);
        }
        
        keyit = latencyHT.keySet().iterator();
        while (keyit.hasNext()) {
            String filename = keyit.next();
            float latency = latencyHT.get(filename);
            P2P.writeToFile("Latency "+filename+" : "+latency);
        }
        
    }

    @Override
    public void startListen(int port) {
        try {

            System.out.println("Attempting to start XML-RPC Server...");

            WebServer server = new WebServer(this.port);
            server.addHandler("stat",this);
            server.start();

            System.out.println("Started successfully.");
            System.out.println("Accepting requests. (Halt program to stop.)");

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception);
        }    
    }

    @Override
    public void run() {
        this.startListen(port);
    }
}
