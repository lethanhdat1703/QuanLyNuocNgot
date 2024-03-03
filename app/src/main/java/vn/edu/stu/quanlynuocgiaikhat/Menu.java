package vn.edu.stu.quanlynuocgiaikhat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button btnDsNuocNgot;
    Button btnDsNhaCungCap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnDsNuocNgot =findViewById(R.id.btnNuocNgot);
        btnDsNhaCungCap=findViewById(R.id.btnNhaCungCap);
    }

    private void addEvents() {
        btnDsNhaCungCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, DanhSachNhaCungCap.class);
                startActivity(intent);
                finish();
            }
        });
        btnDsNuocNgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, DanhSachNuocNgot.class);
                startActivity(intent);
                finish();
            }
        });
    }
}