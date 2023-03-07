package vn.edu.stu.quanlynhansu.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.io.Serializable;

@Entity(tableName = "NhanVien")
public class NhanVien implements Serializable {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long maNV;

    private String tenNV;
    private String phongBan;
    private String hinhAnh;
    private String diaChi;
    @ColumnInfo(name = "email")
    private String email;

    public NhanVien(String tenNV, String phongBan, String hinhAnh, String diaChi, String email) {

        this.tenNV = tenNV;
        this.phongBan = phongBan;
        this.hinhAnh = hinhAnh;
        this.diaChi = diaChi;
        this.email = email;
    }

    public NhanVien() {

    }

    @NonNull
    public Long getMaNV() {
        return maNV;
    }

    public void setMaNV(@NonNull Long maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}