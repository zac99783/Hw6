package com.example.user.hw6;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    Button add,edit ,delete, query;
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
        query = (Button) findViewById(R.id.query);

        MyDBHelper dbhelper = new MyDBHelper(this);
        dbrw = dbhelper.getWritableDatabase();


        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                newBook();
            }
        });


        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                renewBook();
            }
        });

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });

        query.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                querybook();
            }
        });
    }

    public void newBook(){

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

   public  void renewBook(){

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

    public  void deleteBook(){

        if (editname.getText().toString().equals(""))
            Toast.makeText(this,"請輸入要刪除之值",Toast.LENGTH_SHORT).show();

        else {

            dbrw.delete("myTable","title=" + "'" + editname.getText().toString() + "'", null);

            Toast.makeText(this ,"刪除成功", Toast.LENGTH_SHORT).show();

            editname.setText("");
        }
    }


    public  void querybook(){

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
}
