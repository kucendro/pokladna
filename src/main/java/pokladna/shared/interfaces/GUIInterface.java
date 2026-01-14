package pokladna.shared.interfaces;

import pokladna.shared.templates.Order;
import pokladna.shared.templates.Product;
import java.util.List;

public interface GUIInterface {
    void displayProducts(List<Product> products);
    void displayOrder(Order order);
    void displayReceipt(String receipt);
    void displayError(String message);
    void displayLastOrders(List<Order> orders);
}
