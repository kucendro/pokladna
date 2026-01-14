package pokladna.views;

import pokladna.data.productData;
import pokladna.logic.orderLogic;
import pokladna.shared.ErrorLogger;
import pokladna.shared.interfaces.GUIInterface;
import pokladna.shared.interfaces.OrderInterface;
import pokladna.shared.interfaces.ProductDataSource;
import pokladna.shared.templates.Order;
import pokladna.shared.templates.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GUI extends JFrame implements GUIInterface {
    private ProductDataSource productDataSource;
    private OrderInterface orderLogic;
    private Order currentOrder;
    private ErrorLogger logger;

    private JPanel productsPanel;
    private JTable orderTable;
    private DefaultTableModel orderTableModel;
    private JLabel totalLabel;
    private JTextField quantityField;
    private JTextField searchField;
    private PopUpKeyboard keyboard;

    public GUI() {
        this.productDataSource = new productData();
        this.orderLogic = new orderLogic();
        this.logger = ErrorLogger.getInstance();

        initGUI();
        loadProducts();
        createNewOrder();
    }

    private void initGUI() {
        setTitle("POS System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        quantityField = new JTextField("1", 8);
        searchField = new JTextField(15);
        keyboard = new PopUpKeyboard(quantityField);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(searchLabel);

        searchField.setFont(new Font("Arial", Font.PLAIN, 18));
        searchField.setPreferredSize(new Dimension(250, 50));
        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showKeyboardForField(searchField);
            }
        });
        topPanel.add(searchField);

        JButton searchButton = new JButton("🔍 Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 18));
        searchButton.setPreferredSize(new Dimension(150, 50));
        searchButton.addActionListener(e -> searchProduct());
        topPanel.add(searchButton);

        topPanel.add(Box.createHorizontalStrut(30));

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(quantityLabel);

        quantityField.setFont(new Font("Arial", Font.PLAIN, 18));
        quantityField.setPreferredSize(new Dimension(100, 50));
        quantityField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showKeyboardForField(quantityField);
            }
        });
        topPanel.add(quantityField);

        add(topPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(950);

        JPanel productsMainPanel = new JPanel(new BorderLayout());
        productsMainPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                "Available Products",
                0, 0,
                new Font("Arial", Font.BOLD, 20)));

        productsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane productsScroll = new JScrollPane(productsPanel);
        productsScroll.getVerticalScrollBar().setUnitIncrement(16);
        productsMainPanel.add(productsScroll, BorderLayout.CENTER);

        splitPane.setLeftComponent(productsMainPanel);

        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                "Current Order",
                0, 0,
                new Font("Arial", Font.BOLD, 20)));

        String[] orderColumns = { "Product", "Qty", "Total" };
        orderTableModel = new DefaultTableModel(orderColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderTable = new JTable(orderTableModel);
        orderTable.setFont(new Font("Arial", Font.PLAIN, 16));
        orderTable.setRowHeight(40);
        orderTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        orderTable.getTableHeader().setPreferredSize(new Dimension(0, 40));

        JScrollPane orderScroll = new JScrollPane(orderTable);
        orderPanel.add(orderScroll, BorderLayout.CENTER);

        JPanel orderControlsPanel = new JPanel(new BorderLayout());

        totalLabel = new JLabel("Total: 0.00 Kč");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        totalLabel.setOpaque(true);
        totalLabel.setBackground(new Color(240, 240, 240));
        orderControlsPanel.add(totalLabel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton confirmButton = new JButton("CONFIRM");
        confirmButton.setBackground(new Color(46, 204, 113));
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 20));
        confirmButton.setPreferredSize(new Dimension(0, 80));
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(e -> confirmOrder());
        buttonsPanel.add(confirmButton);

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 20));
        cancelButton.setPreferredSize(new Dimension(0, 80));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> cancelOrder());
        buttonsPanel.add(cancelButton);

        JButton viewOrdersButton = new JButton("HISTORY");
        viewOrdersButton.setBackground(new Color(52, 152, 219));
        viewOrdersButton.setForeground(Color.BLACK);
        viewOrdersButton.setFont(new Font("Arial", Font.BOLD, 20));
        viewOrdersButton.setPreferredSize(new Dimension(0, 80));
        viewOrdersButton.setFocusPainted(false);
        viewOrdersButton.addActionListener(e -> showLastOrders());
        buttonsPanel.add(viewOrdersButton);

        orderControlsPanel.add(buttonsPanel, BorderLayout.CENTER);
        orderPanel.add(orderControlsPanel, BorderLayout.SOUTH);

        splitPane.setRightComponent(orderPanel);
        add(splitPane, BorderLayout.CENTER);
    }

    private void showKeyboardForField(JTextField field) {
        boolean isNumeric = field == quantityField;
        keyboard = new PopUpKeyboard(field, isNumeric);
        Point p = field.getLocationOnScreen();
        p.y += field.getHeight() + 5;
        keyboard.setLocation(p);
        keyboard.setVisible(true);
    }

    private void loadProducts() {
        try {
            List<Product> products = productDataSource.loadProducts();
            displayProducts(products);
        } catch (Exception e) {
            String errorMsg = "Error loading products: " + e.getMessage();
            logger.log(errorMsg, e);
            displayError(errorMsg);
        }
    }

    private void createNewOrder() {
        currentOrder = orderLogic.createOrder("Peter Thiel");
        updateOrderDisplay();
    }

    private void searchProduct() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadProducts();
            return;
        }

        try {
            List<Product> allProducts = productDataSource.getAllProducts();
            List<Product> filtered = allProducts.stream()
                    .filter(p -> removeDiacritics(p.getName()).toLowerCase()
                            .contains(removeDiacritics(searchTerm).toLowerCase()) ||
                            p.getEan().contains(searchTerm) ||
                            p.getId().contains(searchTerm))
                    .toList();
            displayProducts(filtered);
        } catch (Exception e) {
            logger.log("Error searching for product", e);
            displayError("Search error: " + e.getMessage());
        }
    }

    private String removeDiacritics(String searchTerm) {
        String normalized = java.text.Normalizer.normalize(searchTerm, java.text.Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }

    private void addProductToOrder(Product product) {
        try {
            int quantity = Integer.parseInt(quantityField.getText().trim());
            if (quantity <= 0) {
                displayError("Quantity must be greater than 0");
                return;
            }

            orderLogic.addProductToOrder(currentOrder, product, quantity);
            updateOrderDisplay();
            quantityField.setText("1");
        } catch (NumberFormatException e) {
            displayError("Invalid quantity");
        } catch (Exception e) {
            logger.log("Error adding product", e);
            displayError("Error: " + e.getMessage());
        }
    }

    private void updateOrderDisplay() {
        orderTableModel.setRowCount(0);

        if (currentOrder != null) {
            for (Order.OrderItem item : currentOrder.getItems()) {
                Product product = item.getProduct();
                Object[] row = {
                        product.getName(),
                        item.getQuantity(),
                        String.format("Kč %.2f", product.getPrice() * item.getQuantity())
                };
                orderTableModel.addRow(row);
            }
            totalLabel.setText(String.format("Total: Kč %.2f", currentOrder.getTotalPrice()));
        }
    }

    private JButton createProductButton(Product product) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setPreferredSize(new Dimension(250, 120));
        button.setBackground(new Color(245, 245, 245));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel priceLabel = new JLabel(String.format("Kč %.2f", product.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        priceLabel.setForeground(Color.BLACK);
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        button.add(nameLabel, BorderLayout.CENTER);
        button.add(priceLabel, BorderLayout.SOUTH);

        button.addActionListener(e -> addProductToOrder(product));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(225, 245, 254));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(52, 152, 219), 3),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(245, 245, 245));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            }
        });

        return button;
    }

    private void confirmOrder() {
        if (currentOrder == null || currentOrder.getItems().isEmpty()) {
            displayError("Order is empty");
            return;
        }

        try {
            String receipt = orderLogic.generateReceipt(currentOrder);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Confirm order and print receipt?\n\nTotal price: " +
                            String.format("Kč %.2f", currentOrder.getTotalPrice()),
                    "Order Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                orderLogic.confirmOrder(currentOrder);
                displayReceipt(receipt);
                createNewOrder();
            }
        } catch (Exception e) {
            logger.log("Error confirming order", e);
            displayError("Confirmation error: " + e.getMessage());
        }
    }

    private void cancelOrder() {
        if (currentOrder == null || currentOrder.getItems().isEmpty()) {
            displayError("Order is already empty");
            return;
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "Do you really want to cancel the current order?",
                "Cancel Order",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            orderLogic.cancelOrder(currentOrder);
            createNewOrder();
            JOptionPane.showMessageDialog(this, "Order cancelled");
        }
    }

    private void showLastOrders() {
        List<Order> lastOrders = orderLogic.getLastOrders(10);

        if (lastOrders.isEmpty()) {
            displayError("No orders available");
            return;
        }

        JDialog dialog = new JDialog(this, "Order History", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        String[] columns = { "ID", "Date", "Cashier", "Total" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Order order : lastOrders) {
            Object[] row = {
                    order.getId().substring(0, 8),
                    order.getOrderDate().toString(),
                    order.getCashier(),
                    String.format("Kč %.2f", order.getTotalPrice())
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton viewButton = new JButton("View Receipt");
        viewButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String orderId = (String) table.getValueAt(selectedRow, 0);
                Order order = lastOrders.stream()
                        .filter(o -> o.getId().startsWith(orderId))
                        .findFirst()
                        .orElse(null);
                if (order != null) {
                    String receipt = orderLogic.generateReceipt(order);
                    displayReceipt(receipt);
                }
            }
        });
        buttonPanel.add(viewButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    @Override
    public void displayProducts(List<Product> products) {
        productsPanel.removeAll();

        for (Product product : products) {
            JButton productButton = createProductButton(product);
            productsPanel.add(productButton);
        }

        productsPanel.revalidate();
        productsPanel.repaint();
    }

    @Override
    public void displayOrder(Order order) {
        this.currentOrder = order;
        updateOrderDisplay();
    }

    @Override
    public void displayReceipt(String receipt) {
        JDialog dialog = new JDialog(this, "Receipt", true);
        dialog.setSize(335, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(receipt);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    @Override
    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void displayLastOrders(List<Order> orders) {
    }
}
