import java.sql.*;
import java.util.Scanner;

// Database connection class
class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/waterco"; // Your database URL
    private static final String USER = "root";  // Your MySQL username
    private static final String PASSWORD = "password";   // Your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// Customer class
class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String phone;
    private String email;

    public Customer(String firstName, String lastName, String address, String city, String state, String postalCode, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.phone = phone;
        this.email = email;
    }

    public void saveToDatabase() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO customers (first_name, last_name, address, city, state, postal_code, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, address);
            statement.setString(4, city);
            statement.setString(5, state);
            statement.setString(6, postalCode);
            statement.setString(7, phone);
            statement.setString(8, email);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.customerId = generatedKeys.getInt(1);
            }
        }
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Method to retrieve all customers from the database
    public static void viewAllCustomers() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM customers";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("customer_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String postalCode = resultSet.getString("postal_code");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                System.out.println("ID: " + id + ", Name: " + firstName + " " + lastName + ", Address: " + address + ", City: " + city + ", State: " + state + ", Postal Code: " + postalCode + ", Phone: " + phone + ", Email: " + email);
            }
        }
    }
}

// Meter class
class Meter {
    private int meterId;
    private int customerId;
    private String meterSerialNo;
    private String installDate;
    private String status;

    public Meter(int customerId, String meterSerialNo, String installDate, String status) {
        this.customerId = customerId;
        this.meterSerialNo = meterSerialNo;
        this.installDate = installDate;
        this.status = status;
    }

    public void saveToDatabase() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO meters (customer_id, meter_serial_no, install_date, status) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            statement.setString(2, meterSerialNo);
            statement.setString(3, installDate);
            statement.setString(4, status);
            statement.executeUpdate();
        }
    }

    // Method to retrieve all meters from the database
    public static void viewAllMeters() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM meters";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int meterId = resultSet.getInt("meter_id");
                int customerId = resultSet.getInt("customer_id");
                String meterSerialNo = resultSet.getString("meter_serial_no");
                String installDate = resultSet.getString("install_date");
                String status = resultSet.getString("status");
                System.out.println("Meter ID: " + meterId + ", Customer ID: " + customerId + ", Serial No: " + meterSerialNo + ", Install Date: " + installDate + ", Status: " + status);
            }
        }
    }
}

// Payment class
class Payment {
    private int billId;
    private double amountPaid;
    private String paymentMethod;

    public Payment(int billId, double amountPaid, String paymentMethod) {
        this.billId = billId;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
    }

    public void saveToDatabase() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO payments (bill_id, amount_paid, payment_method) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, billId);
            statement.setDouble(2, amountPaid);
            statement.setString(3, paymentMethod);
            statement.executeUpdate();
        }
    }

    // Method to retrieve all payments from the database
    public static void viewAllPayments() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM payments";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int paymentId = resultSet.getInt("payment_id");
                int billId = resultSet.getInt("bill_id");
                double amountPaid = resultSet.getDouble("amount_paid");
                String paymentMethod = resultSet.getString("payment_method");
                System.out.println("Payment ID: " + paymentId + ", Bill ID: " + billId + ", Amount Paid: " + amountPaid + ", Payment Method: " + paymentMethod);
            }
        }
    }
}

// Bill class
class Bill {
    private int customerId;
    private String billingPeriodStart;
    private String billingPeriodEnd;
    private double totalUsage;
    private double totalAmount;
    private String dueDate;

    public Bill(int customerId, String billingPeriodStart, String billingPeriodEnd, double totalUsage, double totalAmount, String dueDate) {
        this.customerId = customerId;
        this.billingPeriodStart = billingPeriodStart;
        this.billingPeriodEnd = billingPeriodEnd;
        this.totalUsage = totalUsage;
        this.totalAmount = totalAmount;
        this.dueDate = dueDate;
    }

    public void saveToDatabase() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO bills (customer_id, billing_period_start, billing_period_end, total_usage, total_amount, due_date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            statement.setString(2, billingPeriodStart);
            statement.setString(3, billingPeriodEnd);
            statement.setDouble(4, totalUsage);
            statement.setDouble(5, totalAmount);
            statement.setString(6, dueDate);
            statement.executeUpdate();
        }
    }

    // Method to retrieve all bills from the database
    public static void viewAllBills() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM bills";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int billId = resultSet.getInt("bill_id");
                int customerId = resultSet.getInt("customer_id");
                String billingPeriodStart = resultSet.getString("billing_period_start");
                String billingPeriodEnd = resultSet.getString("billing_period_end");
                double totalUsage = resultSet.getDouble("total_usage");
                double totalAmount = resultSet.getDouble("total_amount");
                String dueDate = resultSet.getString("due_date");
                System.out.println("Bill ID: " + billId + ", Customer ID: " + customerId + ", Billing Period Start: " + billingPeriodStart + ", Billing Period End: " + billingPeriodEnd + ", Total Usage: " + totalUsage + ", Total Amount: " + totalAmount + ", Due Date: " + dueDate);
            }
        }
    }
}

