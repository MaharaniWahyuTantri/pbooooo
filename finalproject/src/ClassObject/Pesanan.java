/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassObject;

/**
 *
 * @author LENOVO
 */
import java.util.Date;

public class Pesanan {
    private int id;
    private Date tanggal;
    private String status;

    // Konstruktor
    public Pesanan(int id, Date tanggal, String status) {
        this.id = id;
        this.tanggal = tanggal;
        this.status = status;
    }

    // Metode untuk menghitung total
    public double hitungTotal() {
        // Implementasi perhitungan total
        return 0.0;
    }

    // Getter dan setter
    public int getId() {
        return id;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
