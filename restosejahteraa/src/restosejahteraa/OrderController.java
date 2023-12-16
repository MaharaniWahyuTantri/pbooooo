package restosejahteraa;

import ClassObject.Pesanan;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.SpinnerValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class OrderController {

    @FXML
    private TableView<Pesanan> tabelPesanan;

    @FXML
    private TableColumn<Pesanan, String> colItem;

    @FXML
    private TableColumn<Pesanan, Integer> colKuantitas;

    @FXML
    private TableColumn<Pesanan, Integer> colHarga;

    @FXML
    private Spinner<Integer> spinnerMakanan1;

    @FXML
    private Spinner<Integer> spinnerMakanan2;

    @FXML
    private Spinner<Integer> spinnerMakanan3;

    @FXML
    private Spinner<Integer> spinnerMakanan4;

    @FXML
    private Spinner<Integer> spinnerMakanan5;

    @FXML
    private TextField tfTotal;

    private ObservableList<Pesanan> pesananList = FXCollections.observableArrayList();

    private static String HOST = "127.0.0.1";
    private static int PORT = 3306;
    private static String DB_NAME = "restosejahtera";
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    @FXML
    private void initialize() {
        // Inisialisasi kolom tabel
        colItem.setCellValueFactory(new PropertyValueFactory<>("makanan"));
        colKuantitas.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));

        // Inisialisasi nilai awal dan batas spinner
        initializeSpinner(spinnerMakanan1);
        initializeSpinner(spinnerMakanan2);
        initializeSpinner(spinnerMakanan3);
        initializeSpinner(spinnerMakanan4);
        initializeSpinner(spinnerMakanan5);

        // Fetch and update names for Makanan 1 to 5 from the database
        updateFoodNames();
    }

    private void updateFoodNames() {
        for (int i = 1; i <= 5; i++) {
            String makanan = getNamaMakananDariDatabase("Makanan " + i);
            updateSpinnerPromptText("spinnerMakanan" + i, makanan);
        }
    }

    private String getNamaMakananDariDatabase(String makanan) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT namaMakanan FROM menumakanan WHERE namaMakanan = ?")) {

            preparedStatement.setString(1, makanan);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("namaMakanan");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return makanan;
    }
private void updateSpinnerPromptText(String spinnerName, String foodName) {
    try {
        // Menggunakan FXMLLoader untuk mendapatkan instance dari controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("path_to_your_fxml_file.fxml"));
        Parent root = fxmlLoader.load();
        OrderController controller = fxmlLoader.getController();

        // Mendapatkan spinner menggunakan controller
        Spinner<Integer> spinner = (Spinner<Integer>) controller.getClass().getDeclaredField(spinnerName).get(controller);

        // Mengatur prompt text
        spinner.getEditor().setText(foodName);
    } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
        e.printStackTrace();
    }
}


    private void initializeSpinner(Spinner<Integer> spinner) {
        // Set nilai awal dan batas untuk spinner
        int initialValue = 0;
        int minValue = 0;
        int maxValue = 10;
        int amountToStepBy = 1;

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue, maxValue, initialValue, amountToStepBy);

        spinner.setValueFactory(valueFactory);

        // Tambahkan listener untuk memperbarui tabel saat nilai spinner diubah
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> updateTable());
    }

    private void updateTable() {
        // Hapus semua item lama dari tabel
        pesananList.clear();

        // Tambahkan item baru ke dalam tabel untuk setiap spinner yang tidak 0
        addToTable("Makanan 1", spinnerMakanan1.getValue());
        addToTable("Makanan 2", spinnerMakanan2.getValue());
        addToTable("Makanan 3", spinnerMakanan3.getValue());
        addToTable("Makanan 4", spinnerMakanan4.getValue());
        addToTable("Makanan 5", spinnerMakanan5.getValue());

        // Hitung total harga
        hitungTotalHarga();
    }

    private void addToTable(String makanan, int jumlah) {
        if (jumlah != 0) {
            // Ambil harga dari database
            int harga = getHargaMakananDariDatabase(makanan);

            // Tambahkan item baru ke dalam tabel
            pesananList.add(new Pesanan(makanan, jumlah, harga));
        }
    }

    private int getHargaMakananDariDatabase(String makanan) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT hargaMenu FROM menumakanan WHERE namaMakanan = ?")) {

            preparedStatement.setString(1, makanan);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("hargaMenu");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void hitungTotalHarga() {
        int totalHarga = pesananList.stream().mapToInt(p -> p.getJumlah() * p.getHarga()).sum();
        tfTotal.setText(String.valueOf(totalHarga));
    }
    
    @FXML
    private void beliButtonClicked(ActionEvent event) {
        // Example: Show a confirmation alert when the "Beli" button is clicked
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm your order");
        alert.setContentText("Do you want to proceed with the order?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK, process the order

            // You can add the logic to handle the order here
            // For example, update a database, show a thank you message, etc.

            // For demonstration purposes, let's add the selected items to the table
            updateTable();
            
            // Clear the spinners and total after processing the order
            clearSpinners();
            clearTotal();
        }
    }

    // ... (existing code)

    private void clearSpinners() {
        // Set all spinners to 0
        spinnerMakanan1.getValueFactory().setValue(0);
        spinnerMakanan2.getValueFactory().setValue(0);
        spinnerMakanan3.getValueFactory().setValue(0);
        spinnerMakanan4.getValueFactory().setValue(0);
        spinnerMakanan5.getValueFactory().setValue(0);
    }

    private void clearTotal() {
        // Clear the total text field
        tfTotal.clear();
    }
}
