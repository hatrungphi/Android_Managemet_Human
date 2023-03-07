package vn.edu.stu.quanlynhansu.apdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

import vn.edu.stu.quanlynhansu.R;
import vn.edu.stu.quanlynhansu.data.NhanVienDataSource;
import vn.edu.stu.quanlynhansu.data.PhongBanDataSource;
import vn.edu.stu.quanlynhansu.model.PhongBan;

public class PhongBanAdapter extends ArrayAdapter<PhongBan> {
    Activity context;
    int resource;
    List<PhongBan> objects;
    private PhongBanDataSource datasource;
    private NhanVienDataSource datasourceNV;

    public PhongBanAdapter(@NonNull Activity context, int resource, @NonNull List<PhongBan> objects) {
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
        view = inflater.inflate(R.layout.item_phongban, null);
        TextView tvMa = view.findViewById(R.id.tvMa);
        TextView tvTen = view.findViewById(R.id.tvTen);
        Button btnXoa = view.findViewById(R.id.btnXoa);
        PhongBan pb = this.objects.get(position);
        tvMa.setText(pb.getMaPB() + "");
        tvTen.setText(pb.getTenPB());
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Bán có muốn xóa phòng ban này!");
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    xuLyXoa(position);
                    }

                });
                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //không làm gì
                    }
                });
                alertDialogBuilder.show();
            }
        });
        return view;
    }

    private void xuLyXoa(int position) {
        boolean co=true;
        datasourceNV = new NhanVienDataSource(context);
        datasource = new PhongBanDataSource(context);
        datasource.open();
        PhongBan phongBan=this.objects.get(position);
        datasourceNV.open();
        if(datasourceNV.getNhanViens(phongBan.getTenPB())){
            AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(context);
            DialogBuilder.setMessage("Xóa Không thành công nhân viên đã tồn tại");
            DialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }

            });
            DialogBuilder.show();
            co=false;
        }
        if(co){
            datasource.deletePhongBan(phongBan);
            this.objects.remove(position);
            this.notifyDataSetChanged();
        }

    }



}
