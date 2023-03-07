package vn.edu.stu.quanlynhansu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class chiTietNV extends AppCompatActivity {

    TextView tvma,tvten,tvphongban,tvdiachi,tvemail;
    ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_nv);
        Intent intent = getIntent();
        tvma = findViewById(R.id.ma_chitiet);
        tvten=findViewById(R.id.ten_chitiet);
        tvphongban=findViewById(R.id.phongban_chitiet);
        tvdiachi=findViewById(R.id.tomtat_chitiet);
        tvemail=findViewById(R.id.ndtomtat_chitiet);
        tvphongban=findViewById(R.id.phongban_chitiet);
        imageView=findViewById(R.id.img_chitiet);
        tvma.setText(intent.getLongExtra("Key_1",0) + "");
        tvten.setText(intent.getStringExtra("Key_6") + "");
        tvphongban.setText(intent.getStringExtra("Key_3") + "");
        tvdiachi.setText(intent.getStringExtra("Key_4") + "");
        tvemail.setText(intent.getStringExtra("Key_5") + "");
        Bitmap bitmap=convertStringToBitMap(intent.getStringExtra("Key_2"));
        imageView.setImageBitmap(bitmap);
    }
    public static Bitmap convertStringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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
                Intent i = new Intent(chiTietNV.this, About.class);
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