// Employee class
class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String phone;
    private String email;

    public Employee(String firstName, String lastName, String jobTitle, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.phone = phone;
        this.email = email;
    }

    public void saveToDatabase() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO employees (first_name, last_name, job_title, phone, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, jobTitle);
            statement.setString(4, phone);
            statement.setString(5, email);
            statement.executeUpdate();
        }
    }

    // Method to retrieve all employees from the database
    public static void viewAllEmployees() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM employees";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employee_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String jobTitle = resultSet.getString("job_title");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                System.out.println("Employee ID: " + employeeId + ", Name: " + firstName + " " + lastName + ", Job Title: " + jobTitle + ", Phone: " + phone + ", Email: " + email);
            }
        }
    }
}

// InventoryItem class
class InventoryItem {
    private int itemId;
    private String itemName;
    private int quantity;
    private String supplierName;

    public InventoryItem(String itemName, int quantity, String supplierName) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.supplierName = supplierName;
    }

    public void saveToDatabase() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO inventory (item_name, quantity, supplier_name) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, itemName);
            statement.setInt(2, quantity);
            statement.setString(3, supplierName);
            statement.executeUpdate();
        }
    }

    // Method to retrieve all inventory items from the database
    public static void viewAllInventoryItems() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM inventory";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String itemName = resultSet.getString("item_name");
                int quantity = resultSet.getInt("quantity");
                String supplierName = resultSet.getString("supplier_name");
                System.out.println("Item ID: " + itemId + ", Item Name: " + itemName + ", Quantity: " + quantity + ", Supplier Name: " + supplierName);
            }
        }
    }
}

// Main WaterSystem class
public class WaterSystem {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getIntInput("Choose an option: ");
            switch (choice) {
                case 1:
                    manageCustomers();
                    break;
                case 2:
                    manageMeters();
                    break;
                case 3:
                    managePayments();
                    break;
                case 4:
                    manageBills();
                    break;
                case 5:
                    manageEmployees();
                    break;
                case 6:
                    manageInventory();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Water Management System");
        System.out.println("1. Manage Customers");
        System.out.println("2. Manage Meters");
        System.out.println("3. Manage Payments");
        System.out.println("4. Manage Bills");
        System.out.println("5. Manage Employees");
        System.out.println("6. Manage Inventory");
        System.out.println("7. Exit");
    }

