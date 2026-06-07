package com.svalero.bstronggym.domain;

public class Subscription {

    private long id;
    private String type; // MONTHLY, QUARTERLY, ANNUAL
    private String startDate;
    private String endDate;
    private float price;
    private boolean active;
    private boolean autoRenewal;
    private long memberId;
    private String memberName;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public boolean isAutoRenewal() { return autoRenewal; }
    public void setAutoRenewal(boolean autoRenewal) { this.autoRenewal = autoRenewal; }
    public long getMemberId() { return memberId; }
    public void setMemberId(long memberId) { this.memberId = memberId; }
    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }
}