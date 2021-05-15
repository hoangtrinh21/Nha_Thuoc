package Controller;

import Entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.MalformedURLException;

public class ProductHistoryController {

    public void setDataProductCancel(Product product) throws MalformedURLException {
        try{
            this.product = product;
            File file = new File(product.getImg());
            Image image = new Image(file.toURI().toURL().toString());
            img.setImage(image);
            id.setText(String.valueOf(product.getID()));
            name.setText(product.getName());
            amount.setText(String.valueOf(product.getAmount()));
            amount.setTextFill(Color.web("#7e8990"));
            status.setFill(Color.web("#7e8990"));
            date.setText(product.getExpiryDate().toString());
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private ImageView img;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private Label amount;

    @FXML
    private Rectangle status;

    private Product product;


    public void setDataProductSell(Product product) throws MalformedURLException {
        try{
            this.product = product;
            File file = new File(product.getImg());
            Image image = new Image(file.toURI().toURL().toString());
            img.setImage(image);
            id.setText(String.valueOf(product.getID()));
            name.setText(product.getName());
            amount.setText(String.valueOf(product.getAmount()));
            amount.setTextFill(Color.web("#13ab55"));
            status.setFill(Color.web("#13ab55"));
            date.setText(product.getExpiryDate().toString());
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setDataProductImport(Product product) throws MalformedURLException {
        try{
            this.product = product;
            File file = new File(product.getImg());
            Image image = new Image(file.toURI().toURL().toString());
            img.setImage(image);
            id.setText(String.valueOf(product.getID()));
            name.setText(product.getName());
            amount.setText(String.valueOf(product.getAmount()));
            amount.setTextFill(Color.RED);
            status.setFill(Color.RED);
            date.setText(product.getExpiryDate().toString());
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
