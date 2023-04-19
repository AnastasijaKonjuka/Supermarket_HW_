package controllers;

import models.*;
import repositories.CustomerRepository;
import repositories.ProductRepository;
import repositories.SalesRepository;


public class UserController {
    private Customer activeCustomer;
    private SalesManager activeManager;
    private ShopOwner activeOwner;
    private boolean isValidated;
    private CustomerRepository customerRepository = new CustomerRepository();
    private ProductRepository productRepository = new ProductRepository();
    private SalesRepository salesRepository = new SalesRepository();

    public String loginCustomer(Customer customer, String password) throws Exception {
        if (!customer.getCustomerPassword().equals(password)) throw new Exception("Invalid password!");

        this.isValidated = true;
        this.activeCustomer = customer;

        return "Login successful!";
    }

    public String loginManager(SalesManager salesManager, String password) throws Exception {
        if (!salesManager.getPassword().equals(password)) throw new Exception("Invalid password!");

        this.isValidated = true;
        this.activeManager = salesManager;

        return "Login successful!";
    }

    public String loginOwner(ShopOwner shopOwner, String password) throws Exception {
        if (!shopOwner.getPassword().equals(password)) throw new Exception("Invalid password!");

        this.isValidated = true;
        this.activeOwner = shopOwner;

        return "Login successful!";
    }

    public String logOffCustomer() {
        this.activeCustomer = null;
        this.isValidated = false;

        return "Good bye!";
    }

    public String logOffManager() {
        this.activeManager = null;
        this.isValidated = false;

        return "Good bye!";
    }

    public String logOffOwner() {
        this.activeOwner = null;
        this.isValidated = false;

        return "Good bye!";
    }

    public String seeBalance() {
        return "Your current balance is " + this.activeCustomer.getCustomerBalance();
    }

    public String addBalance(Double amountToAdd) {
        Double newBalance = this.activeCustomer.getCustomerBalance() + amountToAdd;
        this.customerRepository.updateBalance(this.activeCustomer, newBalance);
        return "Deposit successful, new balance is: " + this.activeCustomer.getCustomerBalance();
    }

    public String buyProduct(String productToBuy, Integer quantityToBuy) throws Exception {

        Product currentProduct = this.productRepository.findProductByName(productToBuy);
        Double totalPrice = quantityToBuy * currentProduct.getSellingPrice();
        Double newBalance = activeCustomer.getCustomerBalance() - totalPrice;


        if (totalPrice > activeCustomer.getCustomerBalance()) return "Not enough money, please increase balance";
        this.customerRepository.updateBalance(this.activeCustomer, newBalance);
        this.productRepository.decreaseQuantity(currentProduct, quantityToBuy);

        String customerEmail = activeCustomer.getCustomerEmail();
        String productName = currentProduct.getProductName();
        Double productCost = currentProduct.getCost();
        Double productPrice = currentProduct.getSellingPrice();

        this.salesRepository.addRecord(customerEmail, productName, quantityToBuy, productCost, productPrice, totalPrice);

        return "Thank you! Payment is successful!";
    }
}
