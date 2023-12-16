package restosejahteraa;

import ClassObject.Pegawai;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import database.DBHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignUp_PegawaiController {

    @FXML
    private Button btnLogInPegawai;

    @FXML
    private Button btnSignUpPegawai;

    @FXML
    private TextField fieldNama;

    @FXML
    private TextField fieldAlamat;

    @FXML
    private TextField fieldEmail;
    
    @FXML
    private TextField fieldPassword;
    
    @FXML
    private TextField fieldJabatan;


    @FXML
    private void handleBtnSignUpPegawai() {
        String nama = fieldNama.getText();
        String alamat = fieldAlamat.getText();
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();
        String jabatan = fieldJabatan.getText();
        

        // Buat objek Pegawai
        Pegawai pegawai = new Pegawai(nama, alamat, email, password, jabatan);

        // Simpan pegawai ke database
        try {
            simpanPegawaiKeDatabase(pegawai);
            // Pindah ke halaman HOMEPAGE.fxml setelah menyimpan data
            pindahKeHalamanHomepage();
        } catch (SQLException e) {
            // Menampilkan pesan kesalahan kepada pengguna
            showAlert("Error", "Gagal menyimpan data ke database. Pastikan semua kolom terisi.");
            e.printStackTrace();
        }
    }

    private void simpanPegawaiKeDatabase(Pegawai pegawai) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet generatedKeys = null;
    
    try {
        connection = DBHelper.getConnection();

        String sql = "INSERT INTO pegawai (namaPegawai, alamatPegawai, email, password, jabatan) VALUES (?, ?, ?, ?, ?)";

        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, pegawai.getNama());
        preparedStatement.setString(3, pegawai.getAlamat());
        preparedStatement.setString(4, pegawai.getEmail());
        preparedStatement.setString(5, pegawai.getPassword());
        preparedStatement.setString(2, pegawai.getJabatan());

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows > 0) {
            // Retrieve the generated key (idPegawai)
            generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                long idPegawai = generatedKeys.getLong(1);
                System.out.println("Pegawai berhasil disimpan dengan ID: " + idPegawai);
                // You can store the generated ID in your Pegawai object or use it as needed.
            }
        } else {
            System.out.println("Gagal menyimpan pegawai.");
        }

    } finally {
        DBHelper.closeConnection(connection);
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (generatedKeys != null) {
            generatedKeys.close();
        }
    }
}


    private void pindahKeHalamanHomepage() {
        try {
            Stage stage = (Stage) btnSignUpPegawai.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("HOMEPAGEPEGAWAI.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Gagal memuat halaman HOMEPAGEPEGAWAI.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleBtnLogInPegawai() {
        try {
            // Mendapatkan stage dari tombol yang di klik atau stage utama jika menggunakan aplikasi JavaFX
            Stage stage = (Stage) btnLogInPegawai.getScene().getWindow();

            // Menggunakan FXMLLoader untuk memuat FXML dari file eksternal (LogInPegawai.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInPegawai.fxml"));
            Parent root = loader.load();

            // Mengganti konten scene dengan halaman LogInPegawai.fxml yang baru
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Gagal memuat halaman LogInPegawai.");
            e.printStackTrace();
        }
    }
}
