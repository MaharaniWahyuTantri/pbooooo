package restosejahteraa;

import ClassObject.MenuMakanan;
import database.DBHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MenuCRUDController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<MenuMakanan, String> kolomNamaMenu;

    @FXML
    private TableColumn<MenuMakanan, Double> kolomHargaMenu;

    @FXML
    private TableColumn<MenuMakanan, Integer> kolomStockMenu;

    @FXML
    private TextField tfNamaMenu;

    @FXML
    private TextField tfHargaMenu;

    @FXML
    private TextField tfStockMenu;

    @FXML
    private TableView<MenuMakanan> tvMenu;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnInsert) {
            insertRecord();
        } else if (event.getSource() == btnUpdate) {
            updateRecord();
        } else if (event.getSource() == btnDelete) {
            deleteRecord();
        }
    }

    public ObservableList<MenuMakanan> getDataMenuMakanan() {
        ObservableList<MenuMakanan> makanan = FXCollections.observableArrayList();
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM `menumakanan`";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            MenuMakanan temp;
            while (rs.next()) {
                temp = new MenuMakanan(rs.getString("namaMenu"), rs.getDouble("hargaMenu"), rs.getInt("stockMenu"));
                makanan.add(temp);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return makanan;
    }

    public void showMakanan() {
        ObservableList<MenuMakanan> list = getDataMenuMakanan();

        kolomNamaMenu.setCellValueFactory(new PropertyValueFactory<>("namaMenu"));
        kolomHargaMenu.setCellValueFactory(new PropertyValueFactory<>("hargaMenu"));
        kolomStockMenu.setCellValueFactory(new PropertyValueFactory<>("stockMenu"));
        tvMenu.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showMakanan();

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        tvMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tfNamaMenu.setText(newSelection.getNamaMenu());
                tfHargaMenu.setText(String.valueOf(newSelection.getHargaMenu()));
                tfStockMenu.setText(String.valueOf(newSelection.getStockMenu()));

                btnInsert.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            } else {
                tfNamaMenu.clear();
                tfHargaMenu.clear();
                tfStockMenu.clear();

                btnInsert.setDisable(false);
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
            }
        });
    }

    private void insertRecord() {
    if (isEmptyTextField()) {
        showAlert("Peringatan", "Isian Tidak Lengkap", "Silakan isi semua kolom.");
        return;
    }

    String query = "INSERT INTO `menumakanan` (`namaMenu`, `hargaMenu`, `stockMenu`) VALUES (?, ?, ?)";

    try (Connection conn = DBHelper.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

        pstmt.setString(1, tfNamaMenu.getText());
        pstmt.setDouble(2, Double.parseDouble(tfHargaMenu.getText()));
        pstmt.setInt(3, Integer.parseInt(tfStockMenu.getText()));

        int affectedRows = pstmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Inserting data failed, no rows affected.");
        }

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int idMenu = generatedKeys.getInt(1);
                System.out.println("Generated ID: " + idMenu);
            } else {
                throw new SQLException("Inserting data failed, no ID obtained.");
            }
        }

        // Print the inserted values
        System.out.println("Inserted values: " +
                tfNamaMenu.getText() + ", " +
                Double.parseDouble(tfHargaMenu.getText()) + ", " +
                Integer.parseInt(tfStockMenu.getText()));

    } catch (SQLException ex) {
        ex.printStackTrace();
        showAlert("Error", "Insert Failed", "An error occurred while inserting data.");
    }

    showMakanan();

    tfNamaMenu.clear();
    tfHargaMenu.clear();
    tfStockMenu.clear();
}



    private void updateRecord() {
        MenuMakanan selectedMenuMakanan = tvMenu.getSelectionModel().getSelectedItem();

        if (selectedMenuMakanan != null) {
            if (tfNamaMenu.getText().isEmpty() || tfHargaMenu.getText().isEmpty() || tfStockMenu.getText().isEmpty()) {
                showAlert("Peringatan", "Isian Tidak Lengkap", "Silakan isi semua kolom.");
                return;
            }

            String query = "UPDATE  `menumakanan` SET hargaMenu = '" + tfHargaMenu.getText() + "', stockMenu = '" + tfStockMenu.getText() + "' WHERE namaMenu = '" + selectedMenuMakanan.getNamaMenu() + "'";
            update(query);
            showMakanan();

            tfNamaMenu.clear();
            tfHargaMenu.clear();
            tfStockMenu.clear();
            btnInsert.setDisable(false);
            btnUpdate.setDisable(true);
        } else {
            showAlert("Peringatan", "Pilih Baris", "Pilih baris yang ingin diupdate.");
        }
    }

    private void deleteRecord() {
        MenuMakanan selectedMenuMakanan = tvMenu.getSelectionModel().getSelectedItem();

        if (selectedMenuMakanan != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Hapus");
            alert.setHeaderText(null);
            alert.setContentText("Apakah Anda yakin ingin menghapus data ini?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String query = "DELETE FROM `menumakanan` WHERE namaMenu ='" + selectedMenuMakanan.getNamaMenu() + "'";
                    update(query);
                    showMakanan();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText(null);
            alert.setContentText("Pilih baris yang ingin dihapus.");
            alert.showAndWait();
        }
    }

    private void update(String query) {
        Connection conn = DBHelper.getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isEmptyTextField() {
        return tfNamaMenu.getText().isEmpty() || tfHargaMenu.getText().isEmpty() || tfStockMenu.getText().isEmpty();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}