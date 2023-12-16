package restosejahteraa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HOMEPAGEPEGAWAIController {

    @FXML
    private Button btnCurrentOrder;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnManageMenu;

    @FXML
    private Button btnRekapOrderan;

    @FXML
    private Label displayJabatan;

    @FXML
    private Label displayNama;

    @FXML
    void handleRekapOrderan(ActionEvent event) {
        // Panggil metode untuk berganti layar ke RekapOrderan.fxml
        changeScene("RekapOrderPegawai.fxml");
    }

    @FXML
    void handlebtnCurrentOrder(ActionEvent event) {
        // Panggil metode untuk berganti layar ke CurrentOrder.fxml
        changeScene("CurrentOrderPegawai.fxml");
    }

    @FXML
    void handlebtnHome(ActionEvent event) {
        // Panggil metode untuk berganti layar ke Home.fxml
        changeScene("HOMEPAGEPEGAWAI.fxml");
    }

    @FXML
    void handlebtnManageMenu(ActionEvent event) {
        // Panggil metode untuk berganti layar ke ManageMenu.fxml
        changeScene("MenuCRUD.fxml");
    }

    // Metode untuk berganti layar
    private void changeScene(String fxmlPath) {
        try {
            // Menggunakan FXMLLoader untuk memuat FXML dari file eksternal
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Mendapatkan stage dari tombol yang di klik atau stage utama jika menggunakan aplikasi JavaFX
            Stage stage = (Stage) btnCurrentOrder.getScene().getWindow();

            // Mengganti konten scene dengan halaman yang baru
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle kesalahan loading FXML
        }
    }
}
