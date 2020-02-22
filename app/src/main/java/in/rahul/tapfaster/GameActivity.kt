package `in`.rahul.tapfaster

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.alert_game_layout.view.*
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    var oldNum = 5
    var score = 0
    var boolViewClicked = true
    var thread = Thread()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        view_red.setOnClickListener {
            viewClicked(0)
            boolViewClicked = true
        }

        view_blue.setOnClickListener {
            boolViewClicked = true
            viewClicked(1)
//            view_red.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
        }

        view_yellow.setOnClickListener {
            viewClicked(2)
            boolViewClicked = true
        }

        view_green.setOnClickListener {
            viewClicked(3)
            boolViewClicked = true
//            backToOriginColor()
        }
        oneSecMethod()
    }

    private fun viewClicked(i: Int) {
        boolViewClicked = false
        if (i == oldNum) {
            score++
            toolbar.title = "You Current Score: $score"
        } else {
            alertDialog()
//            toolbar.title  = "Loose"
        }
    }

    private fun oneSecMethod() {
        thread = object : Thread(){
            override fun run() {
                while (!isInterrupted){
                    try {
                        sleep(1000)
                        runOnUiThread {
                            createRandomNumber()
                            // oneSecMethod()
                        }
                    } catch (e: InterruptedException){
                        currentThread().interrupt()
                        e.printStackTrace()
                    }
                }
            }
        }
        thread.start()
    }

    private fun checkUserClick() {
        if (!boolViewClicked){
//            tv_hello.text = "You Loose"
            alertDialog()
        }
    }

    private fun alertDialog() {
        thread.interrupt()
        Thread.currentThread().interrupt()
        val builder = AlertDialog.Builder(this)
        val alertGameLayout: View = layoutInflater.inflate(R.layout.alert_game_layout, null)
        builder.setView(alertGameLayout)
        val dialog = builder.create()
        alertGameLayout.tv_score.text = score.toString()
        alertGameLayout.btn_exit.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        alertGameLayout.btn_reset.setOnClickListener {
            dialog.dismiss()
            score = 0
            boolViewClicked = true
            oneSecMethod()
        }
        dialog.show()
    }


    private fun createRandomNumber() {
        checkUserClick()
        backToOriginColor()
        val random = Random.nextInt(4)
//        Log.e("Home Act", "random: $random")
        if (oldNum == random) {
            createRandomNumber()
        } else {
            when (random) {
                0 -> view_red.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
                1 -> view_blue.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
                2 -> view_yellow.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
                3 -> view_green.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
                else -> {
                }
            }
            oldNum = random
            boolViewClicked = false
        }
    }

    private fun backToOriginColor() {
        view_red.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        view_green.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
        view_blue.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
        view_yellow.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
    }

    override fun onDestroy() {
        thread.interrupt()
        Thread.currentThread().interrupt()
        super.onDestroy()
    }
}
