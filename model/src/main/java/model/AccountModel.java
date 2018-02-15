package model;

public class AccountModel {

    private String iban;

    private double balance;

    private String name;

    private String type;

    public AccountModel(){}

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "iban='" + iban + '\'' +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
