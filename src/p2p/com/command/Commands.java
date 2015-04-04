/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.com.command;

/**
 *
 * @author rukshan
 */
public class Commands {

    public static String get_JOIN_cmd(String ip, int port) {
        String cmd = "JOIN " + ip + " " + port;
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
    }

    public static String get_LEAVE_cmd(String ip, int port) {
        String cmd = "LEAVE " + ip + " " + port;
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
    }

    public static String get_SER_cmd(String IP, int port, String filename, int hops) {
        String cmd = "SER " + IP + " " + port + " " + filename + " " + hops;
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
    }
}
