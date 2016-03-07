package com.datatech.halalhubs.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Food implements Serializable {

	private String foodName;
	private String foodPrice;
	private String foodDescription;
	private String foodPictureUrl;
	private String foodTypeName;
	private String foodIDNo;
	private int foodType;
	

	public static final int FOOD_ITEM = 0;
	public static final int FOOD_SECTION = 1;

	public static int sectionPosition;
	public static int listPosition;

	ArrayList<Food> arrayList;

	public Food(int type) {
		// TODO Auto-generated constructor stub

		// arrayList = new ArrayList<Food>();

		this.setFoodType(type);

	}

	public void AddFood(Food food) {

		arrayList.add(food);
	}

	/**
	 * @return the foodName
	 */
	public String getFoodName() {
		return foodName;
	}

	/**
	 * @param foodName
	 *            the foodName to set
	 */
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	/**
	 * @return the foodPrice
	 */
	public String getFoodPrice() {
		return foodPrice;
	}

	/**
	 * @param foodPrice
	 *            the foodPrice to set
	 */
	public void setFoodPrice(String foodPrice) {
		this.foodPrice = foodPrice;
	}

	/**
	 * @return the foodDescription
	 */
	public String getFoodDescription() {
		return foodDescription;
	}

	/**
	 * @param foodDescription
	 *            the foodDescription to set
	 */
	public void setFoodDescription(String foodDescription) {
		this.foodDescription = foodDescription;
	}

	/**
	 * @return the foodPictureUrl
	 */
	public String getFoodPictureUrl() {
		return foodPictureUrl;
	}

	/**
	 * @param foodPictureUrl
	 *            the foodPictureUrl to set
	 */
	public void setFoodPictureUrl(String foodPictureUrl) {
		this.foodPictureUrl = foodPictureUrl;
	}

	/**
	 * @return the foodTypeName
	 */
	public String getFoodTypeName() {
		return foodTypeName;
	}

	/**
	 * @param foodTypeName
	 *            the foodTypeName to set
	 */
	public void setFoodTypeName(String foodType) {
		this.foodTypeName = foodType;
	}

	/**
	 * @return the foodIDNo
	 */
	public String getFoodIDNo() {
		return foodIDNo;
	}

	/**
	 * @param foodIDNo
	 *            the foodIDNo to set
	 */
	public void setFoodIDNo(String foodIDNo) {
		this.foodIDNo = foodIDNo;
	}

	/**
	 * @return the foodType
	 */
	public int getFoodType() {
		return foodType;
	}

	/**
	 * @param foodType
	 *            the foodType to set
	 */
	public void setFoodType(int foodType) {
		this.foodType = foodType;
	}

}
