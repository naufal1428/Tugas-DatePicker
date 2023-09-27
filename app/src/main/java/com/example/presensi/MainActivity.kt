package com.example.presensi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.example.presensi.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var kehadiran: Array<String>

    var selectedDate: String? = null
    var selectedTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        kehadiran = resources.getStringArray(R.array.kehadiran)

        with(binding) {

            datePicker.init(
                datePicker.year,
                datePicker.month,
                datePicker.dayOfMonth
            ) {_, year, monthOfYear, dayOfMonth ->
                // Mengonversi bulan dari indeks (dimulai dari 0) menjadi nama bulan
                val monthNames = SimpleDateFormat("MMMM", Locale.getDefault())
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.MONTH, monthOfYear)

                // Mengonversi bulan menjadi nama bulan
                val selectedMonthName = monthNames.format(calendar.time)

                // Menampilkan tanggal dengan format nama bulan dalam Toast
                selectedDate = "$dayOfMonth $selectedMonthName $year"
            }

            timePicker.setOnTimeChangedListener {view, hourOfDay, minute ->
                selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            }

            val adapterKehadiran = ArrayAdapter(this@MainActivity,
                android.R.layout.simple_spinner_item, kehadiran)
            adapterKehadiran.setDropDownViewResource(
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
            spinnerKehadiran.adapter = adapterKehadiran

            spinnerKehadiran.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


                        val statusKehadiran = kehadiran[position]
                        if (statusKehadiran == "Hadir Tepat Waktu") {
                            keterangan.visibility = View.GONE
                        } else {
                            keterangan.visibility = View.VISIBLE
                        }

                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }

            btnSubmit.setOnClickListener {
                if (selectedDate != null && selectedTime != null) {
                    val message = "Presensi berhasil $selectedDate jam $selectedTime"
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Pilih tanggal dan waktu terlebih dahulu", Toast.LENGTH_SHORT).show()
                }
                keterangan.visibility = View.GONE
            }

        }



    }
}