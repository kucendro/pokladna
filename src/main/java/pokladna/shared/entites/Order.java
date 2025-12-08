package pokladna.shared.entites;

import java.sql.Date;

//@formatter:off
/**
 *? Order table structure:
 *? ┌─────────────────┐
 *? │ id (UUID)       │
 *? │ products[]      │
 *? │ cashier         │
 *? │ totalPrice      │
 *? │ orderDate       │
 *? └─────────────────┘
 */
//@formatter:on

public class Order {
    private String id;
    private String[] products;
    private String cashier;
    private double totalPrice;
    private Date orderDate;

    public Order(String id, String[] products, String cashier, double totalPrice, Date orderDate) {
        this.id = id;
        this.products = products;
        this.cashier = cashier;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getProducts() {
        return products;
    }

    public void setProducts(String[] products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = new Date(orderDate.getTime());
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }
}
