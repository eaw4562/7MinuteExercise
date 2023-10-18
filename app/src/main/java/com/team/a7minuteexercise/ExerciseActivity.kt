package com.team.a7minuteexercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.team.a7minuteexercise.databinding.ActivityExerciseBinding
import com.team.a7minuteexercise.databinding.ActivityMainBinding

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding
    private val callback = object  : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            val intent = Intent(this@ExerciseActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbarExercise.setNavigationOnClickListener {
            this.onBackPressedDispatcher.addCallback(this, callback)
        }
    }
}