package pokladna.logic;

import pokladna.shared.interfaces.OrderInterface;
import pokladna.shared.templates.Order;
import pokladna.shared.templates.Product;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class orderLogic implements OrderInterface {
    private static final int MAX_ORDERS = 10;
    private static final String ORDERS_DIR = "store/orders/";
    private static final String RECEIPTS_DIR = "store/reciepts/";

    private LinkedList<Order> lastOrders;
    private Order currentOrder;

    public orderLogic() {
        this.lastOrders = new LinkedList<>();
        loadOrders();
    }

    @Override
    public Order createOrder(String cashier) {
        String orderId = UUID.randomUUID().toString();
        currentOrder = new Order(orderId, cashier);
        return currentOrder;
    }

    @Override
    public void addProductToOrder(Order order, Product product, int quantity) {
        // is product already in order
        if (order.getItems().stream().anyMatch(item -> item.getProduct().getId().equals(product.getId()))) {
            for (Order.OrderItem item : order.getItems()) {
                if (item.getProduct().getId().equals(product.getId())) {
                    int newQuantity = item.getQuantity() + quantity;
                    order.changeQuantity(product, newQuantity);
                }
            }
        } else {
            order.addItem(product, quantity);
        }
    }

    @Override
    public void confirmOrder(Order order) throws Exception {
        if (order == null) {
            throw new IllegalArgumentException("Objednávka je null");
        }

        // serialization
        saveOrder(order);

        String receipt = generateReceipt(order);
        saveReceipt(order.getId(), receipt);

        if (lastOrders.size() >= MAX_ORDERS) {
            Order oldOrder = lastOrders.removeFirst();
            deleteOrder(oldOrder.getId());
        }
        lastOrders.add(order);
    }

    @Override
    public void cancelOrder(Order order) {
        if (order == currentOrder) {
            currentOrder = null;
        }
    }

    @Override
    public List<Order> getLastOrders(int count) {
        int size = Math.min(count, lastOrders.size());
        return new ArrayList<>(lastOrders.subList(Math.max(0, lastOrders.size() - size), lastOrders.size()));
    }

    @Override
    public Order getOrderById(String id) {
        return lastOrders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String generateReceipt(Order order) {
        StringBuilder receipt = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        receipt.append("===============================================\n");
        receipt.append("                   PALANTIR                    \n");
        receipt.append("===============================================\n");
        receipt.append(String.format("Order No.: %s\n", order.getId().substring(0, 8)));
        receipt.append(String.format("Date: %s\n", dateFormat.format(order.getOrderDate())));
        receipt.append(String.format("Cashier: %s\n", order.getCashier()));
        receipt.append("===============================================\n");
        receipt.append(String.format("%-23s %5s %7s %8s\n", "Product", "Qty", "Kč/ks", "Price"));
        receipt.append("-----------------------------------------------\n");

        for (Order.OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double itemTotal = product.getPrice() * quantity;

            receipt.append(String.format("%-23s %5d %7.2f %8.2f\n",
                    product.getName(),
                    quantity,
                    product.getPrice(),
                    itemTotal));
        }

        receipt.append("===============================================\n");
        receipt.append(String.format("%-28s %14.2f Kč\n", "TOTAL:", order.getTotalPrice()));
        receipt.append("===============================================\n");
        receipt.append("           Thank you for shopping!\n");
        receipt.append("===============================================\n");

        return receipt.toString();
    }

    private void saveOrder(Order order) throws IOException {
        File dir = new File(ORDERS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filename = ORDERS_DIR + order.getId() + ".ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(order);
        }
    }

    private void saveReceipt(String orderId, String receipt) throws IOException {
        File dir = new File(RECEIPTS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filename = RECEIPTS_DIR + orderId + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(receipt);
        }
    }

    private void deleteOrder(String orderId) {
        File orderFile = new File(ORDERS_DIR + orderId + ".ser");
        File receiptFile = new File(RECEIPTS_DIR + orderId + ".txt");
        orderFile.delete();
        receiptFile.delete();
    }

    private void loadOrders() {
        File dir = new File(ORDERS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            return;
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".ser"));
        if (files != null) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified));

            for (File file : files) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    Order order = (Order) ois.readObject();
                    lastOrders.add(order);
                } catch (Exception e) {
                    System.err.println("Chyba při načítání objednávky: " + e.getMessage());
                }
            }
        }
    }
}
