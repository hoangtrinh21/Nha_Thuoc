package Controller;

import Entities.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class History implements Initializable {
    private Image image;
    private List<Product> productSells = new ArrayList<>();
    private List<Product> productImports = new ArrayList<>();
    private List<Product> productSellAndImports = new ArrayList<>();
    private List<Product> productCancel = new ArrayList<>();


    @FXML
    private DatePicker calendar;

    @FXML
    private Button historySell;

    @FXML
    private Button historyImport;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    public List<Product> getDataHistoryCancel() {
        List<Product> products = new ArrayList<>();
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            Statement st = cnn.createStatement();
            String sql = "select idCancel, amountCancel, dateCancel from productcancel";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int amount = resultSet.getInt(2);
                Date date = resultSet.getDate(3);
                Product product = new Product(id, "Sản phẩm đã hủy", "", "", amount,
                        0, 0, "cancel", date, "src/image/const.jpg");
                products.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    public List<Product> getDataHistorySell() {
        List<Product> products = new ArrayList<>();
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            Statement st = cnn.createStatement();
            String sql = "select p.img, p.id, p.name, ps.amountSell, ps.dateSell from product p, productsell ps where ps.idSell = p.id";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt(2);
                String name = resultSet.getString(3);
                int amount = resultSet.getInt(4);
                Date date = resultSet.getDate(5);
                String img = resultSet.getString(1);
                if (img == null || img.equals("")) {
                    img = "src/image/const.jpg";
                }
                Product product = new Product(id, name, "", "", amount,
                        0, 0, "sell", date, img);
                products.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    public List<Product> getDataHistoryImport() {
        List<Product> products = new ArrayList<>();
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            Statement st = cnn.createStatement();
            String sql = "select p.img, p.id, p.name, pi.amountImport, pi.dateImport from product p, productimport pi where pi.idImport = p.id;";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt(2);
                String name = resultSet.getString(3);
                int amount = resultSet.getInt(4);
                Date date = resultSet.getDate(5);
                String img = resultSet.getString(1);
                if (img == null || img.equals("")) {
                    img = "src/image/const.jpg";
                }
                Product product = new Product(id, name, "", "", amount,
                        0, 0, "import", date, img);
                products.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    public List<Product> getDataHistory() {
        List<Product> products = new ArrayList<>();
        try {
            Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                    ConnectDatabase.username, ConnectDatabase.password);
            Statement st = cnn.createStatement();
            String sql = "select p.img, p.id, p.name, psi.amount, psi.note, psi.date\n" +
                    "from product p, \n" +
                    "(select idSell id, amountSell amount, if(1=1, 'sell', '') note, dateSell date\n" +
                    "from productsell\n" +
                    "Union\n" +
                    "select idImport id, amountImport amount, if(1=1, 'import', '') note, dateImport date\n" +
                    "from productimport) as psi\n" +
                    "where p.id = psi.id\n" +
                    "Union\n" +
                    "select if(1=1, 'src/image/const.jpg', '') img, idCancel, if(1=1, 'Sản phẩm đã hủy', '') name, amountCancel, if(1=1, 'cancel', '') note, dateCancel \n" +
                    "from productcancel\n" +
                    "order by date DESC";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt(2);
                String name = resultSet.getString(3);
                int amount = resultSet.getInt(4);
                String note = resultSet.getString(5);
                Date date = resultSet.getDate(6);
                String img = resultSet.getString(1);
                if (img == null || img.equals("")) {
                    img = "src/image/const.jpg";
                }
                Product product = new Product(id, name, "", "", amount,
                        0, 0, note, date, img);
                products.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        show();
    }

    public void show() {
        productSellAndImports.removeAll(productSellAndImports);
        productSellAndImports.addAll(getDataHistory());
        draw();
    }


    public void draw() {
        grid.getChildren().removeAll(grid.getChildren());
        int column = 0;
        int row = 1;
        try{
            for(int i = 0; i < productSellAndImports.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL a = new File("src/UI/ProductHistory.fxml").toURI().toURL();
                fxmlLoader.setLocation(a);
                AnchorPane anchorPane = fxmlLoader.load();

                ProductHistoryController product = fxmlLoader.getController();
                if(productSellAndImports.get(i).getNote().equals("sell")) {
                    product.setDataProductSell(productSellAndImports.get(i));
                }
                else if(productSellAndImports.get(i).getNote().equals("import"))
                    product.setDataProductImport(productSellAndImports.get(i));
                else product.setDataProductCancel(productSellAndImports.get(i));

                grid.add(anchorPane, column , row ++); //(child, col, row)

                setting();
                GridPane.setMargin(anchorPane, new Insets(10));

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setting() {
        grid.setMinWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setMaxWidth(Region.USE_PREF_SIZE);

        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setMaxHeight(Region.USE_PREF_SIZE);
    }

    public void searchHistorySell(ActionEvent e) {
        grid.getChildren().removeAll(grid.getChildren());
        productSells.removeAll(productSells);
        productSells.addAll(getDataHistorySell());
        int column = 0;
        int row = 1;
        try{
            for(int i = 0; i < productSells.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL a = new File("src/UI/ProductHistory.fxml").toURI().toURL();
                fxmlLoader.setLocation(a);
                AnchorPane anchorPane = fxmlLoader.load();

                ProductHistoryController product = fxmlLoader.getController();
                product.setDataProductSell(productSells.get(i));

                grid.add(anchorPane, column , row ++); //(child, col, row)

                setting();

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void searchHistoryImport(ActionEvent event) {
        grid.getChildren().removeAll(grid.getChildren());
        productImports.removeAll(productImports);
        productImports.addAll(getDataHistoryImport());
        int column = 0;
        int row = 1;
        try{
            for(int i = 0; i < productImports.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL a = new File("src/UI/ProductHistory.fxml").toURI().toURL();
                fxmlLoader.setLocation(a);
                AnchorPane anchorPane = fxmlLoader.load();

                ProductHistoryController product = fxmlLoader.getController();
                product.setDataProductImport(productImports.get(i));

                grid.add(anchorPane, column , row ++); //(child, col, row)

               setting();

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void searchCalendar(ActionEvent e) {
        String str = calendar.getValue().toString();
        productSellAndImports.removeAll(productSellAndImports);
        productSellAndImports.addAll(getDataHistory());
        for(int i = 0; i < productSellAndImports.size(); i ++) {
            if(!str.equals(productSellAndImports.get(i).getExpiryDate().toString())) {
                productSellAndImports.remove(i);
                i--;
            }
        }
        draw();
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

    @FXML
    void searchHistory(ActionEvent event) {
        show();
    }

    @FXML
    void searchHistoryCancel(ActionEvent event) {
        grid.getChildren().removeAll(grid.getChildren());
        productCancel.removeAll(productCancel);
        productCancel.addAll(getDataHistoryCancel());
        int column = 0;
        int row = 0;
        try{
            for(int i = 0; i < productCancel.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL a = new File("src/UI/ProductHistory.fxml").toURI().toURL();
                fxmlLoader.setLocation(a);
                AnchorPane anchorPane = fxmlLoader.load();

                ProductHistoryController product = fxmlLoader.getController();
                product.setDataProductCancel(productCancel.get(i));

                grid.add(anchorPane, column , row ++); //(child, col, row)

                setting();

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
