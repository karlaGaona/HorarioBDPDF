package EscuelaBD;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GenerarPDF {

    public void createPdfTextTable(Image imagenTEC, PdfPTable tabla, String jText, File Destino, Rectangle TamanioPagina) throws DocumentException {

        /*Declaramos documento como un objeto Document
         Asignamos el tamaño de hoja y los margenes */
        Document documento = new Document(TamanioPagina, 80, 80, 75, 75);

        //writer es declarado como el método utilizado para escribir en el archivo
        PdfWriter writer = null;

        try {
            //Obtenemos la instancia del archivo a utilizar
            writer = PdfWriter.getInstance(documento, new FileOutputStream(Destino + ".pdf"));
        } catch (FileNotFoundException | DocumentException ex) {
            ex.getMessage();
        }

        //Abrimos el documento para edición
        documento.open();
        
        documento.add(imagenTEC);
        
        documento.add(new Paragraph(" "));
        
        Paragraph par = new Paragraph();
        par.setAlignment(Paragraph.ALIGN_CENTER);
        //Agregamos un titulo al archivo
        par.add(new Paragraph("HORARIO DEL ALUMNO",
                              FontFactory.getFont("arial",   // fuente
			      12,                            // tamaño
			      Font.BOLD,                   // estilo
			      BaseColor.BLACK)));   
        documento.add(par);

        documento.add(new Paragraph(" "));
         
      
        //Declaramos un texto como Paragraph
        //Le podemos dar formado como alineación, tamaño y color a la fuente.
        Paragraph parrafo = new Paragraph();
        parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        //parrafo.setFont(FontFactory.getFont("Sans", 20, Font.BOLD, BaseColor.BLUE));
        parrafo.add(new Paragraph(jText,
                              FontFactory.getFont("arial",   // fuente
			      10,                            // tamaño
			      Font.BOLD,                   // estilo
			      BaseColor.BLACK)));

        try {
            //Agregamos el texto al documento
            documento.add(parrafo);

            //Agregamos un salto de linea
            documento.add(new Paragraph(" "));

            //Agregamos la tabla al documento haciendo 
            //la llamada al método tabla()
            documento.add(tabla);
        } catch (DocumentException ex) {
            ex.getMessage();
        }

        documento.close(); //Cerramos el documento
        writer.close(); //Cerramos writer

        try {
            File path;
            path = new File(Destino + ".pdf");
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public File Colocar_Destino(File ruta_destino) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo PDF", "pdf", "PDF");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            ruta_destino = fileChooser.getSelectedFile().getAbsoluteFile();
        }
        return ruta_destino;
    }
}
