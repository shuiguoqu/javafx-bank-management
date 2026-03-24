package dashboard;

import dao.StatisticsDAO;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.BankStatistics;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private BorderPane mainContainer;

    @FXML
    private VBox navMenu;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnAccountManagement;

    @FXML
    private HBox cardsContainer;

    @FXML
    private VBox cardTotalAccounts;

    @FXML
    private Label lblTotalAccounts;

    @FXML
    private Label lblTotalAccountsValue;

    @FXML
    private VBox cardBankCount;

    @FXML
    private Label lblBankCount;

    @FXML
    private Label lblBankCountValue;

    @FXML
    private VBox cardTotalBalance;

    @FXML
    private Label lblTotalBalance;

    @FXML
    private Label lblTotalBalanceValue;

    @FXML
    private VBox cardAvgBalance;

    @FXML
    private Label lblAvgBalance;

    @FXML
    private Label lblAvgBalanceValue;

    @FXML
    private BarChart<String, Number> bankBarChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private StatisticsDAO statisticsDAO;
    private NumberFormat currencyFormat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statisticsDAO = new StatisticsDAO();
        currencyFormat = NumberFormat.getNumberInstance(Locale.US);

        loadStatistics();
        setupBarChart();
        setupNavigation();
    }

    private void loadStatistics() {
        int totalAccounts = statisticsDAO.getTotalAccounts();
        int bankCount = statisticsDAO.getBankCount();
        int totalDABalance = statisticsDAO.getTotalDABalance();
        int totalEuroBalance = statisticsDAO.getTotalEuroBalance();
        int totalBalance = totalDABalance + totalEuroBalance;
        double avgDABalance = statisticsDAO.getAverageDABalance();
        double avgEuroBalance = statisticsDAO.getAverageEuroBalance();
        double avgBalance = (avgDABalance + avgEuroBalance) / 2;

        lblTotalAccountsValue.setText(String.valueOf(totalAccounts));
        lblBankCountValue.setText(String.valueOf(bankCount));
        lblTotalBalanceValue.setText(currencyFormat.format(totalBalance));
        lblAvgBalanceValue.setText(currencyFormat.format((int)avgBalance));
    }

    private void setupBarChart() {
        bankBarChart.getData().clear();
        bankBarChart.setTitle("各银行账户分布统计");
        bankBarChart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("账户数量");

        ObservableList<BankStatistics> stats = statisticsDAO.getBankStatistics();
        String[] colors = {"#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4", "#FFEAA7", "#DDA0DD", "#98D8C8", "#F7DC6F"};
        int colorIndex = 0;

        for (BankStatistics stat : stats) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(stat.getBankName(), stat.getAccountCount());
            series.getData().add(data);

            final String color = colors[colorIndex % colors.length];
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: " + color + ";");
                }
            });
            colorIndex++;
        }

        bankBarChart.getData().add(series);

        xAxis.setLabel("银行名称");
        yAxis.setLabel("账户数量");
    }

    private void setupNavigation() {
        btnDashboard.getStyleClass().add("nav-button-active");

        btnDashboard.setOnAction(event -> {
            setActiveButton(btnDashboard);
        });

        btnAccountManagement.setOnAction(event -> {
            setActiveButton(btnAccountManagement);
            navigateToAccountManagement();
        });
    }

    private void setActiveButton(Button activeButton) {
        btnDashboard.getStyleClass().remove("nav-button-active");
        btnAccountManagement.getStyleClass().remove("nav-button-active");
        activeButton.getStyleClass().add("nav-button-active");
    }

    private void navigateToAccountManagement() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/tableView/tableViewBank.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) mainContainer.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
