package com.craftersconquest.object.guild;

import com.craftersconquest.object.guild.upgrade.Upgrades;

import java.util.List;
import java.util.UUID;

public class Guild {

    private String name;
    private String formattedName;
    private UUID ownerUUID;
    private List<UUID> memberUUIDs;
    private Stockpile stockpile;
    private Upgrades upgrades;
    private int elo;
    private War lastWar;

    private Guild() {
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() { return formattedName; }

    public UUID getOwner() {
        return ownerUUID;
    }

    public List<UUID> getMembers() {
        return memberUUIDs;
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
        private UUID ownerUUID;
        private List<UUID> memberUUIDs;
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

        public Builder ownerUUID(UUID ownerUUID) {
            this.ownerUUID = ownerUUID;
            return this;
        }

        public Builder memberUUIDs(List<UUID> memberUUIDs) {
            this.memberUUIDs = memberUUIDs;
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
            guild.ownerUUID = ownerUUID;
            guild.memberUUIDs = memberUUIDs;
            guild.stockpile = stockpile;
            guild.upgrades = upgrades;
            guild.elo = elo;
            guild.lastWar = lastWar;
            return guild;
        }
    }
}
