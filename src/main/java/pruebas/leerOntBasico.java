/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import org.mindswap.pellet.jena.PelletReasonerFactory;

/**
 *
 * @author pc
 */
public class leerOntBasico {
    
    private static void icuHack() {
    String javaVersion = System.getProperty("java.version");
    int idxOfUnderscore = javaVersion.indexOf('_');
    if( idxOfUnderscore == -1 ) {
        return;
    }
    int patchVersion = Integer.parseInt(javaVersion.substring(idxOfUnderscore+1));
    if( patchVersion < 256 ) {
        return;
    }
//    log.info("Java version '"+javaVersion+"' contains patch version >255, need to do ICU hack.");
    System.setProperty("java.version", "1.8.0_254");
    new com.ibm.icu.impl.ICUDebug();
    System.setProperty("java.version", javaVersion);
}

    public static void main(String[] args) {
//        icuHack();
        OntModel model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
//        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        String fileName = "D:\\file\\NetBeansProjects\\excel-ontologia\\archivos\\Alzheimer2.2.owl";

        try {
            InputStream inputStream = new FileInputStream(fileName);
            model.read(inputStream,"", "RDF/XML");
//            inputStream.close();
        } catch (Exception e) {
            System.out.println("error es : "+e.getMessage());
        }
        ExtendedIterator<OntClass> itI = model.listClasses();

        while (itI.hasNext()) {
            OntClass i = itI.next();
            System.out.println(i.getLocalName());
        }
        
        for (Map.Entry<String, String> en : model.getNsPrefixMap().entrySet()) {
            String key = en.getKey();
            String val = en.getValue();
            System.out.println("key: "+key+" "+"val: "+val);
            
        }
        model.listHierarchyRootClasses();
        
    }
}
