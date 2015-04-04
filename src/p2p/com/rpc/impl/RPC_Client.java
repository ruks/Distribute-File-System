/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.com.rpc.impl;

import java.util.Vector;
import org.apache.xmlrpc.XmlRpcClient;
import p2p.com.interfaces.node_client;

/**
 *
 * @author rukshan
 */
public class RPC_Client implements node_client {

    @Override
    public void sendData(String serverAddress, int serverPort, String message) {
        try {

            XmlRpcClient server = new XmlRpcClient(serverAddress, serverPort);
            Vector params = new Vector();

            params.addElement(message);

//            int result=server.execute("sample.PUT", params);
            server.executeAsync("sample.PUT", params, null);
//            int sum = ((Integer) result).intValue();
//            System.out.println("The sum is: " + sum);

        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception);
        }
    }   

}
