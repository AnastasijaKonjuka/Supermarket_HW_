package repositories;

import database.DatabaseService;
import exceptions.ProductNotFoundException;
import models.Customer;
import models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductRepository {
    private final DatabaseService databaseService;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public ProductRepository() {this.databaseService = new DatabaseService();}

    private ArrayList<Product> products = new ArrayList<>();


    public Product findProductByName(String name) throws ProductNotFoundException {
        try {
            String query = "SELECT * FROM products WHERE name = ? LIMIT 1";

            this.connection = this.databaseService.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setString(1, name);
            this.resultSet = this.statement.executeQuery();

            if (resultSet.next()) return this.createProductFromResultSet(this.resultSet);
        } catch(SQLException exception) {
            exception.printStackTrace();
        } finally {
            this.databaseService.closeConnections(this.connection, this.resultSet, this.statement);
        }
        throw new ProductNotFoundException("Product not found!");
    }

    private Product createProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("measurement"),
                resultSet.getInt("quantityAvailable"),
                resultSet.getDouble("cost"),
                resultSet.getDouble("sellingPrice")
        );
    }

    public String decreaseQuantity(Product product, Integer quantity) {
        for (Product currentProduct : this.products) {
            if (currentProduct.getProductName().equals(product.getProductName())) {
                currentProduct.setQuantityAvailable(currentProduct.getQuantityAvailable() - quantity);
                try {
                    String query = "UPDATE products SET quantityAvailable = ? WHERE name = ?";

                    this.connection = this.databaseService.getConnection();
                    this.statement = this.connection.prepareStatement(query);

                    this.statement.setInt(1, currentProduct.getQuantityAvailable());
                    this.statement.setString(2, currentProduct.getProductName());

                    if (this.statement.executeUpdate() == 1) return "Quantity updated successfully";

                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    this.databaseService.closeConnections(this.connection, this.resultSet, this.statement);
                }
            }
        } return "Failed to update quantity";
    }
}
