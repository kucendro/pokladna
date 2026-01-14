package pokladna.shared.interfaces;

import pokladna.shared.templates.Order;
import pokladna.shared.templates.Product;
import java.util.List;

public interface OrderInterface {
    Order createOrder(String cashier);
    void addProductToOrder(Order order, Product product, int quantity);
    void confirmOrder(Order order) throws Exception;
    void cancelOrder(Order order);
    List<Order> getLastOrders(int count);
    Order getOrderById(String id);
    String generateReceipt(Order order);
}
