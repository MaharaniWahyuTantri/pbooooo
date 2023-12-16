package finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class HOMEPAGEController {

    

    @FXML
    private Button btnSignUpPegawai;

    @FXML
    private Button btnSignUpPelanggan;


    // Metode untuk pindah ke halaman lain
    @FXML
    private void changePage(ActionEvent event, String fxmlPath) {
        try {
            // Mendapatkan stage utama
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Menggunakan FXMLLoader untuk memuat FXML dari file eksternal
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Mengganti konten scene dengan halaman yang baru
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle kesalahan loading FXML
        }
    }

    

    @FXML
    private void handleBtnSignUpPegawai(ActionEvent event) {
        changePage(event, "SignUp_Pegawai.fxml");
    }

    @FXML
    private void handleBtnSignUpPelanggan(ActionEvent event) {
        changePage(event, "SignInPelanggan.fxml");
    }

}
