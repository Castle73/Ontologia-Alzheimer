/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.excel.ontologia;

/**
 * clase de ubucacion el lahoja excel
 * @author pc
 */
public class UbicacionHoja {

    /**variables**/
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
