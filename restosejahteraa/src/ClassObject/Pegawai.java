/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassObject;

/**
 *
 * @author LENOVO
 */
public class Pegawai extends Pengguna {
     private String jabatan;

    public Pegawai(String nama, String jabatan, String alamat, String email, String password) {
        super(nama, alamat, email, password);
        this.jabatan = jabatan;
    }

    
    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
    
 

    //METHOD UNTUK PEGAWAI
    public void kelolaMenu() {
        System.out.println("Pegawai dengan jabatan " + jabatan + " sedang mengelola menu.");

    }

    public void lihatDaftarPesanan() {
        System.out.println("Pegawai dengan jabatan " + jabatan + " sedang melihat daftar pesanan.");
        // Implementasi khusus untuk melihat daftar pesanan
    }

    public void konfirmasiPesanan() {
        System.out.println("Pegawai dengan jabatan " + jabatan + " sedang mengkonfirmasi pesanan.");
        // Implementasi khusus untuk mengkonfirmasi pesanan
    }


    public void notifikasiPesananMasuk() {
        System.out.println("Pegawai dengan jabatan " + jabatan + " mendapatkan notifikasi pesanan masuk.");
        // Implementasi khusus untuk memberikan notifikasi pesanan masuk
    }
}
