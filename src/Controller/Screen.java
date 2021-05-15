package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Screen {
    public void screenProduct(ActionEvent e) throws IOException {
        URL url = new File("src/UI/ShowProduct.fxml").toURI().toURL();
        Parent tableViewParent = FXMLLoader.load(url);
        // tableViewParent = FXMLLoader.load(getClass().getResource("src/Controller/Sells.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void screenImport(ActionEvent e) throws IOException {
        URL url = new File("src/UI/ImportPro.fxml").toURI().toURL();
        Parent tableViewParent = FXMLLoader.load(url);
        // tableViewParent = FXMLLoader.load(getClass().getResource("src/Controller/Sells.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }


    public void screenSell(ActionEvent e) throws IOException {
        URL url = new File("src/UI/Sell.fxml").toURI().toURL();
        Parent tableViewParent = FXMLLoader.load(url);
        // tableViewParent = FXMLLoader.load(getClass().getResource("src/Controller/Sells.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }


    public void screenSeting(ActionEvent e) {
        try {
            URL url = new File("src/UI/Setting1.fxml").toURI().toURL();
            Parent tableViewParent = null;
            tableViewParent = FXMLLoader.load(url);
            // tableViewParent = FXMLLoader.load(getClass().getResource("src/Controller/Sells.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    public void screenWarming(ActionEvent e) {
        try {
            URL url = new File("src/UI/Warming.fxml").toURI().toURL();
            Parent tableViewParent = null;
            tableViewParent = FXMLLoader.load(url);
            // tableViewParent = FXMLLoader.load(getClass().getResource("src/Controller/Sells.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    public void screenProfit(ActionEvent e) {
        try {
            URL url = new File("src/UI/Check.fxml").toURI().toURL();
            Parent tableViewParent = null;
            tableViewParent = FXMLLoader.load(url);
            // tableViewParent = FXMLLoader.load(getClass().getResource("src/Controller/Sells.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void screenHistory(ActionEvent e) {
        try {
            URL url = new File("src/UI/History.fxml").toURI().toURL();
            Parent tableViewParent = null;
            tableViewParent = FXMLLoader.load(url);
            // tableViewParent = FXMLLoader.load(getClass().getResource("src/Controller/Sells.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
