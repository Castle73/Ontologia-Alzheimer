/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import org.mindswap.pellet.jena.PelletReasonerFactory;

/**
 *
 * @author pc
 */
public class leerontologia {

    private OntModel ontologyModelActual; //this is the main of Ontology model
    private OntModel ontologyModelNuevo; //this is the main of Ontology model
    private FileInputStream fileExcel;
    private FileInputStream fileOwl;

    public void cargar() {
        // muestra el cuadro de diálogo de archivos, para que el usuario pueda elegir el archivo a abrir
        JFileChooser selectorArchivos = new JFileChooser();
        selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        //indicamos la descripción del tipo de archivo que abriremos y la extensión del mismo.
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos OWL y RDF", "OWL", "RDF");
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
                try {
                    fileOwl = new FileInputStream(new File(rutaArchivo));
                    createOntologyModelFromFile(rutaArchivo);
                    crearArbolOntologiaActual();

                    JOptionPane.showMessageDialog(null, "ontologia cargada", "Carga archivo", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "accion cancelada");
        }
    }

    public void crearArbolOntologiaActual() {
        List<OntClass> ontologyModelActualLoc = ontologyModelActual.listHierarchyRootClasses().toList();
        if (ontologyModelActualLoc.size() > 0) {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
            crearArbolRecursivo(root, ontologyModelActualLoc);
        }
    }

    public void crearArbolRecursivo(DefaultMutableTreeNode node, List<OntClass> classOnto) {
        DefaultMutableTreeNode nuevoElem = null;
        for (OntClass val : classOnto) {
            if (!val.isComplementClass() && !val.isAnon()) {
                nuevoElem = new DefaultMutableTreeNode(val.getLocalName());
                node.add(nuevoElem);
                System.out.println(val.getLocalName());
            }
            //imprimimos el objeto pivote
            if (val.listSubClasses().toList().size() > 0) {
                crearArbolRecursivo(nuevoElem, val.listSubClasses().toList());
            }
        }
    }

    public void createOntologyModelFromFile(String owlFile) {

        InputStream in;
        // load the ontology with its imports and no reasoning
        in = FileManager.get().open(owlFile);
        /*if (in == null) {
            throw new IllegalArgumentException("Archivo no encontrado");
        }*/
        OntModel modelOnto = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        modelOnto.read(in, "", "RDF/XML");
        ontologyModelActual = modelOnto;

    }

    public static void main(String[] args) {
        leerontologia obj = new leerontologia();
        obj.cargar();
    }

}
