package vn.edu.stu.quanlynhansu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.util.List;

import vn.edu.stu.quanlynhansu.apdapter.PhongBanAdapter;
import vn.edu.stu.quanlynhansu.data.NhanVienDataSource;
import vn.edu.stu.quanlynhansu.data.PhongBanDataSource;
import vn.edu.stu.quanlynhansu.model.PhongBan;

public class DSPhongBan extends AppCompatActivity {

    private PhongBanDataSource datasource;
    private NhanVienDataSource datasourceNV;
    EditText etMaPB, etTenPB;
    Button btnThem, btnSua;
    ArrayAdapter<PhongBan> adapter;
    ListView lv;
    int vitri;
    PhongBan pbChon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsphong_ban);
        datasource = new PhongBanDataSource(this);
        datasource.open();
        datasourceNV = new NhanVienDataSource(this);
        datasourceNV.open();
        addControls();
        addEvents();

    }

    private void addControls() {
        etMaPB = findViewById(R.id.etMaPB);
        etTenPB = findViewById(R.id.etTenPB);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        List<PhongBan> values = datasource.getAllPhongBans();
        adapter = new PhongBanAdapter(
                DSPhongBan.this,
                R.layout.item_phongban,
                values
        );

        lv = findViewById(R.id.list);
        lv.setAdapter(adapter);
    }

    private void addEvents() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThem();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLySua();

            }
        });


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
                vitri = index;
                xuLyChon(index);
                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PhongBan pb = adapter.getItem(i);
                Intent iten = new Intent(DSPhongBan.this, CTPB.class);
                iten.putExtra("pb", pb.getMaPB());
                iten.putExtra("tenpb", pb.getTenPB());
                startActivity(iten);
            }
        });
    }
    private void xuLyChon(int index) {
        if (index >= 0 && index < adapter.getCount()) {
            pbChon = adapter.getItem(index);
            etMaPB.setText(pbChon.getMaPB() + "");
            etTenPB.setText(pbChon.getTenPB());
            etMaPB.requestFocus();
        }
    }


    private void xuLyThem() {
        String ten = etTenPB.getText().toString();
        ArrayAdapter<PhongBan> adapter = (ArrayAdapter<PhongBan>) lv.getAdapter();
        PhongBan phongBan = null;
        phongBan = datasource.createPhongBan(ten);
        adapter.add(phongBan);
        etMaPB.setText("");
        etTenPB.setText("");
        etMaPB.requestFocus();
    }

    private void xuLySua() {
        String ten = etTenPB.getText().toString();
        String ma = etMaPB.getText().toString();
        Long maPB = Long.valueOf(ma);
        PhongBan phongBan = new PhongBan();
        phongBan = adapter.getItem(vitri);
        datasourceNV.getNhanVien(ten,phongBan.getTenPB());
        phongBan.setMaPB(maPB);
        phongBan.setTenPB(ten);
        adapter.notifyDataSetChanged();
        datasource.updateDataList(phongBan);
        etMaPB.setText("");
        etTenPB.setText("");
        etMaPB.requestFocus();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent i = new Intent(DSPhongBan.this, About.class);
                startActivity(i);
                return true;
            case R.id.item2:
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}