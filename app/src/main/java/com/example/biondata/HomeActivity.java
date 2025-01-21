package com.example.biondata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private EditText editNama, editNIM, editJurusanValue, editTempatLahir, editTanggalLahir;
    private RadioGroup radioGroupJenisKelamin;
    private Button buttonSimpan;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        editNama = findViewById(R.id.editNama);
        editNIM = findViewById(R.id.editNIM);
        editJurusanValue = findViewById(R.id.textJurusanValue);
        editTempatLahir = findViewById(R.id.editTempatLahir); // Inisialisasi tambahan
        editTanggalLahir = findViewById(R.id.editTanggalLahir); // Inisialisasi tambahan
        radioGroupJenisKelamin = findViewById(R.id.radioGroupJenisKelamin);
        buttonSimpan = findViewById(R.id.buttonSimpan);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = editNama.getText().toString();
                String nim = editNIM.getText().toString();
                String jurusan = editJurusanValue.getText().toString();
                String tempatLahir = editTempatLahir.getText().toString();
                String tanggalLahir = editTanggalLahir.getText().toString();

                // Mengambil jenis kelamin yang dipilih
                int selectedGenderId = radioGroupJenisKelamin.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedGenderId);
                String jenisKelamin = radioButton != null ? radioButton.getText().toString() : "";

                if (nama.isEmpty() || nim.isEmpty() || jurusan.isEmpty() || tempatLahir.isEmpty() || tanggalLahir.isEmpty() || jenisKelamin.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    // Menyimpan data ke database
                    boolean isInserted = databaseHelper.insertBiodata(nama, nim, jurusan, jenisKelamin, tempatLahir, tanggalLahir);
                    if (isInserted) {
                        // Redirect to DataActivity to show all mahasiswa data
                        Intent intent = new Intent(HomeActivity.this, DataActivity.class);
                        startActivity(intent);
                        finish(); // Optional: Menutup HomeActivity setelah pindah ke DataActivity
                    } else {
                        Toast.makeText(HomeActivity.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
