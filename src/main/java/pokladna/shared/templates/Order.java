package pokladna.shared.templates;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

//@formatter:off
/**
 *? Order table structure:
 *? ┌─────────────────┐
 *? │ id (String)     │
 *? │ items (List)    │
 *? │ cashier         │
 *? │ totalPrice      │
 *? │ orderDate       │
 *? └─────────────────┘
 */
//@formatter:on

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private List<OrderItem> items;
    private String cashier;
    private double totalPrice;
    private Date orderDate;

    public Order(String id, String cashier) {
        this.id = id;
        this.cashier = cashier;
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
        this.orderDate = new Date();
    }

    public void addItem(Product product, int quantity) {
        items.add(new OrderItem(product, quantity));
        calculateTotal();
    }

    private void calculateTotal() {
        totalPrice = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public static class OrderItem implements Serializable {
        private static final long serialVersionUID = 1L;
        private Product product;
        private int quantity;

        public OrderItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        calculateTotal();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public void removeItem(Product product) {
        this.items.removeIf(item -> item.getProduct().getId().equals(product.getId()));
    }

    public void changeQuantity(Product product, int newQuantity) {
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(newQuantity);
                break;
            }
        }
        calculateTotal();
    }
}
