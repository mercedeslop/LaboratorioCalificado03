package com.jennyfer.sapallanay.laboratoriocalificado03

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jennyfer.sapallanay.laboratoriocalificado03.databinding.ActivityEjercicio01Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Ejercicio01Activity : AppCompatActivity() {
    private lateinit var binding: ActivityEjercicio01Binding
    private lateinit var adapter: TeacherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewTeachers.layoutManager = LinearLayoutManager(this)
        fetchTeachers()
    }

    private fun fetchTeachers() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getTeachers()
                val teachers = response.teachers
                adapter = TeacherAdapter(this@Ejercicio01Activity, teachers, ::onItemClick, ::onItemLongClick)
                binding.recyclerViewTeachers.adapter = adapter
            } catch (e: Exception) {
                Log.e("Ejercicio01", "Error al buscar profesoreser", e)
            }
        }
    }

    private fun onItemClick(teacher: Teacher) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${teacher.phone_number}")
        }
        startActivity(intent)
    }

    private fun onItemLongClick(teacher: Teacher) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${teacher.email}")
            putExtra(Intent.EXTRA_SUBJECT, "Contacto")
            putExtra(Intent.EXTRA_TEXT, "Hola ${teacher.name},")
        }
        startActivity(intent)
    }
}