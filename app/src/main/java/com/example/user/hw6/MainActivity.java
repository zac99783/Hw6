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
                    || editaddress.getText().toString().equals(""))
            Toast.makeText(this,"輸入資料不完全",Toast.LENGTH_SHORT).show();
        else {
            double tel = Double.parseDouble(edittel.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("title" , editname.getText().toString());
            cv.put("tel",tel);
            cv.put("address", editaddress.getText().toString());

            dbrw.insert("myTable",null,cv);

            Toast.makeText(this ,"新增\n店名:" + editname.getText().toString()
                            + "價格:" + tel  +
                    "店址:" + editaddress.getText().toString(), Toast.LENGTH_SHORT).show();

            editname.setText("");
            edittel.setText("");
            editaddress.setText("");
        }
    }

   public  void renewStore(){

        if (editname.getText().toString().equals("")
                || edittel.getText().toString().equals("")
                    || editaddress.getText().toString().equals(""))
            Toast.makeText(this,"沒有輸入更新值",Toast.LENGTH_SHORT).show();

        else {
            double newtel = Double.parseDouble(edittel.getText().toString());
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

        if (editname.getText().toString().equals(""))
            Toast.makeText(this,"請輸入要刪除之值",Toast.LENGTH_SHORT).show();

        else {

            dbrw.delete("myTable","title=" + "'" + editname.getText().toString() + "'", null);

            Toast.makeText(this ,"刪除成功", Toast.LENGTH_SHORT).show();

            editname.setText("");
        }
    }


    public  void queryStore(){

        String index = "順序\n",title = "店名\n" , tel = "價格\n" , address = "店址\n";
        String[] colum ={"title" , "tel" ,"address"};

        Cursor c;
        if(editname.getText().toString().equals(""))
            c = dbrw.query("myTable",colum,null,null,null,null,null);
        else {
            c = dbrw.query("mytable", colum, "title=" + "'" +
                    editname.getText().toString() + "'", null, null, null, null);
        }


        if (c.getCount() >0){
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

        final CharSequence[] item1 = {"地圖位置","商品管理","下單管理","歷史銷售紀錄"};
        AlertDialog.Builder br1 = new AlertDialog.Builder(this);
        br1.setTitle("店家詳細")
                .setSingleChoiceItems(item1, -1, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog1, int item) {
                            }
                }).setPositiveButton("確定", new DialogInterface.OnClickListener(){

                public  void onClick(DialogInterface dialog1 , int id){

                    switch (id){
                        case 0: {
                            Intent i1 = new Intent();
                            i1.setClass(MainActivity.this, MapAddress.class);
                            startActivityForResult(i1, 0);
                            break;
                        }
                        case 1: {

                            break;
                        }
                        case 2: {

                            break;
                        }
                        case 3: {

                            break;
                        }
                        default:
                        {

                        }
                    }
                }
        }) ;
        AlertDialog dialog1 = br1.create();
        dialog1.show();

    }
}
