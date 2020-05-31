package com.craftersconquest.database;

import java.util.ArrayList;
import java.util.List;

public class SQLTableGenerator {

    public List<SQLTable> getTables() {
        List<SQLTable> tables = new ArrayList<>();
        tables.add(new PlayersTable());
        return tables;
    }
}
