package pokladna;

import pokladna.view.GUI;

public class Main {
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "Pokladna");
        System.setProperty("apple.awt.application.appearance", "system");

        // initialize GUI
        // SwingUtilities.invokeLater(() -> new GUI().setVisible(true));

        // get collections instances
        // UsersTable users = UsersTable.getInstance();
        // PersonsTable persons = PersonsTable.getInstance();

        // load collections from files
        // users.load();
        // persons.load();

        GUI gui = new pokladna.view.GUI();
        gui.setVisible(true);

    }
}