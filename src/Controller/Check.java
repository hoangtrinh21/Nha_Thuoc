package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Check {
    @FXML
    private TextField Password;

    public void Back(ActionEvent event){
        try {
            URL url = new File("src/UI/Screen.fxml").toURI().toURL();
            Parent tableViewParent = null;
            tableViewParent = FXMLLoader.load(url);
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void CheckAcount(ActionEvent event){
        try{
            String password = "";
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            Statement st = cnn.createStatement();
            String sql = "select * from user";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()){
                password = resultSet.getString(1);
            }
            CheckPassword(event, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void CheckPassword(ActionEvent event, String password){
        if(Password.getText().equals(password)){
            try{
                URL url = new File("src/UI/Profit.fxml").toURI().toURL();
                Parent tableViewParent = FXMLLoader.load(url);
                Scene tableViewScene = new Scene(tableViewParent);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.show();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Đăng Nhập");
            alert.setHeaderText("Mật khẩu sai");
            alert.setContentText("Vui lòng tạo tài khoản mới");
            alert.showAndWait();
        }
    }

}
