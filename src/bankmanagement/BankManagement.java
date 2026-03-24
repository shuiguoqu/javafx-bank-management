/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankmanagement;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author INTEL
 */
public class BankManagement extends Application {
    
    private static Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        showDashboard();
    }
    
    public static void showDashboard() {
        try {
            Parent root = FXMLLoader.load(BankManagement.class.getResource("/dashboard/dashboard.fxml"));
            Scene scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    
    public static void showAccountManagement() {
        try {
            Parent root = FXMLLoader.load(BankManagement.class.getResource("/tableView/tableViewBank.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
}
