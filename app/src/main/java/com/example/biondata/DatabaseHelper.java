package com.example.biondata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 2;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_MAHASISWA = "mahasiswa";

    // Table Columns for Users
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Table Columns for Mahasiswa
    public static final String COLUMN_MAHASISWA_ID = "mahasiswa_id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_NIM = "nim";
    public static final String COLUMN_JURUSAN = "jurusan";
    public static final String COLUMN_JENIS_KELAMIN = "jenis_kelamin";
    public static final String COLUMN_TEMPAT_LAHIR = "tempat_lahir";
    public static final String COLUMN_TANGGAL_LAHIR = "tanggal_lahir";

    // Query to create "users" table
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT NOT NULL, " +
            COLUMN_PASSWORD + " TEXT NOT NULL" +
            ")";

    // Query to create "mahasiswa" table
    private static final String CREATE_TABLE_MAHASISWA = "CREATE TABLE " + TABLE_MAHASISWA + " (" +
            COLUMN_MAHASISWA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAMA + " TEXT NOT NULL, " +
            COLUMN_NIM + " TEXT NOT NULL, " +
            COLUMN_JURUSAN + " TEXT, " +
            COLUMN_JENIS_KELAMIN + " TEXT, " +
            COLUMN_TEMPAT_LAHIR + " TEXT, " +
            COLUMN_TANGGAL_LAHIR + " TEXT" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS); // Membuat tabel users
        db.execSQL(CREATE_TABLE_MAHASISWA); // Membuat tabel mahasiswa
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS); // Hapus tabel jika ada perubahan versi
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAHASISWA);
        onCreate(db); // Buat ulang tabel
    }

    // Method untuk menambahkan pengguna (register)
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1; // True jika data berhasil disimpan
    }

    // Method untuk login pengguna
    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + " =? AND " + COLUMN_PASSWORD + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists; // True jika pengguna ditemukan
    }

    // Method untuk menambahkan data mahasiswa
    public boolean insertBiodata(String nama, String nim, String jurusan, String jenisKelamin, String tempatLahir, String tanggalLahir) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_NIM, nim);
        values.put(COLUMN_JURUSAN, jurusan);
        values.put(COLUMN_JENIS_KELAMIN, jenisKelamin);
        values.put(COLUMN_TEMPAT_LAHIR, tempatLahir);
        values.put(COLUMN_TANGGAL_LAHIR, tanggalLahir);

        long result = db.insert(TABLE_MAHASISWA, null, values);
        db.close();

        return result != -1; // True jika data berhasil disimpan
    }

    // Method untuk mendapatkan semua data mahasiswa
    public Cursor getAllMahasiswa() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                COLUMN_MAHASISWA_ID + " AS _id, " + // Alias untuk SimpleCursorAdapter
                COLUMN_NAMA + ", " +
                COLUMN_NIM + ", " +
                COLUMN_JURUSAN + ", " +
                COLUMN_JENIS_KELAMIN + ", " +
                COLUMN_TEMPAT_LAHIR + ", " +
                COLUMN_TANGGAL_LAHIR +
                " FROM " + TABLE_MAHASISWA;

        return db.rawQuery(query, null); // Mengembalikan semua data mahasiswa
    }
}
