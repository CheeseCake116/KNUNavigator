package com.example.knumaptest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager

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

    //  initialize viewpager
    private val adapter by lazy { ViewPagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager_main = findViewById<ViewPager>(R.id.viewPager_main)
        viewPager_main.adapter = MainActivity@adapter


    }
}
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

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




//페이지 전환 애니메이션
class FirstFragment : Fragment(){



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
    }
}
class SecondFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }
}

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> FirstFragment()
            else       ->  SecondFragment()
        }
    }

    // 생성 할 Fragment 의 개수
    override fun getCount() = 2

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

}
















