package com.craftersconquest.items.heads;

public class HeadsdatabaseItem {

    private final String id;
    private final String headdatabaseId;

    private HeadsdatabaseItem(String id, String headdatabaseId) {
        this.id = id;
        this.headdatabaseId = id;
    }

    public static HeadsdatabaseItem fromIdAndHeaddatabaseId(String id, String headdatabaseId) {
        return new HeadsdatabaseItem(id, headdatabaseId);
    }

    public String getId() {
        return id;
    }

    public String getHeaddatabaseId() {
        return headdatabaseId;
    }
}
