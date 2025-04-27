package b_Money;

public class Money implements Comparable {
    private int amount;
    private Currency currency;

    Money(Integer amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }
    
    public Integer getAmount() {
        return amount;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    public String toString() {
        // Chia amount cho 100 để hiển thị dưới dạng thập phân (1050 -> 10.50)
        return (amount / 100.0) + " " + currency.getName();
    }
    
    public Integer universalValue() {
        return currency.universalValue(amount);
    }
    
    public Boolean equals(Money other) {
        // So sánh giá trị universal của hai số tiền
        return this.universalValue().equals(other.universalValue());
    }
    
    public Money add(Money other) {
        // Chuyển đổi số tiền other sang tiền tệ hiện tại và cộng
        Integer otherInThisCurrency = currency.valueInThisCurrency(other.getAmount(), other.getCurrency());
        return new Money(this.amount + otherInThisCurrency, this.currency);
    }

    public Money sub(Money other) {
        // Chuyển đổi số tiền other sang tiền tệ hiện tại và trừ
        Integer otherInThisCurrency = currency.valueInThisCurrency(other.getAmount(), other.getCurrency());
        return new Money(this.amount - otherInThisCurrency, this.currency);
    }
    
    public Boolean isZero() {
        return amount == 0;
    }
    
    public Money negate() {
        return new Money(-amount, currency);
    }
    
    public int compareTo(Object other) {
        Money otherMoney = (Money) other;
        return this.universalValue().compareTo(otherMoney.universalValue());
    }
}