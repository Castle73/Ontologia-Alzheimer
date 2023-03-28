/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import java.io.File;
import java.io.FileInputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author pc
 */
public class LimitesArchivoExcel {

    public void IndividuoNombre(String VideoNombreFilCol) {
        String datoExcel="";
        // muestra el cuadro de diálogo de archivos, para que el usuario pueda elegir el archivo a abrir
        JFileChooser selectorArchivos = new JFileChooser();
        selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        //indicamos la descripción del tipo de archivo que abriremos y la extensión del mismo.
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos Excel(xls,xlsx)", "xls", "xlsx");
        selectorArchivos.setFileFilter(filtro);

        selectorArchivos.setCurrentDirectory(new File(System.getProperty("user.dir")));

        // indica cual fue la accion de usuario sobre el jfilechooser
        int resultado = selectorArchivos.showOpenDialog(null);

        if (resultado != 1) {
            File archivo = selectorArchivos.getSelectedFile(); // obtiene el archivo seleccionado
            if ((archivo == null) || (archivo.getName().equals(""))) {
                JOptionPane.showMessageDialog(null, "Nombre de archivo inválido", "Nombre de archivo inválido", JOptionPane.ERROR_MESSAGE);
            } else {
                String rutaArchivo = selectorArchivos.getSelectedFile().getAbsolutePath();

                try (FileInputStream file = new FileInputStream(new File(rutaArchivo))) {
                    // leer archivo excel
                    XSSFWorkbook worbook = new XSSFWorkbook(file);
                    //obtener la hoja que se va leer
                    XSSFSheet sheet = worbook.getSheetAt(0);
                    // Obtiene la referencia de la celda que debe seleccionar
                    CellReference ref = new CellReference(VideoNombreFilCol);
                    // Obtiene la fila dependiendo la celda que se configura en el archivo config.properties
                    Row fila = sheet.getRow(ref.getRow());
                    if (fila != null) {
                        // Obtiene la columna dependiendo la celda que se configura en el archivo config.properties
                        Cell columna = fila.getCell(ref.getCol());
                        // Obtiene la informacion que tiene la celda pero no puede ser el resultado de una formula
                        if (columna.getCellType() == CellType.STRING) {
                            datoExcel = columna.getStringCellValue();
                        } else if (columna.getCellType() == CellType.NUMERIC) {
                            datoExcel = String.valueOf(columna.getNumericCellValue());
                        }

                        System.out.println("La informacion es: " + datoExcel);
                        System.out.println("maximo columnas: " + fila.getLastCellNum());
                        System.out.println("maximo filas: " + sheet.getLastRowNum());
                    }
//            System.out.println("sale");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "accion cancelada");
        }

    }

    public static void main(String[] args) {
        String FilCelSelec = "d16";
        //String nombreArchivo = "TODOS_resultado.xlsx";
        //String rutaArchivo = "D:\\file\\NetBeansProjects\\excel-ontologia\\archivos\\" + nombreArchivo;
        //String hoja = "Hoja1";
        //System.out.println("entra");
        LimitesArchivoExcel obj = new LimitesArchivoExcel();
        obj.IndividuoNombre(FilCelSelec);

    }
}
