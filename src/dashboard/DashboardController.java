package dashboard;

import dao.StatisticsDAO;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardController implements Initializable {

    @FXML
    private Label totalAccountsLabel;
    @FXML
    private Label bankCountLabel;
    @FXML
    private Label totalBalanceLabel;
    @FXML
    private Label avgBalanceLabel;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private VBox card1;
    @FXML
    private VBox card2;
    @FXML
    private VBox card3;
    @FXML
    private VBox card4;

    private StatisticsDAO statisticsDAO;
    private DecimalFormat numberFormat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statisticsDAO = new StatisticsDAO();
        numberFormat = new DecimalFormat("#,##0.00");
        loadStatistics();
        loadBarChart();
    }

    private void loadStatistics() {
        int totalAccounts = statisticsDAO.getTotalAccountCount();
        int bankCount = statisticsDAO.getBankCount();
        long totalBalance = statisticsDAO.getTotalBalance();
        double avgBalance = statisticsDAO.getAverageBalance();

        totalAccountsLabel.setText(String.valueOf(totalAccounts));
        bankCountLabel.setText(String.valueOf(bankCount));
        totalBalanceLabel.setText(numberFormat.format(totalBalance));
        avgBalanceLabel.setText(numberFormat.format(avgBalance));
    }

    private void loadBarChart() {
        barChart.getData().clear();
        
        Map<String, Integer> accountCountByBank = statisticsDAO.getAccountCountByBank();
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Account Count");
        
        for (Map.Entry<String, Integer> entry : accountCountByBank.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        
        barChart.getData().add(series);
        
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-bar-fill: " + getRandomColor() + ";");
                }
            });
        }
    }

    private String getRandomColor() {
        String[] colors = {
            "#6C63FF",
            "#FF6B6B",
            "#4ECDC4",
            "#45B7D1",
            "#96CEB4",
            "#FFEAA7",
            "#DDA0DD",
            "#98D8C8"
        };
        int index = (int) (Math.random() * colors.length);
        return colors[index];
    }

    public void refreshData() {
        loadStatistics();
        loadBarChart();
    }
}
