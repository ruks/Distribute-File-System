/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.ui;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import p2p.Node;

/**
 *
 * @author rukshan
 */
public class window extends javax.swing.JFrame {

    private Node node;
    private DefaultTableModel defaultModel;

    /**
     * Creates new form window
     */
    public window() {
        initComponents();
        setAvaiIps();
        ButtonGroup group = new ButtonGroup();
        group.add(is_RPC);
        group.add(is_socket);
    }

    public void addRow(String msg, String host, int port) {
        defaultModel = (DefaultTableModel) resultTable.getModel();
        defaultModel.addRow(new Object[]{host, port, msg});
    }

    public void setStart(boolean started) {
        is_RPC.setEnabled(!started);
        is_socket.setEnabled(!started);
        node_ip.setEnabled(!started);
        node_name.setEnabled(!started);
        node_port.setEnabled(!started);
        boots_host.setEnabled(!started);
        boots_port.setEnabled(!started);
        if (started) {
            start_btn.setText("Stop");
        } else {
            start_btn.setText("Start");
        }
    }

    public void addFiles(ArrayList<String> files) {
        String msg = "";
        for (int i = 0; i < files.size(); i++) {
            msg += files.get(i) + "\n";
        }
        fileList.setText(msg);
    }

    public void setAvaiIps() {
        Enumeration e;
        try {
            e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    String m = "\\b(?:(?:25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d?)\\.){3}(?:25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d?)\\b";
                    if (i.getHostAddress().matches(m)) {
                        System.out.println(i.getHostAddress());
                        node_ip.addItem(i.getHostAddress());
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println("ex = " + ex);
        }
    }

    public void consoleOut(String msg) {
        cosole_out.setText(cosole_out.getText() + "\n" + msg);
    }

    public void initNode() {
        String host = (String) node_ip.getSelectedItem();
        int port = Integer.parseInt(node_port.getText());
        String name = node_name.getText();

        String bhost = boots_host.getText();
        int bport = Integer.parseInt(boots_port.getText());

        boolean isUDP = is_socket.isSelected();
        boolean isRPC = is_RPC.isSelected();

        node = new Node(host, port, name, isUDP, isRPC, bhost, bport, this);
        node.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        node_port = new javax.swing.JTextField();
        node_name = new javax.swing.JTextField();
        start_btn = new javax.swing.JButton();
        is_RPC = new javax.swing.JRadioButton();
        is_socket = new javax.swing.JRadioButton();
        node_ip = new javax.swing.JComboBox();
        search_query = new javax.swing.JTextField();
        search_btn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        cosole_out = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        fileList = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        boots_port = new javax.swing.JTextField();
        boots_host = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        node_port.setText("8001");
        node_port.setToolTipText("");

        node_name.setText("Node1");

        start_btn.setText("Start");
        start_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_btnActionPerformed(evt);
            }
        });

        is_RPC.setSelected(true);
        is_RPC.setText("Use RPC");

        is_socket.setText("Use socket");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(node_port)
                    .addComponent(node_name)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(start_btn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(is_RPC)
                            .addComponent(is_socket))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(node_ip, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(node_ip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(node_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(node_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(is_RPC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(is_socket)
                .addGap(18, 18, 18)
                .addComponent(start_btn)
                .addContainerGap())
        );

        search_btn.setText("Search");
        search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_btnActionPerformed(evt);
            }
        });

        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IP", "Port", "Result"
            }
        ));
        jScrollPane2.setViewportView(resultTable);

        cosole_out.setEditable(false);
        cosole_out.setColumns(20);
        cosole_out.setRows(5);
        jScrollPane1.setViewportView(cosole_out);

        fileList.setEditable(false);
        fileList.setColumns(20);
        fileList.setRows(5);
        jScrollPane3.setViewportView(fileList);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        boots_port.setText("8082");

        boots_host.setText("127.0.0.1");

        jLabel1.setText("Bootsrap Server");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boots_port)
                    .addComponent(boots_host)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addComponent(boots_host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(boots_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(search_query, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_btn))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(search_query, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(search_btn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void start_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_btnActionPerformed
        // TODO add your handling code here:
        if (evt.getActionCommand() == "Start") {
            initNode();
        } else {
            node.stopNode();
        }
    }//GEN-LAST:event_start_btnActionPerformed

    private void search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_btnActionPerformed
        // TODO add your handling code here:
        String file = search_query.getText().trim();
        System.out.println(file);
        if (!file.equalsIgnoreCase("") && !file.equalsIgnoreCase(" ") && file != null) {
            if (node != null) {
                node.searchNet(file);
            } else {
                JOptionPane.showMessageDialog(this, "Connect before Search!..", "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Type name before Search!..", "Search Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_search_btnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField boots_host;
    private javax.swing.JTextField boots_port;
    private javax.swing.JTextArea cosole_out;
    private javax.swing.JTextArea fileList;
    private javax.swing.JRadioButton is_RPC;
    private javax.swing.JRadioButton is_socket;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox node_ip;
    private javax.swing.JTextField node_name;
    private javax.swing.JTextField node_port;
    private javax.swing.JTable resultTable;
    private javax.swing.JButton search_btn;
    private javax.swing.JTextField search_query;
    private javax.swing.JButton start_btn;
    // End of variables declaration//GEN-END:variables
}
