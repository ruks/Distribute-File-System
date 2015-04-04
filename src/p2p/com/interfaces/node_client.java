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


    void sendData(String serverAddress, int serverPort, String message);
    
}
