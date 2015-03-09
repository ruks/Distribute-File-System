/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author rukshan
 */
public class Bootsrap_client {
    //0028 UNREG 64.12.123.190 432
    
    public String get_REG_Command(String ip, int port, String name) {
        String cmd = "REG " + ip + " " + port + " " + name;
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
    }
    
    public String get_UNREG_Command(String ip, int port, String name) {
        String cmd = "UNREG " + ip + " " + port + " " + name;
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
    }

    public String connect_and_send(String serverAddress, int serverPort, String Command) throws Exception {
        Socket socket = null;
        String response = "";
        try {

            InetAddress add = InetAddress.getByName(serverAddress);
            socket = new Socket(add, serverPort);
            OutputStream out = socket.getOutputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s;

            out.write(Command.getBytes());

            out.flush();

            while ((s = read.readLine()) != null) {
                response += s;
            }

            out.close();
            read.close();
            socket.close();
            return response;
        } catch (Exception e) {
            System.out.println("e = " + e);
            throw e;
        }

    }
    

}
