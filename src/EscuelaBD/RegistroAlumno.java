package EscuelaBD;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class RegistroAlumno extends JFrame implements ActionListener {
    JButton agregar;
    JButton quitar;
    JButton continuar;
    JButton generar;
    JLabel imagen1;
    JLabel imagen2;
    JLabel titulo;
    JLabel titulo2;
    JLabel jlnumcontrol;
    JLabel numcontrol;
    JLabel jlnombre;
    JLabel nombre;
    JLabel jlcarrera;
    JLabel carrera;
    JLabel jlturno;
    JLabel turno;
    JLabel jlsemestre;
    JLabel semestre;
    JLabel jlperiodo;
    JLabel periodo;
    JLabel jlmaterias;
    JLabel jlmateriasSelec;
    JTable materias;
    JTable materiasSelec;
    DefaultTableModel dtmmaterias;
    DefaultTableModel dtmmateriasSelec;
    JScrollPane jscmat;
    JScrollPane jscmatSelec;
    ConectarBD conecta;
    String alum;
    String seleccion;
    String opciones [] = {"Sí", "No"};
    int contador;
    GenerarPDF pdf = new GenerarPDF();
    private Rectangle SizePage = null;
    private File ruta_destino = null;
    final String titulos[] = {"C. Grupo","C. Materia","Materia","Profesor","Créditos",
            "Lunes","Martes","Miércoles","Jueves","Viernes","Aula","Cupo","x"};
    final String datos[][] = {};
    Rectangle ArraySizePage[] = {PageSize.LEGAL, PageSize.LETTER};
    
    public RegistroAlumno (String alumno){
        super("Sistema de Generación de Horarios");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize(1000,700); // establece el tamaño del marco
        setVisible( true ); // muestra el marco
        conecta = new ConectarBD();
        this.alum = alumno;
        
        ImageIcon img = new ImageIcon("TEC.jpg");        
        imagen1 = new JLabel(img);
        add(imagen1);
        imagen1.setBounds(150,10,680,100);
                
        titulo2 = new JLabel("Datos del Alumno");      
        add(titulo2); // agrega etiqueta1 a JFrame
        titulo2.setBounds(200,120,200,10);
        
        jlnumcontrol = new JLabel("No. Control:");      
        add(jlnumcontrol); // agrega etiqueta1 a JFrame
        jlnumcontrol.setBounds(150,150,100,10);
        
        numcontrol = new JLabel();      
        add(numcontrol); // agrega etiqueta1 a JFrame
        numcontrol.setBounds(250,150,100,15);
        
        jlnombre = new JLabel("Nombre:");      
        add(jlnombre); // agrega etiqueta1 a JFrame
        jlnombre.setBounds(150,170,100,10);
        
        nombre = new JLabel();      
        add(nombre); // agrega etiqueta1 a JFrame
        nombre.setBounds(250,170,250,15);
        
        jlcarrera = new JLabel("Carrera:");      
        add(jlcarrera); // agrega etiqueta1 a JFrame
        jlcarrera.setBounds(150,190,100,10);
        
        carrera = new JLabel();      
        add(carrera); // agrega etiqueta1 a JFrame
        carrera.setBounds(250,190,300,15);
        
        jlturno = new JLabel("Turno:");      
        add(jlturno); // agrega etiqueta1 a JFrame
        jlturno.setBounds(600,150,100,10);
        
        turno = new JLabel();      
        add(turno); // agrega etiqueta1 a JFrame
        turno.setBounds(700,150,100,15);
        
        jlsemestre = new JLabel("Semestre:");      
        add(jlsemestre); // agrega etiqueta1 a JFrame
        jlsemestre.setBounds(600,170,100,10);
        
        semestre = new JLabel();      
        add(semestre); // agrega etiqueta1 a JFrame
        semestre.setBounds(700,170,100,15);
        
        jlperiodo = new JLabel("Perido escolar:");      
        add(jlperiodo); // agrega etiqueta1 a JFrame
        jlperiodo.setBounds(600,190,100,10);
        
        periodo = new JLabel();      
        add(periodo); // agrega etiqueta1 a JFrame
        periodo.setBounds(700,190,150,15);
        
        jlmaterias = new JLabel("Materias Disponibles");      
        add(jlmaterias); // agrega etiqueta1 a JFrame
        jlmaterias.setBounds(100,220,150,15);
        
        dtmmaterias = new DefaultTableModel();
        materias = new JTable(dtmmaterias);
        jscmat = new JScrollPane(materias);
        dtmmaterias.addColumn("C. Grupo");
        dtmmaterias.addColumn("C. Materia");
        dtmmaterias.addColumn("Materia");
        dtmmaterias.addColumn("Profesor");
        dtmmaterias.addColumn("Créditos");
        dtmmaterias.addColumn("Lunes");
        dtmmaterias.addColumn("Martes");
        dtmmaterias.addColumn("Miércoles");
        dtmmaterias.addColumn("Jueves");
        dtmmaterias.addColumn("Viernes");
        dtmmaterias.addColumn("Aula");
        dtmmaterias.addColumn("Cupo");
        jscmat.setBounds(50, 250, 900, 150);
        add(jscmat);
        
        agregar = new JButton("Agregar");
        add(agregar);
        agregar.setBounds(450,410,100,25);
        agregar.addActionListener(this);
        
        jlmateriasSelec = new JLabel("Materias Seleccionadas");      
        add(jlmateriasSelec); // agrega etiqueta1 a JFrame
        jlmateriasSelec.setBounds(100,420,150,15);
        
        dtmmateriasSelec = new DefaultTableModel();
        materiasSelec = new JTable(dtmmateriasSelec);
        jscmatSelec = new JScrollPane(materiasSelec);
        dtmmateriasSelec.addColumn("C. Grupo");
        dtmmateriasSelec.addColumn("C. Materia");
        dtmmateriasSelec.addColumn("Materia");
        dtmmateriasSelec.addColumn("Profesor");
        dtmmateriasSelec.addColumn("Créditos");
        dtmmateriasSelec.addColumn("Lunes");
        dtmmateriasSelec.addColumn("Martes");
        dtmmateriasSelec.addColumn("Miércoles");
        dtmmateriasSelec.addColumn("Jueves");
        dtmmateriasSelec.addColumn("Viernes");
        dtmmateriasSelec.addColumn("Aula");
        dtmmateriasSelec.addColumn("Cupo");
        jscmatSelec.setBounds(50, 450, 900, 150);
        add(jscmatSelec);
        
        quitar = new JButton("Quitar");
        add(quitar);
        quitar.setBounds(450,615,100,25);
        quitar.addActionListener(this);    
        
        continuar = new JButton("Continuar");
        add(continuar);
        continuar.setBounds(800,615,150,35);
        continuar.addActionListener(this);    
        
        try {
            datos();
        } catch (SQLException ex) {
            Logger.getLogger(RegistroAlumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            listaMaterias();
        } catch (SQLException ex) {
            Logger.getLogger(RegistroAlumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void datos() throws SQLException {
        String Consulta;
        Consulta = "select * from alumno\n"
                    + "where No_Control = '" + alum + "';";
        ResultSet rs = conecta.Lectura(Consulta);
        while (rs.next()) {
            Object[] fila = new Object[6];
            for (int campo = 0; campo < 6; campo++) {
                fila[campo] = rs.getObject(campo + 1);
                numcontrol.setText(rs.getObject(1).toString());
                nombre.setText(rs.getObject(2).toString());
                carrera.setText(rs.getObject(3).toString());
                turno.setText(rs.getObject(4).toString());
                semestre.setText(rs.getObject(5).toString());
                periodo.setText(rs.getObject(6).toString());
            }
        }
        conecta.conexion.close();
    }
    
    public void listaMaterias() throws SQLException {
        String Consulta;
        Consulta = "select Clave_Grupo, C_Materia, Nombre_Materia, Nombre_Profesor, Creditos, Lunes, Martes, Miercoles, Jueves, Viernes, Aula, Cupo\n" +
                    "from (materia join horario on C_Materia = Clave_Materia) join reticula on Clave_Materia = Clave_Mat\n" +
                    "WHERE Semestre_Materia = '"+semestre.getText()+ "' and Carrera_Materia='"+carrera.getText()+"';";
        ResultSet rs = conecta.Lectura(Consulta);
        dtmmaterias.setRowCount(0);
        while (rs.next()) {
            Object[] fila = new Object[12];
            for (int campo = 0; campo < 12; campo++) {
                fila[campo] = rs.getObject(campo + 1);
            }
            dtmmaterias.addRow(fila);
        }
        //materiasSeleccionadas.getSelectionModel().setSelectionInterval(0, 0);
        conecta.conexion.close();
    }
    
    public void agregarMaterias() throws SQLException{
        if(materias.isRowSelected(materias.getSelectedRow())){
            String Consulta = "select Clave_Grupo, C_Materia, Nombre_Materia, Nombre_Profesor, Creditos, Lunes, Martes, Miercoles, Jueves, Viernes, Aula, Cupo\n" +
                    "from (materia join horario on C_Materia = Clave_Materia) join reticula on Clave_Materia = Clave_Mat\n" +
                    "WHERE Clave_Grupo = '"+  materias.getValueAt(materias.getSelectedRow(), 0).toString() + "';";
            ResultSet rs = conecta.Lectura(Consulta);
            while (rs.next()) {
                Object[] fila = new Object[12];
                for (int campo = 0; campo < 12; campo++) {
                    fila[campo] = rs.getObject(campo + 1);
                }
                dtmmateriasSelec.addRow(fila);
                dtmmaterias.removeRow(materias.getSelectedRow());
                materiasSelec.getSelectionModel().setSelectionInterval(0, 0);
            }
            conecta.conexion.close();
        }
    }
    
    public void quitarMaterias() throws SQLException{
        if(materiasSelec.isRowSelected(materiasSelec.getSelectedRow())){
            String Consulta = "select Clave_Grupo, C_Materia, Nombre_Materia, Nombre_Profesor, Creditos, Lunes, Martes, Miercoles, Jueves, Viernes, Aula, Cupo\n" +
                    "from (materia join horario on C_Materia = Clave_Materia) join reticula on Clave_Materia = Clave_Mat\n" +
                    "WHERE Clave_Grupo = '"+  materiasSelec.getValueAt(materiasSelec.getSelectedRow(), 0).toString() + "';";
            ResultSet rs = conecta.Lectura(Consulta);
            //dtmmateriasSelec.setRowCount(0);
            while (rs.next()) {
                Object[] fila = new Object[12];
                for (int campo = 0; campo < 12; campo++) {
                    fila[campo] = rs.getObject(campo + 1);
                }
                dtmmaterias.addRow(fila);
                dtmmateriasSelec.removeRow(materiasSelec.getSelectedRow());
                materias.getSelectionModel().setSelectionInterval(0, 0);
            }
            conecta.conexion.close();
        }
    }
    
    public void registrarHorario() throws SQLException{
        String Consulta = "insert into registro values ('" + contador + "', '" + numcontrol.getText() + "', '" + materiasSelec.getValueAt(materiasSelec.getSelectedRow(), 0).toString() +"');";
        conecta.Escritura(Consulta);
        conecta.conexion.close();
    }
    
    public void confirmar(){
        seleccion = (String) JOptionPane.showInputDialog(null, "Confirmar registro, ¿Está seguro?", "Confirmar Registro", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion.equalsIgnoreCase("sí")){
            this.dispose();
            
        }
    }
    
    public void generaPDF(JTable materiasSelec) throws BadElementException, IOException{
        ruta_destino = pdf.Colocar_Destino(ruta_destino);
        Image imagenTEC = Image.getInstance("TEC.jpg");
        if (ruta_destino != null) {
            try {
                try {
                    imagenTEC.scaleToFit(400, 400);
                    imagenTEC.setAlignment(Chunk.ALIGN_CENTER);
                }
                catch ( Exception e ){
                    e.printStackTrace();
                }
                PdfPTable tabla = new PdfPTable(materiasSelec.getColumnCount());
                //Declaramos un objeto para manejar las celdas
                
                for (int i = 0; i < materiasSelec.getColumnCount(); i++) {
                    tabla.setWidthPercentage(110f);
                    PdfPCell celdas =new PdfPCell(new Paragraph("" + titulos[i],
                              FontFactory.getFont("arial",   // fuente
			      8,                            // tamaño
			      Font.BOLD,                   // estilo
			      BaseColor.BLACK)));
                    celdas.setBackgroundColor(BaseColor.GRAY);
                    tabla.addCell(celdas);
                }
                for (int f = 0; f < dtmmateriasSelec.getRowCount(); f++) {
                    for (int c = 0; c < materiasSelec.getColumnCount(); c++) {
                        Paragraph renglones = new Paragraph(""+dtmmateriasSelec.getValueAt(f, c));
                        renglones.getFont().setStyle(Font.PLAIN);
                        renglones.setAlignment(Paragraph.ALIGN_CENTER);
                        renglones.getFont().setSize(6);
                        tabla.addCell(renglones);
                    }
                }
                String datosAlumno ="No. Control: "+numcontrol.getText()+
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tTurno: "+turno.getText()+
                        "\nNombre: "+nombre.getText()+
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tSemestre: "+semestre.getText()+
                        "\nCarrera: "+carrera.getText()+
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tPeríodo: "+periodo.getText();
                SizePage = ArraySizePage[1];
                pdf.createPdfTextTable(imagenTEC, tabla, datosAlumno, ruta_destino, SizePage);
                JOptionPane.showMessageDialog(null, "Documento PDF creado");
            } catch (Exception ex) {
                Logger.getLogger(RegistroAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == agregar){
            try {
                agregarMaterias();
                registrarHorario();
            } catch (SQLException ex) {
                Logger.getLogger(RegistroAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(e.getSource() == quitar){
            try {
                quitarMaterias();
            } catch (SQLException ex) {
                Logger.getLogger(RegistroAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(e.getSource() == continuar){
            try {
                contador++;
                confirmar();
                generaPDF(materiasSelec);
            } catch (BadElementException ex) {
                Logger.getLogger(RegistroAlumno.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RegistroAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
