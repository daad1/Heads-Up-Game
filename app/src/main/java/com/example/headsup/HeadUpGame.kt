package com.example.headsup

import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.headsup.databinding.ActivityHeadUpGameBinding
import retrofit2.Call
import retrofit2.Response

class HeadUpGame: AppCompatActivity() {
    lateinit var Binding: ActivityHeadUpGameBinding
    val listGame = ArrayList<UsersItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Binding = ActivityHeadUpGameBinding.inflate(layoutInflater)

        setContentView(Binding.root)

        getDate()
        startGame()

    }


    fun startGame() {

        Binding.rotateScreen.text = ""
        Binding.taboo1cele.text = ""
        Binding.taboo2cele.text = ""
        Binding.taboo3cele.text = ""

        Binding.startGame.setOnClickListener {

            val orientation = resources.configuration.orientation

            if (orientation != Configuration.ORIENTATION_LANDSCAPE) {

                Binding.rotateScreen.text = "Rotate To Begin Game"
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                if (listGame.isNotEmpty()) {

                    val holdRandom = (0..listGame.size).random()

                    Binding.rotateScreen.text = listGame[holdRandom].name
                    Binding.taboo1cele.text = listGame[holdRandom].taboo1
                    Binding.taboo2cele.text = listGame[holdRandom].taboo2
                    Binding.taboo3cele.text = listGame[holdRandom].taboo3

                    object : CountDownTimer(60000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            Binding.startGame.isEnabled = false
                            Binding.timer.setText("Seconds Remaining: " + millisUntilFinished / 1000)

                        }

                        override fun onFinish() {
                            Binding.startGame.isEnabled = true
                            Binding.timer.setText("Done!")
                        }

                    }.start()

                } else {
                    Toast.makeText(
                        this,
                        "Please Wait a Date Then Press Start Game Again",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        }
    }

    fun getDate() {

        val getDate = APIClient().getClient()?.create(APIInterface::class.java)
        val getHeadUp = getDate?.getdata()
        if (getHeadUp != null) {
            getHeadUp.enqueue(object : retrofit2.Callback<ArrayList<UsersItem>?> {

                override fun onResponse(

                    call: Call<ArrayList<UsersItem>?>,
                    response: Response<ArrayList<UsersItem>?>
                ) {

                    if (response.code() == 200) {

                        for (resp in response.body()!!) {

                            listGame.add(resp)
                        }
                    } else {
                        Log.d("response code ", "${response.code()}")

                    }
                }

                override fun onFailure(call: Call<ArrayList<UsersItem>?>, t: Throwable) {
                    Log.d("response error", "${t.message}")
                }

            })
        }

    }

}