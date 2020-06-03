package com.craftersconquest.database;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLTableInitializer {

    private final Connection connection;
    private final SQLTableGenerator generator;
    private final List<SQLTable> tables;

    public SQLTableInitializer(Connection connection, SQLTableGenerator generator) {
        this.connection = connection;
        this.generator = generator;
        this.tables = generator.getTables();
    }

    public void initializeTables() {
        createTables();
    }

    private void createTables() {
        for (SQLTable table : generator.getTables()) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(table.getCreationCommand());
            } catch (SQLException exception) {
                Bukkit.getLogger().severe("Error A");
            }

            addTableFields(table);
        }
    }

    private void addTableFields(SQLTable table) {
        for (String column : table.getColumns()) {
            try {

                Bukkit.getLogger().info("Attempting to add table field...");

                String sql = "ALTER TABLE `" + table.getTableName() + "` ADD COLUMN " + column;

                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();

            } catch (SQLException exception) {
                if (exception.getErrorCode() != 1060) {
                    Bukkit.getLogger().severe("Error B: " + exception.toString());
                }
            }
        }

    }
}
