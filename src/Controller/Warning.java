package Controller;

import Models.TableWarningExpired;
import Models.TableWarningExpiry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Warning implements Initializable {

    @FXML
    private TableView<TableWarningExpiry> tableWarningExpiry;
    ObservableList<TableWarningExpiry> observableWarningExpiry = FXCollections.observableArrayList();
    @FXML
    private TableColumn<TableWarningExpiry, Integer> id2;

    @FXML
    private TableColumn<TableWarningExpiry, String> name2;

    @FXML
    private TableColumn<TableWarningExpiry, Integer> price2;

    @FXML
    private TableColumn<TableWarningExpiry, Integer> amount2;

    @FXML
    private TableColumn<TableWarningExpiry, Date> expiry2;

    @FXML
    private Button xoa2;

    @FXML
    private TableView<TableWarningExpired> tableWarningExpired;
    ObservableList<TableWarningExpired> observableWarningExpired = FXCollections.observableArrayList();
    @FXML
    private TableColumn<TableWarningExpired, Integer> id1;

    @FXML
    private TableColumn<TableWarningExpired, String> name1;

    @FXML
    private TableColumn<TableWarningExpired, Integer> price1;

    @FXML
    private TableColumn<TableWarningExpired, Integer> amount1;

    @FXML
    private TableColumn<TableWarningExpired, Date> expiry1;

    @FXML
    private Button xoa;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createCollum();
        addExpired();
        addExpiry();
    }

    public void createCollum() {
        id1.setCellValueFactory(new PropertyValueFactory<TableWarningExpired, Integer>("id"));
        name1.setCellValueFactory(new PropertyValueFactory<TableWarningExpired, String>("name"));
        amount1.setCellValueFactory(new PropertyValueFactory<TableWarningExpired, Integer>("amount"));
        price1.setCellValueFactory(new PropertyValueFactory<TableWarningExpired, Integer>("price"));
        expiry1.setCellValueFactory(new PropertyValueFactory<TableWarningExpired, Date>("expiry"));

        id2.setCellValueFactory(new PropertyValueFactory<TableWarningExpiry, Integer>("id"));
        name2.setCellValueFactory(new PropertyValueFactory<TableWarningExpiry, String>("name"));
        amount2.setCellValueFactory(new PropertyValueFactory<TableWarningExpiry, Integer>("amount"));
        price2.setCellValueFactory(new PropertyValueFactory<TableWarningExpiry, Integer>("price"));
        expiry2.setCellValueFactory(new PropertyValueFactory<TableWarningExpiry, Date>("expiry"));
    }

    public void addExpired() {
        Date now = Date.valueOf(LocalDate.now().toString());
        String sql = "select * from product where expiry < '" + now.toString() + "'";
        Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                ConnectDatabase.username, ConnectDatabase.password);
        try {
            Statement st = cnn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement pst = cnn.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int amount = resultSet.getInt("amount");
                int price = resultSet.getInt("priceImport");
                Date expiry = resultSet.getDate("expiry");
                observableWarningExpired.add(new TableWarningExpired(id, name, amount, price, expiry));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableWarningExpired.setItems(observableWarningExpired);
    }

    public void addExpiry() {
        Date now = Date.valueOf(LocalDate.now().toString());
        String sql = "select p.* from product p where p.expiry In (SELECT expiry from product where expiry between adddate(now(), interval -1 day) and adddate(now(), interval 30 day))";
        Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                ConnectDatabase.username, ConnectDatabase.password);
        try {
            Statement st = cnn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement pst = cnn.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int amount = resultSet.getInt("amount");
                int price = resultSet.getInt("priceImport");
                Date expiry = resultSet.getDate("expiry");
                observableWarningExpiry.add(new TableWarningExpiry(id, name, amount, price, expiry));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableWarningExpiry.setItems(observableWarningExpiry);
    }

    public void ActionDelete1() {
        if (tableWarningExpired.getSelectionModel().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setContentText("Chưa có sản phẩm nào được chọn!");
            al.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Bạn muốn xóa sản phẩm này?");
        ButtonType buttonTypeYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Không", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            TableWarningExpired productDelete = tableWarningExpired.getSelectionModel().getSelectedItem();
            int id = productDelete.getId();
            int price = 0;
            int amount = 0;
            String sqlDel = "delete from product where id = '" + id + "'";
            String sqlSel = "select * from product where id = " + id;
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            try {
                Statement st = cnn.createStatement();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                PreparedStatement pstSel = cnn.prepareStatement(sqlSel);
                ResultSet resultSet = pstSel.executeQuery();
                while (resultSet.next()) {
                    price = resultSet.getInt("priceImport");
                    amount = resultSet.getInt("amount");
                }
                PreparedStatement pstDel = cnn.prepareStatement(sqlDel);
                pstDel.executeUpdate();
                insertDB(id, amount, price);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            observableWarningExpired.removeAll(observableWarningExpired);
            tableWarningExpired.setItems(observableWarningExpired);
            addExpired();
        }
    }

    public void ActionDelete2() {
        if (tableWarningExpiry.getSelectionModel().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setContentText("Chưa có sản phẩm nào được chọn!");
            al.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Bạn muốn xóa sản phẩm này?");
        ButtonType buttonTypeYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Không", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            TableWarningExpiry productDelete = tableWarningExpiry.getSelectionModel().getSelectedItem();
            int id = productDelete.getId();
            int price = 0;
            int amount = 0;
            String sql = "delete from product where id = '" + id + "'";
            String sqlSel = "select * from product where id = " + id;
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            try {
                Statement st = cnn.createStatement();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                PreparedStatement pstSel = cnn.prepareStatement(sqlSel);
                ResultSet resultSet = pstSel.executeQuery();
                while (resultSet.next()) {
                    price = resultSet.getInt("priceImport");
                    amount = resultSet.getInt("amount");
                }
                PreparedStatement pst = cnn.prepareStatement(sql);
                pst.executeUpdate();
                insertDB(id, amount, price);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            observableWarningExpiry.removeAll(observableWarningExpiry);
            tableWarningExpiry.setItems(observableWarningExpiry);
            addExpiry();
            return;
        } else return;
    }


    public void insertDB(int id, int price, int amount) {
        Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                ConnectDatabase.username, ConnectDatabase.password);
        try {
            Statement st = cnn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String sql1 = "insert into productcancel values (?, ?, ?, ?)";
        try {
            PreparedStatement pst1 = cnn.prepareStatement(sql1);
            pst1.setInt(1, id);
            pst1.setInt(2, amount);
            pst1.setInt(3, price);
            pst1.setDate(4, Date.valueOf(LocalDate.now().toString()));
            pst1.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void backHome(ActionEvent e) throws IOException {
        URL url = new File("src/UI/Screen.fxml").toURI().toURL();
        Parent tableViewParent = FXMLLoader.load(url);
        // tableViewParent = FXMLLoader.load(getClass().getResource("src/Controller/Sells.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
