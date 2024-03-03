package vn.edu.stu.quanlynuocgiaikhat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Dao.DBHelper;
import adapter.NuocNgotAdapter;
import model.NuocNgot;
import model.NhaCungCap;
import util.DBConfigUtil;



public class DanhSachNuocNgot extends AppCompatActivity {
    DBHelper dbHelper;
    ListView lvNuocNgot;
    NuocNgotAdapter adapter;
    Button btnThem;
    Button btnTroVe;
    ArrayList<NuocNgot> dsNuocNgot =new ArrayList<>();
    ArrayList<NhaCungCap> dsNhaCungCap=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_nuoc_ngot);
        DBConfigUtil.copyDatabaseFromAssets(DanhSachNuocNgot.this);
        addControls();
        dbHelper=new DBHelper(DanhSachNuocNgot.this);
        addEvents();
        hienthiDanhsachNuocNgot();
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mnuAbout){
            Intent intent = new Intent(DanhSachNuocNgot.this, about.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.mnuExit){
            Intent intent = new Intent(DanhSachNuocNgot.this, Menu.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void hienthiDanhsachNuocNgot() {
        dsNuocNgot =(ArrayList<NuocNgot>) dbHelper.getAllLaptop();
        dsNhaCungCap=(ArrayList<NhaCungCap>)dbHelper.getAllNhaCungCap();
        adapter = new NuocNgotAdapter(
                DanhSachNuocNgot.this,
                R.layout.custom_listview,
                dsNuocNgot,
                dbHelper,
                dsNhaCungCap
        );
        lvNuocNgot.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachNuocNgot.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachNuocNgot.this, FormNuocNgot.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        lvNuocNgot=findViewById(R.id.lvDsNuocNgot);
        btnTroVe=findViewById(R.id.btnTroVe);
        btnThem=findViewById(R.id.btnThem);
    }
}