package Controller;

import Models.TableShowProductSells;
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

public class ImportPro implements Initializable {
    @FXML
    private TextField search;
    @FXML
    private TextField getAmount;
    @FXML
    private Button del;
    @FXML
    private Button update;
    @FXML
    private Button add;

    @FXML
    private TextField pro_id;
    @FXML
    private TextField pro_group;
    @FXML
    private TextField pro_amount;
    @FXML
    private TextField pro_expiry;
    @FXML
    private TextField pro_note;

    @FXML
    private TableView<TableShowProductSells> tableShowProduct;
    ObservableList<TableShowProductSells> observableShowProduct = FXCollections.observableArrayList();
    @FXML
    private TableColumn<TableShowProductSells, String> col_name_show;
    @FXML
    private TableColumn<TableShowProductSells, Integer> col_id_show;
    @FXML
    private TableColumn<TableShowProductSells, String> col_function_show;
    @FXML
    private TableColumn<TableShowProductSells, Integer> col_priceSell_show;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TSP_add_database("");
        createPro();
        col_name_show.setCellValueFactory(new PropertyValueFactory<TableShowProductSells, String>("ten"));
        col_id_show.setCellValueFactory(new PropertyValueFactory<TableShowProductSells, Integer>("MaSP"));
        col_function_show.setCellValueFactory(new PropertyValueFactory<TableShowProductSells, String>("chucNang"));
        col_priceSell_show.setCellValueFactory(new PropertyValueFactory<TableShowProductSells, Integer>("giaThanh"));
    }

    public void TSP_add_database(String s) {
        observableShowProduct.removeAll(observableShowProduct);
        try (
                Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                        ConnectDatabase.username, ConnectDatabase.password);
                Statement st = cnn.createStatement();
        ) {
            String sql = "select * from product where id like  '%" + s + "%'";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                String function = resultSet.getString("functionSP");
                int priceSell = resultSet.getInt("priceSell");
                observableShowProduct.add(new TableShowProductSells(name, id, function, priceSell));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        tableShowProduct.setItems(observableShowProduct);
    }

    public void ActionSearch() {
        String string = search.getText();
        observableShowProduct.removeAll(observableShowProduct);
        TSP_add_database(string);
        createPro();
    }

    public void createPro()  {
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL, ConnectDatabase.username, ConnectDatabase.password);
            Statement st = cnn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        TableShowProductSells selectionProduct = new TableShowProductSells();
        if (tableShowProduct.getSelectionModel().isEmpty()) {
            selectionProduct = tableShowProduct.getItems().get(0);
        } else {
            selectionProduct = tableShowProduct.getSelectionModel().getSelectedItem();
        }
        int id = selectionProduct.getMaSP();
        String sql = "select * from product where id = " + id;
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            PreparedStatement pst = cnn.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                pro_id.setText(String.valueOf(resultSet.getInt("id")));
                pro_group.setText(resultSet.getString("groupSP"));
                pro_amount.setText(String.valueOf(resultSet.getInt("amount")));
                pro_note.setText(resultSet.getString("note"));
                pro_expiry.setText(String.valueOf(resultSet.getDate("expiry")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void ActionDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Bạn muốn xóa sản phẩm này?");
        ButtonType buttonTypeYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Không", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            TableShowProductSells productDelete = tableShowProduct.getSelectionModel().getSelectedItem();
            int id = productDelete.getMaSP();
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
                insertDBC(id, amount, price);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            observableShowProduct.removeAll(observableShowProduct);
            tableShowProduct.setItems(observableShowProduct);
            TSP_add_database("");
        }
    }

    public void insertDBC(int id, int price, int amount) {
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

    @FXML
    void ActionAddProduct() throws IOException {
        URL url = new File("src/UI/addNewProduct.fxml").toURI().toURL();
        Parent root1 = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("Xác nhận");
        stage.setScene(new Scene(root1));
        stage.show();
        getAmount.setText("");
        TSP_add_database("");
        createPro();
    }

    @FXML
    void ActionUpdate() {
        TableShowProductSells selectionProduct = new TableShowProductSells();
        if (tableShowProduct.getSelectionModel().isEmpty()) {
            selectionProduct = tableShowProduct.getItems().get(0);
        } else {
            selectionProduct = tableShowProduct.getSelectionModel().getSelectedItem();
        }
        int idSelect = col_id_show.getCellData(selectionProduct);
        if(getAmount.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi!");
            alert.setHeaderText("Lỗi:");
            alert.setContentText("Bạn chưa nhập số lượng cần thêm!");
            alert.showAndWait();
            return;
        }
        //newAmount.setDisable(false);
        int Value = Integer.valueOf(getAmount.getText());
        int sum = Value + Integer.valueOf(pro_amount.getText());

        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);

            String sql = "update product set amount= " +sum+ " where id = "+idSelect;

            PreparedStatement pst = cnn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for ( int i = 0; i<tableShowProduct.getItems().size(); i++) {
            tableShowProduct.getItems().clear();
        }
        TSP_add_database("");
        updateProImport(idSelect, Value);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo!");
        alert.setHeaderText("Thông báo:");
        alert.setContentText("Bạn đã cập nhật thành công!");
        alert.showAndWait();
        getAmount.setText("");
        TSP_add_database("");
        createPro();
    }

    public void updateProImport(int id, int amount) {
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
//            String sql = "update productimport set amountImport= amountImport+" +amount+ " where idImport = "+id;
            String sql = "insert into productimport values (?, ?, ?)";
            PreparedStatement pst = cnn.prepareStatement(sql);
            pst.setInt(1,id);
            pst.setInt(2,amount);
            pst.setDate(3, Date.valueOf(LocalDate.now().toString()));
            pst.executeUpdate();

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

    @FXML
    void ActonUpdateTable() {
        observableShowProduct.removeAll(observableShowProduct);
        TSP_add_database("");
        createPro();
    }
}
