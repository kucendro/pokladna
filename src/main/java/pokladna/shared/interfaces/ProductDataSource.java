package pokladna.shared.interfaces;

import pokladna.shared.templates.Product;
import java.util.List;

public interface ProductDataSource {
    List<Product> loadProducts() throws Exception;
    Product getProductById(String id);
    Product getProductByEan(String ean);
    List<Product> getAllProducts();
}
