package com.craftersconquest.database;

import java.util.ArrayList;
import java.util.List;

public interface SQLTable {

    String getTableName();
    String getCreationCommand();
    List<String> getColumns();
}

class PlayersTable implements SQLTable {

    @Override
    public String getTableName() {
        return "players";
    }

    @Override
    public String getCreationCommand() {
        return "CREATE TABLE IF NOT EXISTS " + getTableName() + " ("
                + "`UUID` VARCHAR(32) NOT NULL,"
                + "PRIMARY KEY (`UUID`)"
                + ")";
    }

    @Override
    public List<String> getColumns() {
        List<String> columns = new ArrayList<>();
        columns.add("`coins` int(11)  DEFAULT 0");
        return columns;
    }
}
