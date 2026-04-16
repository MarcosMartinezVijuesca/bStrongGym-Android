package com.svalero.bstronggym.domain;

public class Subscription {
    private long id;
    private long memberId;
    private String type; // MONTHLY, QUARTERLY, ANNUAL
    private String startDate;
    private String endDate;
    private boolean active;
    private float price;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getMemberId() { return memberId; }
    public void setMemberId(long memberId) { this.memberId = memberId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
}
