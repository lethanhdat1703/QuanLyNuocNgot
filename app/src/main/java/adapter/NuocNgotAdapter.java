package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import Dao.DBHelper;
import model.NhaCungCap;
import model.NuocNgot;
import vn.edu.stu.quanlynuocgiaikhat.FormNuocNgot;
import vn.edu.stu.quanlynuocgiaikhat.R;

public class NuocNgotAdapter extends ArrayAdapter<NuocNgot> {
    Activity context;
    int resource;
    List<NuocNgot> nuocNgots;
    DBHelper dbHelper;

    ArrayList<NhaCungCap> dsNhaCungCap;


    public NuocNgotAdapter(Activity context, int resource, List<NuocNgot> nuocNgots, DBHelper dbHelper, ArrayList<NhaCungCap> dsNhaCungCap) {
        super(context, resource, nuocNgots);
        this.context = context;
        this.resource =  resource;
        this.nuocNgots = nuocNgots;
        this.dbHelper = dbHelper;
        this.dsNhaCungCap = dsNhaCungCap;
    }

    public String getTenNhaCungCap(int ma, ArrayList<NhaCungCap> ds){
        for (NhaCungCap ncc : ds) {
            if(ncc.getId()==ma) {
                return ncc.getTen();
            }
        }
        return null;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);
        ImageView imageNuocNgot=(ImageView)item.findViewById(R.id.imageNuocNgots);
        TextView txtIdNuocNgot = item.findViewById(R.id.tvIdNuocNgot);
        TextView txtTenNuocNgot = item.findViewById(R.id.tvTenNuocNgot);
        TextView txtNhaCungCap = item.findViewById(R.id.tvNhaCungCap);
        TextView txtPrice = item.findViewById(R.id.tvPrice);
        final Button btnSua = item.findViewById(R.id.btnSua);
        final Button btnXoa = item.findViewById(R.id.btnXoa);
        final NuocNgot nuocNgot = nuocNgots.get(position);
        txtIdNuocNgot.setText(nuocNgot.getId()+"");
        txtTenNuocNgot.setText(nuocNgot.getTen());
        txtNhaCungCap.setText(getTenNhaCungCap(nuocNgot.getIdncc(), dsNhaCungCap));
        txtPrice.setText(nuocNgot.getFormattedPrice());
        Bitmap bmImageNuocNgot= BitmapFactory.decodeByteArray(nuocNgot.getImage(),0, nuocNgot.getImage().length);
        imageNuocNgot.setImageBitmap(bmImageNuocNgot);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(context, FormNuocNgot.class);
                NuocNgot lt= nuocNgots.get(position);
                Intent.putExtra("dsNhaCungCap",dsNhaCungCap);
                Intent.putExtra("nuocngot", lt);
                context.startActivityForResult(Intent,789);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc muốn xóa nước ngọt này?");

                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteNuocNgot(nuocNgots.get(position).getId());
                        nuocNgots.remove(position);
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return item;
    }
}
