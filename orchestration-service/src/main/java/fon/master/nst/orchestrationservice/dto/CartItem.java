package fon.master.nst.orchestrationservice.dto;

public class CartItem {

    private Long itemId;
    private Long productId;
    private String productName;
    private Long price;
    private ShoppingCart shoppingCart;

    public CartItem() {
        super();
    }

    public CartItem(ShoppingCart shoppingCart) {
        super();
        this.shoppingCart = shoppingCart;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "itemId=" + itemId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", shoppingCart=" + shoppingCart +
                '}';
    }
}
