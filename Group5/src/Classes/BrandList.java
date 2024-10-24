package Classes;
import java.io.*;
import java.util.*;

public class BrandList {
    ArrayList<Brand> brands = new ArrayList<>();
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
                if (parts.length < 3) {
                    System.out.println("Error: Incorrect brand format in file.");
                    continue;
                }
                try {
                    String brandID = parts[0];
                    String brandName = parts[1];
                    String[] soundAndPrice = parts[2].split(":");
                    String soundBrand = soundAndPrice[0].trim();
                    double price = Double.parseDouble(soundAndPrice[1].trim());
                    brands.add(new Brand(brandID, brandName, soundBrand, price));
                } catch (Exception e) {
                    System.out.println("Error: Could not parse line - " + line);
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }
    }
    public boolean saveToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Brand brand : brands) {
                bw.write(brand.toString() + "\n");
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
            return false;
        }
    }
    public void listBrands() {
        if (brands.isEmpty()) {
            System.out.println("No brands available.");
        } else {
            for (Brand brand : brands) {
                System.out.println(brand);
            }
        }
    }
    public void addBrand() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Brand ID: ");
        String id = sc.nextLine().trim();
        if (searchID(id) != -1) {
            System.out.println("Error: Brand ID already exists.");
            return;
        }
        System.out.print("Enter Brand Name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: Brand Name cannot be empty.");
            return;
        }
        System.out.print("Enter Sound Brand: ");
        String soundBrand = sc.nextLine().trim();
        if (soundBrand.isEmpty()) {
            System.out.println("Error: Sound Brand cannot be empty.");
            return;
        }
        System.out.print("Enter Price: ");
        double price;
        try {
            price = sc.nextDouble();
            if (price <= 0) {
                System.out.println("Error: Price must be a positive number.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid price.");
            return;
        }
        brands.add(new Brand(id, name, soundBrand, price));
        System.out.println("Brand added successfully.");
    }
    public int searchID(String id) {
        for (int i = 0; i < brands.size(); i++) {
            if (brands.get(i).getBrandID().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }
    public void updateBrand() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Brand ID to update: ");
        String id = sc.nextLine().trim();
        int pos = searchID(id);
        if (pos == -1) {
            System.out.println("Error: Brand not found.");
            return;
        }
        System.out.print("Enter new Brand Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter new Sound Brand: ");
        String soundBrand = sc.nextLine().trim();
        System.out.print("Enter new Price: ");
        double price;
        try {
            price = sc.nextDouble();
            if (price <= 0) {
                System.out.println("Error: Price must be a positive number.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid price.");
            return;
        }
        Brand brand = brands.get(pos);
        brand.setBrandName(name);
        brand.setSoundBrand(soundBrand);
        brand.setPrice(price);
        System.out.println("Brand updated successfully.");
    }
}

 
