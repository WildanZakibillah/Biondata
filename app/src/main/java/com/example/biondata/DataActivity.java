package com.example.biondata;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {

    private ListView listViewData;
    private DatabaseHelper databaseHelper;
    private Button buttonKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Initialize views
        listViewData = findViewById(R.id.listViewData);
        buttonKembali = findViewById(R.id.buttonKembali); // Inisialisasi tombol kembali
        databaseHelper = new DatabaseHelper(this);

        // Retrieve all mahasiswa data
        Cursor cursor = databaseHelper.getAllMahasiswa();

        // Check if there is data
        if (cursor != null && cursor.getCount() > 0) {
            // Define columns to display in ListView
            String[] from = {
                    DatabaseHelper.COLUMN_NAMA,
                    DatabaseHelper.COLUMN_NIM,
                    DatabaseHelper.COLUMN_JURUSAN,
                    DatabaseHelper.COLUMN_JENIS_KELAMIN,
                    DatabaseHelper.COLUMN_TEMPAT_LAHIR,
                    DatabaseHelper.COLUMN_TANGGAL_LAHIR
            };

            // Define the corresponding TextView IDs to show the data
            int[] to = {
                    R.id.textNama,
                    R.id.textNIM,
                    R.id.textJurusan,
                    R.id.textJenisKelamin,
                    R.id.textTempatLahir,
                    R.id.textTanggalLahir
            };

            // Debugging log: Check the cursor count
            Log.d("DataActivity", "Cursor retrieved with count: " + cursor.getCount());

            // Use a custom layout for the ListView
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    R.layout.list_item, // Custom layout for each row
                    cursor,
                    from,
                    to,
                    0
            );

            // Set the adapter to ListView
            listViewData.setAdapter(adapter);

            // No need to close the cursor yet, since SimpleCursorAdapter handles it
        } else {
            // Show a message if no data is found
            Toast.makeText(this, "Tidak ada data yang tersimpan!", Toast.LENGTH_SHORT).show();
        }

        // Handle button "Kembali"
        buttonKembali.setOnClickListener(v -> {
            // Navigate back to HomeActivity
            Intent intent = new Intent(DataActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Optionally, close DataActivity to prevent the user from navigating back here
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Make sure to close the cursor when activity is destroyed
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
