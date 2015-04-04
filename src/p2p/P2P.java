/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

/**
 *
 * @author rukshan
 */
public class P2P {

    public static void main(String[] args) {
        String host;
        int port;
        String name;
        if (args.length > 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
            name = args[2];
        } else {
            System.out.println("error");
            return;
        }
        Node n = new Node(host, port, name,true,false,null);
        n.start();


    }

}
