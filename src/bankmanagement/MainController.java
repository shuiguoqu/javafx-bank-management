package bankmanagement;

import dashboard.DashboardController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private VBox sideNav;
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button accountBtn;

    private DashboardController dashboardController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDashboard();
        dashboardBtn.getStyleClass().add("active");
    }

    @FXML
    private void handleNavigation(ActionEvent event) {
        Button clickedBtn = (Button) event.getSource();

        dashboardBtn.getStyleClass().remove("active");
        accountBtn.getStyleClass().remove("active");
        clickedBtn.getStyleClass().add("active");

        if (clickedBtn == dashboardBtn) {
            loadDashboard();
        } else if (clickedBtn == accountBtn) {
            loadAccountManagement();
        }
    }

    private void loadDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard/dashboard.fxml"));
            Parent dashboard = loader.load();
            dashboard.getStylesheets().add(getClass().getResource("/dashboard/dashboard.css").toExternalForm());
            dashboardController = loader.getController();
            mainBorderPane.setCenter(dashboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAccountManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tableView/tableViewBank.fxml"));
            Parent accountView = loader.load();
            mainBorderPane.setCenter(accountView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshDashboard() {
        if (dashboardController != null) {
            dashboardController.refreshData();
        }
    }
}
