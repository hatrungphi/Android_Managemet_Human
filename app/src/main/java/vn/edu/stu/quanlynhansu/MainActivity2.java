package vn.edu.stu.quanlynhansu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    Button buttonDSNhanVien,buttonDSPhongBan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        addControls();
    }

    private void addControls() {
        buttonDSNhanVien = findViewById(R.id.btndsnhanvien);
        buttonDSPhongBan = findViewById(R.id.btndsbophan);
    }
    public void onClickDSNhanVien(View view) {
        Intent i = new Intent(MainActivity2.this, DSNhanVien.class);
        startActivity(i);
    }
    public void onClickDSPhongBan(View view) {
        Intent i = new Intent(MainActivity2.this, DSPhongBan.class);
        startActivity(i);
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
                Intent i = new Intent(MainActivity2.this, About.class);
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
