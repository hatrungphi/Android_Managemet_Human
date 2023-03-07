package vn.edu.stu.quanlynhansu;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText editUserName, editPassword;
    Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLogin=(Button) findViewById(R.id.login);
        editUserName=(EditText) findViewById(R.id.username);
        editPassword=(EditText) findViewById(R.id.password);
    }
    public void onClick(View view) {
        String userName = editUserName.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(userName.equals("a") && password.equals("a")){
            Intent i = new Intent(MainActivity.this,MainActivity2.class);
            startActivity(i);
        }else {
            Toast.makeText(MainActivity.this,"Nhap sai vui long nhap lai",Toast.LENGTH_LONG).show();
        }
    }
}