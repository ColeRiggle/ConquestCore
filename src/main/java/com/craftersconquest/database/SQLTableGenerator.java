package com.craftersconquest.database;

import java.util.ArrayList;
import java.util.List;

public class SQLTableGenerator {

    public List<SQLTable> getTables() {
        List<SQLTable> tables = new ArrayList<>();
        tables.add(generatePlayersTable());
        tables.add(generateBlocklistTable());
        tables.add(generateSkillsTable());
        tables.add(generateBountiesTable());
        tables.add(generateItemsTable());
        tables.add(generateGuildsTable());
        return tables;
    }

    private SQLTable generatePlayersTable() {
        List<String> columns = new ArrayList<>();
        columns.add("`credits` int(11) DEFAULT 0");

        return new SQLTable("players", "CREATE TABLE IF NOT EXISTS players ("
                + "`UUID` VARCHAR(36) NOT NULL,"
                + "PRIMARY KEY (`UUID`)"
                + ")", columns);
    }

    private SQLTable generateSkillsTable() {
        List<String> columns = new ArrayList<>();
        columns.add("`level` int(11) DEFAULT 0");
        columns.add("`xp` double(35,5) DEFAULT 0.0");

        return new SQLTable("skills", "CREATE TABLE IF NOT EXISTS skills ("
                + "`id` VARCHAR(48) NOT NULL,"
                + "PRIMARY KEY (`id`)"
                + ")", columns);
    }

    private SQLTable generateBountiesTable() {
        List<String> columns = new ArrayList<>();
        columns.add("`kills` int(11) DEFAULT 0");
        columns.add("`amount` double(35,5) DEFAULT 0.0");

        return new SQLTable("players", "CREATE TABLE IF NOT EXISTS bounties ("
                + "`UUID` VARCHAR(36) NOT NULL,"
                + "PRIMARY KEY (`UUID`)"
                + ")", columns);
    }

    private SQLTable generateBlocklistTable() {
        return new SQLTable("players", "CREATE TABLE IF NOT EXISTS blocklist ("
                + "`location` VARCHAR(80) NOT NULL,"
                + "PRIMARY KEY (`location`)"
                + ")", List.of());
    }

    private SQLTable generateItemsTable() {
        List<String> columns = new ArrayList<>();
        columns.add("`base_representation` VARCHAR(2000)");

        return new SQLTable("items", "CREATE TABLE IF NOT EXISTS items ("
                + "`id` VARCHAR(60) NOT NULL,"
                + "PRIMARY KEY (`id`)"
                + ")", columns);
    }

    private SQLTable generateGuildsTable() {
        List<String> columns = new ArrayList<>();
        columns.add("`formatted_name` VARCHAR(75)");
        columns.add("`owner_uuid` VARCHAR(36)");
        columns.add("`member_1_uuid` VARCHAR(36)");
        columns.add("`member_2_uuid` VARCHAR(36)");
        columns.add("`member_3_uuid` VARCHAR(36)");
        columns.add("`stockpile_id` VARCHAR(36)");
        columns.add("`upgrades_id` VARCHAR(36)");
        columns.add("`elo` int(11) DEFAULT 1000");
        columns.add("`last_war_date` VARCHAR(50)");

        return new SQLTable("guilds", "CREATE TABLE IF NOT EXISTS guilds ("
                + "`name` VARCHAR(60) NOT NULL,"
                + "PRIMARY KEY (`name`)"
                + ")", columns);
    }
}
