package fon.master.nst.orchestrationservice.dto;

public class MailRequest {

    private String email;
    private ShoppingCart shoppingCart;

    public MailRequest(String email, ShoppingCart shoppingCart) {
        this.email = email;
        this.shoppingCart = shoppingCart;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
