package com.craftersconquest.database;

import java.util.List;

public class SQLTable {

    private final String tableName;
    private final String creationCommand;
    private final List<String> columns;

    public SQLTable(String tableName, String creationCommand, List<String> columns) {
        this.tableName = tableName;
        this.creationCommand = creationCommand;
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public String getCreationCommand() {
        return creationCommand;
    }

    public List<String> getColumns() {
        return columns;
    }
}
