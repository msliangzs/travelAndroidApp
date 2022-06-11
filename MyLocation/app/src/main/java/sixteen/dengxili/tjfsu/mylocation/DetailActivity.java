package sixteen.dengxili.tjfsu.mylocation;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static sixteen.dengxili.tjfsu.mylocation.DatabaseHelper.TABLE_NAME;
import static sixteen.dengxili.tjfsu.mylocation.TravelNoteActivity.TAG_INSERT;
import static sixteen.dengxili.tjfsu.mylocation.TravelNoteActivity.TAG_UPDATE;
import static sixteen.dengxili.tjfsu.mylocation.TravelNoteActivity.getDbHelper;


public class DetailActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    EditText title;  //标题
    EditText content;  //内容
    public DatabaseHelper dbHelper=getDbHelper();
    private int tag;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        title=(EditText)findViewById(R.id.detail_title);
        content=(EditText)findViewById(R.id.detail_content);
        title.setSelection(title.getText().length());
        content.setSelection(content.getText().length());
        db= dbHelper.getWritableDatabase();
        Intent intent=getIntent();
        tag=intent.getIntExtra("TAG",-1);
        switch(tag){
            case TAG_INSERT:
                break;
            case TAG_UPDATE:
                id=intent.getIntExtra("ID",-1);
                Cursor cursor=db.query(TABLE_NAME,null,"id=?",
                        new String[]{String.valueOf(id)},null,null,null);
                if(cursor.moveToFirst()){
                    String select_title=cursor.getString(cursor.getColumnIndex("title"));
                    String select_content=cursor.getString(cursor.getColumnIndex("content"));
                    title.setText(select_title);
                    content.setText(select_content);
                }
                break;
            default:
        }
    }

    //将menu中的actionbar添加进来
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    //设置“保存”或者“删除”按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.save:
                if(tag==TAG_INSERT) {
                    ContentValues values = new ContentValues();
                    values.put("title", title.getText().toString());
                    values.put("content", content.getText().toString());
                    db.insert(TABLE_NAME, null, values);
                    values.clear();
                    Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }else if(tag==TAG_UPDATE){
                    //修改title和content
                    String update_title=title.getText().toString();
                    String update_content=content.getText().toString();
                    ContentValues values=new ContentValues();
                    values.put("title",update_title);
                    values.put("content",update_content);
                    db.update(TABLE_NAME,values,"id=?",new String[]{String.valueOf(id)});
                    finish();
                    break;
                }
            case R.id.delete:
                if(tag==TAG_UPDATE) {
                    db.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
                }
                Toast.makeText(this,"Delete",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
        }
        return true;
    }

}