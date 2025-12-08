package pokladna.shared;

//@formatter:off
/**
 *? Cashier table structure:
 *? ┌─────────────────┐
 *? │ id (String)     │
 *? │ name            │
 *? │ surname         │
 *? │ password        │
 *? └─────────────────┘
 */
//@formatter:on

public class Cashier {
    private String id;
    private String name;
    private String surname;
    private String password;

    public Cashier(String id, String name, String surname, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
