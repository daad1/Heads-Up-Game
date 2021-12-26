package com.example.headsup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.headsup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var Binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(Binding.root)

        gotoHeadUpCelebrity()
        gotoHeadUpGame()
    }

    fun gotoHeadUpCelebrity() {
        Binding.cel.setOnClickListener {

            val gotoHeadUpCelebrity = Intent(this@MainActivity, HeadUpCelebrity::class.java)
            startActivity(gotoHeadUpCelebrity)

        }
    }

    fun gotoHeadUpGame() {
        Binding.game.setOnClickListener {
            val gotoHeadUpGame = Intent(this@MainActivity, HeadUpGame::class.java)
            startActivity(gotoHeadUpGame)
        }
    }
}