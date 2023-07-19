package donets.danylo.testtask.activities


import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

        checkPermissions()

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


    private val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST = 1
    private val WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 2

    // Функція для перевірки дозволів
    private fun checkPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val permissionsToRequest = mutableListOf<String>()

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), READ_EXTERNAL_STORAGE_PERMISSION_REQUEST)
        }
    }

    // Обробник результатів запиту дозволів
    // Обробник результатів запиту дозволів
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            READ_EXTERNAL_STORAGE_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Дозвіл на доступ до читання зовнішнього сховища отримано
                    // Виконувати необхідні дії
                } else {
                    // Дозвіл на доступ до читання зовнішнього сховища не отримано
                    // Обробка відсутності дозволу
                }
            }
            WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Дозвіл на доступ до запису в зовнішнє сховище отримано
                    // Виконувати необхідні дії
                } else {
                    // Дозвіл на доступ до запису в зовнішнє сховище не отримано
                    // Обробка відсутності дозволу
                }
            }
        }
    }



}