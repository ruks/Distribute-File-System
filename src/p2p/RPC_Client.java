/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.util.Vector;
import org.apache.xmlrpc.XmlRpcClient;

/**
 *
 * @author rukshan
 */
public class RPC_Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        RPC_Client c = new RPC_Client();
        c.sendData("127.0.0.1", 8080, "rukshan");
    }

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
