package vn.edu.stu.quanlynhansu.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.quanlynhansu.model.NhanVien;
import vn.edu.stu.quanlynhansu.util.SQLite;

public class NhanVienDataSource {
    // Các trường database.
    private SQLiteDatabase database;
    private SQLite dbHelper;
    private String[] allColumns = {SQLite.COLUMN_Ma,
            SQLite.COLUMN_Ten,SQLite.COLUMN_IDPhongBan,
            SQLite.COLUMN_HinhAnh,SQLite.COLUMN_DiaChi,
            SQLite.COLUMN_Email};

    public static final String KEY_ID = "manv";

    public NhanVienDataSource(Context context) {
        dbHelper = new SQLite(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public NhanVien createNhanVien(String ten, String idphong, String hinhanh, String diachi, String email) {
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_Ten, ten);
        values.put(SQLite.COLUMN_IDPhongBan, idphong);
        values.put(SQLite.COLUMN_HinhAnh,hinhanh );
        values.put(SQLite.COLUMN_DiaChi, diachi);
        values.put(SQLite.COLUMN_Email, email);

        long insertId = database.insert(SQLite.TABLE_NhanVien, null,
                values);
        Cursor cursor = database.query(SQLite.TABLE_NhanVien,
                allColumns, SQLite.COLUMN_Ma + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        NhanVien nhanVien = cursorToNhanVien(cursor);
        cursor.close();
        return nhanVien;
    }



    public List<NhanVien> getAllNhanViens() {
        List<NhanVien> nhanViens = new ArrayList<NhanVien>();

        Cursor cursor = database.query(SQLite.TABLE_NhanVien,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanVien nhanVien = cursorToNhanVien(cursor);
            nhanViens.add(nhanVien);
            cursor.moveToNext();
        }
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return nhanViens;
    }

    public boolean getNhanViens(String maPB) {
         List<NhanVien> nv=getAllNhanViens();
        for (NhanVien nv1 : nv) {
           if(maPB.equals(nv1.getPhongBan())){
                return true;
           }
        }
        return false;
    }

    public void getNhanVien(String tenPBM,String tenPBC) {
        List<NhanVien> nv=getAllNhanViens();
        for (NhanVien nv1 : nv) {
            if(tenPBC.equals(nv1.getPhongBan())){
                nv1.setPhongBan(tenPBM);
                updateDataList(nv1.getMaNV(), nv1.getTenNV(),nv1.getPhongBan(),nv1.getHinhAnh(),nv1.getDiaChi(),nv1.getEmail());

            }
        }
    }


    private NhanVien cursorToNhanVien(Cursor cursor) {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(cursor.getLong(0));
        nhanVien.setTenNV(cursor.getString(1));
        nhanVien.setPhongBan(cursor.getString(2));
        nhanVien.setHinhAnh(cursor.getString(3));
        nhanVien.setDiaChi(cursor.getString(4));
        nhanVien.setEmail(cursor.getString(5));
        return nhanVien;
    }
    public void deleteNhanVien(NhanVien p) {
        long id = p.getMaNV();
        Log.e("SQLite", "Person entry deleted with id: " + id);
        database.delete(SQLite.TABLE_NhanVien, SQLite.COLUMN_Ma
                + " = " + id, null);
    }
    public void updateDataList(Long ma,String ten,String idphong,String hinhanh,String diachi,String email){
        database = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_Ten, ten);
        values.put(SQLite.COLUMN_IDPhongBan, idphong);
        values.put(SQLite.COLUMN_HinhAnh, hinhanh);
        values.put(SQLite.COLUMN_DiaChi, diachi);
        values.put(SQLite.COLUMN_Email, email);
        long result = database.update(SQLite.TABLE_NhanVien, values, KEY_ID + "=?", new String[]{String.valueOf(ma)});
        database.close(); // Closing database connection
    }

}
