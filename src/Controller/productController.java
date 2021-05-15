package Controller;

import Entities.Product;
import com.sun.javafx.scene.control.LambdaMultiplePropertyChangeListenerHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import myListener.MyListener;

import java.io.File;
import java.net.MalformedURLException;

public class productController {
    @FXML
    private Label nameProduct;

    @FXML
    private Label groupProduct;

    @FXML
    private Label idProduct;

    @FXML
    private Label priceSellProduct;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(product);
    }

    private Product product;
    private MyListener myListener;

    public void setData(Product product, MyListener myListener) {

        this.myListener = myListener;
        this.product = product;
        nameProduct.setText(product.getName());
        groupProduct.setText(product.getGroup());
        idProduct.setText(String.valueOf(product.getID()));
        priceSellProduct.setText(String.valueOf(product.getPriceSell()));

    }
}