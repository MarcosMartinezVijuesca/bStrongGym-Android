package com.svalero.bstronggym.domain;

public class Booking {
    private long id;
    private long memberId;
    private long activityId;
    private String bookingDate;
    private boolean attended;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getMemberId() { return memberId; }
    public void setMemberId(long memberId) { this.memberId = memberId; }
    public long getActivityId() { return activityId; }
    public void setActivityId(long activityId) { this.activityId = activityId; }
    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
    public boolean isAttended() { return attended; }
    public void setAttended(boolean attended) { this.attended = attended; }
}
