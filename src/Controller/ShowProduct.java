package Controller;

import Entities.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import myListener.MyListener;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowProduct implements Initializable {
    private List<Product> products = new ArrayList<>();
    private Image image;
    private MyListener myListener;

    @FXML
    private TextField textSearch;

    @FXML
    private VBox chooseItem;

    @FXML
    private TextArea nameItem;

    @FXML
    private Label idItem;

    @FXML
    private ImageView imageItem;

    @FXML
    private Label amountItem;

    @FXML
    private Label priceSellItem;

    @FXML
    private Label priceImportItem;

    @FXML
    private TextArea functionItem;

    @FXML
    private Label expiryDateItem;

    @FXML
    private TextArea noteItem;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    private List<Product> getData() {
        List<Product> products = new ArrayList<>();
        try (
                Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                        ConnectDatabase.username, ConnectDatabase.password);
                Statement st = cnn.createStatement();
        ) {
            String sql = "select * from product";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String group = resultSet.getString("groupSP");
                String function = resultSet.getString("functionSP");
                int amount = resultSet.getInt("amount");
                int priceSell = resultSet.getInt("priceSell");
                int priceImport = resultSet.getInt("priceImport");
                String note = resultSet.getString("note");
                Date date = resultSet.getDate("expiry");
                String img = resultSet.getString("img");
                if (img == null || img.equals("")) img = "src/image/const.jpg";
                Product product = new Product(id, name, group, function, amount,
                        priceImport, priceSell, note, date, img);
                products.add(product);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    private void setChooseItem(Product product) throws MalformedURLException {
        try{
            nameItem.setText(product.getName());
            idItem.setText(String.valueOf(product.getID()));
            amountItem.setText(String.valueOf(product.getAmount()));
            priceSellItem.setText(String.valueOf(product.getPriceSell()));
            priceImportItem.setText(String.valueOf(product.getPriceImport()));
            functionItem.setText(product.getFunction());
            noteItem.setText(product.getNote());
            expiryDateItem.setText(product.getExpiryDate().toString());
            File file = new File(product.getImg());
            image = new Image(file.toURI().toURL().toString());
            imageItem.setImage(image);
            imageItem.setFitWidth(200);
            imageItem.setFitHeight(140);
        }catch (MalformedURLException s) {
            s.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        products.addAll(getData());
        draw();
    }

    public void draw() {
        if(products.size() > 0) {
            try {
                setChooseItem(products.get(0));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product product) {
                    try {
                        setChooseItem(product);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            };
        }

        grid.getChildren().removeAll(grid.getChildren());
        int column = 0;
        int row = 1;
        try{
            for(int i = 0; i < products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL a = new File("src/UI/product.fxml").toURI().toURL();
                fxmlLoader.setLocation(a);
                AnchorPane anchorPane = fxmlLoader.load();

                productController product = fxmlLoader.getController();
                product.setData(products.get(i), myListener);

                if(column == 2) {
                    column = 0;
                    row ++;
                }

                grid.add(anchorPane, column++, row); //(child, col, row)

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateScoll() {
        products.removeAll(products);
        getDataSearch("");
    }

    public void ActionSearch() {
        String string = textSearch.getText();
        products.removeAll(products);
        getDataSearch(string);
//        createProSells();
    }

    public void getDataSearch(String str) {
        try (
                Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                        ConnectDatabase.username, ConnectDatabase.password);
                Statement st = cnn.createStatement();
        ) {
            String sql = "select * from product where groupSP like '%"+ str + "%' or name like '" + str + "%'" +
                    " or id like '" + str + "'" ;
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String group = resultSet.getString(3);
                String function = resultSet.getString(4);
                int amount = resultSet.getInt(5);
                int priceSell = resultSet.getInt(6);
                int priceImport = resultSet.getInt(7);
                String note = resultSet.getString(8);
                Date date = resultSet.getDate(9);
                String img = resultSet.getString(10);
                if(img == null || img.equals("")) img = "src/image/const.jpg"; //add file const.jpg vào image
                Product product = new Product(id, name, group, function, amount,
                        priceImport, priceSell, note, date, img);
                products.add(product);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        draw();
    }

    public void backHome(ActionEvent e) throws IOException {
        URL url = new File("src/UI/Screen.fxml").toURI().toURL();
        Parent tableViewParent = FXMLLoader.load(url);
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void editProduct(ActionEvent e) throws MalformedURLException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL a = new File("src/UI/Edit.fxml").toURI().toURL();
            fxmlLoader.setLocation(a);
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit");
            stage.show();

            String name = nameItem.getText();
            String expiry = expiryDateItem.getText();
            Product product = new Product();


            try (
                    Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                            ConnectDatabase.username, ConnectDatabase.password);
                    Statement st = cnn.createStatement();
            ) {
                String sql = "select id, expiry, img from product\n" +
                        "where name = '" + name + "' and expiry = '" + expiry + "'";
                ResultSet resultSet = st.executeQuery(sql);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Date date = resultSet.getDate("expiry");
                    String img = resultSet.getString("img");
                    product.setID(id);
                    product.setName(name);
                    product.setGroup(idItem.getText());
                    product.setFunction(functionItem.getText());
                    product.setAmount(Integer.parseInt(amountItem.getText()));
                    product.setPriceSell(Integer.parseInt(priceSellItem.getText()));
                    product.setPriceImport(Integer.parseInt(priceImportItem.getText()));
                    product.setNote(noteItem.getText());
                    product.setExpiryDate(date);
                    product.setImg(img);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            EditProduct controller = fxmlLoader.getController();
            controller.setDataProduct(product);
            textSearch.setText("");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void ActionDelete() throws MalformedURLException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL a = new File("src/UI/Delete.fxml").toURI().toURL();
            fxmlLoader.setLocation(a);
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Remove");
            stage.show();

            String name = nameItem.getText();
            String expiry = expiryDateItem.getText();
            String amount = amountItem.getText();
            String priceImport = priceImportItem.getText();

            Delete controller = fxmlLoader.getController();


            try (
                    Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                            ConnectDatabase.username, ConnectDatabase.password);
                    Statement st = cnn.createStatement();
            ) {
                String sql = "select id from product\n" +
                        "where name = '" + name + "' and expiry = '" + expiry + "'";
                ResultSet resultSet = st.executeQuery(sql);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    controller.setData(String.valueOf(id), amount, priceImport);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            updateScoll();
            textSearch.setText("");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
