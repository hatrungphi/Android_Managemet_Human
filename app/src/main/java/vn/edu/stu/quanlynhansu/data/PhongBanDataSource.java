package vn.edu.stu.quanlynhansu.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.quanlynhansu.model.PhongBan;
import vn.edu.stu.quanlynhansu.util.SQLite;


public class PhongBanDataSource {
    // Các trường database.
    private SQLiteDatabase database;
    private SQLite dbHelper;
    private String[] allColumns = {SQLite.COLUMN_MaPB,
            SQLite.COLUMN_PhongBan};
    public static final String KEY_ID = "mapb";
    private String Tag;

    public PhongBanDataSource(Context context) {
        dbHelper = new SQLite(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public PhongBan createPhongBan(String pName) {
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_PhongBan, pName);
        long insertId = database.insert(SQLite.TABLE_PhongBan, null,
                values);
        Cursor cursor = database.query(SQLite.TABLE_PhongBan,
                allColumns, SQLite.COLUMN_MaPB + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        PhongBan phongBan = cursorToPhongBan(cursor);
        cursor.close();
        return phongBan;
    }


    public void deletePhongBan(PhongBan p) {
        long id = p.getMaPB();
        Log.e("SQLite", "Person entry deleted with id: " + id);
        database.delete(SQLite.TABLE_PhongBan, SQLite.COLUMN_MaPB
                + " = " + id, null);
    }

    public List<PhongBan> getAllPhongBans() {
        List<PhongBan> phongBans = new ArrayList<PhongBan>();

        Cursor cursor = database.query(SQLite.TABLE_PhongBan,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PhongBan phongBan = cursorToPhongBan(cursor);
            phongBans.add(phongBan);
            cursor.moveToNext();
        }
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return phongBans;
    }
    public void updateDataList(PhongBan phongBan){
       database = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_PhongBan, phongBan.getTenPB());
        long result = database.update(SQLite.TABLE_PhongBan, values, KEY_ID + "=?", new String[]{String.valueOf(phongBan.getMaPB())});
        database.close(); // Closing database connection
        Log.d(Tag, "Cập nhật thành công " + result);
 /*       if(result == -1){
            return false;
        }else {
            return true;
        }*/
    }
    private PhongBan cursorToPhongBan(Cursor cursor) {
        PhongBan phongBan = new PhongBan();
        phongBan.setMaPB(cursor.getLong(0));
        phongBan.setTenPB(cursor.getString(1));
        return phongBan;
    }
}
