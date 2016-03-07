package com.datatech.halalhubs.model;

public class Order {

    private String foodName;
    private double foodPrice;
    private double quantity;
    private double singleItemTotal;
    private String deliveryFree;
    private String salesTax;
    private String total;
    private String subTotal;
    private String orderType;

    public static final int SUBTOTAL = 0;
    public static final int DELIVERY_FEE = 1;
    public static final int SALES_TAX = 2;
    public static final int TOTAL = 3;


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

    /**
     * @return the foodPrice
     */
    public double getFoodPrice() {
        return foodPrice;
    }

    /**
     * @param foodPrice the foodPrice to set
     */
    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    /**
     * @return the quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuatity(int quatity) {
        this.quantity = quatity;
    }

    /**
     * @return the deliveryFree
     */
    public String getDeliveryFree() {
        return deliveryFree;
    }

    /**
     * @param deliveryFree the deliveryFree to set
     */
    public void setDeliveryFree(String deliveryFree) {
        this.deliveryFree = deliveryFree;
    }

    /**
     * @return the salesTax
     */
    public String getSalesTax() {
        return salesTax;
    }

    /**
     * @param salesTax the salesTax to set
     */
    public void setSalesTax(String salesTax) {
        this.salesTax = salesTax;
    }

    /**
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return the subTotal
     */
    public String getSubTotal() {
        return subTotal;
    }

    /**
     * @param subTotal the subTotal to set
     */
    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * @return the singleItemTotal
     */
    public double getSingleItemTotal() {
        return singleItemTotal;
    }

    /**
     * @param singleItemTotal the singleItemTotal to set
     */
    public void setSingleItemTotal(double singleItemTotal) {
        this.singleItemTotal = singleItemTotal;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
