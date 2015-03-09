/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rukshan
 */
public class Node {

    private UDP_Server server;
    private UDP_client client;
    private Bootsrap_client B_Server;
    private ArrayList<TableElement> routingTable;
    private ArrayList<String> fileList;
    private String host;
    private int port;

    public Node() {
        routingTable = new ArrayList();
    }

    public void start() {

    }

    public int addToTable(String host, int port) {

        TableElement ele = new TableElement(host, port);
        if (!routingTable.contains(ele)) {
            routingTable.add(ele);
//            showTable();
            return 0;
        } else {
            return 9999;
        }

    }

    public int removeFromTable(String host, int port) {
//        showTable();
        TableElement ele = new TableElement(host, port);
        if (routingTable.contains(ele)) {
            routingTable.remove(ele);
            return 0;
        } else {
            return 9999;
        }
    }

    public void showTable() {
        for (TableElement r : routingTable) {
            System.out.println(r.host + " " + r.port);
        }
    }

    public searchResult search(String file) {
        return new searchResult(1, "");
    }

    public static void decode(String args) {
        String reg = "^(\\d){4} REGOK ([0-9]+)*";
//        args.com
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(args);
        if (m.find()) {

            int status = Integer.parseInt(m.group(2));

            if (status == 9999) {
                System.out.println("Already Connected.....");
            } else {
                System.out.println("No of nodes= " + status);
                //String reg1 = "^(\\d){4} REGOK ([0-9]+)* ((\\d)+\\.){3}(\\d)+ (\\d)+ (\\w)";
                //String reg1 = "(((\\d)+\\.){3}(\\d)+ (\\d)+ (\\w)+( )*)+?";
//                String reg1="((\\d+\\.)+\\d+ \\d+ (?=node)( )*)+?";
//                
//                Pattern p1 = Pattern.compile(reg1);
//                Matcher m1 = p1.matcher(args);
//                if (m1.find()) {
//
//                    System.out.println(m1.groupCount());
//                    for (int i = 0; i < m1.groupCount(); i++) {
//                        System.out.println("Found value " + i + " : " + m1.group(i));
//                    }
//                } else {
//                    System.out.println("NO");
//                }
// (\\d)+ (\\w)+( )*
                String[] data = args.split(" ");
                for (int i = 0; i < status; i++) {
                    String ip = data[3 + 3 * i];
                    int port = Integer.parseInt(data[3 + 3 * i + 1]);
                    String name = data[3 + 3 * i + 2];
                    System.out.println("node " + i + " :" + ip + " " + port + " " + name);
                }
            }

        } else {
            System.out.println("NO MATCH");
        }
    }

    public String handleMsg(String msg) {
        String[] cmd = msg.split(" ");
        String res;
        if (cmd[1].equals("JOIN")) {
            int st = this.addToTable(cmd[2], Integer.parseInt(cmd[3].trim()));
            res = this.create_JOINOK_response(st);
        } else if (cmd[1].equals("LEAVE")) {
            int st = this.removeFromTable(cmd[2], Integer.parseInt(cmd[3].trim()));
            res = this.create_LEAVEOK_response(st);
        } else if (cmd[1].equals("SER")) {
            //length SER IP port file_name hops
            searchResult r = this.search(cmd[4]);
            res = create_SEROK_response(r.status, this.host, this.port, 10, r.result);
        } else {
            res = "9090";
        }
        return res;
    }

    public String create_JOINOK_response(int status) {
        String cmd = "JOINOK " + status;
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
    }

    public String create_LEAVEOK_response(int status) {
        String cmd = "LEAVEOK " + status;
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
    }

    public String create_SEROK_response(int no_files, String IP, int port, int hops, String filename) {
        String cmd;
        if (no_files == 0) {
            cmd = "SEROK " + no_files;
        } else {
            cmd = "SEROK " + no_files + " " + IP + " " + port + " " + hops + " " + filename;
        }
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
    }
}

class TableElement {

    String host;
    int port;

    public TableElement(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean equals(Object obj) {
        TableElement e = (TableElement) obj;
        return e.host.equals(this.host) && e.port == this.port;
    }

}

class searchResult {

    int status;
    String result;

    public searchResult() {
    }

    public searchResult(int status, String result) {
        this.status = status;
        this.result = result;
    }

}
