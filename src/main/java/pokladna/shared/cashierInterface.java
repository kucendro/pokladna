package pokladna.shared;

import java.util.List;

import pokladna.shared.entites.Cashier;

public interface cashierInterface {

    Cashier get(String id);

    List<Cashier> getAll();

    boolean add(Cashier cashier);

    boolean remove(String id);

    boolean update(String id, Cashier cashier);

    boolean authenticate(String password);
}
