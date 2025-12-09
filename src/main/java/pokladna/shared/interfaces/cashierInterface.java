package pokladna.shared.interfaces;

import java.util.List;

import pokladna.shared.templates.Cashier;

public interface cashierInterface {

    Cashier get(String id);

    List<Cashier> getAll();

    boolean add(Cashier cashier);

    boolean remove(String id);

    boolean update(String id, Cashier cashier);

    boolean authenticate(String password);
}
