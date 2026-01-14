package pokladna.shared.templates;

import java.io.Serializable;

//@formatter:off
/**
 *? Product table structure:
 *? ┌─────────────────┐
 *? │ id (String)     │
 *? │ ean (String)    │
 *? │ name (String)   │
 *? │ price (double)  │
 *? └─────────────────┘
 */
//@formatter:on

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String ean;
    private String name;
    private double price;

    public Product(String id, String ean, String name, double price) {
        this.id = id;
        this.ean = ean;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
