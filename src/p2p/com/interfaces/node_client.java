/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.com.interfaces;

/**
 *
 * @author rukshan
 */
public interface node_client {

    String get_JOIN_cmd(String ip, int port);

    String get_LEAVE_cmd(String ip, int port);

    String get_SER_cmd(String IP, int port, String filename, int hops);

    String sendData(String serverAddress, int serverPort, String message);
    
}
