package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddNewProduct {
    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField group;

    @FXML
    private TextArea function;

    @FXML
    private TextField priceImport;

    @FXML
    private TextField priceSell;

    @FXML
    private TextField amount;

    @FXML
    private DatePicker expire;

    @FXML
    private TextField note;

    @FXML
    void ActionAdd() {
        if(id.getText().isEmpty() || name.getText().isEmpty() || group.getText().isEmpty()
              || function.getText().isEmpty() || amount.getText().isEmpty() || priceSell.getText().isEmpty()
              || priceImport.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo!");
            alert.setHeaderText("Cảnh báo:");
            alert.setContentText("Vui lòng nhập đủ các ô");
            alert.showAndWait();
            return;
        }
        String sql1 = "insert into product values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            PreparedStatement pst1 = cnn.prepareStatement(sql1);
            pst1.setInt(1, Integer.valueOf(id.getText()));
            pst1.setString(2, name.getText());
            pst1.setString(3, group.getText());
            pst1.setString(4, function.getText());
            pst1.setInt(5, Integer.valueOf(amount.getText()));
            pst1.setInt(6, Integer.valueOf(priceSell.getText()));
            pst1.setInt(7, Integer.valueOf(priceImport.getText()));
            pst1.setString(8, note.getText());
            pst1.setDate(9, Date.valueOf(expire.getValue()));
            pst1.setString(10, "");
            pst1.executeUpdate();
        } catch (Exception throwables) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Thông báo!");
            alert1.setHeaderText("Thông báo:");
            alert1.setContentText("Thêm sản phẩm không thành công");
            alert1.showAndWait();
            return;
        }
        insertProImport(Integer.valueOf(id.getText()), Integer.valueOf(amount.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo!");
        alert.setHeaderText("Thông báo:");
        alert.setContentText("Thêm sản phẩm thành công");
        alert.showAndWait();

        id.setText("");
        name.setText("");
        group.setText("");
        function.setText("");
        priceImport.setText("");
        priceSell.setText("");
        note.setText("");
        amount.setText("");
        expire.setValue(null);

    }
    public void insertProImport(int id, int amount) {
        String sql1 = "insert into productimport values (?, ?, ?)";
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            PreparedStatement pst1 = cnn.prepareStatement(sql1);
            pst1.setInt(1, id);
            pst1.setInt(2, amount);
            pst1.setDate(3, Date.valueOf(LocalDate.now().toString()));
            pst1.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
