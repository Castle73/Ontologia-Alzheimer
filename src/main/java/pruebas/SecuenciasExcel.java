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
public class SecuenciasExcel {

    public void IndividuoNombre(UbicacionHoja VideoNombreObj, UbicacionHoja datoTiempoObj) {
        String datoExcel = "";
        String datoExcel1 = "";
        String datoExcel2 = "";
        int filasLimite = 0;
        int columnasLimite = 0;
        int numVideos = 0;
        int saltos = 8;
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
                    filasLimite = sheet.getLastRowNum();
                    columnasLimite = sheet.getRow(
                            (new CellReference(VideoNombreObj.getF2(), VideoNombreObj.getC2()))
                                    .getRow())
                            .getLastCellNum();
                    numVideos = filasLimite / saltos;
                    for (int i = 0; i < numVideos; i++) {
                        // Obtiene la referencia de la celda que debe seleccionar
                        CellReference ref = new CellReference(VideoNombreObj.getF2(), VideoNombreObj.getC2());
                        // Obtiene la fila dependiendo la celda que se configura en el archivo config.properties
                        Row fila = sheet.getRow(ref.getRow());
                        if (fila != null) {
                            // Obtiene la columna dependiendo la celda que se configura en el archivo config.properties
                            Cell columna = fila.getCell(ref.getCol());
                            if (columna != null && columna.getCellType() != CellType.BLANK) {
                                // Obtiene la informacion que tiene la celda pero no puede ser el resultado de una formula
                                datoExcel = "";
                                if (columna.getCellType() == CellType.STRING) {
                                    datoExcel = columna.getStringCellValue();
                                } else if (columna.getCellType() == CellType.NUMERIC) {
                                    datoExcel = String.valueOf(columna.getNumericCellValue());
                                }
                                System.out.println("La informacion es: " + datoExcel);
                                /*System.out.println("maximo filas: " + filasLimite);
                                System.out.println("maximo columnas: " + columnasLimite);
                                System.out.println("numero videos: " + numVideos);*/
                                
                                //parte 1
                                for (int j = datoTiempoObj.getC2(); j < columnasLimite; j++) {
                                    // Obtiene la referencia de la celda que debe seleccionar
                                    CellReference ref2 = new CellReference(datoTiempoObj.getF2(), j);
                                    // Obtiene la fila dependiendo la celda que se configura en el archivo config.properties
                                    Row fila2 = sheet.getRow(ref2.getRow());
                                    if (fila2 != null) {
                                        // Obtiene la columna dependiendo la celda que se configura en el archivo config.properties
                                        Cell columna2 = fila2.getCell(ref2.getCol());
                                        if (columna2 != null && columna2.getCellType() != CellType.BLANK) {
                                            if (columna2.getCellType() == CellType.STRING) {
                                                datoExcel1 = columna2.getStringCellValue();
                                            } else if (columna2.getCellType() == CellType.NUMERIC) {
                                                datoExcel1 = String.valueOf(columna2.getNumericCellValue());
                                            }
                                        }
                                    }
                                    
                                    // parte 2
                                    // Obtiene la referencia de la celda que debe seleccionar
                                    CellReference ref3 = new CellReference(datoTiempoObj.getF2() + 2, j);
                                    // Obtiene la fila dependiendo la celda que se configura en el archivo config.properties
                                    Row fila3 = sheet.getRow(ref3.getRow());
                                    if (fila3 != null) {
                                        // Obtiene la columna dependiendo la celda que se configura en el archivo config.properties
                                        Cell columna3 = fila3.getCell(ref3.getCol());
                                        if (columna3 != null && columna3.getCellType() != CellType.BLANK) {
                                            if (columna3.getCellType() == CellType.STRING) {
                                                datoExcel2 = columna3.getStringCellValue();
                                            } else if (columna3.getCellType() == CellType.NUMERIC) {
                                                datoExcel2 = String.valueOf(columna3.getNumericCellValue());
                                            }
                                            if (!datoExcel1.isEmpty() && !datoExcel2.isEmpty()) {
                                                System.out.println("La informacion 2.1 es: " + datoExcel1);
                                                System.out.println("La informacion 2.2 es: " + datoExcel2);
                                            }
                                        }
                                    }
                                }
                                datoTiempoObj.setF2(datoTiempoObj.getF2() + saltos);
                            }
                        }
                        VideoNombreObj.setF2(VideoNombreObj.getF2() + saltos);
                    }
                    //System.out.println("sale");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "accion cancelada");
        }

    }

    public static void main(String[] args) {
//        String FilCelSelec = "d16";
        //String nombreArchivo = "TODOS_resultado.xlsx";
        //String rutaArchivo = "D:\\file\\NetBeansProjects\\excel-ontologia\\archivos\\" + nombreArchivo;
        //String hoja = "Hoja1";
        //System.out.println("entra");
        SecuenciasExcel obj = new SecuenciasExcel();
        obj.IndividuoNombre(new UbicacionHoja(4, 1), new UbicacionHoja(7, 3));

    }
}

class UbicacionHoja {

    private String c1;
    private int f2;
    private int c2;

    /**
     * construtor del objeto, ubicacion de la celda en excel
     *
     * @param c1 es la columna
     * @param f2 es la fila
     */
    public UbicacionHoja(String c1, int f2) {
        this.c1 = c1;
        this.f2 = f2;
    }

    /**
     * construtor del objeto, ubicacion de la celda en excel
     *
     * @param f2 es la fila
     * @param c2 es la columna
     */
    public UbicacionHoja(int f2, int c2) {
        this.f2 = f2;
        this.c2 = c2;
    }

    /**
     * devuelve la columna
     *
     * @return la columna
     */
    public String getC1() {
        return c1;
    }

    /**
     * se establece la columna
     *
     * @param c1 es la columna
     */
    public void setC1(String c1) {
        this.c1 = c1;
    }

    /**
     * devuelve la fila
     *
     * @return retorna la fila
     */
    public int getF2() {
        return f2;
    }

    /**
     * se establece la fila
     *
     * @param f2 es la fila
     */
    public void setF2(int f2) {
        this.f2 = f2;
    }

    /**
     * devuelve la columna
     *
     * @return retorna la columna
     */
    public int getC2() {
        return c2;
    }

    /**
     * se establece la columna
     *
     * @param c2 es la columna
     */
    public void setC2(int c2) {
        this.c2 = c2;
    }

}
