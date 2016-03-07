package com.datatech.halalhubs.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Restaurant implements Serializable {

    private String restaurentID;
    private String restaurentName;
    private String Lat;
    private String Long;
    private String distance;
    private String address;
    private String image_Url;
    private String opening_Time;
    private String closing_Time;
    private String email;
    private String geoLocation;
    private String postcode;
    private String status;

    private String minimumOrderPrice;

    private ArrayList<FoodMenu> foodMenuList;

    public Restaurant() {

        foodMenuList = new ArrayList<FoodMenu>();
    }

    public void addFoodItem(FoodMenu foodMenu) {

        if (true) {
            foodMenuList.add(foodMenu);
        }
    }

    public ArrayList<FoodMenu> getFoodItem() {

        return foodMenuList;

    }

    class FoodMenu {

        private String foodName;

        /**
         * @return the foodName
         */
        public String getFoodName() {
            return foodName;
        }

        /**
         * @param foodName the foodName to set
         */
        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

    }

    /**
     * @return the name
     */
    public String getRestaurentName() {
        return restaurentName;
    }

    /**
     * @param name the name to set
     */
    public void setRestaurentName(String name) {
        this.restaurentName = name;
    }

    /**
     * @return the lat
     */
    public String getLat() {
        return Lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(String lat) {
        Lat = lat;
    }

    /**
     * @return the long
     */
    public String getLong() {
        return Long;
    }

    /**
     * @param _long the long to set
     */
    public void setLong(String _long) {
        Long = _long;
    }

    /**
     * @return the distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * @return the area
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param area the area to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the image_Url
     */
    public String getImage_Url() {
        return image_Url;
    }

    /**
     * @param image_Url the image_Url to set
     */
    public void setImage_Url(String image_Url) {
        this.image_Url = image_Url;
    }

    /**
     * @return the opening_Time
     */
    public String getOpening_Time() {
        return opening_Time;
    }

    /**
     * @param opening_Time the opening_Time to set
     */
    public void setOpening_Time(String opening_Time) {
        this.opening_Time = opening_Time;
    }

    /**
     * @return the closing_Time
     */
    public String getClosing_Time() {
        return closing_Time;
    }

    /**
     * @param closing_Time the closing_Time to set
     */
    public void setClosing_Time(String closing_Time) {
        this.closing_Time = closing_Time;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the geoLocation
     */
    public String getGeoLocation() {
        return geoLocation;
    }

    /**
     * @param geoLocation the geoLocation to set
     */
    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode the postcode to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the restaurentID
     */
    public String getRestaurentID() {
        return restaurentID;
    }

    /**
     * @param restaurentID the restaurentID to set
     */
    public void setRestaurentID(String restaurentID) {
        this.restaurentID = restaurentID;
    }

    /**
     * @return the minimumOrderPrice
     */
    public String getMinimumOrderPrice() {
        return minimumOrderPrice;
    }

    /**
     * @param minimumOrderPrice the minimumOrderPrice to set
     */
    public void setMinimumOrderPrice(String minimumOrderPrice) {
        this.minimumOrderPrice = minimumOrderPrice;
    }

}
