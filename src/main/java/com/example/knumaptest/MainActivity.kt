package com.example.knumaptest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.core.content.ContextCompat

class TitleActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}


//스플래시 스크린 -> 없어도 됌 -> 로그인 화면
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainAct2::class.java)
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


//메인화면 1, 수업과 시간표
class MainAct : AppCompatActivity() {


    var ContentList = arrayListOf<ListViewItem>(
        ListViewItem("1A","운영관리", "융복합관", "7:00 - 9:00")

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        ContentList.add(ListViewItem("2A", "자바 프로그래밍", "IT5", "10:30 - 12:00"))
        ContentList.add(ListViewItem("3A", "자료 구조", "IT4", "1:30 - 3:00"))
        val classAdapter = ListViewAdapter(this, ContentList)

        val classlist = findViewById<ListView>(R.id.classlist)
        classlist.adapter = classAdapter


    }
}

//메인화면 2, 식단표
class MainAct2 : AppCompatActivity() {

    var ContentList = arrayListOf<Rest_LVItem>(
            Rest_LVItem("복지관 식당","11:00 - 13:00", "복지관", "복지관 치즈 돈가스")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main2)
        ContentList.add(Rest_LVItem("첨성관 식당", "11:00 - 13:00", "첨성관", "-  김치찌개\n-  흰 쌀밥\n-  김치\n-  시금치 무침\n-  불고기"))
        val classAdapter = Rest_LVAdapter(this, ContentList)

        val restList = findViewById<ListView>(R.id.restList)
        restList.adapter = classAdapter




    }
}



















