import controllers.MenuController;


public class Main {
    public static void main(String[] args) {

        MenuController menu = new MenuController();

        try {
            menu.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
