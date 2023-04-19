package controllers;

import database.DatabaseService;
import models.Customer;
import models.SalesManager;
import models.ShopOwner;
import repositories.CustomerRepository;
import repositories.SalesManagerRepository;
import repositories.ShopOwnerRepository;

import javax.swing.*;

public class MenuController {

    private DatabaseService databaseService = new DatabaseService();
    private CustomerRepository customerRepository = new CustomerRepository();
    private UserController userController = new UserController();
    private SalesManagerRepository salesManagerRepository = new SalesManagerRepository();
    private ShopOwnerRepository shopOwnerRepository = new ShopOwnerRepository();

    public MenuController() {}

    public void start() throws Exception {
        this.displayMessage("Welcome to Supermarket!");
        this.displayMainMenu();
    }

    private void displayMainMenu() throws Exception {
        String[] availableOptions = {"Customer", "Sales Manager", "Shop owner"};
        String option = (String) JOptionPane.showInputDialog(
                null,
                "Select interface",
                "Supermarket",
                JOptionPane.QUESTION_MESSAGE,
                null,
                availableOptions,
                availableOptions[0]
        );

        switch (option) {
            case "Customer" -> this.customerStartMenu();
            case "Sales Manager" -> this.managerLogin();
            case "Shop owner" -> this.ownerLogin();
        }
        this.displayMainMenu();
    }

    private void ownerLogin() throws Exception {
        String email = this.getUserInput("Please enter email");
        String password = this.getUserInput("Please enter password");

        try {
            ShopOwner shopOwner = this.shopOwnerRepository.findOwnerByEmail(email);
            this.displayMessage(this.userController.loginOwner(shopOwner, password));
        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
        this.ownerMenu();
    }

    private void ownerMenu() {
        String[] availableOptions = {"Register new Sales Manager", "Sales History", "Profit/Loss Statement", "Exit"};
        String option = (String) JOptionPane.showInputDialog(
                null,
                "Select option",
                "Supermarket. Owner menu",
                JOptionPane.QUESTION_MESSAGE,
                null,
                availableOptions,
                availableOptions[0]
        );

        switch (option) {
            case "Register new Sales Manager" -> managerRegistration();
            //case "Sales History" -> viewSalesHistory();
            //case "Profit/Loss Statement" -> viewProfitLoss();
            case "Exit" -> this.exitOwner();
        }
        this.ownerMenu();
    }

    private void managerRegistration() {
        String managerName = this.getUserInput("Enter Sales Manager Name");
        String managerEmail = this.getUserInput("Enter Sales Manager email");
        String managerPassword = this.getUserInput("Enter Sales Manager password");

        SalesManager salesManager = new SalesManager(managerName, managerEmail, managerPassword);

        this.displayMessage(this.salesManagerRepository.createSalesManager(salesManager));
    }

    private void managerLogin() throws Exception {
        String email = this.getUserInput("Please enter email");
        String password = this.getUserInput("Please enter password");

        try {
            SalesManager salesManager = this.salesManagerRepository.findManagerByEmail(email);
            this.displayMessage(this.userController.loginManager(salesManager, password));
        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
        this.managerMenu();
    }

    private void managerMenu() {
        String[] availableOptions = {"Add product", "Update product quantity", "Sales History", "Exit"};
        String option = (String) JOptionPane.showInputDialog(
                null,
                "Select option",
                "Supermarket. Owner menu",
                JOptionPane.QUESTION_MESSAGE,
                null,
                availableOptions,
                availableOptions[0]
        );

        switch (option) {
            //case "Add product" -> addProduct();
            //case "Update product quantity" -> updateProductQuantity();
            //case "Sales History" -> viewSalesHistory();

            case "Exit" -> this.exitManager();
        }
        this.managerMenu();
    }


    private void customerStartMenu() throws Exception {
        String[] availableOptions = {"Login", "Register"};
        String option = (String) JOptionPane.showInputDialog(
                null,
                "Select option",
                "Supermarket. Customer menu",
                JOptionPane.QUESTION_MESSAGE,
                null,
                availableOptions,
                availableOptions[0]
        );

        switch (option) {
            case "Login" -> customerLogin();
            case "Register" -> customerRegistration();
        }
    }

    private void customerRegistration() {
        String customerName = this.getUserInput("Enter your Name");
        String customerEmail = this.getUserInput("Enter your email");
        String customerPassword = this.getUserInput("Enter your password");
        double customerBalance = Double.parseDouble(this.getUserInput("Enter the amount to deposit"));

        Customer customer = new Customer(customerName, customerEmail, customerPassword, customerBalance);

        this.displayMessage(this.customerRepository.createCustomer(customer));
    }

    private void customerLogin() throws Exception {
        String email = this.getUserInput("Please enter email");
        String password = this.getUserInput("Please enter password");

        try {
            Customer customer = this.customerRepository.findCustomerByEmail(email);
            this.displayMessage(this.userController.loginCustomer(customer, password));
        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
        this.customerMenu();
    }

    private void customerMenu() throws Exception {
        String[] availableOptions = {"Buy product", "See balance", "Increase balance", "Exit"};
        String option = (String) JOptionPane.showInputDialog(
                null,
                "Select option",
                "Supermarket. Owner menu",
                JOptionPane.QUESTION_MESSAGE,
                null,
                availableOptions,
                availableOptions[0]
        );

        switch (option) {
            case "Buy product" -> this.buyProduct();
            case "See balance" -> this.displayMessage(this.userController.seeBalance());
            case "Increase balance" -> this.handleAddBalance();
            case "Exit" -> this.exitCustomer();
        }
        this.customerMenu();
    }


    private void buyProduct() throws Exception {
        String productToBuy = this.getUserInput("Enter product name");
        Integer quantityToBuy = Integer.parseInt(this.getUserInput("Enter the quantity"));

        this.displayMessage(this.userController.buyProduct(productToBuy, quantityToBuy));
    }

    private void handleAddBalance() {
        double amountToAdd = Double.parseDouble(this.getUserInput("Please enter amount to add"));
        this.displayMessage(this.userController.addBalance(amountToAdd));
    }

    private void exitCustomer() {
        this.displayMessage(this.userController.logOffCustomer());
        System.exit(0);
    }

    private void exitManager() {
        this.displayMessage(this.userController.logOffManager());
        System.exit(0);
    }

    private void exitOwner() {
        this.displayMessage(this.userController.logOffOwner());
        System.exit(0);
    }

    private String getUserInput(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

}
