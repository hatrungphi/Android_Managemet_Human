package vn.edu.stu.quanlynhansu.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SQLite extends SQLiteOpenHelper{


    public static final String TABLE_PhongBan = "phongban";
    public static final String COLUMN_MaPB = "mapb";
    public static final String COLUMN_PhongBan = "tenpb";

    public static final String TABLE_NhanVien = "nhanvien";
    public static final String COLUMN_Ma = "manv";
    public static final String COLUMN_Ten = "tennv";
    public static final String COLUMN_IDPhongBan = "mapb";
    public static final String COLUMN_HinhAnh = "hinhanh";
    public static final String COLUMN_DiaChi = "diachi";
    public static final String COLUMN_Email = "email";

    private static final String DATABASE_NAME = "nhanvien.db";
    private static final int DATABASE_VERSION = 1;

    // Câu lệnh khởi tạo Database.
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PhongBan + "( " + COLUMN_MaPB
            + " integer primary key autoincrement, " + COLUMN_PhongBan
            + " text not null);";

    private static final String CREATE = "create table "
            +  TABLE_NhanVien + "( " +  COLUMN_Ma
            + " integer primary key autoincrement, " + COLUMN_Ten
            + " text not null, " + COLUMN_IDPhongBan + " text not null, "
            + COLUMN_HinhAnh +" text not null, "
            + COLUMN_DiaChi +" text not null ,"
            + COLUMN_Email +" text not null);";

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE);
        database.execSQL(DATABASE_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLite.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PhongBan);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NhanVien);
        onCreate(db);
    }
}

