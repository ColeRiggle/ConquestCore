package com.craftersconquest.database;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.util.Errors;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.object.skill.Skill;
import com.craftersconquest.object.skill.SkillFactory;
import com.craftersconquest.object.skill.type.TypeFactory;
import com.craftersconquest.player.ConquestPlayer;
import com.craftersconquest.player.CraftConquestPlayer;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class ConquestSQLSource extends ConquestDataSource {

    private final ConquestCore instance;
    private HikariDataSource hikari;

    private final String hostname;
    private final int port;
    private final String databaseName;
    private final String username;
    private final String password;

    private static final String NO_GUILD_PLACEHOLDER = "$none";

    private final SQLGuildParser sqlGuildParser;

    public ConquestSQLSource(ConquestCore instance) {
        this.instance = instance;
        hostname = SQLSettings.getSQLHostName();
        port = SQLSettings.getSQLPort();
        databaseName = SQLSettings.getSQLDatabaseName();
        username = SQLSettings.getSQLUsername();
        password = SQLSettings.getSQLPassword();
        sqlGuildParser = new SQLGuildParser();
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
    public String getStoredItem(String id) {
        return getString("items", "id", id, "base_representation");
    }

    @Override
    public void addStoredItem(String id, String data) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("REPLACE INTO items (id,base_representation) VALUES(?,?)");
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, data);
            preparedStatement.execute();
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }
    }

    @Override
    public ConquestPlayer loadPlayer(UUID playerUUID) {
        if (!databaseContainsPlayer(playerUUID)) {
            createPlayerInDatabase(playerUUID);
        }

        List<Skill> skills = loadSkills(playerUUID);
        Guild guild = loadPlayerGuild(playerUUID);

        return new CraftConquestPlayer(playerUUID, skills, guild, null);
    }

    private Guild loadPlayerGuild(UUID playerUUID) {
        String guildName = getString("players", "UUID", playerUUID.toString(), "guild");
        return guildName.equals(NO_GUILD_PLACEHOLDER) ? null : instance.getGuildManager().getGuild(guildName);
    }

    private boolean databaseContainsPlayer(UUID playerUUID) {
        return tableContains("players", "UUID", playerUUID.toString());
    }

    private boolean tableContains(String table, String keyName, String key) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT EXISTS (SELECT * FROM " + table + " WHERE " + keyName + "='"
                            + key + "' LIMIT 1)");
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
            PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO players (UUID,guild,credits) VALUES(?,?,?)");
            preparedStatement.setString(1, playerUUID.toString());
            preparedStatement.setString(2, NO_GUILD_PLACEHOLDER);
            preparedStatement.setInt(3, 0);
            preparedStatement.execute();
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }
    }

    private void addPlayerToSkillsTable(UUID playerUUID) {
        try (Connection connection = getConnection()) {
            for (String skillPrefix : TypeFactory.TYPES) {
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

    private List<Skill> loadSkills(UUID playerUUID) {

        List<Skill> skills = new ArrayList<>();
        SkillFactory skillFactory = new SkillFactory();

        for (String skillPrefix : TypeFactory.TYPES) {
            String databasePrefix = playerUUID.toString() + "_" + skillPrefix;
            double xp = getDouble("skills", "id", databasePrefix, "xp");
            int level = getInt("skills", "id", databasePrefix, "level");
            skills.add(skillFactory.getSkill(skillPrefix, xp, level));
        }

        return skills;
    }

    private String getString(String table, String keyName, String key, String column) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM " + table + " WHERE + " + keyName + " = '" + key + "';");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString(keyName).equalsIgnoreCase(key)) {
                    return resultSet.getString(column);
                }
            }

        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }

        return null;
    }

    private void setDouble(String table, String keyName, String key, String column, double value) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE " + table + " SET " + column +
                            "='" + value + "' WHERE " + keyName + " = '" + key + "';");
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }
    }

    private double getDouble(String table, String keyName, String key, String column) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM " + table + " WHERE + " + keyName + " = '" + key + "';");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString(keyName).equalsIgnoreCase(key)) {
                    return resultSet.getDouble(column);
                }
            }

        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }

        return 0.0;
    }

    private void setInt(String table, String keyName, String key, String column, int value) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE " + table + " SET " + column +
                            "='" + value + "' WHERE " + keyName + " = '" + key + "';");
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }
    }

    private int getInt(String table, String keyName, String key, String column) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM " + table + " WHERE + " + keyName + " = '" + key + "';");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString(keyName).equalsIgnoreCase(key)) {
                    return resultSet.getInt(column);
                }
            }

        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }

        return 0;
    }

    @Override
    public void savePlayer(ConquestPlayer player) {
        saveSkills(player);
        saveBasicPlayerInformation(player);
    }

    private void saveSkills(ConquestPlayer player) {
        for (Skill skill : player.getSkills()) {
            saveSkill(player, skill);
        }
    }

    private void saveSkill(ConquestPlayer player, Skill skill) {
        String databasePrefix = player.getUUID().toString() + "_" + skill.getType().getName();
        setDouble("skills", "id", databasePrefix, "xp", skill.getXp());
        setInt("skills", "id", databasePrefix, "level", skill.getLevel());
    }

    private void saveBasicPlayerInformation(ConquestPlayer player) {
        String guild = player.getGuild() == null ? NO_GUILD_PLACEHOLDER : player.getGuild().getName();
        setString("players", "guild", "uuid", player.getUUID().toString(), guild);
    }

    @Override
    public List<Guild> loadGuilds() {
        List<Guild> guilds = new ArrayList<>();

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from guilds");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                guilds.add(readCurrentGuild(resultSet));
            }
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }

        return guilds;
    }

    private Guild readCurrentGuild(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String formattedName = resultSet.getString("formatted_name");
        UUID ownerUUID = UUID.fromString(resultSet.getString("owner"));
        List<UUID> memberUUIDs = sqlGuildParser.readMembers(resultSet.getString("members"));

        return Guild.builder().name(name).formattedName(formattedName).ownerUUID(ownerUUID).memberUUIDs(memberUUIDs).build();
    }

    @Override
    public void saveGuilds(List<Guild> guilds) {
        for (Guild guild : guilds) {
            saveGuild(guild);
        }
    }

    private void saveGuild(Guild guild) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("REPLACE INTO guilds (name,formatted_name,owner,members,elo,forges,stockpile_id,upgrades_id,last_war_date) " +
                                    "VALUES(?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, guild.getName());
            preparedStatement.setString(2, guild.getFormattedName());
            preparedStatement.setString(3, guild.getOwner().toString());
            preparedStatement.setString(4, sqlGuildParser.serializeMembers(guild.getMembers()));
            preparedStatement.setInt(5, 1000);
            preparedStatement.setString(6, "");
            preparedStatement.setString(7, "id");
            preparedStatement.setString(8, "id");
            preparedStatement.setString(9, "never");
            preparedStatement.execute();
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }
    }

    @Override
    public boolean blocklistContains(Location location) {
        return tableContains("blocklist", "location", location.toString());
    }

    @Override
    public void addToBlocklist(Location location) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("REPLACE INTO blocklist (location) VALUES(?)");
            preparedStatement.setString(1, location.toString());
            preparedStatement.execute();
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }
    }

    @Override
    public void removeFromBlocklist(Location location) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM blocklist WHERE location = ?");
            preparedStatement.setString(1, location.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Bukkit.getLogger().log(Level.SEVERE, Errors.SQLStatementError, exception);
        }
    }

    private void setString(String table, String column, String keyName, String key, String value) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE " + table + " SET " + column + "='" + value + "' WHERE " + keyName + " = '" + key + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
