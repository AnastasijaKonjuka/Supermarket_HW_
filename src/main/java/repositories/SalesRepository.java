package repositories;

import database.DatabaseService;
import models.Customer;
import models.Product;
import models.Sales;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalesRepository {
    private final DatabaseService databaseService;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public SalesRepository() {this.databaseService = new DatabaseService();}

    private ArrayList<Sales> salesHistory = new ArrayList<>();


    public String addRecord(String customerEmail, String productName, Integer quantity, Double productCost, Double productPrice, Double totalSales) throws SQLException {
        try {
            String query = "INSERT INTO sales (customerEmail, productName, quantitySold, productCost, productPrice, totalSales) VALUES(?, ?, ?, ?, ?, ?)";
        this.connection = this.databaseService.getConnection();
        this.statement = this.connection.prepareStatement(query);

        this.statement.setString(1, customerEmail);
        this.statement.setString(2, productName);
        this.statement.setInt(3, quantity);
        this.statement.setDouble(4, productCost);
        this.statement.setDouble(5, productPrice);
        this.statement.setDouble(6, totalSales);

        if (this.statement.executeUpdate() == 1) return "Sales record created successfully!";
        Sales sales = new Sales(customerEmail, productName, quantity, productCost, productPrice, totalSales);
        this.salesHistory.add(sales);
    } catch (
    SQLException e) {
        e.printStackTrace();
    } finally {
        this.databaseService.closeConnections(this.connection, null, this.statement);
    }
        return "Failed to create sales record";
    }
}
