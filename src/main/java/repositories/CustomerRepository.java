package repositories;

import controllers.UserController;
import database.DatabaseService;
import exceptions.ProductNotFoundException;
import exceptions.UserNotFoundException;
import models.Customer;
import models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerRepository {
    private final DatabaseService databaseService;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private ProductRepository productRepository = new ProductRepository();

    public CustomerRepository() {this.databaseService = new DatabaseService();}

    public ArrayList<Customer> customers = new ArrayList<>();

    public String createCustomer(Customer customer) {
        try {
            String query = "INSERT INTO customers (customerName, email, password, balance) VALUES(?, ?, ?, ?)";
            this.connection = this.databaseService.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setString(1, customer.getCustomerName());
            this.statement.setString(2, customer.getCustomerEmail());
            this.statement.setString(3, customer.getCustomerPassword());
            this.statement.setDouble(4, customer.getCustomerBalance());

            if (this.statement.executeUpdate() == 1) return "Customer created successfully!";
            this.customers.add(customer);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.databaseService.closeConnections(this.connection, null, this.statement);
        }
        return "Failed to create customer";
    }

    public Customer findCustomerByEmail(String email) throws UserNotFoundException {
       try {
           String query = "SELECT * FROM customers WHERE email = ? LIMIT 1";

           this.connection = this.databaseService.getConnection();
           this.statement = this.connection.prepareStatement(query);

           this.statement.setString(1, email);
           this.resultSet = this.statement.executeQuery();

           if (resultSet.next()) return this.createCustomerFromResultSet(this.resultSet);
       } catch(SQLException exception) {
           exception.printStackTrace();
       } finally {
           this.databaseService.closeConnections(this.connection, this.resultSet, this.statement);
       }
        throw new UserNotFoundException("Customer not found!");
    }

    private Customer createCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        return new Customer(
                resultSet.getInt("id"),
                resultSet.getString("customerName"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getDouble("balance")
        );
    }

    public String updateBalance(Customer customer, Double amount) {
        for (Customer currentCustomer : this.customers) {
            if (currentCustomer.getCustomerEmail().equals(customer.getCustomerEmail())) {
                currentCustomer.setCustomerBalance(amount);
                try {
                    String query = "UPDATE customers SET balance = ? WHERE email = ?";

                    this.connection = this.databaseService.getConnection();
                    this.statement = this.connection.prepareStatement(query);

                    this.statement.setDouble(1, amount);
                    this.statement.setString(2, currentCustomer.getCustomerEmail());

                    if (this.statement.executeUpdate() == 1) return "Balance updated successfully";

                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    this.databaseService.closeConnections(this.connection, this.resultSet, this.statement);
                }
            }
        } return "Failed to update balance";
    }


    public String decreaseBalance(Customer customer, Double amount) {
        for (Customer currentCustomer : this.customers) {
            if (currentCustomer.getCustomerEmail().equals(customer.getCustomerEmail())) {
                currentCustomer.setCustomerBalance(currentCustomer.getCustomerBalance() - amount);
                try {
                    String query = "UPDATE customers SET balance = ? WHERE email = ?";

                    this.connection = this.databaseService.getConnection();
                    this.statement = this.connection.prepareStatement(query);

                    this.statement.setDouble(1, currentCustomer.getCustomerBalance());
                    this.statement.setString(2, currentCustomer.getCustomerEmail());

                    if (this.statement.executeUpdate() == 1) return "Balance updated successfully";

                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    this.databaseService.closeConnections(this.connection, this.resultSet, this.statement);
                }
            }
        } return "Failed to update balance";
    }
}
