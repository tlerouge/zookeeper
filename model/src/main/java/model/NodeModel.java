package model;

import java.util.List;

public class NodeModel {

    private List<AccountModel> accounts;

    private UserModel user;

    public List<AccountModel> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountModel> accounts) {
        this.accounts = accounts;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "NodeModel{" +
                "accounts=" + accounts +
                ", user=" + user +
                '}';
    }
}
