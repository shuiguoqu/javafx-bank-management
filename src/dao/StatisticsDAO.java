package dao;

import DbConnection.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.BankStatistics;

import java.sql.*;

public class StatisticsDAO {

    public int getTotalAccounts() {
        String query = "SELECT COUNT(Bank_Num) as total FROM bank";
        try (Connection con = DbConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex.getMessage());
        }
        return 0;
    }

    public int getBankCount() {
        String query = "SELECT COUNT(DISTINCT Name) as count FROM bank";
        try (Connection con = DbConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex.getMessage());
        }
        return 0;
    }

    public int getTotalDABalance() {
        String query = "SELECT SUM(DA_Account_Num) as total FROM bank";
        try (Connection con = DbConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex.getMessage());
        }
        return 0;
    }

    public int getTotalEuroBalance() {
        String query = "SELECT SUM(Euro_Account_Num) as total FROM bank";
        try (Connection con = DbConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex.getMessage());
        }
        return 0;
    }

    public double getAverageDABalance() {
        String query = "SELECT AVG(DA_Account_Num) as avg FROM bank";
        try (Connection con = DbConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getDouble("avg");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex.getMessage());
        }
        return 0;
    }

    public double getAverageEuroBalance() {
        String query = "SELECT AVG(Euro_Account_Num) as avg FROM bank";
        try (Connection con = DbConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getDouble("avg");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex.getMessage());
        }
        return 0;
    }

    public ObservableList<BankStatistics> getBankStatistics() {
        ObservableList<BankStatistics> list = FXCollections.observableArrayList();
        String query = "SELECT Name, COUNT(Bank_Num) as accountCount, " +
                      "SUM(DA_Account_Num) as totalDA, SUM(Euro_Account_Num) as totalEuro " +
                      "FROM bank GROUP BY Name ORDER BY accountCount DESC";
        try (Connection con = DbConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                String bankName = rs.getString("Name");
                int accountCount = rs.getInt("accountCount");
                int totalDA = rs.getInt("totalDA");
                int totalEuro = rs.getInt("totalEuro");
                list.add(new BankStatistics(bankName, accountCount, totalDA, totalEuro));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex.getMessage());
        }
        return list;
    }
}
