package main;

import dashboard.DashboardController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private AnchorPane contentArea;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnAccountManagement;
    @FXML
    private VBox navBar;

    private Button currentActiveButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentActiveButton = btnDashboard;
        setActiveButton(btnDashboard);
        loadDashboard();
    }

    @FXML
    private void handleDashboardClick() {
        if (currentActiveButton != btnDashboard) {
            setActiveButton(btnDashboard);
            loadDashboard();
        }
    }

    @FXML
    private void handleAccountManagementClick() {
        if (currentActiveButton != btnAccountManagement) {
            setActiveButton(btnAccountManagement);
            loadAccountManagement();
        }
    }

    private void setActiveButton(Button button) {
        if (currentActiveButton != null) {
            currentActiveButton.getStyleClass().remove("nav-btn-active");
        }
        button.getStyleClass().add("nav-btn-active");
        currentActiveButton = button;
    }

    private void loadDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard/dashboard.fxml"));
            Node dashboardView = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(dashboardView);
            AnchorPane.setTopAnchor(dashboardView, 0.0);
            AnchorPane.setBottomAnchor(dashboardView, 0.0);
            AnchorPane.setLeftAnchor(dashboardView, 0.0);
            AnchorPane.setRightAnchor(dashboardView, 0.0);
        } catch (IOException e) {
            System.out.println("ERROR loading dashboard: " + e.getMessage());
        }
    }

    private void loadAccountManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tableView/tableViewBank.fxml"));
            Node accountView = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(accountView);
            AnchorPane.setTopAnchor(accountView, 0.0);
            AnchorPane.setBottomAnchor(accountView, 0.0);
            AnchorPane.setLeftAnchor(accountView, 0.0);
            AnchorPane.setRightAnchor(accountView, 0.0);
        } catch (IOException e) {
            System.out.println("ERROR loading account management: " + e.getMessage());
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) contentArea.getScene().getWindow();
        stage.close();
    }
}
