package vn.edu.stu.quanlynhansu.apdapter;




import static vn.edu.stu.quanlynhansu.chiTietNV.convertStringToBitMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



import java.util.List;

import vn.edu.stu.quanlynhansu.R;
import vn.edu.stu.quanlynhansu.addNhanVien;
import vn.edu.stu.quanlynhansu.data.NhanVienDataSource;
import vn.edu.stu.quanlynhansu.model.NhanVien;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    Activity context;
    int resource;
    List<NhanVien> objects;
    private NhanVienDataSource datasource;

    public NhanVienAdapter(@NonNull Activity context, int resource, @NonNull List<NhanVien> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        LayoutInflater inflater = this.context.getLayoutInflater();
        view = inflater.inflate(R.layout.item_nhanvien, null);
        ImageView imageView =view.findViewById(R.id.img_nhanvien);
        TextView tvMaNV = view.findViewById(R.id.maNV);
        TextView tvTenNV = view.findViewById(R.id.tenNV);
        TextView tvPB = view.findViewById(R.id.tenPB);
        ImageButton simpleImageButton=(ImageButton) view.findViewById(R.id.btnXoa);
        ImageButton btnSua = (ImageButton) view.findViewById(R.id.btnSua);
        NhanVien nv = this.objects.get(position);
        tvMaNV.setText(nv.getMaNV() + "");
        tvTenNV.setText(nv.getTenNV());
        tvPB.setText(nv.getPhongBan());
        Bitmap bitmap = convertStringToBitMap(nv.getHinhAnh());
        imageView.setImageBitmap(bitmap);
        simpleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyXoa(position);
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, addNhanVien.class);
                intent.putExtra("NV",nv);
                intent.putExtra("Key_10",1);
                context.startActivity(intent);
            }
        });
        return view;
    }

    private void xuLyXoa(int position) {
        datasource = new NhanVienDataSource(context);
        datasource.open();
        NhanVien nhanVien=this.objects.get(position);
        datasource.deleteNhanVien(nhanVien);
        this.objects.remove(position);
        this.notifyDataSetChanged();
    }

}
