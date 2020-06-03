package com.craftersconquest.database;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.ConquestSettings;
import com.craftersconquest.core.utility.Errors;
import com.craftersconquest.objects.Guild;
import com.craftersconquest.objects.skill.SkillFactory;
import com.craftersconquest.player.ConquestPlayer;
import com.craftersconquest.player.OfflineConquestPlayer;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

public class ConquestSQLSource extends ConquestDataSource {

    private final ConquestCore conquestCore;
    private HikariDataSource hikari;

    private final String hostname;
    private final int port;
    private final String databaseName;
    private final String username;
    private final String password;

    public ConquestSQLSource(ConquestCore conquestCore) {
        this.conquestCore = conquestCore;
        hostname = ConquestSettings.getSQLHostName();
        port = ConquestSettings.getSQLPort();
        databaseName = ConquestSettings.getSQLDatabaseName();
        username = ConquestSettings.getSQLUsername();
        password = ConquestSettings.getSQLPassword();
    }

    private void setupConnection() {
        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(10);
        hikari.setJdbcUrl(generateJdbcUrl());
        hikari.setUsername(username);
        hikari.setPassword(password);
    }

    private String generateJdbcUrl() {
        return String.format("jdbc:mysql://%s:%s/%s?autoReconnect=true&useSSL=false", hostname, port, databaseName);
    }

    public Connection getConnection() {
        try {
            return hikari.getConnection();
        } catch (SQLException exception) {
            Bukkit.getLogger().severe("Error while setting up MySQL: " + exception.toString());
        }

        return null;
    }

    @Override
    public void open() {
        setupConnection();
        SQLTableInitializer tableInitializer = new SQLTableInitializer(getConnection(), new SQLTableGenerator());
        tableInitializer.initializeTables();
    }

    @Override
    public void close() {
        hikari.close();
    }

    @Override
    public ConquestPlayer loadPlayer(UUID playerUUID) {
        if (!databaseContainsPlayer(playerUUID)) {
            createPlayerInDatabase(playerUUID);
        }

        // Create player object from database values

        return new OfflineConquestPlayer(playerUUID, null, null, null);
    }

    private boolean databaseContainsPlayer(UUID playerUUID) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS (SELECT * FROM players WHERE UUID='"
                    + playerUUID.toString() + "' LIMIT 1)");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }

        return false;
    }


    private void createPlayerInDatabase(UUID playerUUID) {
        addPlayerToPlayersTable(playerUUID);
        addPlayerToSkillsTable(playerUUID);
    }

    private void addPlayerToPlayersTable(UUID playerUUID) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO players (UUID,credits) VALUES(?,?)");
            preparedStatement.setString(1, playerUUID.toString());
            preparedStatement.setInt(2, 0);
            preparedStatement.execute();
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }
    }

    private void addPlayerToSkillsTable(UUID playerUUID) {
        try (Connection connection = getConnection()) {
            for (String skillPrefix : (new SkillFactory()).getTypes()) {
                PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO skills (id,level,xp) VALUES(?,?,?)");
                preparedStatement.setString(1, playerUUID.toString() + "_" + skillPrefix);
                preparedStatement.setInt(2, 0);
                preparedStatement.setDouble(3, 0.0);
                preparedStatement.execute();
            }
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }
    }

    private void loadSkills(UUID playerUUID) {

    }

    @Override
    public void savePlayer(ConquestPlayer player) {

    }

    @Override
    public Guild loadGuild(String name) {
        return null;
    }
}
