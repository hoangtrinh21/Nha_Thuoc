package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Delete {

    @FXML
    private Text ID;

    @FXML
    private Text amount;

    @FXML
    private Text priceImport;

    @FXML
    void ActionCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void ActionRemove(ActionEvent event) {
        Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                ConnectDatabase.username, ConnectDatabase.password);
        try {
            Statement st = cnn.createStatement();
            LocalDate localDate = LocalDate.now();
            String sql1 = "insert productcancel(idCancel, amountCancel, dateCancel, price)\n" +
                    "values('" + ID.getText() + "', '" + amount.getText() +"'" +
                    ", '" + localDate.toString() + "', '" + priceImport.getText() + "')";
            st.executeUpdate(sql1);

            String sql = "delete from product\n" +
                    "where id = " + ID.getText();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setData(String _id, String _amount, String _price) {
        ID.setText(_id);
        amount.setText(_amount);
        priceImport.setText(_price);
    }

}
