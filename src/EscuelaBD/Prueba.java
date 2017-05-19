package EscuelaBD;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Prueba extends JFrame implements ActionListener {
    JButton continuar;
    JButton salir;
    JLabel imagen1;
    JLabel imagen2;
    JLabel titulo;
    JLabel titulo2;
    JLabel control;
    JTextField jtfNControl;
    ConectarBD conector;
    String alumno;
            
    Prueba(){
        super("Sistema de Generación de Horarios");
        setSize(700,400);
        getContentPane().setBackground(Color.WHITE);
        setLocation(380,100);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        conector= new ConectarBD();
        
        ImageIcon img = new ImageIcon("TEC.jpg");        
        imagen1 = new JLabel(img);
        add(imagen1);
        imagen1.setBounds(10,10,670,100);        
        
        titulo2 = new JLabel("<html><body> BIENVENIDO <br>"
                + "Para comenzar a registrar tu horario, por favor ingresa tu "
                + "número de control. </body></html>");      
        add(titulo2); // agrega etiqueta1 a JFrame
        titulo2.setBounds(200,150,300,50);
        
        control = new JLabel("No. Control");      
        add(control); // agrega etiqueta1 a JFrame
        control.setBounds(200,230,200,10);
        
        jtfNControl = new JTextField();
        add(jtfNControl);
        jtfNControl.setBounds(200,260,200,25);
        
        continuar = new JButton("Continuar");
        add(continuar);
        continuar.setBounds(460,320,100,25);
        continuar.addActionListener(this);
        
        salir = new JButton("Salir");
        add(salir);
        salir.setBounds(570,320,100,25);
        salir.addActionListener(this);
    }
    
    public boolean verificar (String alumno){
        boolean estado=false;
        String consulta="select No_Control from alumno;";
        conector.Conectar();
        try{
            if (!conector.conexion.isClosed()){
                Statement st = conector.conexion.createStatement();
                ResultSet rs = conector.Lectura(consulta);
                while (rs.next()){
                    Object[] fila = new Object[1];
                        for (int campo = 0; campo < 1; campo++) {
                            fila[campo] = rs.getObject(campo + 1);
                        }
                        if(fila[0].toString().equals(alumno)) estado = true;
                }
                conector.conexion.close();
            }
        } 
        catch(Exception a){
            System.out.println("Error");
        }
        return estado;
    }  
    
    public void estableceConexion(){
        alumno = jtfNControl.getText();
        if(verificar(alumno)){
            conector.setUsuario("root");
            conector.setPass("11223344");
            conector.Conectar();
        if(conector.estado==true){
            this.dispose();
            new RegistroAlumno(alumno);
            dispose();
        } else 
            JOptionPane.showMessageDialog(null,"No se puede ingresar");
        }
    }
               
    public void actionPerformed(ActionEvent e) {   
        if(e.getSource() == continuar){
            estableceConexion();
        }
        else {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        new Prueba();
    }
    
}