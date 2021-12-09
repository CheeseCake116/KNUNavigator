package com.fantastic4.knumap

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.fantastic4.knumap.FloatingButton as FloatingButton1

class TitleActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, LobbyActivity::class.java)
        startActivity(intent)
        finish()
    }
}

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
    }
}
class TestActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test1)
        var testBtn = findViewById<FloatingActionButton>(R.id.testBtn)
        testBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        testBtn.setOnClickListener{
            testBtn.imageTintList = ColorStateList.valueOf(Color.parseColor("#ffffff"))
            testBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DA2127")));
            testBtn.setBackgroundResource(R.color.C1Red)
        }
    }
}
//스플래시 스크린 -> 없어도 됌 -> 로그인 화면
class LobbyActivity : AppCompatActivity() {

    //  initialize viewpager
    private val adapter by lazy { ViewPagerAdapter(supportFragmentManager) }

    //1,2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.lobby)
        val viewPager_main = findViewById<ViewPager>(R.id.viewPager_main)
        viewPager_main.adapter = LobbyActivity@adapter
        val mapBtn = findViewById<ImageButton>(R.id.mapBtn)
        mapBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

    }
}

//메인화면 1, 수업과 시간표
class FirstFragment : Fragment() {
    var ContentList = arrayListOf<ListViewItem>(
        ListViewItem("1A - 2A", "운영관리", "IT대학융복합공학관(415)", "9:00 - 10:30")
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.main, container, false)
        ContentList.add(ListViewItem("5A - 7B", "자바 프로그래밍", "IT대학2호관(416)", "13:00 - 16:00"))
        ContentList.add(ListViewItem("8B - 9B", "자료 구조", "IT대학2호관(416)", "16:30 - 18:00"))
        val classAdapter = getActivity()?.let { ListViewAdapter(it, ContentList) }
        val classlist = view.findViewById<ListView>(R.id.classlist)
        classlist.adapter = classAdapter

        val classplus = view.findViewById<ImageButton>(R.id.plusBtn)
        classplus.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("타이틀 입니다.")
                .setMessage("asd")

        }


        return view
    }

}

//메인화면 2, 식단표
class SecondFragment : Fragment(){
    var ContentListRest = ArrayList<Rest_LVItem>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        ContentListRest = ArrayList()

        val view = inflater.inflate(R.layout.main2, container, false)
        ContentListRest.add(Rest_LVItem("정보센터식당", "11:00 - 13:00", "정보센터식당", "산채돌솥밥★\n" +
                "흰밥\n" +
                "어묵무국\n" +
                "배추김치\n" +
                "￦ 4,500\n" +
                "치킨스테이크\n" +
                "￦ 4,500\n" +
                "일반돈가스★\n" +
                "￦ 3,000\n" +
                "통등심돈가스★\n" +
                "￦ 3,500\n" +
                "통등심\n" +
                "치즈돈가스★\n" +
                "￦ 3,800\n" +
                "고구마치즈돈가스★\n" +
                "￦ 3,800\n" +
                "왕돈가스★\n" +
                "￦ 4,500\n" +
                "베지스테이크\n" +
                "￦ 4,000\n" +
                "우동\n" +
                "우동밥\n" +
                "￦ 3,200"))
        ContentListRest.add(Rest_LVItem("복지관 교직원식당", "11:00 - 13:00", "복지관 교직원식당", "흰밥\n" +
                "닭개장\n" +
                "치즈콩불★\n" +
                "두부스테이크★\n" +
                "상추겉절이\n" +
                "포기김치\n" +
                "￦ 5,000"))
        ContentListRest.add(Rest_LVItem("카페테리아 첨성", "11:00 - 13:00", "카페테리아 첨성", "순살치즈찜닭\n" +
                "흰밥\n" +
                "미역국\n" +
                "배추김치\n" +
                "￦ 4,500\n" +
                "스팸후리가께덮밥★\n" +
                "￦ 3,800\n" +
                "오삼비빔밥★\n" +
                "￦ 3,800\n" +
                "라면\n" +
                "오후1시-3시반 판매\n" +
                "￦ 2,500\n" +
                "일반돈가스★\n" +
                "￦ 3,000\n" +
                "통등심돈가스★\n" +
                "￦ 3,500\n" +
                "치즈돈가스★\n" +
                "￦ 3,800\n" +
                "고구마돈가스★\n" +
                "￦ 3,800\n" +
                "복돈이★\n" +
                "￦ 4,500\n" +
                "치즈복돈이★\n" +
                "￦ 4,500"))
        ContentListRest.add(Rest_LVItem("공식당(교직원)", "11:00 - 13:00", "공식당(교직원)", "정식\n" +
                "흑미밥/흰밥\n" +
                "시금치장국\n" +
                "홍초불닭\n" +
                "프렌치후라이드/케찹\n" +
                "멕시칸샐러드★\n" +
                "직접담근김치\n" +
                "온국수\n" +
                "커피"))
        ContentListRest.add(Rest_LVItem("공식당(학생)", "11:00 - 13:00", "공식당(학생)", "특식\n" +
                "육회비빔밥\n" +
                "5,000\n" +
                "수제등심돈가스+스프★\n" +
                "4,000\n" +
                "치킨마요\n" +
                "4,000\n" +
                "불닭마요\n" +
                "4,000\n" +
                "참치덮밥\n" +
                "4,000\n" +
                "양푼이비빔밥+된장찌개\n" +
                "4,000\n" +
                "특최루탄라면+주먹밥★\n" +
                "4,000\n" +
                "뚝배기알밥\n" +
                "4,000\n" +
                "치즈뚝배기알밥\n" +
                "4,500\n" +
                "양푼이돈육양념찜★\n" +
                "5,000\n" +
                "삼겹살김치전골★(2인)\n" +
                "10,000"))
        val restAdapter = getActivity()?.let{Rest_LVAdapter(it, ContentListRest)}
        val restList = view.findViewById<ListView>(R.id.restList)
        restList.adapter = restAdapter
        return view
    }
}

//페이지 전환 애니메이션
class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> FirstFragment()
            else  ->  SecondFragment()
        }
    }

    // 생성 할 Fragment 의 개수
    override fun getCount() = 2

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

}