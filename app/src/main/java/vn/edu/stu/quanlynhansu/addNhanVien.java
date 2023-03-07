package vn.edu.stu.quanlynhansu;



import static vn.edu.stu.quanlynhansu.chiTietNV.convertStringToBitMap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;



import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import vn.edu.stu.quanlynhansu.data.NhanVienDataSource;
import vn.edu.stu.quanlynhansu.data.PhongBanDataSource;
import vn.edu.stu.quanlynhansu.model.NhanVien;
import vn.edu.stu.quanlynhansu.model.PhongBan;


public class addNhanVien extends AppCompatActivity {
    public static final int REQUEST_CODE_IMAGE=1;
    public static final int REQUEST_CODE_Storage=0;

    String imgstring,img;
    ImageView imageView ;
    ImageButton btnImage;
    private PhongBanDataSource datasourcePB;
    private NhanVienDataSource datasource;
    EditText etMaNV,etTenNV,etDiaChi,etEmail;
    Button btnThem;
    Spinner spinner;
    NhanVien nhanVien=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhan_vien2);
        datasourcePB = new PhongBanDataSource(this);
        datasourcePB.open();
        datasource = new NhanVienDataSource(this);
        datasource.open();
        addControls();
        addEvents();
    }

    private void addControls() {
        etMaNV=findViewById(R.id.manhanvien);
        etTenNV = findViewById(R.id.tennhanvien);
        etEmail = findViewById(R.id.email);
        etDiaChi = findViewById(R.id.diachi);
        btnImage = (ImageButton) findViewById(R.id.imageButton);
        imageView = findViewById(R.id.img_chitiet);
        btnThem = findViewById(R.id.btnThem);
        spinner = (Spinner) findViewById(R.id.phongban);
        List<PhongBan> values = datasourcePB.getAllPhongBans();
        ArrayAdapter<PhongBan> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        spinner.setAdapter(adapter);
        Intent intent = getIntent();
        if(intent.getIntExtra("Key_10",0)==1){
            btnThem.setText("Cập Nhật");
            nhanVien= (NhanVien) intent.getSerializableExtra("NV");
            etMaNV.setText(String.valueOf(nhanVien.getMaNV()));
            etTenNV.setText(nhanVien.getTenNV());
            etEmail.setText(nhanVien.getEmail());
            etDiaChi.setText(nhanVien.getDiaChi());
            img= nhanVien.getHinhAnh();
            Bitmap bitmap = convertStringToBitMap(nhanVien.getHinhAnh());
            imageView.setImageBitmap(bitmap);
}
    }


    private void addEvents() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if(intent.getIntExtra("Key_10",0)==1){
                    if(imgstring==null){
                        imgstring=img;
                        xuLySua();
                    }else {
                        xuLySua();
                    }
                }else {
                    xuLyThem();
                }
            }
        });
        btnImage.setOnClickListener(v->{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_Storage);
        });
}
    private void xuLyThem() {
        String ten = etTenNV.getText().toString();
        String diachi = etDiaChi.getText().toString();
        String email = etEmail.getText().toString();
        PhongBan phongBan= (PhongBan) spinner.getSelectedItem();
        String maPB= phongBan.getTenPB();
        datasource.createNhanVien(ten,maPB,imgstring,diachi,email);
        Intent i = new Intent(addNhanVien.this,DSNhanVien.class);
        startActivity(i);
    }

    private void xuLySua() {
        String ma=etMaNV.getText().toString();
        String ten = etTenNV.getText().toString();
        String diachi = etDiaChi.getText().toString();
        String email = etEmail.getText().toString();
        PhongBan phongBan= (PhongBan) spinner.getSelectedItem();
        String maPB= phongBan.getTenPB();
        Long maNV= Long.valueOf(ma);
        datasource.updateDataList(maNV,ten,maPB,imgstring,diachi,email);
        Intent i = new Intent(addNhanVien.this,DSNhanVien.class);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==  REQUEST_CODE_Storage&& grantResults.length>=0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }else{
                Toast.makeText(this, "sai roi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_IMAGE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_IMAGE&&resultCode==RESULT_OK){
            Uri image = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(image);
                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream byt = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, byt);
                byte[] bytes = byt.toByteArray();
                imgstring = Base64.encodeToString(bytes, 0, bytes.length, 0);
                imageView.setImageBitmap(bm);
                Log.d("msg",image+"");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    String getPathUri(Uri image){
        String path;
        Cursor cursor = getContentResolver().query(image,null,null,null);
        if(cursor==null){
            path=image.getPath();
        }else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            path =cursor.getString(index);
        }
        return path;
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
                Intent i = new Intent(addNhanVien.this, About.class);
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