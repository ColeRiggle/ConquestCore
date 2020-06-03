package com.craftersconquest.database;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.ConquestSettings;
import com.craftersconquest.core.utility.Errors;
import com.craftersconquest.objects.Guild;
import com.craftersconquest.objects.skill.SkillFactory;
import com.craftersconquest.player.ConquestPlayer;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

        return null;
    }

    private boolean databaseContainsPlayer(UUID playerUUID) {
        PreparedStatement preparedStatement = createPreparedStatement("SELECT EXISTS (SELECT * FROM players WHERE UUID='"
                + playerUUID.toString() + "' LIMIT 1)");

        ResultSet resultSet = executeSQLStatementWithResult(preparedStatement);

        try {
            return resultSet.getBoolean(1);
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }

        return false;
    }

    private PreparedStatement createPreparedStatement(String statement) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(statement);
            return preparedStatement;
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }

        return null;
    }

    private void executeSQLStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }
    }

    private ResultSet executeSQLStatementWithResult(PreparedStatement preparedStatement) {
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }

        return null;
    }

    private void createPlayerInDatabase(UUID playerUUID) {
        addPlayerToPlayersTable(playerUUID);
        addPlayerToSkillsTable(playerUUID);
    }

    private void addPlayerToPlayersTable(UUID playerUUID) {
        try {
            PreparedStatement preparedStatement = createPreparedStatement("REPLACE INTO players (UUID,coins) VALUES(?,?)");
            preparedStatement.setString(1, playerUUID.toString());
            preparedStatement.setInt(2, 0);
            executeSQLStatement(preparedStatement);
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }
    }

    private void addPlayerToSkillsTable(UUID playerUUID) {
        try {
            for (String skillPrefix : (new SkillFactory()).getTypes()) {
                PreparedStatement preparedStatement = createPreparedStatement("REPLACE INTO skills (id,level,xp) VALUES(?,?,?)");
                preparedStatement.setString(1, playerUUID.toString() + "_" + skillPrefix);
                preparedStatement.setInt(2, 0);
                preparedStatement.setDouble(3, 0.0);
                executeSQLStatement(preparedStatement);
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
