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
        //設定畫面元件連接
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

        //開啟資料庫
        MyDBHelper dbhelper = new MyDBHelper(this);
        dbrw = dbhelper.getWritableDatabase();

        //新增商家資料功能
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                newStore();
            }
        });
        //編輯商家資料功能
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                renewStore();
            }
        });
        //刪除商家資料功能
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteStore();
            }
        });
        //尋找商家資料與顯示功能
        query.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                queryStore();
            }
        });
        //跳至額外"abcd"之功能
        detail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                detailStore();
            }
        });





    }


    //新增商家資料功能
    public void newStore(){

        //辨識輸入(皆未輸入、Toast顯示資料不正確)
        if (editname.getText().toString().equals("")
                || edittel.getText().toString().equals("")
                    || editaddress.getText().toString().equals("")) {
            Toast.makeText( this, "輸入資料不完全", Toast.LENGTH_SHORT ).show();

            //清空輸入之EditText
            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }

        //新增商家、電話、地址至資料庫(Toast顯示輸入值)
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
            //清空輸入之EditText
            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }
    }
    //編輯商家資料功能
    public  void renewStore(){
        //辨識輸入(皆未輸入、Toast顯示資料不正確)
        if (editname.getText().toString().equals("") ||
                (edittel.getText().toString().equals("") == true) &&
                        (editaddress.getText().toString().equals("")== true) ) {
            Toast.makeText(this, "沒有輸入更新值", Toast.LENGTH_SHORT).show();
            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }//只有更新商家地址
        else if ((edittel.getText().toString().equals("") == true) &&
                (editaddress.getText().toString().equals("")== false)) {

            ContentValues cv = new ContentValues();
            cv.put("address", editaddress.getText().toString());

            dbrw.update("myTable",cv, "title=" + "'" + editname.getText().toString() + "'", null);

            Toast.makeText(this ,"成功", Toast.LENGTH_SHORT).show();
            //清空輸入之EditText
            editname.setText("");
            edittel.setText("");
            editaddress.setText("");

        }//只有更新商家電話
        else if((edittel.getText().toString().equals("") == false) &&
                (editaddress.getText().toString().equals("")== true)){
            int newtel = Integer.parseInt(edittel.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("tel",newtel);


            dbrw.update("myTable",cv, "title=" + "'" + editname.getText().toString() + "'", null);

            Toast.makeText(this ,"成功", Toast.LENGTH_SHORT).show();
            //清空輸入之EditText
            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }
        else {//資料皆更新
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
    //刪除商家資料功能
    public  void deleteStore(){

        //辨識輸入是否有值
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

    //尋找商家資料與顯示功能
    public  void queryStore(){

        String index = "順序\n",title = "店名\n" , tel = "電話\n" , address = "店址\n";
        String[] colum ={"title" , "tel" ,"address"};

        Cursor c;

        //判斷是否尋找(店家名稱為空值，則顯示出所有商家
        if(editname.getText().toString().equals("")) {
            c = dbrw.query("myTable", colum, null, null, null, null, null);
        }
        else {
            c = dbrw.query("mytable", colum, "title=" + "'" +
                    editname.getText().toString() + "'", null, null, null, null);
        }

        //判斷沒有找到該商家之輸出與提示
        if(c.getCount() == 0){
            Toast.makeText(this , "共有" + c.getCount()+ "筆記錄" , Toast.LENGTH_SHORT).show();

            texno.setText(index);
            texname.setText(title);
            textel.setText(tel);
            texaddress.setText(address);
        }
        //判斷有值並將商家資料顯示與提示
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

    //轉跳至a,b,c,d功能
    public  void detailStore() {

        final String[] item1 = {"地圖位置(功能 2-a)","商品管理(功能 2-b)","下單管理(功能 2-c&d)","取消"};
        AlertDialog.Builder dialog_list = new AlertDialog.Builder(MainActivity.this);
        dialog_list.setTitle("請選擇:");
        dialog_list.setItems(item1, new DialogInterface.OnClickListener(){
            @Override

            //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
            public void onClick(DialogInterface dialog, int which) {
                switch (item1[which]) {
                    //功能a
                    case "地圖位置(功能 2-a)":

                        //判斷使用者有無填寫商家欄位並提示
                        if (editname.getText().toString().equals( "" )) {
                            Toast.makeText( MainActivity.this, "未輸入店名", Toast.LENGTH_SHORT ).show();
                            editname.setText("");
                            edittel.setText("");
                            editaddress.setText("");
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
                                address = c.getString( 2 );
                                c.moveToNext();
                            }
                            //如果輸入之商家在資料庫有值，執行數值傳遞至地圖位置頁面
                            if((address == "") == false ) {

                                Toast.makeText( MainActivity.this, "轉至" + editname.getText().toString() + "的" + item1[which], Toast.LENGTH_SHORT ).show();

                                //建立Bundle傳送資料至頁面
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
                            else {//如果輸入之商家沒有在資料庫中，提示並清空填寫欄位
                                Toast.makeText( MainActivity.this, "未找到店名", Toast.LENGTH_SHORT ).show();
                                editname.setText("");
                                edittel.setText("");
                                editaddress.setText("");
                            }
                        }
                     break;
                    //功能b
                    case "商品管理(功能 2-b)":

                        //判斷使用者有無填寫商家欄位並提示
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
                            //如果輸入之商家在資料庫有值，執行數值傳遞至商品管理頁面
                            if((address == "") == false ) {

                                Toast.makeText( MainActivity.this, "轉至" + editname.getText().toString() + "的" + item1[which], Toast.LENGTH_SHORT ).show();

                                //建立Bundle傳送資料至頁面
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
                            else {//如果輸入之商家沒有在資料庫中，提示並清空填寫欄位
                                Toast.makeText( MainActivity.this, "未找到店名", Toast.LENGTH_SHORT ).show();
                                editname.setText("");
                                edittel.setText("");
                                editaddress.setText("");
                            }
                        }
                        break;

                    case "下單管理(功能 2-c&d)":

                            Toast.makeText( MainActivity.this, "轉至" + item1[which], Toast.LENGTH_SHORT ).show();
                            Intent intent3 = new Intent();
                            intent3.setClass( MainActivity.this, OrderManagement.class );
                            startActivityForResult( intent3, 0);

                             break;

                    case "取消":

                        break;


                    default:
                        break;
                }



            }
        });
        dialog_list.show();

    }


}
