package com.aus.corsafe.config;

import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

    public static String BASE_URL = "https://dsm-1.tasklist.camunda.io/6feae57c-2f49-4861-a383-7c43f82db777/v1";

    public static String BASE_URL_CAMUNDA_TOKEN = "https://login.cloud.camunda.io";

    public static final String PASSWORD_MISMATCH_MESSAGE = "Passwords do not match";

    public static final String USER_NOT_FOUND_MESSAGE = "User not found with this email ID";

    public static final String EMAIL_NOT_REGISTERED_MESSAGE = "Email not registered with : ";
    public static final String AREA_NOT_FOUND ="area not found !! ";

    public static String TASKLIST_ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsIn" +
            "R5cCI6IkpXVCIsImtpZCI6IlFVVXdPVFpDUTBVM01qZEVRME0wTkRFelJrUkJORFk0T0RZeE1FRT" +
            "BSa1pFUlVWRVF6bERNZyJ9.eyJodHRwczovL2NhbXVuZGEuY29tL2NsdXN0ZXJJZCI6ImM0NzY1Zj" +
            "U0LWIzMGMtNGViYS1iMDlmLTI5MTQ3NDFkYjQ1MCIsImh0dHBzOi8vY2FtdW5kYS5jb20vb3JnSW" +
            "QiOiJlMDAzODY2Mi0yNmI2LTQyNTgtOWFjMy01N2IxNTY3YmJkYWMiLCJodHRwczovL2NhbXVuZ" +
            "GEuY29tL2NsaWVudElkIjoiN0prZWVDcDRsOTNlOE0uSW1iMUVnYTBVc1pnMVlscHciLCJpc3" +
            "MiOiJodHRwczovL3dlYmxvZ2luLmNsb3VkLmNhbXVuZGEuaW8vIiwic3ViIjoibXdvOTB0MnI" +
            "zMTYwN3ozNkJOSDY5dFdGS0JYNTVqMVdAY2xpZW50cyIsImF1ZCI6InRhc2tsaXN0LmNhbXV" +
            "uZGEuaW8iLCJpYXQiOjE3MjkwODM0MjcsImV4cCI6MTcyOTE2OTgyNywic2NvcGUiOiJjNDc" +
            "2NWY1NC1iMzBjLTRlYmEtYjA5Zi0yOTE0NzQxZGI0NTAiLCJndHkiOiJjbGllbnQtY3JlZG" +
            "VudGlhbHMiLCJhenAiOiJtd285MHQycjMxNjA3ejM2Qk5INjl0V0ZLQlg1NWoxVyJ9." +
            "jovcI7jjW1sPCvQhlRBv0qaVfY9DDkEU6tWBBTIFO1RhapRx8CEtqn_hMwz_AWZZmGQAH" +
            "_pely3eq2Mb_4HKgfAfNj8skMGUHU0pTwLcRL3L-47OURC2mvRBQZrdcrEUidzauWSjs" +
            "pBEVsvFZrrsHPZumIfKnxmpISb-4RFqN8TZPJcnUKIlU-nw-bDkmj46Hhf7T8WF3pXwnOvl" +
            "FhPVu6ossOJAjV11LIx_53qEgm-1YIcrs3CuzmiZMEoyl1s_FGzWf_mDAOOrqYmPwxuG9c" +
            "8xhkkM8ADPQ81UsHbwE5VPfO0SRMVTKnruBMPtP2VHKShMHU3l1qEqj1hsv5Ensw";


    public static final String PASSWORD_UPDATE_SUCCESS = "Password updated successfully";
    public static final String USER_OR_CART_NOT_FOUND = "User or Cart not found";
    public static final String PRODUCT_NOT_FOUND = "Product not found with ID: ";
    public static final String NO_ITEMS_FOUND = "No items found in the cart";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String ORDER_CREATE_SUCCESS = "Order created successfully";
    public static final String PAYMENT_NOT_FOUND = "Payment not found with ID: ";

}
