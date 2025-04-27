package b_Money;

public class Currency {
    private String name;
    private Double rate;
    
    Currency(String name, Double rate) {
        this.name = name;
        this.rate = rate;
    }

    public Integer universalValue(Integer amount) {
        return (int) (amount * rate); // Chuyển đổi số tiền sang giá trị "universal currency"
    }

    public String getName() {
        return name;
    }
    
    public Double getRate() {
        return rate;
    }
    
    public void setRate(Double rate) {
        this.rate = rate;
    }
    
    public Integer valueInThisCurrency(Integer amount, Currency othercurrency) {
        // Chuyển đổi số tiền từ othercurrency sang universal currency, rồi từ universal currency sang this currency
        Integer universal = othercurrency.universalValue(amount);
        return (int) (universal / this.rate);
    }
}