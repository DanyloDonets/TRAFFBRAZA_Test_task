package donets.danylo.testtask.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import donets.danylo.testtask.R


class MainActivity : AppCompatActivity() {

    lateinit var gameBtn: Button
    lateinit var webBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        gameBtn = findViewById(R.id.gameBtn)
        webBtn = findViewById(R.id.webBtn)

        webBtn.width = gameBtn.width

        gameBtn.setOnClickListener {
            setContentView(R.layout.activity_game)
            val intent = Intent(this@MainActivity, Game::class.java)
            startActivity(intent)
        }

        webBtn.setOnClickListener {
            setContentView(R.layout.activity_web_view)
            val intent = Intent(this@MainActivity, WebView::class.java)
            startActivity(intent)
        }
    }
}