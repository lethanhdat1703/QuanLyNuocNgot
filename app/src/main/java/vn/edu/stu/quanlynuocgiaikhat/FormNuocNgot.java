package vn.edu.stu.quanlynuocgiaikhat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import Dao.DBHelper;
import model.NuocNgot;
import model.NhaCungCap;
import util.DBConfigUtil;



public class FormNuocNgot extends AppCompatActivity {
    static final int REQUEST_CHOOSE_PHOTO=321;
    ImageView imageNuocNgot;
    Spinner spinner;
    EditText etTenNuocNgot,etPrice,etSoLuong,etMota;
    DBHelper dbHelper;

    ArrayList<NhaCungCap> dsNhaCungCap = new ArrayList<>();
    ArrayAdapter<String> spinnerTen;
    Button btnLuu,btnTroVe,btnChon;
    NuocNgot nuocNgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_nuoc_ngot);
        Intent intent = getIntent();
        nuocNgot = (NuocNgot) intent.getSerializableExtra("nuocngot");
        dbHelper=new DBHelper(FormNuocNgot.this);
        DBConfigUtil.copyDatabaseFromAssets(FormNuocNgot.this);
        addControls();
        addEvents();

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
            Intent intent = new Intent(FormNuocNgot.this, about.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.mnuExit){
            Intent intent = new Intent(FormNuocNgot.this, DanhSachNuocNgot.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void addEvents() {
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormNuocNgot.this, DanhSachNuocNgot.class);
                startActivity(intent);
                finish();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nuocNgot !=null){
                    nuocNgot.setTen(etTenNuocNgot.getText().toString());
                    nuocNgot.setPrice(Double.parseDouble(etPrice.getText().toString()));
                    nuocNgot.setQuantity(Integer.parseInt(etSoLuong.getText().toString()));
                    nuocNgot.setMota(etMota.getText().toString());
                    nuocNgot.setImage(getBytesFromImageView(imageNuocNgot));
                    dbHelper.updateNuocNgot(nuocNgot);
                }
                else {
                    nuocNgot =new NuocNgot();
                    nuocNgot.setTen(etTenNuocNgot.getText().toString());
                    nuocNgot.setPrice(Double.parseDouble(etPrice.getText().toString()));
                    nuocNgot.setQuantity(Integer.parseInt(etSoLuong.getText().toString()));
                    nuocNgot.setMota(etMota.getText().toString());
                    nuocNgot.setImage(getBytesFromImageView(imageNuocNgot));
                    dbHelper.addNuocNgot(nuocNgot);
                }
                Intent intent = new Intent(FormNuocNgot.this, DanhSachNuocNgot.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        this.spinner = (Spinner) findViewById(R.id.spinner);
        etTenNuocNgot = findViewById(R.id.etTenNuocNgot);
        etPrice=findViewById(R.id.etPrice);
        etSoLuong=findViewById(R.id.etSoLuong);
        etMota=findViewById(R.id.etMota);
        btnLuu= findViewById(R.id.btnLuu);
        btnTroVe= findViewById(R.id.btnTroVe);
        btnChon=findViewById(R.id.btnChon);
        imageNuocNgot=(ImageView) findViewById(R.id.imageLaptop);
        if(nuocNgot !=null){
            etTenNuocNgot.setText(nuocNgot.getTen());
            etPrice.setText(nuocNgot.getPrice()+"");
            etSoLuong.setText(nuocNgot.getQuantity()+"");
            etMota.setText(nuocNgot.getMota());
            Bitmap bmImageNuocNgot= BitmapFactory.decodeByteArray(nuocNgot.getImage(),0, nuocNgot.getImage().length);
            imageNuocNgot.setImageBitmap(bmImageNuocNgot);
            selectedAdapterNcc();
        }
        else {
            hienthiAdapterNcc();
        }
    }
    private void selectedAdapterNcc(){
        dsNhaCungCap=(ArrayList<NhaCungCap>) dbHelper.getAllNhaCungCap();
        ArrayList<String> dsTenNCC = new ArrayList<>();
        int pos = -1;
        for(int i=0;i<dsNhaCungCap.size();i++) {
            dsTenNCC.add(dsNhaCungCap.get(i).getTen());
            if(dsNhaCungCap.get(i).getId()== nuocNgot.getIdncc()){
                pos = i;
            }
        }
        spinnerTen = new ArrayAdapter(FormNuocNgot.this, android.R.layout.simple_spinner_dropdown_item, dsTenNCC);
        spinnerTen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerTen);
        spinner.setSelection(pos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                nuocNgot.setIdncc(dsNhaCungCap.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void hienthiAdapterNcc(){
        dsNhaCungCap=(ArrayList<NhaCungCap>) dbHelper.getAllNhaCungCap();
        ArrayList<String> ds = new ArrayList<>();
        for(int i=0;i<dsNhaCungCap.size();i++)
            ds.add(dsNhaCungCap.get(i).getTen());
        spinnerTen = new ArrayAdapter(FormNuocNgot.this, android.R.layout.simple_spinner_dropdown_item, ds);
        spinnerTen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerTen);
    }
    private void choosePhoto(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if (requestCode==REQUEST_CHOOSE_PHOTO){
                try {
                    Uri imageUri=data.getData();
                    InputStream inputStream=getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    imageNuocNgot.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private byte[] getBytesFromImageView(ImageView imageLaptop) {
        BitmapDrawable drawable=(BitmapDrawable) imageLaptop.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray=stream.toByteArray();
        return byteArray;
    }
}