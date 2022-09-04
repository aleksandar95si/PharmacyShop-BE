package fon.master.nst.orchestrationservice.dto;

public class OrderRequest {

    private ShoppingCart shoppingCart;
    private OrderStatus orderStatus;
    private Long orderId;

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "shoppingCart=" + shoppingCart +
                ", orderStatus=" + orderStatus +
                ", orderId=" + orderId +
                '}';
    }
}
