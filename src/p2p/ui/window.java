/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.ui;

import java.net.DatagramPacket;
import java.util.ArrayList;
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
    }

    public void addRow(String msg,String host,int port) {
        defaultModel = (DefaultTableModel) resultTable.getModel();
        defaultModel.addRow(new Object[]{host, port,msg});
    }
    
    public void addFiles(ArrayList<String> files) {
        String msg="";
         for (int i = 0; i < files.size(); i++) {
            msg+=files.get(i)+"\n";
        }
        fileList.setText(msg);
    }

    public void consoleOut(String msg) {
        cosole_out.setText(cosole_out.getText() + "\n" + msg);
    }

    public void initNode() {
        String host = node_ip.getText();
        int port = Integer.parseInt(node_port.getText());
        String name = node_name.getText();
        boolean isUDP = is_socket.isSelected();
        boolean isRPC = is_RPC.isSelected();

        node = new Node(host, port, name, isUDP, isRPC, this);
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
        node_ip = new javax.swing.JTextField();
        node_port = new javax.swing.JTextField();
        node_name = new javax.swing.JTextField();
        start_btn = new javax.swing.JButton();
        is_RPC = new javax.swing.JRadioButton();
        is_socket = new javax.swing.JRadioButton();
        search_query = new javax.swing.JTextField();
        search_btn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        cosole_out = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        fileList = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        node_ip.setText("127.0.0.1");

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
                    .addComponent(node_ip)
                    .addComponent(node_port)
                    .addComponent(node_name)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(is_RPC)
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(start_btn, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(is_socket, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(node_ip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(search_query, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(search_btn))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(search_query, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(search_btn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void start_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_btnActionPerformed
        // TODO add your handling code here:
        initNode();
    }//GEN-LAST:event_start_btnActionPerformed

    private void search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_btnActionPerformed
        // TODO add your handling code here:
        String file = search_query.getText().trim();
        System.out.println(file);
        if (!file.equalsIgnoreCase("") && !file.equalsIgnoreCase(" ") && file != null) {
            node.searchNet(file);
        } else {
            JOptionPane.showMessageDialog(this, "Type name before Search!..", "Search Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_search_btnActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea cosole_out;
    private javax.swing.JTextArea fileList;
    private javax.swing.JRadioButton is_RPC;
    private javax.swing.JRadioButton is_socket;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField node_ip;
    private javax.swing.JTextField node_name;
    private javax.swing.JTextField node_port;
    private javax.swing.JTable resultTable;
    private javax.swing.JButton search_btn;
    private javax.swing.JTextField search_query;
    private javax.swing.JButton start_btn;
    // End of variables declaration//GEN-END:variables
}