    private static void manageCustomers() {
        System.out.println("Manage Customers:");
        System.out.println("1. Add Customer");
        System.out.println("2. View All Customers");
        System.out.println("3. Back to Main Menu");

        int choice = getIntInput("Choose an option: ");
        switch (choice) {
            case 1:
                addCustomer();
                break;
            case 2:
                viewAllCustomers();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addCustomer() {
        System.out.println("Enter customer first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter customer last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter customer address:");
        String address = scanner.nextLine();
        System.out.println("Enter customer city:");
        String city = scanner.nextLine();
        System.out.println("Enter customer state:");
        String state = scanner.nextLine();
        System.out.println("Enter customer postal code:");
        String postalCode = scanner.nextLine();
        System.out.println("Enter customer phone:");
        String phone = scanner.nextLine();
        System.out.println("Enter customer email:");
        String email = scanner.nextLine();

        Customer customer = new Customer(firstName, lastName, address, city, state, postalCode, phone, email);
        try {
            customer.saveToDatabase();
            System.out.println("Customer added with ID: " + customer.getCustomerId());
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    private static void viewAllCustomers() {
        try {
            Customer.viewAllCustomers();
        } catch (SQLException e) {
            System.out.println("Error viewing customers: " + e.getMessage());
        }
    }

    private static void manageMeters() {
        System.out.println("Manage Meters:");
        System.out.println("1. Add Meter");
        System.out.println("2. View All Meters");
        System.out.println("3. Back to Main Menu");

        int choice = getIntInput("Choose an option: ");
        switch (choice) {
            case 1:
                addMeter();
                break;
            case 2:
                viewAllMeters();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addMeter() {
        System.out.println("Enter customer ID:");
        int customerId = getIntInput("");
        System.out.println("Enter meter serial number:");
        String meterSerialNo = scanner.nextLine();
        System.out.println("Enter installation date (YYYY-MM-DD):");
        String installDate = scanner.nextLine();
        System.out.println("Enter meter status (active/inactive/repair):");
        String status = scanner.nextLine();

        Meter meter = new Meter(customerId, meterSerialNo, installDate, status);
        try {
            meter.saveToDatabase();
            System.out.println("Meter added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding meter: " + e.getMessage());
        }
    }

    private static void viewAllMeters() {
        try {
            Meter.viewAllMeters();
        } catch (SQLException e) {
            System.out.println("Error viewing meters: " + e.getMessage());
        }
    }

    private static void managePayments() {
        System.out.println("Manage Payments:");
        System.out.println("1. Add Payment");
        System.out.println("2. View All Payments");
        System.out.println("3. Back to Main Menu");

        int choice = getIntInput("Choose an option: ");
        switch (choice) {
            case 1:
                addPayment();
                break;
            case 2:
                viewAllPayments();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addPayment() {
        System.out.println("Enter bill ID:");
        int billId = getIntInput("");
        System.out.println("Enter amount paid:");
        double amountPaid = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter payment method (credit_card/debit_card/cash/bank_transfer):");
        String paymentMethod = scanner.nextLine();

        Payment payment = new Payment(billId, amountPaid, paymentMethod);
        try {
            payment.saveToDatabase();
            System.out.println("Payment added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding payment: " + e.getMessage());
        }
    }

    private static void viewAllPayments() {
        try {
            Payment.viewAllPayments();
        } catch (SQLException e) {
            System.out.println("Error viewing payments: " + e.getMessage());
        }
    }

    private static void manageBills() {
        System.out.println("Manage Bills:");
        System.out.println("1. Add Bill");
        System.out.println("2. View All Bills");
        System.out.println("3. Back to Main Menu");

        int choice = getIntInput("Choose an option: ");
        switch (choice) {
            case 1:
                addBill();
                break;
            case 2:
                viewAllBills();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addBill() {
        System.out.println("Enter customer ID:");
        int customerId = getIntInput("");
        System.out.println("Enter billing period start date (YYYY-MM-DD):");
        String billingPeriodStart = scanner.nextLine();
        System.out.println("Enter billing period end date (YYYY-MM-DD):");
        String billingPeriodEnd = scanner.nextLine();
        System.out.println("Enter total usage:");
        double totalUsage = scanner.nextDouble();
        System.out.println("Enter total amount:");
        double totalAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter due date (YYYY-MM-DD):");
        String dueDate = scanner.nextLine();

        Bill bill = new Bill(customerId, billingPeriodStart, billingPeriodEnd, totalUsage, totalAmount, dueDate);
        try {
            bill.saveToDatabase();
            System.out.println("Bill added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding bill: " + e.getMessage());
        }
    }

    private static void viewAllBills() {
        try {
            Bill.viewAllBills();
        } catch (SQLException e) {
            System.out.println("Error viewing bills: " + e.getMessage());
        }
    }

    private static void manageEmployees() {
        System.out.println("Manage Employees:");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Back to Main Menu");

        int choice = getIntInput("Choose an option: ");
        switch (choice) {
            case 1:
                addEmployee();
                break;
            case 2:
                viewAllEmployees();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addEmployee() {
        System.out.println("Enter employee first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter employee last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter employee job title:");
        String jobTitle = scanner.nextLine();
        System.out.println("Enter employee phone:");
        String phone = scanner.nextLine();
        System.out.println("Enter employee email:");
        String email = scanner.nextLine();

        Employee employee = new Employee(firstName, lastName, jobTitle, phone, email);
        try {
            employee.saveToDatabase();
            System.out.println("Employee added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    private static void viewAllEmployees() {
        try {
            Employee.viewAllEmployees();
        } catch (SQLException e) {
            System.out.println("Error viewing employees: " + e.getMessage());
        }
    }

    private static void manageInventory() {
        System.out.println("Manage Inventory:");
        System.out.println("1. Add Inventory Item");
        System.out.println("2. View All Inventory Items");
        System.out.println("3. Back to Main Menu");

        int choice = getIntInput("Choose an option: ");
        switch (choice) {
            case 1:
                addInventoryItem();
                break;
            case 2:
                viewAllInventoryItems();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addInventoryItem() {
        System.out.println("Enter item name:");
        String itemName = scanner.nextLine();
        System.out.println("Enter quantity:");
        int quantity = getIntInput("");
        System.out.println("Enter supplier name:");
        String supplierName = scanner.nextLine();

        InventoryItem item = new InventoryItem(itemName, quantity, supplierName);
        try {
            item.saveToDatabase();
            System.out.println("Inventory item added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding inventory item: " + e.getMessage());
        }
    }

    private static void viewAllInventoryItems() {
        try {
            InventoryItem.viewAllInventoryItems();
        } catch (SQLException e) {
            System.out.println("Error viewing inventory items: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter an integer: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return value;
    }
}
