/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.com.rpc.impl;

import java.net.MalformedURLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcClient;
import p2p.com.interfaces.node_client;

/**
 *
 * @author rukshan
 */
public class RPC_StatClient{

    XmlRpcClient client;
    String serverIP;
    int serverPort;
    public RPC_StatClient(String serverAddress, int serverPort) {
        try {       
            serverIP=serverAddress;
            this.serverPort=serverPort;
            client = new XmlRpcClient(serverAddress, serverPort);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RPC_StatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void searchFound(String filename,int hops) {
        try {

            Vector params = new Vector();
            
            params.addElement(filename);
            params.addElement(hops);
            
            client.executeAsync("stat.searchFound", params, null);
        } catch (Exception exception) {
            System.err.println("StatClient: " + exception);
        }
    }   
    
    public void latencyRecord(String filename,int latency) {
        try {

            Vector params = new Vector();
            
            params.addElement(filename);
            params.addElement(latency);
            
            client.executeAsync("stat.latencyRecord", params, null);
        } catch (Exception exception) {
            System.err.println("StatClient: " + exception);
        }
    }   

}
