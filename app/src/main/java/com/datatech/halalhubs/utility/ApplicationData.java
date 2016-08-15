package com.datatech.halalhubs.utility;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.model.Food;
import com.datatech.halalhubs.model.MenuItem;
import com.datatech.halalhubs.model.Order;
import com.datatech.halalhubs.model.Restaurant;
import com.datatech.halalhubs.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class ApplicationData {

    // GENERAL PRODUCT DETAILS
    public static ArrayList<String> GeoCodeTemp = new ArrayList<String>();


    public static ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
    public static ArrayList<Food> foodList = new ArrayList<Food>();

    public static ArrayList<Order> foodOrderList = new ArrayList<Order>();
    public static ImageLoader imageLoader = ImageLoader.getInstance();

    public static String GoogleMapKey = "AIzaSyCN_tzqvvH2YMCVVj3C96vKIVJjysh9CNs";
    public static String SUB_TOTAL = "AIzaSyCN_tzqvvH2YMCVVj3C96vKIVJjysh9CNs";
    public static String DELIVERY_FREE = "AIzaSyCN_tzqvvH2YMCVVj3C96vKIVJjysh9CNs";
    public static String TAX_FEE = "AIzaSyCN_tzqvvH2YMCVVj3C96vKIVJjysh9CNs";

    public static String CLIENT_ID = "288792073128-mlgts0c2a8cgci0tpp7ugqbg0bgqv9qe.apps.googleusercontent.com";

    public static final int MAX_NO_QUANTITY = 20;
    public static final int MIN_NO_QUANTITY = 1;


    public static final String CURRENCY = "BDT";

    public static String BASE_URL = "http://45.55.196.7:1337";
    public final static String SHOW_ALL_RESTAURENT = "http://45.55.196.7:1337/rest/searchRestaurant";
    public final static String SHOW_RESTAURENT_MENU = "http://45.55.196.7:1337/rest/restaurantMenu/all/with/menuItems";
    public final static String SHOW_FOOD_DETAILS = "http://45.55.196.7:1337/rest/foodItem/get/";
    public final static String LOG_IN_BASE_URL = "http://45.55.196.7:1337/auth/local";
    public final static String REGISTRATION_BASE_URL = "http://45.55.196.7:1337/auth/local/register";
    public final static String REGISTRATION_ORDER_URL = "http://45.55.196.7:1337/rest/order/createOrUpdate";

    public static User aUser = new User();

    // Restaurant FoodMenu

    // http://45.55.196.7:1337/rest/restaurantMenu/all/with/menuItems
    // http://45.55.196.7:1337/rest/foodItem/get/2

    public final static String POST_CODE = "postCode";

    public static ImageLoader ImageLoaderGetInstance() {

        return imageLoader;

    }

    private static int iamge_Id[] = {

            R.mipmap.ic_edit_profile, R.mipmap.ic_write_review,
            R.mipmap.ic_setting, R.mipmap.ic_recent_shopping, R.mipmap.ic_recent_review, R.mipmap.ic_halal_hub_point, R.mipmap.ic_fav_food

    };

//    private static int iamge_Id[] = {
//
//            R.mipmap.ic_edit_profile, R.mipmap.ic_edit_profile,
//            R.mipmap.ic_edit_profile, R.mipmap.ic_edit_profile, R.mipmap.ic_edit_profile, R.mipmap.ic_edit_profile, R.mipmap.ic_edit_profile
//
//    };

    private static String menu_Text[] = {"Edit your profile", "Write a review", "Setting", "Your recent orders", "Your recent reviews", "Your HalalHub points", "Your favourite food"};


    static ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

    public static ArrayList<MenuItem> setMenuItems() {

        //
        if (menuItems.size()== 0){

            menuItems.clear();

            for (int i = 0; i < iamge_Id.length; i++) {

                MenuItem menuItem = new MenuItem();
                menuItem.setName(menu_Text[i]);
                menuItem.setPicture(iamge_Id[i]);

                menuItems.add(menuItem);
            }

        }



        return menuItems;

    }
    // halal hubs

    // show all restaurent
    // http://45.55.196.7:1337/rest/restaurant/all/with/profile,category,tags

    // show menu of a restaurent
    // http://45.55.196.7:1337/rest/restaurantMenu/all/with/menuItems


    //	Library We use
    //	Toast: https://github.com/JohnPersano/Supertoasts/wiki/SuperActivityToast


}
