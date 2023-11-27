package ginat.lanchat.Client;

public class Main {
    public static void main(String[] args) {
        AppUI app = new AppUI();
        String name = app.promptForName();
        Client.init(name, app);

    }
}
