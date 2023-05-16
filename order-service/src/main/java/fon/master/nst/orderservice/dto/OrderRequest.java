package fon.master.nst.orderservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequest {

    private Long bill;
    private List<CartItem> cartItems = new ArrayList<>();

}
