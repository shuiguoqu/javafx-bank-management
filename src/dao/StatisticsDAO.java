package dao;

import DbConnection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StatisticsDAO {

    public int getTotalAccounts() {
        String query = "SELECT COUNT(*) AS total FROM bank";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR getTotalAccounts: " + ex.getMessage());
        }
        return 0;
    }

    public int getBankCount() {
        String query = "SELECT COUNT(DISTINCT Name) AS bankCount FROM bank";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("bankCount");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR getBankCount: " + ex.getMessage());
        }
        return 0;
    }

    public long getTotalBalance() {
        String query = "SELECT SUM(DA_Account_Num + Euro_Account_Num) AS totalBalance FROM bank";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("totalBalance");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR getTotalBalance: " + ex.getMessage());
        }
        return 0;
    }

    public double getAverageBalance() {
        String query = "SELECT AVG(DA_Account_Num + Euro_Account_Num) AS avgBalance FROM bank";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("avgBalance");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR getAverageBalance: " + ex.getMessage());
        }
        return 0;
    }

    public Map<String, Integer> getAccountsByBank() {
        Map<String, Integer> accountsByBank = new HashMap<>();
        String query = "SELECT Name, (DA_Account_Num + Euro_Account_Num) AS totalAccounts FROM bank ORDER BY Name";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                accountsByBank.put(rs.getString("Name"), rs.getInt("totalAccounts"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR getAccountsByBank: " + ex.getMessage());
        }
        return accountsByBank;
    }

    public Map<String, Long> getBalanceByBank() {
        Map<String, Long> balanceByBank = new HashMap<>();
        String query = "SELECT Name, (DA_Account_Num + Euro_Account_Num) AS totalBalance FROM bank ORDER BY Name";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                balanceByBank.put(rs.getString("Name"), rs.getLong("totalBalance"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR getBalanceByBank: " + ex.getMessage());
        }
        return balanceByBank;
    }
}
