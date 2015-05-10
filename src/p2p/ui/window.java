/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.ui;

import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import p2p.Node;
import p2p.P2P;
import p2p.com.rpc.impl.RPC_StatClient;
import p2p.com.rpc.impl.RPC_StatServer;

/**
 *
 * @author rukshan
 */
public class window extends javax.swing.JFrame {

    private Node node;
    private DefaultTableModel defaultModel;
    
    private RPC_StatServer statserver;
    private RPC_StatClient statclient;
    public long searchtime;

    /**
     * Creates new form window
     */
    public window() {
        initComponents();
        setAvaiIps();
        ButtonGroup group = new ButtonGroup();
        group.add(is_RPC);
        group.add(is_socket);
                
        String host = (String) node_ip.getSelectedItem();
        String[] hostparts=host.split("\\.");
        String nodename = "Node"+hostparts[hostparts.length-1];
        node_name.setText(nodename);
        P2P.openFile("output-"+ nodename+".txt");    
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent winEvt) {
                P2P.closeFile();
                System.exit(0);
            }
        });
        
        
    }

    public void addRow(String msg, String host, int port) {
        if(searchtime>100000){
            searchtime=System.currentTimeMillis()-searchtime;
            statclient.latencyRecord(msg, (int)searchtime);
        }
        
        defaultModel = (DefaultTableModel) resultTable.getModel();
        defaultModel.addRow(new Object[]{host, port, msg});
        
    }
    
    public void addRowToRT(String host, int port) {
        DefaultTableModel defmodel = (DefaultTableModel) routingTable.getModel();
        defmodel.addRow(new Object[]{host, port});
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        node_port = new javax.swing.JTextField();
        node_name = new javax.swing.JTextField();
        is_RPC = new javax.swing.JRadioButton();
        is_socket = new javax.swing.JRadioButton();
        node_ip = new javax.swing.JComboBox();
        chkStatServer = new javax.swing.JCheckBox();
        chkStatClient = new javax.swing.JCheckBox();
        start_btn = new javax.swing.JButton();
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
        jScrollPane4 = new javax.swing.JScrollPane();
        routingTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtStatPort = new javax.swing.JTextField();
        btnTestStat = new javax.swing.JButton();
        txtStatIP = new javax.swing.JTextField();
        btnSaveStats = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtMessagesPerFile = new javax.swing.JTextArea();
        btnUpdatNodeMsgCountTxt = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblTotalMsgs = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        node_port.setText("8001");
        node_port.setToolTipText("");

        node_name.setText("Node1");

        is_RPC.setSelected(true);
        is_RPC.setText("Use RPC");

        is_socket.setText("Use socket");

        chkStatServer.setText("Stat Server");
        chkStatServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkStatServerActionPerformed(evt);
            }
        });

        chkStatClient.setSelected(true);
        chkStatClient.setText("Stat Client");

        start_btn.setText("Start");
        start_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(node_port)
                    .addComponent(node_name)
                    .addComponent(node_ip, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(is_RPC)
                            .addComponent(is_socket)
                            .addComponent(chkStatServer)
                            .addComponent(chkStatClient))
                        .addGap(0, 79, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(131, Short.MAX_VALUE)
                    .addComponent(start_btn)
                    .addGap(22, 22, 22)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(node_ip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(node_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(node_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(is_RPC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(is_socket)
                .addGap(24, 24, 24)
                .addComponent(chkStatServer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkStatClient)
                .addGap(47, 47, 47))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(307, Short.MAX_VALUE)
                    .addComponent(start_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
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
                .addGap(21, 21, 21)
                .addComponent(boots_host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(boots_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        routingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "IP", "Port"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        routingTable.setCellSelectionEnabled(true);
        jScrollPane4.setViewportView(routingTable);
        routingTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("<html>Stat client & server<br>(for statistics collection)");
        jLabel2.setToolTipText("");

        txtStatPort.setText("9009");
        txtStatPort.setToolTipText("");
        txtStatPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatPortActionPerformed(evt);
            }
        });

        btnTestStat.setText("Test");
        btnTestStat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestStatActionPerformed(evt);
            }
        });

        txtStatIP.setText("192.168.43.80");
        txtStatIP.setToolTipText("");
        txtStatIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatIPActionPerformed(evt);
            }
        });

        btnSaveStats.setText("Save Stats");
        btnSaveStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveStatsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStatIP)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStatPort, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnTestStat, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveStats, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtStatIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveStats)
                    .addComponent(btnTestStat))
                .addGap(20, 20, 20))
        );

        txtMessagesPerFile.setEditable(false);
        txtMessagesPerFile.setColumns(20);
        txtMessagesPerFile.setRows(5);
        jScrollPane5.setViewportView(txtMessagesPerFile);

        btnUpdatNodeMsgCountTxt.setText("Update");
        btnUpdatNodeMsgCountTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatNodeMsgCountTxtActionPerformed(evt);
            }
        });

        jLabel3.setText("File List");

        jLabel4.setText("Processed Message Counts");

        jLabel5.setText("Console");

        jLabel6.setText("Routing Table");

        lblTotalMsgs.setText("----");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(search_query)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_btn))
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdatNodeMsgCountTxt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotalMsgs)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane5)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(0, 527, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(search_query, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(search_btn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(btnUpdatNodeMsgCountTxt)
                            .addComponent(lblTotalMsgs))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(jScrollPane5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdatNodeMsgCountTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatNodeMsgCountTxtActionPerformed
        txtMessagesPerFile.setText(node.getMessageCountHTString());
        lblTotalMsgs.setText(node.getAllMessageCountHT() + "");
    }//GEN-LAST:event_btnUpdatNodeMsgCountTxtActionPerformed

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

    private void txtStatPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatPortActionPerformed

    private void btnTestStatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestStatActionPerformed
        statclient.searchFound("SDAD", 122);
    }//GEN-LAST:event_btnTestStatActionPerformed

    private void txtStatIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatIPActionPerformed

    private void chkStatServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkStatServerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkStatServerActionPerformed

    private void btnSaveStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveStatsActionPerformed
        String nodename = node_name.getText();
        P2P.openFile("output-"+ nodename+".txt");  
        statserver.saveToFile();
        P2P.closeFile();
    }//GEN-LAST:event_btnSaveStatsActionPerformed

    private void start_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_btnActionPerformed

        if ("Start".equals(evt.getActionCommand())) {
            initNode();
        } else {
            node.stopNode();
        }
        
        if(chkStatClient.isSelected()){
            if(statclient==null){
                statclient = new RPC_StatClient(txtStatIP.getText(), Integer.parseInt(txtStatPort.getText()));
            }
        }
        
        if(chkStatServer.isSelected()){
            statserver = new RPC_StatServer(Integer.parseInt(txtStatPort.getText()));        
            statserver.startListen(0);            
        }
    }//GEN-LAST:event_start_btnActionPerformed

    public RPC_StatClient getStatclient() {
        return statclient;
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField boots_host;
    private javax.swing.JTextField boots_port;
    private javax.swing.JButton btnSaveStats;
    private javax.swing.JButton btnTestStat;
    private javax.swing.JButton btnUpdatNodeMsgCountTxt;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkStatClient;
    private javax.swing.JCheckBox chkStatServer;
    private javax.swing.JTextArea cosole_out;
    private javax.swing.JTextArea fileList;
    private javax.swing.JRadioButton is_RPC;
    private javax.swing.JRadioButton is_socket;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblTotalMsgs;
    private javax.swing.JComboBox node_ip;
    private javax.swing.JTextField node_name;
    private javax.swing.JTextField node_port;
    private javax.swing.JTable resultTable;
    private javax.swing.JTable routingTable;
    private javax.swing.JButton search_btn;
    private javax.swing.JTextField search_query;
    private javax.swing.JButton start_btn;
    private javax.swing.JTextArea txtMessagesPerFile;
    private javax.swing.JTextField txtStatIP;
    private javax.swing.JTextField txtStatPort;
    // End of variables declaration//GEN-END:variables
}
