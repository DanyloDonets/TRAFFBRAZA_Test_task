package donets.danylo.testtask.activities


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import donets.danylo.testtask.R
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    lateinit var gameBtn: Button
    lateinit var webBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)




        gameBtn = findViewById(R.id.gameBtn)
        webBtn = findViewById(R.id.webBtn)

        webBtn.width = gameBtn.width

        gameBtn.setOnClickListener {

            val intent = Intent(this@MainActivity, Game::class.java)
            startActivity(intent)
            finish()
    }

        webBtn.setOnClickListener {

            val intent = Intent(this@MainActivity, WebView::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        showExitConfirmationDialog()
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Закрити додаток")
        builder.setMessage("Ви впевнені що хочете закрити додаток?")
        builder.setPositiveButton("Так") { dialogInterface: DialogInterface, i: Int ->
            finish()
            exitProcess(-1)
        }
        builder.setNegativeButton("Ні", null)
        val dialog = builder.create()
        dialog.show()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}