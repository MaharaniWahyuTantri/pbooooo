package ClassObject;

public class Pesanan {

    private String makanan;
    private int jumlah;
    private int harga;
    private int id;

    public Pesanan(String makanan, int jumlah, int harga) {
        this.makanan = makanan;
        this.jumlah = jumlah;
        this.harga = harga;
    }
    public int getId() {
        // Implementasi getId() sesuai kebutuhan
        return id;
    }
    public String getMakanan() {
        return makanan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getHarga() {
        return harga;
    }

    public void setMakanan(String makanan) {
        this.makanan = makanan;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
