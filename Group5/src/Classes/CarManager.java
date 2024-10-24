package Classes;
import java.util.Scanner;
public class CarManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BrandList brandList = new BrandList();
        CarList carList = new CarList(brandList);
        if (!brandList.loadFromFile("brands.txt")) {
            System.out.println("Error loading brands from file.");
            return;
        }
        if (!carList.loadFromFile("cars.txt")) {
            System.out.println("Error loading cars from file.");
            return;
        }
        int choice;
        do {
            System.out.println("\nCar Showroom Management Menu:");
            System.out.println("1. List all brands");
            System.out.println("2. Add a new brand");
            System.out.println("3. Search for a brand by ID");
            System.out.println("4. Update a brand");
            System.out.println("5. Save brands to file");
            System.out.println("6. List all cars");
            System.out.println("7. Add a new car");
            System.out.println("8. Remove a car by ID");
            System.out.println("9. Update a car");
            System.out.println("10. Save cars to file");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    brandList.listBrands();
                    break;
                case 2:
                    brandList.addBrand();
                    break;
                case 3:
                    System.out.print("Enter Brand ID to search: ");
                    String searchID = sc.nextLine();
                    int pos = brandList.searchID(searchID);
                    if (pos != -1) {
                        System.out.println("Brand found: " + brandList.brands.get(pos));
                    } else {
                        System.out.println("Brand not found.");
                    }
                    break;
                case 4:
                    brandList.updateBrand();
                    break;
                case 5:
                    if (brandList.saveToFile("brands.txt")) {
                        System.out.println("Brands saved to file successfully.");
                    } else {
                        System.out.println("Error saving brands to file.");
                    }
                    break;
                case 6:
                    carList.listCars();
                    break;
                case 7:
                    carList.addCar();
                    break;
                case 8:
                    System.out.print("Enter Car ID to remove: ");
                    String carID = sc.nextLine();
                    if (carList.searchID(carID) != -1) {
                        carList.deleteCar();
                    } else {
                        System.out.println("Car not found.");
                    }
                    break;
                case 9:
                    carList.updateCar();
                    break;
                case 10:
                    if (carList.saveToFile("cars.txt")) {
                        System.out.println("Cars saved to file successfully.");
                    } else {
                        System.out.println("Error saving cars to file.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        brandList.saveToFile("brands.txt");
        carList.saveToFile("cars.txt");
        sc.close();
    }
}
