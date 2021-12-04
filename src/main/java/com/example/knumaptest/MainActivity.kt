package com.example.knumaptest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TitleActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

    }
}
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        var btnLogin = findViewById(R.id.btnLogin) as Button
        btnLogin.setOnClickListener ({
            val intent = Intent(this, MainAct::class.java)
            startActivity(intent)
            finish()
        })
    }
}

class MainAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)


        setSupportActionBar(findViewById(R.id.toolbar))
        getSupportActionBar()?.setLogo(R.mipmap.ic_launcher);
        getSupportActionBar()?.setDisplayUseLogoEnabled(true);

        var btnLogin = findViewById(R.id.testbtn) as Button
        btnLogin.setOnClickListener ({
            val intent = Intent(this, MainAct2::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);
            finish()
        })
    }
}

class MainAct2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main2)

        setSupportActionBar(findViewById(R.id.toolbar))
        getSupportActionBar()?.setLogo(R.mipmap.ic_launcher);
        getSupportActionBar()?.setDisplayUseLogoEnabled(true);

        var btnLogin = findViewById(R.id.testbtn2) as Button
        btnLogin.setOnClickListener ({
            val intent = Intent(this, MainAct::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);
            finish()
        })
    }
}