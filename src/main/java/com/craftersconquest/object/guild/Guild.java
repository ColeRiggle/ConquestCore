package com.craftersconquest.object.guild;

import com.craftersconquest.object.guild.upgrade.Upgrades;
import com.craftersconquest.player.ConquestPlayer;

import java.util.List;

public class Guild {

    private String name;
    private String formattedName;
    private ConquestPlayer owner;
    private List<ConquestPlayer> members;
    private Stockpile stockpile;
    private Upgrades upgrades;
    private int elo;
    private War lastWar;

    private Guild() {
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() { return name; }

    public ConquestPlayer getOwner() {
        return owner;
    }

    public List<ConquestPlayer> getMembers() {
        return members;
    }

    public Stockpile getStockpile() {
        return stockpile;
    }

    public Upgrades getUpgrades() {
        return upgrades;
    }

    public int getElo() {
        return elo;
    }

    public War getLastWar() {
        return lastWar;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String name;
        private String formattedName;
        private ConquestPlayer owner;
        private List<ConquestPlayer> members;
        private Stockpile stockpile;
        private Upgrades upgrades;
        private int elo;
        private War lastWar;

        private Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder formattedName(String formattedName) {
            this.formattedName = formattedName;
            return this;
        }

        public Builder owner(ConquestPlayer owner) {
            this.owner = owner;
            return this;
        }

        public Builder members(List<ConquestPlayer> members) {
            this.members = members;
            return this;
        }

        public Builder stockpile(Stockpile stockpile) {
            this.stockpile = stockpile;
            return this;
        }

        public Builder upgrades(Upgrades upgrades) {
            this.upgrades = upgrades;
            return this;
        }

        public Builder elo(int elo) {
            this.elo = elo;
            return this;
        }

        public Builder lastWar(War lastWar) {
            this.lastWar = lastWar;
            return this;
        }

        public Guild build() {
            Guild guild = new Guild();
            guild.name = name;
            guild.formattedName = formattedName;
            guild.owner = owner;
            guild.members = members;
            guild.stockpile = stockpile;
            guild.upgrades = upgrades;
            guild.elo = elo;
            guild.lastWar = lastWar;
            return guild;
        }
    }
}
