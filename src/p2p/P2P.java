/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.util.ArrayList;

/**
 *
 * @author rukshan
 */
public class P2P {

    public static void main1(String[] args) {
        Bootsrap_client c = new Bootsrap_client();
        Node n=new Node();
        String res;
        try {
            String cmd = c.get_REG_Command("127.0.0.22", 8006, "node2");
            res = c.connect_and_send("127.0.0.1", 8002, cmd);
            System.out.println(res);
            n.decode(res);

            cmd = c.get_UNREG_Command("127.0.0.11", 8006, "node1");
            System.out.println(cmd);
            res = c.connect_and_send("127.0.0.1", 8002, cmd);
            System.out.println(res);

        } catch (Exception ex) {
            System.out.println("ex = " + ex);
        }
    }

    public static void main(String[] args) {
        UDP_Server server=new UDP_Server(8000);
        server.start();
                
        UDP_client client=new UDP_client();
        
        String req=client.get_JOIN_cmd("localhost",8000);        
        client.sendData("localhost",8000,req);        
        
        
//        req=client.get_LEAVE_cmd("localhost",8000);        
//        client.sendData("localhost",8000,req);
        
        req=client.get_SER_cmd("localhost",8000, "name",10);
        client.sendData("localhost",8000,req);

    }
    
}
