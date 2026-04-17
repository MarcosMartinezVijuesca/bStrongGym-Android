package com.svalero.bstronggym.domain;

public class Booking {

    private long id;
    private String bookingDate;
    private boolean attended;
    private int reviewNote;
    private String reviewText;
    private float pricePaid;
    private long memberId;
    private long activityId;
    private String memberName;
    private String activityName;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
    public boolean isAttended() { return attended; }
    public void setAttended(boolean attended) { this.attended = attended; }
    public int getReviewNote() { return reviewNote; }
    public void setReviewNote(int reviewNote) { this.reviewNote = reviewNote; }
    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
    public float getPricePaid() { return pricePaid; }
    public void setPricePaid(float pricePaid) { this.pricePaid = pricePaid; }
    public long getMemberId() { return memberId; }
    public void setMemberId(long memberId) { this.memberId = memberId; }
    public long getActivityId() { return activityId; }
    public void setActivityId(long activityId) { this.activityId = activityId; }
    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }
    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }
}