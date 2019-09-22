
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Client extends javax.swing.JFrame {

    public Client() throws RemoteException {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_server = new javax.swing.JLabel();
        lb_nick = new javax.swing.JLabel();
        btn_connect = new javax.swing.JButton();
        tf_ip = new javax.swing.JTextField();
        tf_nick = new javax.swing.JTextField();
        tf_message = new javax.swing.JTextField();
        btn_send = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ta_message = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat ");
        setName("Chat_Client"); // NOI18N

        lb_server.setText("Servidor IP");

        lb_nick.setText("Apelido");

        btn_connect.setText("Conectar");
        btn_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_connectActionPerformed(evt);
            }
        });

        btn_send.setText("Enviar");
        btn_send.setEnabled(false);
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });

        ta_message.setEditable(false);
        ta_message.setColumns(20);
        ta_message.setRows(5);
        jScrollPane2.setViewportView(ta_message);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lb_server)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_ip, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lb_nick)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_nick, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_connect))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tf_message, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_send)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_server)
                    .addComponent(tf_ip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_nick)
                    .addComponent(tf_nick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_connect))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_message, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_send))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_connectActionPerformed
        server = tf_ip.getText();
        nick = tf_nick.getText();

        if (server.isEmpty() || nick.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O Campo Não Pode Ser Vazio!");
        } else {
            try {
                chat = (IChat) Naming.lookup("rmi://" + server + "/Chat");
                ta_message.append("Conectado com Sucesso!\n\n");
                btn_send.setEnabled(true);
                listMessage.addAll(chat.readMessage());
                tf_message.requestFocus();
                new Thread() {
                    public void run() {
                        do {
                            try {
                                if (chat.readMessage().size() > listMessage.size()) {
                                    ta_message.setText("");
                                    listMessage.clear();
                                    for (Iterator<String> iterator = chat.readMessage().iterator(); iterator.hasNext();) {
                                        String f = iterator.next();
                                        ta_message.append(f + "\n");
                                        listMessage.add(f);
                                    }
                                }
                                Thread.sleep(2000);
                            } catch (RemoteException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } while (true);
                    }
                }.start();
                btn_connect.setEnabled(false);
            } catch (MalformedURLException | NotBoundException | RemoteException e) {
                JOptionPane.showMessageDialog(null, "Erro ao Conectar com o Servidor!");
            }
        }
    }//GEN-LAST:event_btn_connectActionPerformed

    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendActionPerformed
        str = tf_message.getText();
        if (!str.isEmpty()) {
            try {
                chat.responseMessage(str);
                tf_message.setText("");
                ta_message.append(nick + ": " + chat.readMessage().getLast() + "\n");
            } catch (RemoteException e) {
            }
        }
    }//GEN-LAST:event_btn_sendActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Client().setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    IChat chat;
    String server;
    String nick;
    String str;
    LinkedList<String> listMessage = new LinkedList<>();
    Thread thread;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_connect;
    private javax.swing.JButton btn_send;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_nick;
    private javax.swing.JLabel lb_server;
    public javax.swing.JTextArea ta_message;
    public javax.swing.JTextField tf_ip;
    public javax.swing.JTextField tf_message;
    public javax.swing.JTextField tf_nick;
    // End of variables declaration//GEN-END:variables
}
