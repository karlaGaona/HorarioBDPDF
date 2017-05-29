package EscuelaBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConectarBD {
    private String Usuario="root";
    private String Pass="11223344";
    public Connection conexion;
    public boolean estado;
    
    public void setUsuario(String usuario){
        this.Usuario=usuario;
    }
    
    public void setPass(String pass){
        this.Pass=pass;
    }
    
    
    public void Conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/escuela", Usuario, Pass);
            estado=true;
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"No se puedo conectar","Error",JOptionPane.ERROR_MESSAGE);
            estado=false;
        }
    }
    
    public ResultSet Lectura(String Consulta){
        Statement st;
        ResultSet rs = null;
        try{
        Conectar();
        if (!conexion.isClosed()){
            st = conexion.createStatement();
            rs = st.executeQuery(Consulta);
            //conexion.close();
        }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
        }
        return rs;
    }
    
    public void Escritura(String Consulta){
        try{
            Conectar();
            System.out.println(estado);
            if(!conexion.isClosed()){
                Statement st = conexion.createStatement();
                st.executeUpdate(Consulta);
            }
        } catch (Exception e){
            System.out.println("NO!!!!!!!!");
        }
    }
    
}
