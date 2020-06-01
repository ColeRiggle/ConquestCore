package com.craftersconquest.database;

import java.util.ArrayList;
import java.util.List;

public class SQLTableGenerator {

    public List<SQLTable> getTables() {
        List<SQLTable> tables = new ArrayList<>();
        tables.add(generatePlayersTable());
        tables.add(generateBlocklistTable());
        tables.add(generateSkillsTable());
        return tables;
    }

    private SQLTable generatePlayersTable() {
        List<String> columns = new ArrayList<>();
        columns.add("`coins` int(11)  DEFAULT 0");

        return new SQLTable("players", "CREATE TABLE IF NOT EXISTS players ("
                + "`UUID` VARCHAR(32) NOT NULL,"
                + "PRIMARY KEY (`UUID`)"
                + ")", columns);
    }

    private SQLTable generateBlocklistTable() {
        return new SQLTable("players", "CREATE TABLE IF NOT EXISTS blocklist ("
                + "`location` VARCHAR(32) NOT NULL,"
                + "PRIMARY KEY (`location`)"
                + ")", List.of());
    }

    private SQLTable generateSkillsTable() {
        List<String> columns = new ArrayList<>();
        columns.add("`level` int(11)  DEFAULT 0");
        columns.add("`xp` double(12)  DEFAULT 0.0");

        return new SQLTable("skills", "CREATE TABLE IF NOT EXISTS skills ("
                + "`id` VARCHAR(32) NOT NULL,"
                + "PRIMARY KEY (`id`)"
                + ")", List.of());
    }

}
