package com.abpatil1.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    // Database configuration constants
    private static final String DATABASE_NAME = "healthcare.db";
    private static final int DATABASE_VERSION = 2;


    // Users table and columns
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // Cart table and columns
    private static final String TABLE_CART = "cart";
    private static final String COLUMN_PRODUCT = "product";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_OTYPE = "otype";


    // Order place table and columns
    private static final String TABLE_ORDERPLACE = "orderplace";
    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CONTACTNO = "contactno";
    private static final String COLUMN_PINCODE = "pincode";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_AMOUNT = "amount";

    // Constructor
    public Database( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);

        String createCartTable = "CREATE TABLE " + TABLE_CART + "(" +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PRODUCT + " TEXT, " +
                COLUMN_PRICE + " FLOAT, " +
                COLUMN_OTYPE + " TEXT)";
        db.execSQL(createCartTable);

        String createOrderPlaceTable = "CREATE TABLE " + TABLE_ORDERPLACE + "(" +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_FULLNAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_CONTACTNO + " TEXT, " +
                COLUMN_PINCODE + " INT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_AMOUNT + " FLOAT, " +
                COLUMN_OTYPE + " TEXT)";
        db.execSQL(createOrderPlaceTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERPLACE);
        onCreate(db);
    }

    // Register a new user
    public void register(String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        try (SQLiteDatabase db = getWritableDatabase()) {
            db.insert(TABLE_USERS, null, cv);
        }
    }

    // Login a user
    public int login(String username, String password) {
        int result = 0;
        String[] args = {username, password};
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", args)) {
            if (c.moveToFirst()) {
                result = 1;
            }
        }
        return result;
    }

    // Add product to cart
    public void addCart(String username, String product, float price, String otype) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PRODUCT, product);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_OTYPE, otype);
        try (SQLiteDatabase db = getWritableDatabase()) {
            db.insert(TABLE_CART, null, cv);
        }
    }

    // Check if product is already in the cart
    public int checkCart(String username, String product) {
        int result = 0;
        String[] args = {username, product};
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PRODUCT + "=?", args)) {
            if (c.moveToFirst()) {
                result = 1;
            }
        }
        return result;
    }

    // Remove products from cart based on order type
    public int removeCart(String username, String otype) {
        String[] args = {username, otype};
        int rowsDeleted;
        try (SQLiteDatabase db = getWritableDatabase()) {
            rowsDeleted = db.delete(TABLE_CART, COLUMN_USERNAME + "=? AND " + COLUMN_OTYPE + "=?", args);
        }
        return rowsDeleted;
    }

    // Get cart data for a user and order type
    public ArrayList<String> getCartData(String username, String otype) {
        ArrayList<String> arr = new ArrayList<>();
        String[] args = {username, otype};
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_OTYPE + "=?", args)) {
            if (c.moveToFirst()) {
                do {
                    String product = c.getString(1);
                    String price = c.getString(2);
                    arr.add(product + "$" + price);
                } while (c.moveToNext());
            }
        }
        return arr;
    }

    // Add order
    public void addOrder(String username, String fullname, String address, String contact, int pincode, String date, String time, float amount, String otype) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_FULLNAME, fullname);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_CONTACTNO, contact);
        cv.put(COLUMN_PINCODE, pincode);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_OTYPE, otype);
        try (SQLiteDatabase db = getWritableDatabase()) {
            db.insert(TABLE_ORDERPLACE, null, cv);
        }
    }

    // Get order data for a user
    public ArrayList<String> getOrderData(String username) {
        ArrayList<String> arr = new ArrayList<>();
        String[] args = {username};
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ORDERPLACE + " WHERE " + COLUMN_USERNAME + "=?", args)) {
            if (c.moveToFirst()) {
                do {
                    arr.add(c.getString(1) + "$" + c.getString(2) + "$" + c.getString(3) + "$" + c.getString(4) + "$" + c.getString(5) + "$" + c.getString(6) + "$" + c.getString(7) + "$" + c.getString(8));
                } while (c.moveToNext());
            }
        }
        return arr;
    }

    // Check if an appointment already exists
    public int checkAppointmentExists(String username, String fullname, String address, String contact, String date, String time) {
        int result = 0;
        String[] args = {username, fullname, address, contact, date, time};
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ORDERPLACE + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_FULLNAME + "=? AND " + COLUMN_ADDRESS + "=? AND " + COLUMN_CONTACTNO + "=? AND " + COLUMN_DATE + "=? AND " + COLUMN_TIME + "=?", args)) {
            if (c.moveToFirst()) {
                result = 1;
            }
        }
        return result;
    }
    // Assuming TABLE_ORDERPLACE, COLUMN_USERNAME, COLUMN_FULLNAME, COLUMN_ADDRESS, COLUMN_CONTACTNO, COLUMN_DATE, COLUMN_TIME are defined elsewhere

    // Example of creating a database (optional)

}