package fon.master.nst.paymentservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartItem implements Serializable {

    private Long productId;
    private String productName;
    private Long price;

}
