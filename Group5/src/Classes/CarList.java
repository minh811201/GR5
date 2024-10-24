package Classes;
import java.io.*;
import java.util.*;

public class CarList {
    ArrayList<Car> cars = new ArrayList<>();
    private BrandList brandList; 
    public CarList(BrandList brandList) {
        this.brandList = brandList;
    }
    public boolean loadFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Error: File " + fileName + " does not exist.");
            return false;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length < 5) {
                    System.out.println("Error: Incorrect car format in file.");
                    continue;
                }
                String carID = parts[0];
                String brandID = parts[1];
                Brand brand = getBrandByID(brandID); 
                if (brand == null) {
                    System.out.println("Error: Brand ID " + brandID + " not found.");
                    continue;
                }
                String color = parts[2];
                String frameID = parts[3];
                String engineID = parts[4];
                cars.add(new Car(carID, brand, color, frameID, engineID));
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }
    }
    public boolean saveToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Car car : cars) {
                bw.write(car.toString() + "\n");
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
            return false;
        }
    }
    public void listCars() {
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }
    private Brand getBrandByID(String brandID) {
        int pos = brandList.searchID(brandID);
        if (pos == -1) {
            return null; 
        }
        return brandList.brands.get(pos);
    }
    public int searchID(String carID) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getCarID().equalsIgnoreCase(carID)) {
                return i;
            }
        }
        return -1;
    }
    public int searchFrame(String frameID) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getFrameID().equalsIgnoreCase(frameID)) {
                return i;
            }
        }
        return -1;
    }
    public int searchEngine(String engineID) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getEngineID().equalsIgnoreCase(engineID)) {
                return i;
            }
        }
        return -1;
    }
    public void addCar() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Car ID: ");
        String carID = sc.nextLine().trim();
        if (searchID(carID) != -1) {
            System.out.println("Error: Car ID already exists.");
            return;
        }
        System.out.print("Enter Brand ID: ");
        String brandID = sc.nextLine().trim();
        Brand brand = getBrandByID(brandID); // Retrieve the Brand object
        if (brand == null) {
            System.out.println("Error: Invalid Brand ID.");
            return;
        }
        System.out.print("Enter Color: ");
        String color = sc.nextLine().trim();
        if (color.isEmpty()) {
            System.out.println("Error: Color cannot be empty.");
            return;
        }
        System.out.print("Enter Frame ID (FXXXXX): ");
        String frameID = sc.nextLine().trim();
        if (!frameID.matches("F\\d{5}") || searchFrame(frameID) != -1) {
            System.out.println("Error: Invalid or duplicate Frame ID.");
            return;
        }
        System.out.print("Enter Engine ID (EXXXXX): ");
        String engineID = sc.nextLine().trim();
        if (!engineID.matches("E\\d{5}") || searchEngine(engineID) != -1) {
            System.out.println("Error: Invalid or duplicate Engine ID.");
            return;
        }
        cars.add(new Car(carID, brand, color, frameID, engineID));  // Pass the Brand object here
        System.out.println("Car added successfully.");
    }
    public void updateCar() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Car ID to update: ");
        String carID = sc.nextLine().trim();
        int pos = searchID(carID);
        if (pos == -1) {
            System.out.println("Error: Car not found.");
            return;
        }
        Car car = cars.get(pos);
        System.out.print("Enter new Brand ID: ");
        String brandID = sc.nextLine().trim();
        Brand brand = getBrandByID(brandID); 
        if (brand == null) {
            System.out.println("Error: Invalid Brand ID.");
            return;
        }
        System.out.print("Enter new Color: ");
        String color = sc.nextLine().trim();
        if (color.isEmpty()) {
            System.out.println("Error: Color cannot be empty.");
            return;
        }
        System.out.print("Enter new Frame ID (FXXXXX): ");
        String frameID = sc.nextLine().trim();
        if (!frameID.matches("F\\d{5}") || (searchFrame(frameID) != -1 && !car.getFrameID().equals(frameID))) {
            System.out.println("Error: Invalid or duplicate Frame ID.");
            return;
        }
        System.out.print("Enter new Engine ID (EXXXXX): ");
        String engineID = sc.nextLine().trim();
        if (!engineID.matches("E\\d{5}") || (searchEngine(engineID) != -1 && !car.getEngineID().equals(engineID))) {
            System.out.println("Error: Invalid or duplicate Engine ID.");
            return;
        }
        car.setBrand(brand);
        car.setColor(color);
        car.setFrameID(frameID);
        car.setEngineID(engineID);

        System.out.println("Car updated successfully.");
    }
    public void deleteCar() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Car ID to delete: ");
        String carID = sc.nextLine().trim();
        int pos = searchID(carID);
        if (pos == -1) {
            System.out.println("Error: Car not found.");
            return;
        }
        cars.remove(pos);
        System.out.println("Car removed successfully.");
    }
}