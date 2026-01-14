package pokladna.data;

import pokladna.shared.interfaces.ProductDataSource;
import pokladna.shared.templates.Product;
import java.io.*;
import java.util.*;

public class productData implements ProductDataSource {
    private static final String PRODUCTS_FILE = "store/products/products.csv";
    private List<Product> products;
    private Map<String, Product> productById;
    private Map<String, Product> productByEan;

    public productData() {
        this.products = new ArrayList<>();
        this.productById = new HashMap<>();
        this.productByEan = new HashMap<>();
    }

    @Override
    public List<Product> loadProducts() throws Exception {
        products.clear();
        productById.clear();
        productByEan.clear();

        File file = new File(PRODUCTS_FILE);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + PRODUCTS_FILE);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }
                
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    String id = parts[0].trim();
                    String ean = parts[1].trim();
                    String name = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());
                    
                    Product product = new Product(id, ean, name, price);
                    products.add(product);
                    productById.put(id, product);
                    productByEan.put(ean, product);
                }
            }
        }
        
        return products;
    }

    @Override
    public Product getProductById(String id) {
        return productById.get(id);
    }

    @Override
    public Product getProductByEan(String ean) {
        return productByEan.get(ean);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
}
