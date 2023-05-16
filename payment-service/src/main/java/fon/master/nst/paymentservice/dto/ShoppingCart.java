package fon.master.nst.paymentservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCart implements Serializable {

    private Long cartId;
    private String username;
    private Long bill;
    private List<CartItem> cartItems = new ArrayList<>();

}
