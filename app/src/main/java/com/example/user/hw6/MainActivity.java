package com.example.user.hw6;



import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText editname ,edittel , editaddress;
    TextView texname,textel , texaddress , texno;
    Button add,edit ,delete, query , detail;
    SQLiteDatabase dbrw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editname = (EditText) findViewById(R.id.editname);
        edittel = (EditText) findViewById(R.id.edittel);
        editaddress = (EditText) findViewById(R.id.editaddress);
        texno = (TextView) findViewById(R.id.texno);
        texname = (TextView) findViewById(R.id.textname);
        textel = (TextView) findViewById(R.id.texttel);
        texaddress = (TextView) findViewById(R.id.texaddress);
        add =(Button) findViewById(R.id.add);
        edit = (Button)findViewById(R.id.edit);
        delete =(Button)findViewById(R.id.delete);
        detail =(Button)findViewById(R.id.detail);
        query = (Button) findViewById(R.id.query);

        MyDBHelper dbhelper = new MyDBHelper(this);
        dbrw = dbhelper.getWritableDatabase();


        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                newStore();
            }
        });


        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                renewStore();
            }
        });

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteStore();
            }
        });

        query.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                queryStore();
            }
        });


        detail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                detailStore();
            }
        });





    }



    public void newStore(){

        if (editname.getText().toString().equals("")
                || edittel.getText().toString().equals("")
                    || editaddress.getText().toString().equals("")) {
            Toast.makeText( this, "輸入資料不完全", Toast.LENGTH_SHORT ).show();
            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }

      else{
           int tel = Integer.parseInt(edittel.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("title" , editname.getText().toString());
            cv.put("tel",tel);
            cv.put("address", editaddress.getText().toString());

            dbrw.insert("myTable",null,cv);

            Toast.makeText(this ,"新增\n店名:" + editname.getText().toString()
                            + "\n價格:" + tel  +
                    "\n店址:" + editaddress.getText().toString(), Toast.LENGTH_SHORT).show();

            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }
    }

    public  void renewStore(){

        if (editname.getText().toString().equals("") ||
                (edittel.getText().toString().equals("") == true) &&
                        (editaddress.getText().toString().equals("")== true) ) {
            Toast.makeText(this, "沒有輸入更新值", Toast.LENGTH_SHORT).show();
            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }
        else if ((edittel.getText().toString().equals("") == true) &&
                (editaddress.getText().toString().equals("")== false)) {

            ContentValues cv = new ContentValues();
            cv.put("address", editaddress.getText().toString());

            dbrw.update("myTable",cv, "title=" + "'" + editname.getText().toString() + "'", null);

            Toast.makeText(this ,"成功", Toast.LENGTH_SHORT).show();

            editname.setText("");
            edittel.setText("");
            editaddress.setText("");

        }
        else if((edittel.getText().toString().equals("") == false) &&
                (editaddress.getText().toString().equals("")== true)){
            int newtel = Integer.parseInt(edittel.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("tel",newtel);


            dbrw.update("myTable",cv, "title=" + "'" + editname.getText().toString() + "'", null);

            Toast.makeText(this ,"成功", Toast.LENGTH_SHORT).show();

            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }
        else {
            int newtel = Integer.parseInt(edittel.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("tel",newtel);
            cv.put("address", editaddress.getText().toString());


            dbrw.update("myTable",cv, "title=" + "'" + editname.getText().toString() + "'", null);

            Toast.makeText(this ,"成功", Toast.LENGTH_SHORT).show();

            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }

    }

    public  void deleteStore(){

        if (editname.getText().toString().equals("")) {
            Toast.makeText(this, "請輸入要刪除之值", Toast.LENGTH_SHORT).show();
            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }
        else {

            dbrw.delete("myTable","title=" + "'" + editname.getText().toString() + "'", null);

            Toast.makeText(this ,"刪除成功", Toast.LENGTH_SHORT).show();

            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }
    }


    public  void queryStore(){

        String index = "順序\n",title = "店名\n" , tel = "電話\n" , address = "店址\n";
        String[] colum ={"title" , "tel" ,"address"};

        Cursor c;

        if(editname.getText().toString().equals("")) {
            c = dbrw.query("myTable", colum, null, null, null, null, null);
        }
        else {
            c = dbrw.query("mytable", colum, "title=" + "'" +
                    editname.getText().toString() + "'", null, null, null, null);
        }


        if(c.getCount() == 0){
            Toast.makeText(this , "共有" + c.getCount()+ "筆記錄" , Toast.LENGTH_SHORT).show();

            texno.setText(index);
            texname.setText(title);
            textel.setText(tel);
            texaddress.setText(address);
        }

        else if (c.getCount() >0){

            c.moveToFirst();
            for (int i =0; i<c.getCount();i++){
                index += (i+1) + "\n";
                title += c.getString(0) + "\n";
                tel += c.getString(1) + "\n";
                address += c.getString(2) + "\n";
                c.moveToNext();
            }



                texno.setText(index);
                texname.setText(title);
                textel.setText(tel);
                texaddress.setText(address);

            Toast.makeText(this , "共有" + c.getCount()+ "筆記錄" , Toast.LENGTH_SHORT).show();
        }
    }


    public  void detailStore() {

        final String[] item1 = {"地圖位置","商品管理","下單管理","歷史銷售紀錄","取消"};
        AlertDialog.Builder dialog_list = new AlertDialog.Builder(MainActivity.this);
        dialog_list.setTitle("請選擇:");
        dialog_list.setItems(item1, new DialogInterface.OnClickListener(){
            @Override

            //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
            public void onClick(DialogInterface dialog, int which) {
                switch (item1[which]) {

                    case "地圖位置":

                        if (editname.getText().toString().equals( "" )) {
                            Toast.makeText( MainActivity.this, "未輸入店名", Toast.LENGTH_SHORT ).show();
                        }
                        else {


                            //查詢輸入之店家住址
                            String index = "", title = "", tel = "", address = "";
                            String[] colum = {"title", "tel", "address"};

                            Cursor c;

                            c = dbrw.query( "mytable", colum, "title=" + "'" +
                                    editname.getText().toString() + "'", null, null, null, null );

                            c.moveToFirst();
                            for (int i = 0; i < c.getCount(); i++) {
                                address += c.getString( 2 ) + "\n";
                                c.moveToNext();
                            }

                            if((address == "") == false ) {

                                Toast.makeText( MainActivity.this, "轉至" + editname.getText().toString() + "的" + item1[which], Toast.LENGTH_SHORT ).show();

                                //建立Bundle傳送資料至Address頁面
                                Bundle bundle = new Bundle();
                                bundle.putString("StoreName", editname.getText().toString());
                                bundle.putString("StoreLocation", address);

                                Intent intent1 = new Intent();
                                intent1.putExtra("key1", bundle);
                                intent1.setClass(MainActivity.this, MapAddress.class);
                                startActivityForResult(intent1, 0);

                                editname.setText("");
                                edittel.setText("");
                                editaddress.setText("");
                            }
                            else {
                                Toast.makeText( MainActivity.this, "未找到店名", Toast.LENGTH_SHORT ).show();
                                editname.setText("");
                                edittel.setText("");
                                editaddress.setText("");
                            }
                        }
                     break;

                    case "商品管理":


                        if (editname.getText().toString().equals( "" )) {
                            Toast.makeText( MainActivity.this, "未輸入店名", Toast.LENGTH_SHORT ).show();
                        }
                        else {


                            //查詢輸入之店家住址
                            String index = "", title = "", tel = "", address = "";
                            String[] colum = {"title", "tel", "address"};

                            Cursor c;

                            c = dbrw.query( "mytable", colum, "title=" + "'" +
                                    editname.getText().toString() + "'", null, null, null, null );

                            c.moveToFirst();
                            for (int i = 0; i < c.getCount(); i++) {
                                address += c.getString( 2 ) + "\n";
                                c.moveToNext();
                            }

                            if((address == "") == false ) {

                                Toast.makeText( MainActivity.this, "轉至" + editname.getText().toString() + "的" + item1[which], Toast.LENGTH_SHORT ).show();

                                //建立Bundle傳送資料至Address頁面
                                Bundle bundle = new Bundle();
                                bundle.putString("StoreName", editname.getText().toString());

                                Intent intent2 = new Intent();
                                intent2.putExtra("key2", bundle);
                                intent2.setClass(MainActivity.this, CommodityManagement.class);
                                startActivityForResult(intent2, 0);

                                editname.setText("");
                                edittel.setText("");
                                editaddress.setText("");
                            }
                            else {
                                Toast.makeText( MainActivity.this, "未找到店名", Toast.LENGTH_SHORT ).show();
                                editname.setText("");
                                edittel.setText("");
                                editaddress.setText("");
                            }
                        }
                        break;

                    case "下單管理":
                        if(editname.getText().toString().equals("")) {
                            Toast.makeText( MainActivity.this,"未輸入店名",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText( MainActivity.this, "轉至" + item1[which], Toast.LENGTH_SHORT ).show();
                            Intent intent3 = new Intent();
                            intent3.setClass( MainActivity.this, OrderManagement.class );
                            startActivityForResult( intent3, 0 );
                        }
                        break;

                    case "歷史銷售紀錄":
                        if(editname.getText().toString().equals("")) {
                            Toast.makeText( MainActivity.this,"未輸入店名",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText( MainActivity.this, "轉至" + item1[which], Toast.LENGTH_SHORT ).show();
                            Intent intent4 = new Intent();
                            intent4.setClass( MainActivity.this, SaleHistory.class );
                            startActivityForResult( intent4, 0 );
                        }
                        break;

                    default:
                        break;
                }



            }
        });
        dialog_list.show();

    }


}
