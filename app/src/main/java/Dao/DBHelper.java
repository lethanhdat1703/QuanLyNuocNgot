package Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import model.NhaCungCap;
import model.NuocNgot;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quanlynuocngot.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME1 = "nhacungcap";
    private static final String COLUMN_idNcc = "id";
    private static final String COLUMN_tenNcc = "ten";
    private static final String TABLE_NAME2 = "nuocngot";
    private static final String COLUMN_idNuocNgot = "id";
    private static final String COLUMN_tenNuocNgot = "ten";
    private static final String COLUMN_idNhaCungCap = "id_nhacungcap";
    private static final String COLUMN_image = "image";
    private static final String COLUMN_price = "price";
    private static final String COLUMN_quantity = "quantity";
    private static final String COLUMN_mota = "mota";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<NuocNgot> getAllLaptop(){
        List<NuocNgot> nuocNgots = new ArrayList<>();
        String[] projection ={
                DBHelper.COLUMN_idNuocNgot,
                DBHelper.COLUMN_tenNuocNgot,
                DBHelper.COLUMN_idNhaCungCap,
                DBHelper.COLUMN_image,
                DBHelper.COLUMN_price,
                DBHelper.COLUMN_quantity,
                DBHelper.COLUMN_mota
        };
        Cursor cursor = getReadableDatabase().query(
                DBHelper.TABLE_NAME2,projection,null,null,null,null,null
        );
        while (cursor.moveToNext()){
            NuocNgot nuocNgot =new NuocNgot(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_idNuocNgot)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_tenNuocNgot)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_idNhaCungCap)),
                    cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_image)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_price)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_quantity)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_mota))
            );
            nuocNgots.add(nuocNgot);
        }
        cursor.close();
        return nuocNgots;
    }

    public List<NhaCungCap> getAllNhaCungCap(){
        List<NhaCungCap> nhaCungCaps = new ArrayList<>();
        String[] projection ={
                DBHelper.COLUMN_idNcc,
                DBHelper.COLUMN_tenNcc
        };
        Cursor cursor = getReadableDatabase().query(
                DBHelper.TABLE_NAME1,projection,null,null,null,null,null
        );
        while (cursor.moveToNext()){

            NhaCungCap nhaCungCap=new NhaCungCap(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_idNcc)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_tenNcc))
            );
            nhaCungCaps.add(nhaCungCap);
        }
        cursor.close();
        return nhaCungCaps;
    }
    public void addNcc(NhaCungCap nhaCungCap) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenNuocNgot, nhaCungCap.getTen());
        long them= getWritableDatabase().insert(DBHelper.TABLE_NAME1, null, values);
    }
    public void updateNcc(NhaCungCap nhaCungCap){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenNcc, nhaCungCap.getTen());
        String selection = DBHelper.COLUMN_idNcc+" = ?";
        String[] selectionArgs={nhaCungCap.getId()+""};
        int updatedRows= getWritableDatabase().update(
                DBHelper.TABLE_NAME1,values,selection,selectionArgs
        );
    }
    public void deleteNcc(int ma){
        String selection = DBHelper.COLUMN_idNcc+" = ?";
        String[] selectionArgs={String.valueOf(ma)};
        int deletedRows= getWritableDatabase().delete(
                DBHelper.TABLE_NAME1,selection,selectionArgs
        );
    }
    public void addNuocNgot(NuocNgot nuocNgot) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenNuocNgot, nuocNgot.getTen());
        values.put(DBHelper.COLUMN_idNhaCungCap, nuocNgot.getIdncc());
        values.put(DBHelper.COLUMN_image, nuocNgot.getImage());
        values.put(DBHelper.COLUMN_price, nuocNgot.getPrice());
        values.put(DBHelper.COLUMN_quantity, nuocNgot.getQuantity());
        values.put(DBHelper.COLUMN_mota, nuocNgot.getMota());
        long them= getWritableDatabase().insert(DBHelper.TABLE_NAME2, null, values);
    }
    public void deleteNuocNgot(int ma){
        String selection = DBHelper.COLUMN_idNuocNgot+" = ?";
        String[] selectionArgs={String.valueOf(ma)};
        int deletedRows= getWritableDatabase().delete(
                DBHelper.TABLE_NAME2,selection,selectionArgs
        );
    }
    public void updateNuocNgot(NuocNgot nuocNgot){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenNuocNgot, nuocNgot.getTen());
        values.put(DBHelper.COLUMN_idNhaCungCap, nuocNgot.getIdncc());
        values.put(DBHelper.COLUMN_image, nuocNgot.getImage());
        values.put(DBHelper.COLUMN_price, nuocNgot.getPrice());
        values.put(DBHelper.COLUMN_quantity, nuocNgot.getQuantity());
        values.put(DBHelper.COLUMN_mota, nuocNgot.getMota());
        String selection = DBHelper.COLUMN_idNuocNgot+" = ?";
        String[] selectionArgs={nuocNgot.getId()+""};
        int updatedRows= getWritableDatabase().update(
                DBHelper.TABLE_NAME2,values,selection,selectionArgs
        );
    }
    public boolean hasNuocNgots(int idNcc) {
        String[] projection = {COLUMN_idNuocNgot};
        String selection = COLUMN_idNhaCungCap + " = ?";
        String[] selectionArgs = {String.valueOf(idNcc)};

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME2, projection, selection, selectionArgs, null, null, null
        );
        boolean hasNuocNgots = cursor.getCount() > 0;
        cursor.close();
        System.out.println( "Has NuocNgots: " + hasNuocNgots);
        return hasNuocNgots;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
