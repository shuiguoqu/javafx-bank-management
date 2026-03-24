package dao;

import DbConnection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StatisticsDAO {

    public int getTotalAccountCount() {
        String sql = "SELECT COUNT(*) as total FROM bank";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return 0;
    }

    public int getBankCount() {
        String sql = "SELECT COUNT(DISTINCT Name) as bank_count FROM bank";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("bank_count");
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return 0;
    }

    public long getTotalBalance() {
        String sql = "SELECT COALESCE(SUM(DA_Account_Num + Euro_Account_Num), 0) as total_balance FROM bank";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("total_balance");
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return 0;
    }

    public double getAverageBalance() {
        String sql = "SELECT COALESCE(AVG(DA_Account_Num + Euro_Account_Num), 0) as avg_balance FROM bank";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("avg_balance");
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return 0.0;
    }

    public Map<String, Integer> getAccountCountByBank() {
        Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT Name, COUNT(*) as count FROM bank GROUP BY Name ORDER BY count DESC";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("Name"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return result;
    }

    public Map<String, Long> getTotalBalanceByBank() {
        Map<String, Long> result = new HashMap<>();
        String sql = "SELECT Name, SUM(DA_Account_Num + Euro_Account_Num) as total FROM bank GROUP BY Name ORDER BY total DESC";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("Name"), rs.getLong("total"));
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return result;
    }
}
