package com.example.hellodelivery.utils;

public class Constants {
    public static final String BASE_URL = "https://api.example-delivery.com/";
    public static final String SOCKET_URL = "https://api.example-delivery.com/";
    
    public static final String PREF_NAME = "DeliveryAppPrefs";
    public static final String KEY_TOKEN = "jwt_token";
    public static final String KEY_USER = "user_session";
    public static final String KEY_IS_LOGGED_IN = "is_logged_in";
    
    public static final String EXTRA_STORE_ID = "store_id";
    public static final String EXTRA_PRODUCT_ID = "product_id";
    public static final String EXTRA_ORDER_ID = "order_id";
    public static final String EXTRA_PHONE_NUMBER = "phone_number";
    
    // Order Statuses
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_PREPARING = "Preparing";
    public static final String STATUS_OUT_FOR_DELIVERY = "Out for Delivery";
    public static final String STATUS_DELIVERED = "Delivered";
    public static final String STATUS_CANCELLED = "Cancelled";
}
