package repositories;

import database.DatabaseService;
import exceptions.UserNotFoundException;
import models.SalesManager;
import models.ShopOwner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShopOwnerRepository {
    private final DatabaseService databaseService;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public ShopOwnerRepository() {this.databaseService = new DatabaseService();}
    public ArrayList<ShopOwner> shopOwners = new ArrayList<>();


    public ShopOwner findOwnerByEmail(String email) throws UserNotFoundException {
        try {
            String query = "SELECT * FROM shopOwners WHERE email = ? LIMIT 1";

            this.connection = this.databaseService.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setString(1, email);
            this.resultSet = this.statement.executeQuery();

            if (resultSet.next()) return this.createOwnerFromResultSet(this.resultSet);
        } catch(SQLException exception) {
            exception.printStackTrace();
        } finally {
            this.databaseService.closeConnections(this.connection, this.resultSet, this.statement);
        }
        throw new UserNotFoundException("Owner not found!");
    }

    private ShopOwner createOwnerFromResultSet(ResultSet resultSet) throws SQLException {
        return new ShopOwner(
                resultSet.getInt("id"),
                resultSet.getString("ownerName"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }
}
