/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import p2p.com.udp.impl.UDP_client;
import p2p.com.udp.impl.UDP_Server;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import p2p.com.command.Commands;
import p2p.com.interfaces.node_client;
import p2p.com.interfaces.node_server;
import p2p.com.rpc.impl.RPC_Client;
import p2p.com.rpc.impl.RPC_Server;
import p2p.ui.window;

/**
 *
 * @author rukshan
 */
public class Node extends Thread {

    private node_server server;
    private Thread serverThread;
    private node_client client;
    private Thread clientThread;
    private Bootsrap_client B_Server;
    private ArrayList<TableElement> routingTable;
    private ArrayList<String> fileList;
    private ArrayList<String> cmdList;

    private String host = "127.0.0.1";
    private int port = 8002;
    private String nodeName = "node1";

    //private String Bhost = "unhosted.projects.uom.lk";
    private String Bhost = "127.0.0.1";
    private int Bport = 8082;
    private window window;
    public static final int hoplimit = 5;
    
    private Hashtable<String,Integer> messageCounts;

    public Node(String host, int port, String name, boolean isUDP, boolean isRPC, String bhost, int bport, window win) {
        this.host = host;
        this.port = port;
        this.nodeName = name;
        this.window = win;

        this.Bhost = bhost;
        this.Bport = bport;

        messageCounts = new Hashtable<String,Integer>();
        routingTable = new ArrayList();
        fileList = new ArrayList<>();
        cmdList = new ArrayList<>();
//        fileList.add("file");
//        fileList.add("file1");
//        fileList.add("File1");
//        for (int i = 0; i < 10; i++) {
//            fileList.add(this.nodeName + "_" + i);
//        }
        ArrayList<String> files = loadFiles();
        int no = (int) (3 + Math.ceil((int) (Math.random() * 2)));
        for (int i = 0; i < no; i++) {
            int id = (int) (Math.random() * files.size());
            fileList.add(files.remove(id));
        }

        for (int i = 0; i < fileList.size(); i++) {
            System.out.println(fileList.get(i));
        }

        window.addFiles(fileList);

        if (isUDP) {
            server = new UDP_Server(this.port, this);
            client = new UDP_client();
        } else {
            server = new RPC_Server(this.port, this);
            client = new RPC_Client();
        }
    }
    
    
    private void addToMessageCountHT(String filename){
         if(!messageCounts.containsKey(filename)){
            messageCounts.put(filename, 1);
        }else{
            int num=messageCounts.get(filename) + 1;
            messageCounts.put(filename, num);
        }
    }
    
    public String getMessageCountHTString(){
        String txt = "";
        
        Iterator<String> keyit = messageCounts.keySet().iterator();
        while (keyit.hasNext()) {
            String filename = keyit.next();
            int count = messageCounts.get(filename);
            txt +=filename+" : "+count +"\n";
        }
        return txt;
    }
    
        public int getAllMessageCountHT(){
        String txt = "";
        int total = 0;
        Iterator<String> keyit = messageCounts.keySet().iterator();
        while (keyit.hasNext()) {
            String filename = keyit.next();
            int count = messageCounts.get(filename);
            total +=count;
        }
        return total;
    }


    public void console_out(String msg) {
        window.consoleOut(msg);
    }

    public ArrayList<String> loadFiles() {
        ArrayList<String> files = new ArrayList<>();
        try {
            BufferedReader read = new BufferedReader(new FileReader("File_Names.txt"));
            String s;
            while ((s = read.readLine()) != null) {
                files.add(s);
            }

        } catch (Exception ex) {
            System.out.println("ex = " + ex);
        }
        return files;
    }

