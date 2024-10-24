package Classes;
import java.util.ArrayList;
import java.util.Scanner;
public class Menu {
    private ArrayList<String> options;
    private Scanner sc = new Scanner(System.in);
    public Menu(ArrayList<String> options) {
        this.options = options;
    }
    public int int_getChoice() {
        System.out.println("\nMenu:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.print("Please choose an option (1-" + options.size() + "): ");
        return sc.nextInt();
    }
}

