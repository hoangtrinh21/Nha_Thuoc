package Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Profit implements Initializable {

    @FXML
    private TextField month;

    @FXML
    private TextField year;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis AxisX;

    @FXML
    private NumberAxis AxisY;

    @FXML
    private Text Total;

    @FXML
    private ChoiceBox<String> choiceBox;


    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String str1 = "Lợi nhuận theo tháng";
        String str2 = "Lợi nhuận theo quý";
        String str3 = "Lợi nhuận theo năm";


        ObservableList<String> str //
                = FXCollections.observableArrayList(str1, str2, str3);

        choiceBox.setItems(str);
        choiceBox.getSelectionModel().select(0);

        month.setText(String.valueOf(LocalDate.now().getMonthValue()));
        year.setText(String.valueOf(LocalDate.now().getYear()));

        getDataMonth();

        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1.equals("Lợi nhuận theo tháng")) {
                    month.setText(String.valueOf(LocalDate.now().getMonthValue()));
                    year.setText(String.valueOf(LocalDate.now().getYear()));
                    getDataMonth();
                }
                else if(t1.equals("Lợi nhuận theo quý")) {
                    month.setText("1");
                    year.setText(String.valueOf(LocalDate.now().getYear()));
                    getDataThreeMonth();
                }
                else {
                    month.setText(String.valueOf(LocalDate.now().getMonthValue()));
                    year.setText(String.valueOf(LocalDate.now().getYear()));
                    getDataYear();
                }
            }
        };
        // Sự kiện khi thay đổi Item trên ChoiceBox
        choiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);

        month.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(choiceBox.getValue().equals("Lợi nhuận theo tháng")) {
                    getDataMonth();
                }
                else if(choiceBox.getValue().equals("Lợi nhuận theo quý")) {
                    getDataThreeMonth();
                }
                else getDataYear();
            }
        });

        year.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(choiceBox.getValue().equals("Lợi nhuận theo tháng")) {
                    getDataMonth();
                }
                else if(choiceBox.getValue().equals("Lợi nhuận theo quý")) {
                    getDataThreeMonth();
                }
                else getDataYear();
            }
        });
    }

    public void getDataMonth() {
        month.setPromptText("Tháng");
        String monthDate = month.getText();
        String yearDate = year.getText();

        int[] array = {0, 0, 0, 0};


        try (
                Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                        ConnectDatabase.username, ConnectDatabase.password);
                Statement st = cnn.createStatement();
        ) {
            String sql = "select * \n" +
                    "from (select (ps.amountSell * p.priceSell) 'totalSell', (ps.amountSell * p.priceImport * -1) 'totalImport', ps.dateSell 'date'\n" +
                    "from product p, productsell ps\n" +
                    "where p.id = ps.idSell\n" +
                    "union\n" +
                    "select if(1=1, 0,0) 'totalSell', (amountCancel * price * -1) 'totalImport', dateCancel 'date'\n" +
                    "from productcancel) p\n" +
                    "where extract(MONTH from date) = " + monthDate + " and extract(YEAR from date) = " + yearDate;
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                int totalSell = resultSet.getInt(1);
                int totalImport = resultSet.getInt(2);
                Date date = resultSet.getDate(3);
                LocalDate localDate = date.toLocalDate();
                if(localDate.getDayOfMonth() >= 1 && localDate.getDayOfMonth() <= 7)
                    array[0] = array[0] + totalSell + totalImport;
                else if(localDate.getDayOfMonth() >= 8 && localDate.getDayOfMonth() <= 14)
                    array[1] = array[1] + totalSell + totalImport;
                else if(localDate.getDayOfMonth() >= 15 && localDate.getDayOfMonth() <= 21)
                    array[2] = array[2] + totalSell + totalImport;
                else array[3] = array[3] + totalSell + totalImport;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();

        series1.getData().add(new XYChart.Data<>("Tuần 1", array[0]/1000));
        series1.getData().add(new XYChart.Data<>("Tuần 2", array[1]/1000));
        series1.getData().add(new XYChart.Data<>("Tuần 3", array[2]/1000));
        series1.getData().add(new XYChart.Data<>("Tuần 4", array[3]/1000));

        int profit = 0;
        for(int i = 0; i < 4; i++) {
            profit += array[i];
        }
        Total.setText(String.valueOf(profit/1000));

        barChart.getData().removeAll(barChart.getData());

        barChart.getData().addAll(series1);
    }

    public void getDataThreeMonth() {
        month.setPromptText("Quý");
        String ThreeMonthDate = month.getText();
        String yearDate = year.getText();

        int[] array = {0, 0, 0};



        try (
                Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                        ConnectDatabase.username, ConnectDatabase.password);
                Statement st = cnn.createStatement();
        ) {
            String sql = "";
            if(ThreeMonthDate.equals("1")) sql = "select * \n" +
                    "from (select (ps.amountSell * p.priceSell) 'totalSell', (ps.amountSell * p.priceImport * -1) 'totalImport', ps.dateSell 'date'\n" +
                    "from product p, productsell ps\n" +
                    "where p.id = ps.idSell\n" +
                    "union\n" +
                    "select if(1=1, 0,0) 'totalSell', (amountCancel * priceImport * -1) 'totalImport', expirydate 'date'\n" +
                    "from productcancel) p\n" +
                    "where extract(MONTH from date) in (1,2,3) and extract(YEAR from date) = " + yearDate;
            else if(ThreeMonthDate.equals("2")) sql = "select * \n" +
                    "from (select (ps.amountSell * p.priceSell) 'totalSell', (ps.amountSell * p.priceImport * -1) 'totalImport', ps.dateSell 'date'\n" +
                    "from product p, productsell ps\n" +
                    "where p.id = ps.idSell\n" +
                    "union\n" +
                    "select if(1=1, 0,0) 'totalSell', (amountCancel * priceImport * -1) 'totalImport', expirydate 'date'\n" +
                    "from productcancel) p\n" +
                    "where extract(MONTH from date) in (4,5,6) and extract(YEAR from date) = " + yearDate;
            else if(ThreeMonthDate.equals("3")) sql = "select * \n" +
                    "from (select (ps.amountSell * p.priceSell) 'totalSell', (ps.amountSell * p.priceImport * -1) 'totalImport', ps.dateSell 'date'\n" +
                    "from product p, productsell ps\n" +
                    "where p.id = ps.idSell\n" +
                    "union\n" +
                    "select if(1=1, 0,0) 'totalSell', (amountCancel * priceImport * -1) 'totalImport', expirydate 'date'\n" +
                    "from productcancel) p\n" +
                    "where extract(MONTH from date) in (7,8,9) and extract(YEAR from date) = " + yearDate;
            else {
                if(!month.getText().equals("4")) month.setText("4");
                sql = "select * \n" +
                        "from (select (ps.amountSell * p.priceSell) 'totalSell', (ps.amountSell * p.priceImport * -1) 'totalImport', ps.dateSell 'date'\n" +
                        "from product p, productsell ps\n" +
                        "where p.id = ps.idSell\n" +
                        "union\n" +
                        "select if(1=1, 0,0) 'totalSell', (amountCancel * priceImport * -1) 'totalImport', expirydate 'date'\n" +
                        "from productcancel) p\n" +
                        "where extract(MONTH from date) in (10,11,12) and extract(YEAR from date) = 2021";
            }

            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                int totalSell = resultSet.getInt(1);
                int totalImport = resultSet.getInt(2);
                Date date = resultSet.getDate(3);
                LocalDate localDate = date.toLocalDate();
                if(localDate.getMonthValue() == 1 || localDate.getMonthValue() == 4 ||
                        localDate.getMonthValue() == 7 || localDate.getMonthValue() == 10) {
                    array[0] = array[0] + totalSell + totalImport;
                }
                else if(localDate.getMonthValue() == 2 || localDate.getMonthValue() == 5 ||
                        localDate.getMonthValue() == 8 || localDate.getMonthValue() == 11) {
                    array[1] = array[1] + totalSell + totalImport;
                }
                else array[2] = array[2] + totalSell + totalImport;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();

        int one = 1 + 3 * (Integer.parseInt(ThreeMonthDate) - 1);
        int two = 2 + 3 * (Integer.parseInt(ThreeMonthDate) - 1);
        int three = 3 + 3 * (Integer.parseInt(ThreeMonthDate) - 1);

        series1.getData().add(new XYChart.Data<>("Tháng " + String.valueOf(one), array[0]/1000));
        series1.getData().add(new XYChart.Data<>("Tháng " + String.valueOf(two), array[1]/1000));
        series1.getData().add(new XYChart.Data<>("Tháng " + String.valueOf(three), array[2]/1000));


        int profit = 0;
        for(int i = 0; i < 3; i++) {
            profit += array[i];
        }
        Total.setText(String.valueOf(profit/1000));

        barChart.getData().removeAll(barChart.getData());

        barChart.getData().addAll(series1);
    }

    public void getDataYear() {
        String yearDate = year.getText();

        int[] array = {0, 0, 0, 0};


        try (
                Connection cnn = ConnectDatabase.getConnection(ConnectDatabase.jdbcURL,
                        ConnectDatabase.username, ConnectDatabase.password);
                Statement st = cnn.createStatement();
        ) {
            String sql = "select *\n" +
                    "from (select (ps.amountSell * p.priceSell) 'totalSell', (ps.amountSell * p.priceImport * -1) 'totalImport', ps.dateSell 'date'\n" +
                    "from product p, productsell ps\n" +
                    "where p.id = ps.idSell\n" +
                    "union\n" +
                    "select if(1=1, 0,0) 'totalSell', (amountCancel * priceImport * -1) 'totalImport', expirydate 'date'\n" +
                    "from productcancel) p\n" +
                    "where extract(YEAR from date) = " + yearDate;
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                int totalSell = resultSet.getInt(1);
                int totalImport = resultSet.getInt(2);
                Date date = resultSet.getDate(3);
                LocalDate localDate = date.toLocalDate();
                if(localDate.getMonthValue() == 1 || localDate.getMonthValue() == 2 || localDate.getMonthValue() == 3)
                    array[0] = array[0] + totalSell + totalImport;
                else if(localDate.getMonthValue() == 4 || localDate.getMonthValue() == 5 || localDate.getMonthValue() == 6)
                    array[1] = array[1] + totalSell + totalImport;
                else if(localDate.getMonthValue() == 7 || localDate.getMonthValue() == 8 || localDate.getMonthValue() == 9)
                    array[2] = array[2] + totalSell + totalImport;
                else array[3] = array[3] + totalSell + totalImport;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();

        series1.getData().add(new XYChart.Data<>("Quý 1", array[0]/1000));
        series1.getData().add(new XYChart.Data<>("Quý 2", array[1]/1000));
        series1.getData().add(new XYChart.Data<>("Quý 3", array[2]/1000));
        series1.getData().add(new XYChart.Data<>("Quý 4", array[3]/1000));

        int profit = 0;
        for(int i = 0; i < 4 ;i++) profit += array[i];
        Total.setText(String.valueOf(profit/1000));

        barChart.getData().removeAll(barChart.getData());

        barChart.getData().addAll(series1);
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

}

