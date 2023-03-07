package vn.edu.stu.quanlynhansu.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NhanVien")
public class PhongBan {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long maPB;
    private String tenPB;

    public PhongBan() {
    }

    public PhongBan (Long maPB, String tenPB) {
        this.maPB = maPB;
        this.tenPB = tenPB;
    }

    @NonNull
    public Long getMaPB() {
        return maPB;
    }

    public void setMaPB(@NonNull Long maPB) {
        this.maPB = maPB;
    }

    public String getTenPB() {
        return tenPB;
    }

    public void setTenPB(String tenPB) {
        this.tenPB = tenPB;
    }

    @Override
    public String toString() {
        return tenPB ;

    }
}

