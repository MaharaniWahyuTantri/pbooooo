/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassObject;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class  Pembayaran {
    private int id;
    private String metodePembayaran;
    private Date tanggal;
    private double jumlahPembayaran;
    private Pesanan pemesanan; // Referensi ke objek Pemesanan

    // Konstruktor
    public Pembayaran(int id, String metodePembayaran, Date tanggal, double jumlahPembayaran) {
        this.id = id;
        this.metodePembayaran = metodePembayaran;
        this.tanggal = tanggal;
        this.jumlahPembayaran = jumlahPembayaran;
        this.pemesanan = pemesanan;
    }

    // Metode untuk konfirmasi pembayaran
    public void konfirmasiPembayaran() {
        System.out.println("Pembayaran dengan ID " + id + " untuk Pemesanan ID " + pemesanan.getId() + " telah dikonfirmasi.");
    }

    // Getter dan setter
    public int getId() {
        return id;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public double getJumlahPembayaran() {
        return jumlahPembayaran;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public void setJumlahPembayaran(double jumlahPembayaran) {
        this.jumlahPembayaran = jumlahPembayaran;
    }
}