package SQLITE.si61.uts.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabTambah;
    private RecyclerView rvDestinasi;
    private ArrayList<String> arrId ,arrNpm, arrNama, arrProdi;
    private AdapterMahasiswa adMahasiswa;
    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabTambah = findViewById(R.id.fab_tambah);
        rvDestinasi = findViewById(R.id.tv_destinasi);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
    }

    private void SQLiteToArrayList(){
        Cursor varCursor = myDB.bacaDataMahasiswa();
        if(varCursor.getCount() == 0){
            Toast.makeText(this, "Data Tidak Tersedia!", Toast.LENGTH_SHORT).show();
        }
        else {
            while (varCursor.moveToNext()){
                arrId.add((varCursor.getString(0)));
                arrNpm.add(varCursor.getString(1));
                arrNama.add(varCursor.getString(2));
                arrProdi.add(varCursor.getString(3));
            }
        }
    }

    private void tampilanDestinasi(){
        arrNpm = new ArrayList<>();
        arrNama = new ArrayList<>();
        arrProdi = new ArrayList<>();
        arrId = new ArrayList<>();

        SQLiteToArrayList();
        adMahasiswa = new AdapterMahasiswa(MainActivity.this, arrId,arrNpm, arrNama, arrProdi);
        rvDestinasi.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvDestinasi.setAdapter(adMahasiswa);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilanDestinasi();
    }
}