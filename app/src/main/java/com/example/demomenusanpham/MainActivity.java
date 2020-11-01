package com.example.demomenusanpham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<SanPham> sanPhams;
    private ListView listView;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                confirmXoaSanPham(sanPhams.get(i));
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogSuaSanPham(sanPhams.get(i));
            }
        });
    }

    private void init() {
        listView = (ListView) findViewById(R.id.listViewSanPham);
        sanPhams = new ArrayList<>();
        sanPhams.add(new SanPham("1", "Sữa Ngôi sao", "Lon"));
        sanPhams.add(new SanPham("2", "Đường", "KG"));
        sanPhams.add(new SanPham("3", "Dầu ăn", "chai"));

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sanPhams);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.themSanPham: {
                dialogThemSanPham();
                break;
            }
            case R.id.timSanPham: {
                dialogTimSanPham();
                break;
            }
            case R.id.trangChu: {
                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, sanPhams);
                listView.setAdapter(adapter);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean uniqueMaSanPham(String maSanPham) {
        for (int i = 0; i < sanPhams.size(); i++) {
            if (sanPhams.get(i).getMa().equals(maSanPham)) {
                return true;
            }
        }
        return false;
    }

    private void dialogThemSanPham() {
        final Dialog dialog = new Dialog(this);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().widthPixels * 0.93);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_sanpham);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(width, height);

        final EditText txtMaSanPham = (EditText) dialog.findViewById(R.id.txtMaSanPhamThem);
        final EditText txtTenSanPham = (EditText) dialog.findViewById(R.id.txtTenSanPhamThem);
        final EditText txtDonViTinh = (EditText) dialog.findViewById(R.id.txtDonViTinhThem);

        Button btnLuu = (Button) dialog.findViewById(R.id.btnLuuThem);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuyThem);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maSanPham = txtMaSanPham.getText().toString();
                String tenSanPham = txtTenSanPham.getText().toString();
                String donViTinh = txtDonViTinh.getText().toString();

                if (maSanPham.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Chưa nhập Mã sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                } else if (uniqueMaSanPham(maSanPham)) {
                    Toast.makeText(MainActivity.this, "Mã sản phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                } else if (tenSanPham.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Chưa nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                } else if (donViTinh.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Chưa nhập đơn vị tính", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    SanPham sanPham = new SanPham(maSanPham, tenSanPham, donViTinh);
                    sanPhams.add(sanPham);
                    adapter.notifyDataSetChanged();
                    txtMaSanPham.setText("");
                    txtTenSanPham.setText("");
                    txtDonViTinh.setText("");

                    Toast.makeText(MainActivity.this, "Thêm Sản phẩm thành công!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void dialogSuaSanPham(final SanPham sanPham) {
        final Dialog dialog = new Dialog(this);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().widthPixels * 0.93);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_sanpham);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(width, height);

        final EditText txtMaSanPham = dialog.findViewById(R.id.txtMaSanPhamSua);
        final EditText txtTenSanPham = dialog.findViewById(R.id.txtTenSanPhamSua);
        final EditText txtDonViTinh = dialog.findViewById(R.id.txtDonViTinhSua);

        Button btnLuu = (Button) dialog.findViewById(R.id.btnLuuSua);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuySua);

        txtMaSanPham.setText(sanPham.getMa() + "");
        txtTenSanPham.setText(sanPham.getTen() + "");
        txtDonViTinh.setText(sanPham.getDvt() + "");


        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSanPham = txtTenSanPham.getText().toString();
                String donViTinh = txtDonViTinh.getText().toString();

                if (tenSanPham.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Chưa nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                } else if (donViTinh.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Chưa nhập đơn vị tính", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sanPham.setTen(txtTenSanPham.getText().toString());
                    sanPham.setDvt(txtDonViTinh.getText().toString());
                    sanPhams.set(sanPhams.indexOf(sanPham), sanPham);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Sửa Sản phẩm thành công!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void dialogTimSanPham(){
        final Dialog dialog = new Dialog(this);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().widthPixels * 0.60);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_find_sanpham);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(width, height);

        final EditText txtTenSanPhamTim = (EditText) dialog.findViewById(R.id.txtTenSanPhamTim);

        Button btnTim = (Button) dialog.findViewById(R.id.btnTim);
        Button btnHuyTim = (Button) dialog.findViewById(R.id.btnHuyTim);

        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSanPham = txtTenSanPhamTim.getText().toString();

                List<SanPham> tempTimKiem = new ArrayList<>();
                if(tenSanPham.isEmpty()){
                    Toast.makeText(MainActivity.this, "Chưa nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    for (SanPham sanPham : sanPhams){
                        if(sanPham.getTen().toLowerCase().trim().contains(tenSanPham.toLowerCase().trim())){
                            tempTimKiem.add(sanPham);
                        }
                    }
                }

                if(tempTimKiem.size() > 0){
                    adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, tempTimKiem);
                    listView.setAdapter(adapter);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Không tìm thấy sản phẩm có tên" + tenSanPham, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHuyTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, sanPhams);
                listView.setAdapter(adapter);
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void confirmXoaSanPham(final SanPham sanPham){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có muốn xoá sản phẩm " + sanPham.getTen());
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sanPhams.remove(sanPham);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Xoá sản phẩm thành công", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        builder.create().show();
    }
}