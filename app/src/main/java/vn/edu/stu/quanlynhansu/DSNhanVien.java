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
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import java.util.List;

import vn.edu.stu.quanlynhansu.apdapter.NhanVienAdapter;
import vn.edu.stu.quanlynhansu.data.NhanVienDataSource;
import vn.edu.stu.quanlynhansu.data.PhongBanDataSource;
import vn.edu.stu.quanlynhansu.model.NhanVien;

public class DSNhanVien extends AppCompatActivity {

    private NhanVienDataSource datasource;
    private PhongBanDataSource datasourcePB;
    Button btnThem;
    ImageButton simpleImageButton;
    ArrayAdapter<NhanVien> adapter;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsnhan_vien);
        datasource = new NhanVienDataSource(this);
        datasource.open();

        addControls();
        addEvents();
    }
    private void addControls() {
        lv=findViewById(R.id.list);
        simpleImageButton = (ImageButton) findViewById(R.id.imageFilterButton);
        List<NhanVien> values = datasource.getAllNhanViens();
        adapter = new NhanVienAdapter(
                DSNhanVien.this,
                R.layout.item_nhanvien,
                values
        );

        lv.setAdapter(adapter);
    }


        private void addEvents() {
            simpleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DSNhanVien.this,addNhanVien.class);
                startActivity(i);
            }
        });
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
                    Intent intent = new Intent(DSNhanVien.this,chiTietNV.class);
                    NhanVien pbChon =adapter.getItem(index);
                    intent.putExtra("Key_1", pbChon.getMaNV());  // Truyền một String
                    intent.putExtra("Key_2", pbChon.getHinhAnh());
                    intent.putExtra("Key_3", pbChon.getPhongBan());  // Truyền một Int
                    intent.putExtra("Key_4",pbChon.getDiaChi() );
                    intent.putExtra("Key_5",pbChon.getEmail() );
                    intent.putExtra("Key_6", pbChon.getTenNV());
                    startActivity(intent);
                    return true;
                }
            });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent i = new Intent(DSNhanVien.this, About.class);
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