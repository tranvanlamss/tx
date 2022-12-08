package com.vietsoft.payload;

public class EncroachmentForm {
    private String id;

    private String checkCustomerId;

    private String checkProvince;

    private String notes;

    private long lat = 0;
    private long lng = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckCustomerId() {
        return checkCustomerId;
    }

    public void setCheckCustomerId(String checkCustomerId) {
        this.checkCustomerId = checkCustomerId;
    }

    public String getCheckProvince() {
        return checkProvince;
    }

    public void setCheckProvince(String checkProvince) {
        this.checkProvince = checkProvince;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }
}
