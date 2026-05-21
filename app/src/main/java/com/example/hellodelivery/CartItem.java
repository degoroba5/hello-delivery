package com.example.hellodelivery;

public class CartItem {
    private DeliveryItem item;
    private int quantity;

    public CartItem(DeliveryItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public DeliveryItem getItem() { return item; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public double getTotalPrice() {
        return item.getPrice() * quantity;
    }
}