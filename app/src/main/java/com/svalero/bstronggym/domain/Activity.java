package com.svalero.bstronggym.domain;

public class Activity {
    private long id;
    private String name;
    private String description;
    private int capacity;
    private int durationMinutes;
    private boolean active;
    private float pricePerSession;
    private long monitorId;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public float getPricePerSession() { return pricePerSession; }
    public void setPricePerSession(float pricePerSession) { this.pricePerSession = pricePerSession; }
    public long getMonitorId() { return monitorId; }
    public void setMonitorId(long monitorId) { this.monitorId = monitorId; }
}
