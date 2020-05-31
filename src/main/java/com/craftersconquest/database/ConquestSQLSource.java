package com.craftersconquest.database;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.ConquestSettings;
import com.craftersconquest.objects.Guild;
import com.craftersconquest.player.ConquestPlayer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;

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
    public ConquestPlayer loadPlayer(String UUID) {
        return null;
    }

    @Override
    public Guild loadGuild(String name) {
        return null;
    }
}
