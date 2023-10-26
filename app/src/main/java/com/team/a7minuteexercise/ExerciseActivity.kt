package com.team.a7minuteexercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.team.a7minuteexercise.databinding.ActivityExerciseBinding
import com.team.a7minuteexercise.databinding.ActivityMainBinding

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

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

        exerciseList = Constants.defaultExerciseList()

        binding.toolbarExercise.setNavigationOnClickListener {
            this.onBackPressedDispatcher.addCallback(this, callback)
        }

        setupRestView()
    }

    private fun setupRestView() {
        binding.flRestView.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.VISIBLE
        binding.tvExerciseName.visibility = View.INVISIBLE
        binding.flExerciseView.visibility = View.INVISIBLE
        binding.ivImage.visibility = View.INVISIBLE
        binding.tvUpComingLabel.visibility = View.VISIBLE
        binding.tvUpcomingExerciseName.visibility = View.VISIBLE

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding.tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar()
    }

    private fun setupExerciseView() {
        binding.flRestView.visibility = View.INVISIBLE
        binding.tvTitle.visibility = View.INVISIBLE
        binding.tvExerciseName.visibility = View.VISIBLE
        binding.flExerciseView.visibility = View.VISIBLE
        binding.ivImage.visibility = View.VISIBLE
        binding.tvUpComingLabel.visibility = View.INVISIBLE
        binding.tvUpcomingExerciseName.visibility = View.INVISIBLE

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }

    private fun setRestProgressBar(){
        binding.progressBar.progress = restProgress

        restTimer = object : CountDownTimer(10000,1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Here now we will start the exercise",
                    Toast.LENGTH_SHORT
                ).show()
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding.progressBarExercise.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000,1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.progressBarExercise.progress = 30 - exerciseProgress
                binding.tvTimerExercise.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! -1) {
                    setupRestView()
                }else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "축하 운동 끝",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
    }
}