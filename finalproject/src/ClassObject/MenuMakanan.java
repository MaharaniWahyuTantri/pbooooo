/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassObject;

/**
 *
 * @author LENOVO
 */

public class MenuMakanan {

    private String namaMenu;
    private double hargaMenu;
    private int stockMenu;

    public MenuMakanan(String namaMenu, double hargaMenu, int stockMenu) {
        this.namaMenu = namaMenu;
        this.hargaMenu = hargaMenu;
        this.stockMenu = stockMenu;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public double getHargaMenu() {
        return hargaMenu;
    }

    public void setHargaMenu(double hargaMenu) {
        this.hargaMenu = hargaMenu;
    }

    public int getStockMenu() {
        return stockMenu;
    }

    public void setStockMenu(int stockMenu) {
        this.stockMenu = stockMenu;
    }

    @Override
    public String toString() {
        return "MenuMakanan{" +
                "namaMenu='" + namaMenu + '\'' +
                ", hargaMenu=" + hargaMenu +
                ", stockMenu=" + stockMenu +
                '}';
    }
}
