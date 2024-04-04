package com.lammyngoc.karaoke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.lammyngoc.karaoke.adapter.AllAdapter;
import com.lammyngoc.karaoke.adapter.LoveAdapter;
import com.lammyngoc.karaoke.model.Song;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    TabHost tabSong;
    public static final String DATABASE_NAME = "KaraokeAriang.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    ListView lvAll,lvLove;
    AllAdapter allAdapter;
    LoveAdapter loveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addViews();
        addControls();
        copyDataBase();
        loadSong();

    }
    public void updateFavoriteStatus(Song song) {
        try {
            SQLiteDatabase database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (database != null) {
                ContentValues record = new ContentValues();
                // Cập nhật trạng thái yêu thích của bài hát trong record ContentValues
                record.put("favorite", song.getFavorite());
                // Cập nhật dữ liệu trong cơ sở dữ liệu SQLite
                long result = database.update("songs", record, "ma=?", new String[]{song.getMa()});
                if (result > 0) {
                    // Nếu cập nhật thành công, bạn có thể cập nhật giao diện hoặc thực hiện các thao tác khác ở đây
                    loadSong(); // Đây là một ví dụ, hãy sử dụng phương thức tương ứng để cập nhật giao diện
                }
                // Đóng cơ sở dữ liệu sau khi hoàn thành
                database.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyDataBase(){
        try{
            File dbFile = getDatabasePath(DATABASE_NAME);
            if(!dbFile.exists()){
                if(CopyDBFromAsset()){
                    Toast.makeText(MainActivity.this,
                            "Copy database successful!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Copy database fail!", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.e("Error: ", e.toString());
        }
    }

    private boolean CopyDBFromAsset() {
        String dbPath = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
        try {
            InputStream inputStream = getAssets().open(DATABASE_NAME);
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024]; int length;
            while((length=inputStream.read(buffer))>0){
                outputStream.write(buffer,0, length);
            }
            outputStream.flush();  outputStream.close(); inputStream.close();
            return  true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void loadSong() {
        database = openOrCreateDatabase(DATABASE_NAME,
                MODE_PRIVATE, null);

        Cursor cursor = database.rawQuery("SELECT * FROM songs", null);
        allAdapter.clear();
        loveAdapter.clear();

        while(cursor.moveToNext()){
            String ma = cursor.getString(0);
            String tenbaihat = cursor.getString(1);
            String tacgia = cursor.getString(3);
            Integer favorite = cursor.getInt(5);

            Song song =new Song(ma, tenbaihat, tacgia, favorite);
            allAdapter.add(song);
            if (favorite == 1) {
                loveAdapter.add(song);
            }
        }
    }
    private void addControls() {
        tabSong = findViewById(R.id.tabSong);
        tabSong.setup();

        //Tạo tab 1
        TabHost.TabSpec tab1 = tabSong.newTabSpec("tab1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("ALL");
        tabSong.addTab(tab1);

        //Tạo tab 2
        TabHost.TabSpec tab2 = tabSong.newTabSpec("tab2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("LOVE");
        tabSong.addTab(tab2);

        tabSong.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                Toast.makeText(MainActivity.this,
                        "Tab " + s + " selected, index: " + tabSong.getCurrentTab(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addViews() {
        lvAll=findViewById(R.id.lvAll);
        allAdapter=new AllAdapter(
                MainActivity.this,R.layout.song_item);
        lvAll.setAdapter(allAdapter);
        lvLove=findViewById(R.id.lvLove);
        loveAdapter = new LoveAdapter(MainActivity.this, R.layout.song_item);
        lvLove.setAdapter(loveAdapter);
    }
}