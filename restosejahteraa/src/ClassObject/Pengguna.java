package ClassObject;

public abstract class Pengguna {
    private String nama;
    private String alamat;
    private String email;
    private String password;

    public Pengguna(String nama, String alamat, String email, String password) {
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
        this.password = password;
    }

    // Getter dan setter untuk atribut nama, alamat, email, dan password

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Metode lain sesuai kebutuhan
}
