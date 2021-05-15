

package Controller;

import Models.TableProductSells;
import Models.TableShowProductSells;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Sells implements Initializable {

    @FXML
    private TextField getAmount;
    @FXML
    private TextField search;
    @FXML
    private Button pay;
    @FXML
    private Button add;
    @FXML
    private Label total;
    @FXML
    private Button delete;

    @FXML
    private TableView<TableShowProductSells> tableShowProductSells;
    ObservableList<TableShowProductSells> observableShowProductSells = FXCollections.observableArrayList();
    @FXML
    private TableColumn<TableShowProductSells, String> col_name_show;
    @FXML
    private TableColumn<TableShowProductSells, Integer> col_id_show;
    @FXML
    private TableColumn<TableShowProductSells, String> col_function_show;
    @FXML
    private TableColumn<TableShowProductSells, Integer> col_priceSell_show;

    @FXML
    private TableView<TableProductSells> tableProductSells;
    ObservableList<TableProductSells> observableProductSells = FXCollections.observableArrayList();
    @FXML
    private TableColumn<TableProductSells, String> col_name_Sells;
    @FXML
    private TableColumn<TableProductSells, Integer> col_amount_Sells;
    @FXML
    private TableColumn<TableProductSells, Integer> col_coin_Sells;
    @FXML
    private TableColumn<TableProductSells, Integer> col_id_Sells;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TSP_add_database("");
        createProSells();
        col_name_show.setCellValueFactory(new PropertyValueFactory<TableShowProductSells, String>("ten"));
        col_id_show.setCellValueFactory(new PropertyValueFactory<TableShowProductSells, Integer>("MaSP"));
        col_function_show.setCellValueFactory(new PropertyValueFactory<TableShowProductSells, String>("chucNang"));
        col_priceSell_show.setCellValueFactory(new PropertyValueFactory<TableShowProductSells, Integer>("giaThanh"));

        col_name_Sells.setCellValueFactory(new PropertyValueFactory<TableProductSells, String>("ten"));
        col_id_Sells.setCellValueFactory(new PropertyValueFactory<TableProductSells, Integer>("id"));
        col_amount_Sells.setCellValueFactory(new PropertyValueFactory<TableProductSells, Integer>("soLuong"));
        col_coin_Sells.setCellValueFactory(new PropertyValueFactory<TableProductSells, Integer>("thanhTien"));

    }

    @FXML
    private Label pro_name;
    @FXML
    private Label pro_group;
    @FXML
    private TextArea pro_function;
    @FXML
    private Label pro_amount;
    @FXML
    private Label pro_expity;
    @FXML
    private Label pro_note;
    @FXML
    private Label pro_price_sell;
    private int pro_id;

    public void TSP_add_database(String str) {
        try (
                Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                        ConnectDatabase.username, ConnectDatabase.password);
                Statement st = cnn.createStatement();
        ) {
            String sql = "select * from product where groupSP like '%"+ str + "%' or name like '" + str + "%'" +
                    " or id like '" + str + "'" ;
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                String function = resultSet.getString("functionSP");
                int priceSell = resultSet.getInt("priceSell");
                observableShowProductSells.add(new TableShowProductSells(name, id, function, priceSell));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        tableShowProductSells.setItems(observableShowProductSells);
    }

    public void ActionSearch() {
        String string = search.getText();
        observableShowProductSells.removeAll(observableShowProductSells);
        TSP_add_database(string);
        createProSells();
    }

    public void createProSells()  {
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL, ConnectDatabase.username, ConnectDatabase.password);
            Statement st = cnn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        TableShowProductSells selectionProduct = new TableShowProductSells();
        if (tableShowProductSells.getSelectionModel().isEmpty()) {
            selectionProduct = tableShowProductSells.getItems().get(0);
        } else {
            selectionProduct = tableShowProductSells.getSelectionModel().getSelectedItem();
        }
        int id = selectionProduct.getMaSP();
        String sql = "select * from product where id = " + id;
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            PreparedStatement pst = cnn.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                pro_name.setText(resultSet.getString("name"));
                pro_group.setText(resultSet.getString("groupSP"));
                pro_amount.setText(String.valueOf(resultSet.getInt("amount")));
                pro_function.setText(resultSet.getString("functionSP"));
                pro_price_sell.setText(String.valueOf(resultSet.getInt("priceSell")));
                pro_expity.setText(String.valueOf(resultSet.getDate("expiry")));
                pro_id = resultSet.getInt("id");
                pro_note.setText(resultSet.getString("note"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableProductSells.setItems(observableProductSells);
    }

    public void ActionTotal() {
        int sum = 0;
        for(int i = 0; i < observableProductSells.size(); i++) {
            sum += observableProductSells.get(i).getThanhTien();
        }
        total.setText(String.valueOf(sum));
    }

    public void ActionAddAmount(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            String sql = "select * from product where id =" + pro_id;
            try {
                Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL, ConnectDatabase.username, ConnectDatabase.password);
                PreparedStatement pst = cnn.prepareStatement(sql);
                ResultSet resultSet = pst.executeQuery();
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int soLuong = 1;
                    if (!getAmount.getText().isEmpty())
                        soLuong = Integer.valueOf(getAmount.getText());
                    if (soLuong <= Integer.valueOf(pro_amount.getText())) {
                        int priceSell = resultSet.getInt("priceSell");
                        int ID = resultSet.getInt("id");
                        observableProductSells.add(new TableProductSells(name, soLuong, priceSell * soLuong, ID));
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Cảnh báo!");
                        alert.setHeaderText("Cảnh báo:");
                        alert.setContentText("Số lượng trong kho không đủ");
                        alert.showAndWait();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            search.setText("");
            getAmount.setText("");
            ActionTotal();
        }
    }

    public void ActionDelete() {
        TableProductSells productDelete = tableProductSells.getSelectionModel().getSelectedItem();
        observableProductSells.remove(productDelete);
        ActionTotal();
        tableProductSells.setItems(observableProductSells);
    }

    public void ActionEditAmountClick() {
        getAmount.setEditable(true);
        getAmount.getText();
    }

    public void ActionEditAmountKey(KeyEvent e) {
        if (e.getCode().equals(KeyCode.TAB)) {
            getAmount.getText();
        }
    }

    public void ActionAdd() {
        String sql = "select * from product where id =" + pro_id;
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            PreparedStatement pst = cnn.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int soLuong = 1;
                if (!getAmount.getText().isEmpty())
                    soLuong = Integer.valueOf(getAmount.getText());
                if (soLuong <= Integer.valueOf(pro_amount.getText())) {
                    int priceSell = resultSet.getInt("priceSell");
                    int ID = resultSet.getInt("id");
                    observableProductSells.add(new TableProductSells(name, soLuong, priceSell * soLuong, ID));
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Cảnh báo!");
                    alert.setHeaderText("Cảnh báo:");
                    alert.setContentText("Số lượng trong kho không đủ");
                    alert.showAndWait();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        search.setText("");
        getAmount.setText("");
        ActionTotal();
    }


    public void editNumber() {
        tableProductSells.setEditable(true);
        col_amount_Sells.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        col_amount_Sells.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableProductSells, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableProductSells, Integer> event) {
                TableProductSells productEdit = event.getRowValue();
                productEdit.setSoLuong(event.getNewValue());

                int ID = productEdit.getId();
                String sql = "select priceSell from product where id = " + ID;
                try {
                    Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                            ConnectDatabase.username, ConnectDatabase.password);
                    PreparedStatement pst = cnn.prepareStatement(sql);
                    ResultSet resultSet = pst.executeQuery();
                    while (resultSet.next()) {
                        int price  = resultSet.getInt(1);
                        productEdit.setThanhTien(event.getNewValue() * price);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                ActionTotal();
            }
        });
        tableProductSells.setItems(observableProductSells);
    }

    public void ActionPay() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Xác nhận thanh toán?");
        ButtonType buttonTypeYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Không", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeYes) {
            for(int i = 0; i < observableProductSells.size(); i++) {
                sub(observableProductSells.get(i).getId(), observableProductSells.get(i).getSoLuong());
                int price = observableProductSells.get(i).getThanhTien() / observableProductSells.get(i).getSoLuong();
                insertDB(observableProductSells.get(i).getId(), price, observableProductSells.get(i).getSoLuong());
            }
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText("Thanh toán thành công!");
            alert1.showAndWait();
            search.getText();
        }
    }

    public void sub(int id, int amount) {
        String sql = "update product set amount = amount - " + amount + " where id = " + id;
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            PreparedStatement pst = cnn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertDB(int id, int price, int amount) {
        String sql1 = "insert into productsell values (?, ?, ?)";
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            PreparedStatement pst1 = cnn.prepareStatement(sql1);
            pst1.setInt(1, id);
            pst1.setInt(2,amount);
            pst1.setDate(3, Date.valueOf(LocalDate.now().toString()));
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
