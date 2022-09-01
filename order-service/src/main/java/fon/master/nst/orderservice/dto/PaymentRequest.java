package fon.master.nst.orderservice.dto;

public class PaymentRequest {
    
    private String username;
    private Long amount;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "username='" + username + '\'' +
                ", amount=" + amount +
                '}';
    }
}
