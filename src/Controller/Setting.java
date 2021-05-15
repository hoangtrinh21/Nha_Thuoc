package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class Setting {

    @FXML
    private PasswordField current_password;

    @FXML
    private PasswordField new_password1;

    @FXML
    private PasswordField password2;

    public void confirm(ActionEvent event){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Xác nhận thay đổi?");
            ButtonType buttonTypeYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("Không", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get().equals(buttonTypeYes)) {
                String password = "";
                Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                        ConnectDatabase.username, ConnectDatabase.password);
                Statement st = cnn.createStatement();
                String sql = "select * from user";
                ResultSet resultSet = st.executeQuery(sql);
                while (resultSet.next()) {
                    password = resultSet.getString(1);
                }
                CheckPassword(event, password);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setHeaderText("Thay đổi thành công!");
                alert1.showAndWait();
                current_password.setText("");
                new_password1.setText("");
                password2.setText("");
            } else return;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void CheckPassword(ActionEvent event, String password){
        if(current_password.getText().equals(password)){
            CheckNewPassword(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Password");
            alert.setHeaderText("Nhập mật khẩu sai");
            alert.setContentText("Vui lòng nhập lại");
            alert.showAndWait();
        }
    }

    public void CheckNewPassword(ActionEvent event) {
        if(new_password1.getText().equals(password2.getText()) && new_password1.getText().length() > 7){
            try{
                String password = "";
                Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                        ConnectDatabase.username, ConnectDatabase.password);
                Statement st = cnn.createStatement();
                String sql = "update user set password = '" + new_password1.getText() + "'";
                st.executeUpdate(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("New Password");
            alert.setHeaderText("mật khẩu không trung nhau");
            alert.setContentText("Mật khẩu phải lớn hơn 8 kí tự " + '\n' + "Vui lòng nhập lại");
            alert.showAndWait();
        }
    }

    public void backHome(ActionEvent e){
        try {
            URL url = new File("src/UI/Screen.fxml").toURI().toURL();
            Parent tableViewParent = FXMLLoader.load(url);
            // tableViewParent = FXMLLoader.load(getClass().getResource("src/Controller/Sells.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void backSetting(ActionEvent e) throws IOException {
        URL url = new File("src/UI/Setting1.fxml").toURI().toURL();
        Parent tableViewParent = FXMLLoader.load(url);
        // tableViewParent = FXMLLoader.load(getClass().getResource("src/Controller/Sells.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void ScreenSetting2(ActionEvent e){
        try {
            URL url = new File("src/UI/Setting2.fxml").toURI().toURL();
            Parent tableViewParent = FXMLLoader.load(url);
            // tableViewParent = FXMLLoader.load(getClass().getResource("src/Controller/Sells.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
