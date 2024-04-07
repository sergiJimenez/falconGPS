package falcongps;
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

public class FalconGPS {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "password";
    private static final String url = "jdbc:mysql://localhost:3306/falcongps?&characterEncoding=latin1&useConfigs=maxPerformance";
    
    int Id_Machine;
    String sendingId = "";
    String[] Id_Storage;
    int counter = 0;

    ComunicacionSerial_Arduino conection = new ComunicacionSerial_Arduino();
    SerialPortEventListener listen = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if(conection.isMessageAvailable()){
                    System.out.println("Localizacion: " + conection.printMessage());
                    sendingId = conection.printMessage();
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
            }
        }
    };
    
    public falcongps() {
        try {
            conection.arduinoRXTX("COM5", 9600, listen);
        } catch (ArduinoExcepcion ex) {
            Logger.getLogger(falcongps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void queryFunction(String idStorage){
        try {
            Class.forName(driver);
            try (java.sql.Connection con = DriverManager.getConnection(url, user, pass)) {
                if (con != null){
                    System.out.println("Succesful Connection");
                }
                Statement consultVariable = con.createStatement();
                ResultSet rs = consultVariable.executeQuery("SELECT * FROM mainuser WHERE ID_Device = " + idStorage);
                while (rs.next()){
                    System.out.println(rs.getString(1) + " " + rs.getString(2));
                }
            }
        } catch(ClassNotFoundException | SQLException e){
            System.out.println("Failed Connection: " + e);
        }
    }
    
    public static void main(String[] args) {
        new falcongps();
    }
}