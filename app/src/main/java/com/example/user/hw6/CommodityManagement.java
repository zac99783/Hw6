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

public class CommodityManagement extends AppCompatActivity {


    EditText editgoods , editprice , editgoods_amount;
    TextView texname,texgoods_amount,texgoods , texprice , texno,textitle ;
    Button add,edit ,delete, query , return_main;
    SQLiteDatabase dbrw2;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_management);

        add =(Button) findViewById(R.id.add);
        edit = (Button)findViewById(R.id.edit);
        delete =(Button)findViewById(R.id.delete);
        query = (Button) findViewById(R.id.query);
        return_main =(Button)findViewById(R.id.return_main);
        editgoods= (EditText) findViewById(R.id.editgoods);
        editprice = (EditText) findViewById(R.id.editprice);
        editgoods_amount = (EditText) findViewById(R.id.editgoods_amount);
        texno = (TextView) findViewById(R.id.texno);
        texgoods = (TextView) findViewById(R.id.textgoods);
        texprice = (TextView) findViewById(R.id.textprice);
        texgoods_amount = (TextView) findViewById(R.id.texgoods_amount);
        textitle = (TextView) findViewById(R.id.textitle);

        MyDBHelper CommodityDB = new MyDBHelper(this);
        dbrw2 = CommodityDB.getWritableDatabase();

        //取得店名
        Bundle bundle = getIntent().getExtras().getBundle("key2");
        String getStoreData = bundle.getString("StoreName");
        texname = (TextView) findViewById(R.id.texname);
        texname.setText(getStoreData + "\n");




       add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGoods();
            }
        });


        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                renewGoods();
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
                intent1.setClass(CommodityManagement.this, MainActivity.class);
                startActivityForResult(intent1, 0);
                Toast.makeText(CommodityManagement.this, "返回主畫面", Toast.LENGTH_SHORT ).show();
            }
        });
    }


    public void newGoods() {

        Bundle bundle = getIntent().getExtras().getBundle("key2");
        String getStoreData = bundle.getString("StoreName");


        if (editgoods.getText().toString().equals("")
                || editprice.getText().toString().equals("")
                || editgoods_amount.getText().toString().equals("")) {
            Toast.makeText(this, "輸入資料不完全", Toast.LENGTH_SHORT).show();
            editgoods.setText("");
            editprice.setText("");
            editgoods_amount.setText("");
        } else {
            int price = Integer.parseInt(editprice.getText().toString());
            int goods_amount = Integer.parseInt(editgoods_amount.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("goods", editgoods.getText().toString());
            cv.put("price", price);
            cv.put("goods_amount", goods_amount);
            cv.put("title", getStoreData);

            dbrw2.insert("myGoods", null, cv);

            Toast.makeText(this, "在店名:"+ getStoreData +"  新增" +
                    "\n商品:" + editgoods.getText().toString()
                    +"\n價格:" + price +
                    "\n數量:" + goods_amount, Toast.LENGTH_SHORT).show();

            editgoods.setText("");
            editprice.setText("");
            editgoods_amount.setText("");
        }
    }

    public  void renewGoods(){



        if (editgoods.getText().toString().equals("") ||
                (editprice.getText().toString().equals("") == true) &&
                        (editgoods_amount.getText().toString().equals("")== true) ) {
            Toast.makeText(this, "沒有輸入更新值", Toast.LENGTH_SHORT).show();
            editgoods.setText("");
            editprice.setText("");
            editgoods_amount.setText("");
        }
        else if ((editprice.getText().toString().equals("") == true) &&
                (editgoods_amount.getText().toString().equals("")== false)) {
            int newgoods_amount = Integer.parseInt(editgoods_amount.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("goods_amount", newgoods_amount);


            dbrw2.update("myGoods",cv, "goods=" + "'" + editgoods.getText().toString() + "'", null);

            Toast.makeText(this ,"成功", Toast.LENGTH_SHORT).show();

            editgoods.setText("");
            editprice.setText("");
            editgoods_amount.setText("");

        }
        else if((editprice.getText().toString().equals("") == false) &&
                (editgoods_amount.getText().toString().equals("")== true)){
            int newprice = Integer.parseInt(editprice.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("price",newprice);



            dbrw2.update("myGoods",cv, "goods=" + "'" + editgoods.getText().toString() + "'", null);

            Toast.makeText(this ,"成功", Toast.LENGTH_SHORT).show();
            editgoods.setText("");
            editprice.setText("");
            editgoods_amount.setText("");
        }
        else {

            ContentValues cv = new ContentValues();
            int newprice = Integer.parseInt(editprice.getText().toString());
            int newgoods_amount = Integer.parseInt(editgoods_amount.getText().toString());
            cv.put("price",newprice);
            cv.put("goods_amount", newgoods_amount);


            dbrw2.update("myGoods",cv, "goods=" + "'" + editgoods.getText().toString() + "'", null);

            Toast.makeText(this ,"成功", Toast.LENGTH_SHORT).show();

            editgoods.setText("");
            editprice.setText("");
            editgoods_amount.setText("");
        }
    }

    public  void deleteGoods(){




        if (editgoods.getText().toString().equals(""))
            Toast.makeText(this,"請輸入要刪除之商品",Toast.LENGTH_SHORT).show();

        else {

            dbrw2.delete("myGoods","goods=" + "'" + editgoods.getText().toString() + "'", null);

            Toast.makeText(this ,"刪除成功", Toast.LENGTH_SHORT).show();

            editgoods.setText("");
            editprice.setText("");
            editgoods_amount.setText("");
        }
    }

    public  void  queryGoods() {

        Bundle bundle = getIntent().getExtras().getBundle("key2");
        String getStoreData = bundle.getString("StoreName");




        String index2 = "順序\n", goods = "商品\n", price = "價格\n", goods_amount = "數量\n", name = "店名\n";
        String[] colum2 = {"goods", "price", "goods_amount", "title"};

        Cursor c2;
        if (editgoods.getText().toString().equals("")) {
            c2 = dbrw2.query("myGoods", colum2, "title=" + "'" + getStoreData + "'", null, null, null, null);
        }
        else{

            c2 = dbrw2.query("myGoods", colum2,
                             "goods=" + "'" + editgoods.getText().toString() + "'"+ " and " + "title=" + "'" + getStoreData + "'", null, null, null, null);
        }


        if (c2.getCount() >0) {
            c2.moveToFirst();

                for (int a = 0; a < c2.getCount(); a++) {
                    index2 += (a + 1) + "\n";
                    goods += c2.getString(0) + "\n";
                    price += c2.getString(1) + "\n";
                    goods_amount += c2.getString(2) + "\n";
                    name += c2.getString(3) + "\n";
                    c2.moveToNext();

                }

                texno.setText(index2);
                texgoods.setText(goods);
                texprice.setText(price);
                texgoods_amount.setText(goods_amount);
                textitle.setText(name);
                Toast.makeText(this, "共有" + c2.getCount() + "筆記錄", Toast.LENGTH_SHORT).show();

        }


        else{

            texno.setText(index2);
            texgoods.setText(goods);
            texprice.setText(price);
            texgoods_amount.setText(goods_amount);
            textitle.setText(name);
            Toast.makeText(this, "共有0筆記錄", Toast.LENGTH_SHORT).show();

        }


    }
}