    @Override
    public void run() {

        serverThread = new Thread(server);
        serverThread.start();

        B_Server = new Bootsrap_client();

        String res;
        try {
            String cmd = B_Server.get_REG_Command(this.host, this.port, this.nodeName);
            res = B_Server.connect_and_send(this.Bhost, this.Bport, cmd);
            System.out.println(res);
            console_out(res);
            TableElement[] nodes = this.decode(res);
            if (nodes == null) {
                return;
            } else {
                window.setStart(true);
            }
            if (nodes.length > 0) {

                for (TableElement node : nodes) {
                    String req = Commands.get_JOIN_cmd(this.host, this.port);
                    client.sendData(node.host, node.port, req);
                }

                while (true) {
                    System.out.println("Enter name to search: ");
                    BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
                    String file = read.readLine();
                    searchNet(file);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ex = " + ex);
        }
    }

    public void searchNet(String file) {
        window.searchtime=System.currentTimeMillis();
        searchLocal(file);
        extend_search(file, this.host, this.port, this.hoplimit);
    }

    public void searchLocal(String file) {
        for (String f : fileList) {
            if (f.contains(file)) {                
                //f
                window.addRow(f, this.host, this.port);
            }
        }
    }

    public int addToTable(String host, int port) {

        TableElement ele = new TableElement(host, port);
        if (!routingTable.contains(ele)) {
            routingTable.add(ele);
            window.addRowToRT(host, port);
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

    public void search(String file, String host, int port, int hops) {
        int cnt = 0;
        String res = "";
        for (String f : fileList) {
            if (f.contains(file)) {
                cnt++;
                res += f + ",";
            }
        }

        if (cnt == 0) {
            extend_search(file, host, port, hops);
        } else {
            //String msg = create_SEROK_response(cnt, this.host, this.port, hops - 1, res);
            String msg = create_SEROK_response(cnt, this.host, this.port, this.hoplimit, res);
//          sendData(msg, host, port);
            client.sendData(host, port, msg);
            window.getStatclient().searchFound(file, hops);
        }
    }

    public void extend_search(String file, String host, int port, int hops) {
        if (hops == 0) {
            P2P.writeToFile(file +" Hop exceed");
            return;
        }
        String cmd = get_SER_cmd(host, port, file, hops - 1);
//        cmdList.add(cmd);
        for (TableElement node : routingTable) {
//            UDP_client client = new UDP_client();
//            System.out.println(node.host + " " + host + " " + node.port + " " + port);
            if (!node.host.equals(host) || node.port != port) {
                System.out.println("FWD: " + node.host + " " + node.port);
                client.sendData(node.host, node.port, cmd);
//                if (msg != null) {
//                    return new searchResult(-1, msg);
//                }
            }
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

                int n, n1 = 0, n2 = 1;
                if (status > 2) {
                    n1 = (int) (Math.random() * status);
                    n2 = (int) (Math.random() * status);
                    while (n1 == n2) {
                        n2 = (int) (Math.random() * status);
                    }
                    status = 2;
                    nodes = new TableElement[status];
                } else {
                    nodes = new TableElement[status];
                }

                for (int i = 0; i < status; i++) {
                    if (i == 0) {
                        n = n1;
                    } else {
                        n = n2;
                    }
                    String ip = data[3 + 3 * n];
                    int port = Integer.parseInt(data[3 + 3 * n + 1]);
                    String name = data[3 + 3 * n + 2];
                    System.out.println("node " + i + " :" + ip + " " + port + " " + name);
                    int st = this.addToTable(ip, port);
                    nodes[i] = new TableElement(ip, port);
                    
                }
            }
            return nodes;
        } else {
            System.out.println("NO MATCH");
            return null;
        }
    }

    public void handleMsg(String msg, String host, int port) {

        String[] cmd = msg.split(" ");
        String res;
        if (cmd[1].equals("JOIN")) {
            int st = this.addToTable(cmd[2], Integer.parseInt(cmd[3].trim()));
            res = this.create_JOINOK_response(st);
//            sendData(res, cmd[2], Integer.parseInt(cmd[3].trim()));
            client.sendData(cmd[2], Integer.parseInt(cmd[3].trim()), res);
        } else if (cmd[1].equals("LEAVE")) {
            int st = this.removeFromTable(cmd[2], Integer.parseInt(cmd[3].trim()));
            res = this.create_LEAVEOK_response(st);
//            sendData(res, cmd[2], Integer.parseInt(cmd[3].trim()));
            client.sendData(cmd[2], Integer.parseInt(cmd[3].trim()), res);
        } else if (cmd[1].equals("SER")) {

            //length SER IP port file_name hops
            this.search(cmd[4], cmd[2], Integer.parseInt(cmd[3].trim()), Integer.parseInt(cmd[5].trim()));
            addToMessageCountHT(cmd[4].trim());
            
        } else if (cmd[1].equals("SEROK")) {

            int nof = Integer.parseInt(cmd[2]);
            
            int lenCmd =5;
            for (int i = 0; i < 6; i++) {
                lenCmd+= cmd[i].length();
            }
            String strFiles = msg.substring(lenCmd).trim();
            System.out.println(strFiles);
            
            String ip = cmd[3];
            int nport = Integer.parseInt(cmd[4]);
            String [] files = strFiles.split(",");

            for (String file : files) {
                window.addRow(file, ip, nport);
            }            
        } else {
            System.out.println("else: " + msg);
        }
//        return res;
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

    public void decodeSearchresult(String msg) {

    }

    public void stopNode() {
        try {
            B_Server = new Bootsrap_client();

            String res;
            String cmd = B_Server.get_UNREG_Command(this.host, this.port, "");
            res = B_Server.connect_and_send(this.Bhost, this.Bport, cmd);
            System.out.println(res);
            console_out(res);

            serverThread.stop();
            window.setStart(false);

        } catch (Exception ex) {
            System.out.println("ex = " + ex);
        }

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
