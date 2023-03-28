/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import java.util.Map;
import java.util.Properties;

/**
 *
 * @author pc
 */
public class NewClass {
    public static void main(String[] args) {
        Properties lis = System.getProperties();
        for (Map.Entry<Object, Object> entry : lis.entrySet()) {
            Object key = entry.getKey();
            Object val = entry.getValue();
            System.out.println("key: "+key+" "+"val: "+val);
        }
    }
}
