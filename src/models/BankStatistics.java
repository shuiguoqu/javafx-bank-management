package models;

public class BankStatistics {
    private String bankName;
    private int accountCount;
    private int totalDA;
    private int totalEuro;

    public BankStatistics(String bankName, int accountCount, int totalDA, int totalEuro) {
        this.bankName = bankName;
        this.accountCount = accountCount;
        this.totalDA = totalDA;
        this.totalEuro = totalEuro;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(int accountCount) {
        this.accountCount = accountCount;
    }

    public int getTotalDA() {
        return totalDA;
    }

    public void setTotalDA(int totalDA) {
        this.totalDA = totalDA;
    }

    public int getTotalEuro() {
        return totalEuro;
    }

    public void setTotalEuro(int totalEuro) {
        this.totalEuro = totalEuro;
    }

    public int getTotalBalance() {
        return totalDA + totalEuro;
    }
}
