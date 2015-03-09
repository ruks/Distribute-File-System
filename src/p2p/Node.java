/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    private String host = "127.0.0.1";
    private int port = 8002;
    private String nodeName = "node1";

    private String Bhost = "unhosted.projects.uom.lk";
    private int Bport = 8082;

    public Node(String host, int port, String name) {
        this.host = host;
        this.port = port;
        this.nodeName = name;

        routingTable = new ArrayList();
        fileList = new ArrayList<>();
        fileList.add("file");
        fileList.add("file1");
        fileList.add("File1");
        for (int i = 0; i < 10; i++) {
            fileList.add(this.nodeName + "_" + i);
        }
    }

    public void start() {
        server = new UDP_Server(this.port, this);
        server.start();
        client = new UDP_client();

        B_Server = new Bootsrap_client();

        String res;
        try {
            String cmd = B_Server.get_REG_Command(this.host, this.port, this.nodeName);
            res = B_Server.connect_and_send(this.Bhost, this.Bport, cmd);
            System.out.println(res);
            TableElement[] nodes = this.decode(res);
            if (nodes != null && nodes.length > 0) {
                for (TableElement node : nodes) {
                    String req = client.get_JOIN_cmd(this.host, this.port);
                    client.sendData(node.host, node.port, req);
                }
            }

            while (true) {
                System.out.println("Enter name to search: ");
                BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
                String file = read.readLine();
                searchNet(file);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ex = " + ex);
        }
    }

    public void searchNet(String file) {
        extend_search(file, this.host, this.port, 5);
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

    public searchResult search(String file, String host, int port, int hops) {
        int cnt = 0;
        String res = "";
        for (String f : fileList) {
            if (f.contains(file)) {
                cnt++;
                res += f + " ";
            }
        }
//        extend_search(file, host, port, hops);
        return new searchResult(cnt, res);
    }

    public void extend_search(String file, String host, int port, int hops) {
        if (hops == 0) {
            return;
        }
        String cmd = get_SER_cmd(host, port, file, hops - 1);
        for (TableElement node : routingTable) {
//            UDP_client client = new UDP_client();
//            if (!node.host.equals(host) && node.port != port) {
                client.sendData(node.host, node.port, cmd);
//            }
        }
    }

    public TableElement[] decode(String args) {
        String reg = "^(\\d){4} REGOK ([0-9]+)*";
        TableElement[] nodes = null;
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(args);
        if (m.find()) {

            int status = Integer.parseInt(m.group(2));

            if (status == 9999) {
                System.out.println("Already Connected.....");
            } else {
                System.out.println("No of nodes= " + status);
                String[] data = args.split(" ");
                nodes = new TableElement[status];
                for (int i = 0; i < status; i++) {
                    String ip = data[3 + 3 * i];
                    int port = Integer.parseInt(data[3 + 3 * i + 1]);
                    String name = data[3 + 3 * i + 2];
                    System.out.println("node " + i + " :" + ip + " " + port + " " + name);
                    int st = this.addToTable(ip, port);
                    System.out.println(st);
                    nodes[i] = new TableElement(ip, port);
                }
            }
            return nodes;
        } else {
            System.out.println("NO MATCH");
            return null;
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
            searchResult r = this.search(cmd[4], cmd[2], Integer.parseInt(cmd[3].trim()), Integer.parseInt(cmd[5].trim()));
            res = create_SEROK_response(r.status, this.host, this.port, Integer.parseInt(cmd[5].trim()) - 1, r.result);
        } else {
            res = "9090";
        }
        return res;
    }

    public String get_SER_cmd(String IP, int port, String filename, int hops) {
        String cmd = "SER " + IP + " " + port + " " + filename + " " + hops;
        int length = cmd.length() + 5;
        cmd = "00" + length + " " + cmd;
        return cmd;
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
