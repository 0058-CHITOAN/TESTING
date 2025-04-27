package b_Money;

import java.util.Hashtable;

public class Bank {
    private Hashtable<String, Account> accountlist = new Hashtable<String, Account>();
    private String name;
    private Currency currency;
    
    Bank(String name, Currency currency) {
        this.name = name;
        this.currency = currency;
    }
    
    public String getName() {
        return name;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    public void openAccount(String accountid) throws AccountExistsException {
        if (accountlist.containsKey(accountid)) {
            throw new AccountExistsException();
        } else {
            accountlist.put(accountid, new Account(accountid, currency)); // Sửa: Tạo tài khoản mới
        }
    }
    
    public void deposit(String accountid, Money money) throws AccountDoesNotExistException {
        if (!accountlist.containsKey(accountid)) { // Sửa: Đảo ngược điều kiện
            throw new AccountDoesNotExistException();
        } else {
            Account account = accountlist.get(accountid);
            account.deposit(money);
        }
    }
    
    public void withdraw(String accountid, Money money) throws AccountDoesNotExistException {
        if (!accountlist.containsKey(accountid)) { // Sửa: Đảo ngược điều kiện
            throw new AccountDoesNotExistException();
        } else {
            Account account = accountlist.get(accountid);
            account.withdraw(money); // Sửa: Gọi withdraw thay vì deposit
        }
    }
    
    public Integer getBalance(String accountid) throws AccountDoesNotExistException {
        if (!accountlist.containsKey(accountid)) {
            throw new AccountDoesNotExistException();
        } else {
            return accountlist.get(accountid).getBalance().getAmount();
        }
    }

    public void transfer(String fromaccount, Bank tobank, String toaccount, Money amount) throws AccountDoesNotExistException {
        if (!accountlist.containsKey(fromaccount) || !tobank.accountlist.containsKey(toaccount)) {
            throw new AccountDoesNotExistException();
        } else {
            accountlist.get(fromaccount).withdraw(amount);
            tobank.accountlist.get(toaccount).deposit(amount);
        }       
    }

    public void transfer(String fromaccount, String toaccount, Money amount) throws AccountDoesNotExistException {
        transfer(fromaccount, this, toaccount, amount); // Sửa: Sử dụng toaccount thay vì fromaccount
    }

    public void addTimedPayment(String accountid, String payid, Integer interval, Integer next, Money amount, Bank tobank, String toaccount) throws AccountDoesNotExistException {
        if (!accountlist.containsKey(accountid)) { // Thêm kiểm tra tài khoản
            throw new AccountDoesNotExistException();
        }
        Account account = accountlist.get(accountid);
        account.addTimedPayment(payid, interval, next, amount, tobank, toaccount);
    }
    
    public void removeTimedPayment(String accountid, String id) throws AccountDoesNotExistException {
        if (!accountlist.containsKey(accountid)) { // Thêm kiểm tra tài khoản
            throw new AccountDoesNotExistException();
        }
        Account account = accountlist.get(accountid);
        account.removeTimedPayment(id);
    }
    
    public void tick() throws AccountDoesNotExistException {
        for (Account account : accountlist.values()) {
            account.tick();
        }
    }   
}