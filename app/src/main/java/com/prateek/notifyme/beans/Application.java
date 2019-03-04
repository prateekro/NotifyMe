package com.prateek.notifyme.beans;

public class Application {

    private String appId;
    private String appName;
    private String priority;
    private boolean enabled;
    private String category;
    private int totalNotifications;
    private int unreadNotifications;
    private String lastNotificationTimestamp;
    private String readTimestamp;
    private String userId;

    public Application(String appId, String appName, String priority, boolean enabled, String category, int totalNotifications, int unreadNotifications, String lastNotificationTimestamp, String readTimestamp, String userId) {
        this.appId = appId;
        this.appName = appName;
        this.priority = priority;
        this.enabled = enabled;
        this.category = category;
        this.totalNotifications = totalNotifications;
        this.unreadNotifications = unreadNotifications;
        this.lastNotificationTimestamp = lastNotificationTimestamp;
        this.readTimestamp = readTimestamp;
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalNotifications() {
        return totalNotifications;
    }

    public void setTotalNotifications(int totalNotifications) {
        this.totalNotifications = totalNotifications;
    }

    public int getUnreadNotifications() {
        return unreadNotifications;
    }

    public void setUnreadNotifications(int unreadNotifications) {
        this.unreadNotifications = unreadNotifications;
    }

    public String getLastNotificationTimestamp() {
        return lastNotificationTimestamp;
    }

    public void setLastNotificationTimestamp(String lastNotificationTimestamp) {
        this.lastNotificationTimestamp = lastNotificationTimestamp;
    }

    public String getReadTimestamp() {
        return readTimestamp;
    }

    public void setReadTimestamp(String readTimestamp) {
        this.readTimestamp = readTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
