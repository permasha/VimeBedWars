package ru.permasha.vimebedwars.database;

import org.bukkit.entity.Player;

import java.sql.*;

public class BedWarsDatabase {
    private final Connection connection;

    public BedWarsDatabase(String path) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS players (" +
                    "uuid TEXT PRIMARY KEY, " +
                    "username TEXT NOT NULL, " +
                    "kills INTEGER NOT NULL DEFAULT 0, " +
                    "beds INTEGER NOT NULL DEFAULT 0, " +
                    "wins INTEGER NOT NULL DEFAULT 0)");
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void addPlayer(Player player) {
        validateConnection();
        //this should error if the player already exists
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (uuid, username) VALUES (?, ?)")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, player.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlayerExists(Player player) {
        validateConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updatePlayerData(Player player, DataType dataType, int amount) {
        validateConnection();

        if (!isPlayerExists(player)){
            addPlayer(player);
        }

        String dStr = dataType.toString().toLowerCase();

        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET " + dStr +" = ? WHERE uuid = ?")) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerData(Player player, DataType dataType) {
        validateConnection();
        String dStr = dataType.toString().toLowerCase();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + dStr + " FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(dStr);
            } else {
                return 0; // Return 0 if the player has no points
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void validateConnection() {
        try {
            connection.isValid(100000);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
