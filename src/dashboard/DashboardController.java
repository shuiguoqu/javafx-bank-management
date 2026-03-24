package dashboard;

import dao.StatisticsDAO;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class DashboardController implements Initializable {

    @FXML
    private Label totalAccountsLabel;
    @FXML
    private Label bankCountLabel;
    @FXML
    private Label totalBalanceLabel;
    @FXML
    private Label averageBalanceLabel;
    @FXML
    private BarChart<String, Number> bankBarChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private StatisticsDAO statisticsDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statisticsDAO = new StatisticsDAO();
        loadStatistics();
        loadBarChart();
    }

    private void loadStatistics() {
        int totalAccounts = statisticsDAO.getTotalAccounts();
        int bankCount = statisticsDAO.getBankCount();
        long totalBalance = statisticsDAO.getTotalBalance();
        double averageBalance = statisticsDAO.getAverageBalance();

        totalAccountsLabel.setText(String.valueOf(totalAccounts));
        bankCountLabel.setText(String.valueOf(bankCount));
        totalBalanceLabel.setText(String.format("%,d", totalBalance));
        averageBalanceLabel.setText(String.format("%,.2f", averageBalance));
    }

    private void loadBarChart() {
        Map<String, Integer> accountsByBank = statisticsDAO.getAccountsByBank();

        ObservableList<String> bankNames = FXCollections.observableArrayList(accountsByBank.keySet());
        xAxis.setCategories(bankNames);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("账户数量");

        for (Map.Entry<String, Integer> entry : accountsByBank.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        bankBarChart.getData().add(series);
        bankBarChart.setLegendVisible(false);
    }

    public void refreshData() {
        loadStatistics();
        bankBarChart.getData().clear();
        loadBarChart();
    }
}
