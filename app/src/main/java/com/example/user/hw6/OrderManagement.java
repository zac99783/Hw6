package com.example.user.hw6;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class OrderManagement extends AppCompatActivity {


    EditText editgoods , editorder_amount;
    TextView texname,texgoods_amount,texgoods , texprice , texno,textitle ,texorder_amount ;
    Button add,delete, query , return_main;
    SQLiteDatabase dbrw2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        add =(Button) findViewById(R.id.add);
        delete =(Button)findViewById(R.id.delete);
        query = (Button) findViewById(R.id.query);
        return_main =(Button)findViewById(R.id.return_main);
        editgoods = (EditText) findViewById(R.id.editgoods);
        editorder_amount = (EditText) findViewById(R.id.editorder_amount);
        texno = (TextView) findViewById(R.id.texno);
        texgoods = (TextView) findViewById(R.id.textgoods);
        texprice = (TextView) findViewById(R.id.textprice);
        texgoods_amount = (TextView) findViewById(R.id.texgoods_amount);
        textitle = (TextView) findViewById(R.id.textitle);
        texorder_amount = (TextView) findViewById(R.id.texorder_amount);

        MyDBHelper CommodityDB = new MyDBHelper(this);
        dbrw2 = CommodityDB.getWritableDatabase();



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGoods();
            }
        });




        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteGoods();
            }
        });

      query.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                queryGoods();
            }
        });


        return_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent();
                intent1.setClass(OrderManagement.this, MainActivity.class);
                startActivityForResult(intent1, 0);
                Toast.makeText(OrderManagement.this, "返回主畫面", Toast.LENGTH_SHORT ).show();
            }
        });
    }


    public void newGoods() {

        if (editgoods.getText().toString().equals("") ||
                editorder_amount.getText().toString().equals("")) {
            Toast.makeText(this, "沒有輸入更新值", Toast.LENGTH_SHORT).show();
            editgoods.setText("");
            editorder_amount.setText("");
        }


        else {

            String index2 = "", goods = "", price = "", goods_amount = "", name = "", order_amount = "";
            String[] colum2 = {"goods","price","goods_amount", "title","order_amount"};

            Cursor c2;
            c2 = dbrw2.query("myGoods", colum2, "goods=" + "'" +
                    editgoods.getText().toString() + "'", null, null, null, null);


                c2.moveToFirst();
            for (int a = 0; a < c2.getCount(); a++) {
                goods_amount = c2.getString(2);
                order_amount = c2.getString(4);
                c2.moveToNext();
            }

            if( goods_amount.equals("") == false) {
                int intgoods_amount = Integer.parseInt(goods_amount);
                int intorder_amount = Integer.parseInt(order_amount);
                int inteditorder_amount = Integer.parseInt(editorder_amount.getText().toString());



                intgoods_amount = intgoods_amount - inteditorder_amount;
                intorder_amount = intorder_amount + inteditorder_amount;
                    if (intgoods_amount >= 0) {
                        ContentValues cv = new ContentValues();
                        cv.put("goods_amount", intgoods_amount);
                        cv.put("order_amount", intorder_amount);

                        dbrw2.update("myGoods", cv, "goods=" + "'" + editgoods.getText().toString() + "'", null);

                        Toast.makeText(this, "下單成功", Toast.LENGTH_SHORT).show();
                        editgoods.setText("");
                        editorder_amount.setText("");
                    } else {

                        Toast.makeText(this, "庫存不足", Toast.LENGTH_SHORT).show();

                    }
            }
            else {

                Toast.makeText(this, "無此商品名稱", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public  void deleteGoods(){



        if (editgoods.getText().toString().equals("")) {
            Toast.makeText(this, "沒有輸入商品名稱", Toast.LENGTH_SHORT).show();
            editgoods.setText("");
            editorder_amount.setText("");
        }


        else {

            String index2 = "", goods = "", price = "", goods_amount = "", name = "", order_amount = "";
            String[] colum2 = {"goods","price","goods_amount", "title","order_amount"};

            Cursor c2;
            c2 = dbrw2.query("myGoods", colum2, "goods=" + "'" +
                    editgoods.getText().toString() + "'", null, null, null, null);


            c2.moveToFirst();
            for (int a = 0; a < c2.getCount(); a++) {
                goods_amount = c2.getString(2);
                order_amount = c2.getString(4);
                c2.moveToNext();
            }

            if( goods_amount.equals("") == false) {
                int intgoods_amount = Integer.parseInt(goods_amount);
                int intorder_amount = Integer.parseInt(order_amount);
                int inteditorder_amount = 0;


                intgoods_amount = intgoods_amount + intorder_amount;

                if (intorder_amount > 0) {
                    ContentValues cv = new ContentValues();
                    cv.put("goods_amount", intgoods_amount);
                    cv.put("order_amount", inteditorder_amount);

                    dbrw2.update("myGoods", cv, "goods=" + "'" + editgoods.getText().toString() + "'", null);

                    Toast.makeText(this, "刪除成功", Toast.LENGTH_SHORT).show();
                    editgoods.setText("");
                    editorder_amount.setText("");
                } else {

                    Toast.makeText(this, "無此購買量", Toast.LENGTH_SHORT).show();

                }
            }
            else {

                Toast.makeText(this, "無此商品名稱", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void  queryGoods() {


        String index2 = "順序\n", goods = "商品\n", price = "價格\n", goods_amount = "庫存數量\n", name = "店名\n", order_amount = "購買數量\n";
        String[] colum2 = {"goods", "price", "goods_amount", "title", "order_amount"};

        Cursor c2;
        if (editgoods.getText().toString().equals("")) {
            c2 = dbrw2.query("myGoods", colum2,null , null, null, null, null);
        }
        else{

            c2 = dbrw2.query("myGoods", colum2,
                    "goods=" + "'" + editgoods.getText().toString() + "'", null, null, null, null);
        }

        if (c2.getCount() >0) {
            c2.moveToFirst();

            for (int a = 0; a < c2.getCount(); a++) {
                index2 += (a + 1) + "\n";
                goods += c2.getString(0) + "\n";
                price += c2.getString(1) + "\n";
                goods_amount += c2.getString(2) + "\n";
                name += c2.getString(3) + "\n";
                order_amount += c2.getString(4) + "\n";
                c2.moveToNext();

            }

            texno.setText(index2);
            texgoods.setText(goods);
            texprice.setText(price);
            texgoods_amount.setText(goods_amount);
            textitle.setText(name);
            texorder_amount.setText(order_amount);
            Toast.makeText(this, "共有" + c2.getCount() + "筆記錄", Toast.LENGTH_SHORT).show();

        }


        else{

            texno.setText(index2);
            texgoods.setText(goods);
            texprice.setText(price);
            texgoods_amount.setText(goods_amount);
            textitle.setText(name);
            texorder_amount.setText(order_amount);
            Toast.makeText(this, "共有0筆記錄", Toast.LENGTH_SHORT).show();

        }


    }
}


