package restosejahteraa;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import database.DBHelper;
import ClassObject.Pelanggan; // Ganti dengan nama class Pelanggan yang sesuai

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
import javafx.event.ActionEvent;

public class SignUp_PelangganController {

    @FXML
    private Button btnLogInPelanggan;

    @FXML
    private Button btnSignUpPelanggan;

    @FXML
    private TextField fieldAlamat;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldNama;

    @FXML
    private TextField fieldPassword;

    @FXML
    void handleBtnLogInPelanggan(ActionEvent event) {

    }

    @FXML
    void handleBtnSignUpPelanggan(ActionEvent event) {
        String nama = fieldNama.getText();
        String alamat = fieldAlamat.getText();
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();

        // Buat objek Pelanggan
        Pelanggan pelanggan = new Pelanggan(nama, alamat, email, password);

        // Simpan pelanggan ke database
        try {
            simpanPelangganKeDatabase(pelanggan);
            // Pindah ke halaman HOMEPAGEPELANGGAN.fxml setelah menyimpan data
            pindahKeHalamanHomepagePelanggan();
        } catch (SQLException e) {
            // Menampilkan pesan kesalahan kepada pengguna
            showAlert("Error", "Gagal menyimpan data ke database. Pastikan semua kolom terisi.");
            e.printStackTrace();
        }
    }

   private void simpanPelangganKeDatabase(Pelanggan pelanggan) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet generatedKeys = null;

    try {
        connection = DBHelper.getConnection();

        String sql = "INSERT INTO pelanggan (idPelanggan, namaPelanggan, alamatPelanggan, emailPelanggan, passwordPelanggan) VALUES (DEFAULT, ?, ?, ?, ?)";

        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, pelanggan.getNama());
        preparedStatement.setString(2, pelanggan.getAlamat());
        preparedStatement.setString(3, pelanggan.getEmail());
        preparedStatement.setString(4, pelanggan.getPassword());

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows > 0) {
            // Retrieve the generated key (idPelanggan)
            generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                long idPelanggan = generatedKeys.getLong(1);
                System.out.println("Pelanggan berhasil disimpan dengan ID: " + idPelanggan);
                // You can store the generated ID in your Pelanggan object or use it as needed.
            }
        } else {
            System.out.println("Gagal menyimpan pelanggan.");
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





    private void pindahKeHalamanHomepagePelanggan() {
        try {
            Stage stage = (Stage) btnSignUpPelanggan.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("HOMEPAGEPELANGGAN.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Gagal memuat halaman HOMEPAGEPELANGGAN.");
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
    private void handleBtnLogInPelanggan() {
        try {
            // Mendapatkan stage dari tombol yang di klik atau stage utama jika menggunakan aplikasi JavaFX
            Stage stage = (Stage) btnLogInPelanggan.getScene().getWindow();

            // Menggunakan FXMLLoader untuk memuat FXML dari file eksternal (LogInPelanggan.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInPelanggan.fxml"));
            Parent root = loader.load();

            // Mengganti konten scene dengan halaman LogInPelanggan.fxml yang baru
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Gagal memuat halaman LogInPelanggan.");
            e.printStackTrace();
        }
    }
}
