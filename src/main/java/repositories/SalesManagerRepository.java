package repositories;

import database.DatabaseService;
import exceptions.UserNotFoundException;
import models.Customer;
import models.SalesManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalesManagerRepository {
    private final DatabaseService databaseService;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public SalesManagerRepository() {this.databaseService = new DatabaseService();}
    public ArrayList<SalesManager> salesManagers = new ArrayList<>();

    public String createSalesManager(SalesManager salesManager) {
        try {
            String query = "INSERT INTO salesManagers (salesName, email, password) VALUES(?, ?, ?)";
            this.connection = this.databaseService.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setString(1, salesManager.getSalesName());
            this.statement.setString(2, salesManager.getEmail());
            this.statement.setString(3, salesManager.getPassword());

            if (this.statement.executeUpdate() == 1) return "Sales Manager created successfully!";
            this.salesManagers.add(salesManager);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.databaseService.closeConnections(this.connection, null, this.statement);
        }
        return "Failed to create Sales Manager";
    }

    public SalesManager findManagerByEmail(String email) throws UserNotFoundException {
            try {
                String query = "SELECT * FROM salesManagers WHERE email = ? LIMIT 1";

                this.connection = this.databaseService.getConnection();
                this.statement = this.connection.prepareStatement(query);

                this.statement.setString(1, email);
                this.resultSet = this.statement.executeQuery();

                if (resultSet.next()) return this.createManagerFromResultSet(this.resultSet);
            } catch(SQLException exception) {
                exception.printStackTrace();
            } finally {
                this.databaseService.closeConnections(this.connection, this.resultSet, this.statement);
            }
            throw new UserNotFoundException("Sales Manager not found!");
        }

    private SalesManager createManagerFromResultSet(ResultSet resultSet) throws SQLException {
        return new SalesManager(
                resultSet.getInt("id"),
                resultSet.getString("salesName"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }
}
