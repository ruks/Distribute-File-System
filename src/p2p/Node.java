/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rukshan
 */
public class Node {

    private UDP_Server server;
    private UDP_client client;
    private Connect_Server B_Server;

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
}
