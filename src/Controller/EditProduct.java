package Controller;

import Entities.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class EditProduct {

    @FXML
    private Button agree;

    @FXML
    private Text idProduct;

    @FXML
    private TextField nameProduct;

    @FXML
    private TextField groupProduct;

    @FXML
    private TextArea functionProduct;

    @FXML
    private Text amountProduct;

    @FXML
    private TextField priceSellProduct;

    @FXML
    private TextField priceImportProduct;

    @FXML
    private TextArea noteProduct;

    @FXML
    private TextField expiryProduct;

    @FXML
    private TextField imageProduct;

    public void setDataProduct(Product product) {
        idProduct.setText(String.valueOf(product.getID()));
        nameProduct.setText(product.getName());
        groupProduct.setText(product.getGroup());
        functionProduct.setText(product.getFunction());
        expiryProduct.setText(product.getExpiryDate().toString());
        amountProduct.setText(String.valueOf(product.getAmount()));
        priceSellProduct.setText(String.valueOf(product.getPriceSell()));
        priceImportProduct.setText(String.valueOf(product.getPriceImport()));
        noteProduct.setText(product.getNote());
        imageProduct.setText(product.getImg());
    }

    public void ActionAgree(ActionEvent e) {

        try (
                Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                        ConnectDatabase.username, ConnectDatabase.password);
                Statement st = cnn.createStatement();
        ) {
            String sql = "update product\n" +
                    "set name = '" + nameProduct.getText() + "', product.groupSP = '"+ groupProduct.getText() + "', " +
                    "product.functionSP = '" + functionProduct.getText() + "', amount = " + amountProduct.getText() + ", \n" +
                    "priceSell = " + priceSellProduct.getText() + ", priceImport = " + priceImportProduct.getText() + "," +
                    " note = '" + noteProduct.getText() + "', expiry = '" + expiryProduct.getText() + "', " +
                    "img = '" + imageProduct.getText() + "'\n" +
                    "where id = " + idProduct.getText();

            int rowCount = st.executeUpdate(sql);
            if (rowCount == 1) System.out.println("finished");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public void ActionCancel(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
