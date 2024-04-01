package falcongps;
import com.mysql.jdbc.Connection;
import comunicacionserial.ArduinoExcepcion;
import comunicacionserial.ComunicacionSerial_Arduino;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;


public class falcongps extends javax.swing.JFrame {
    private static Connection con;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "password";
    private static final String url = "jdbc:mysql://localhost:3306/falcongps?&characterEncoding=latin1&useConfigs=maxPerformance";
    
    String sendingId = "";
    String[] Id_Storage;
    int counter = 0;

    ComunicacionSerial_Arduino conection = new ComunicacionSerial_Arduino();
    SerialPortEventListener listen = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if(conection.isMessageAvailable()){
                    Localizacion.setText(conection.printMessage());
                    sendingId = Localizacion.getText();
                    if(sendingId.contains("ID_Machine@") && counter == 0){
                        Id_Storage = sendingId.split("@", 3);
                        System.out.println(Id_Storage[1]);
                        counter = 1;
                        queryFunction(Id_Storage[1]);
                    }
                }
            } catch (SerialPortException ex) {
                Logger.getLogger(falcongps.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArduinoExcepcion ex) {
                Logger.getLogger(falcongps.class.getName()).log(Level.SEVERE, null, ex);
            };
        }
    };
    
    public falcongps() {
        initComponents();
        try {
            conection.arduinoRXTX("COM5", 9600, listen);
        } catch (ArduinoExcepcion ex) {
            Logger.getLogger(falcongps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void queryFunction(String idStorage){
        con = null;
        try{
            Class.forName(driver);
            con = (Connection) DriverManager.getConnection(url, user, pass);
            if (con != null){
                StatusMessage.setText("Succesful Connection");
            }
            Statement consultVariable = con.createStatement();
            ResultSet rs = consultVariable.executeQuery ("SELECT * FROM mainuser WHERE ID_Device = " + idStorage);
            while (rs.next()){
                System.out.println (rs.getString(1) + " " + rs.getString(2));
            }
            con.close();
        } catch(ClassNotFoundException | SQLException e){
            StatusMessage.setText("Failed Connection: " + e);
        }
    }
    @SuppressWarnings("unchecked")
    
    private void initComponents() {
        Localizacion = new javax.swing.JLabel();
        StatusMessage = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Localizacion.setText("Localizacion:");
        StatusMessage.setText("Status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(Localizacion))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(StatusMessage)))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(Localizacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                .addComponent(StatusMessage)
                .addContainerGap())
        );
        pack();
    }
    
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(falcongps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(falcongps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(falcongps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(falcongps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new falcongps().setVisible(true);
            }
        });
    }
    
    private javax.swing.JLabel Localizacion;
    private javax.swing.JLabel StatusMessage;
}