package com.fantastic4.knumap

import android.Manifest
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.graphics.Color
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skt.Tmap.*
import java.lang.Exception
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationListener
import android.widget.*
import androidx.core.graphics.scale
import com.skt.Tmap.poi_item.TMapPOIItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.skt.Tmap.TMapMarkerItem

class MainActivity : AppCompatActivity(), TMapGpsManager.onLocationChangedCallback, TMapView.OnCalloutRightButtonClickCallback {

    var m_bTrackingMode : Boolean = true
    lateinit var tmapGps : TMapGpsManager
    lateinit var tmapview : TMapView
    var myLong : Double = 0.0 // 현재 위치 _ 경도
    var myLat : Double = 0.0 // 현재 위치 _ 위도
    val mApiKey : String = "l7xx6a347111bc9842009151e620e7301037"

    lateinit var tMapPolyLine: TMapPolyLine

    lateinit var FB: FloatingButton     // FloatingButton 객체 선언
    var dayOfWeek: Int = 0              // 오늘 요일

    lateinit var searchMapitems: ArrayList<String>  // searchMap의 key{건물이름(번호)}를 저장할 ArrayList 선언
    lateinit var autoEditDestinationDlg : AutoCompleteTextView // 목적지 다이얼로그 뷰의 자동완성텍스트뷰
    var destText : String = "" // 목적지 문자열을 저장하는 변수

    companion object{
        var isLoc : Boolean = true
        val searchMap = mapOf<String,TMapPoint>("본관(100)" to TMapPoint(35.890512, 128.612028), "대강당(101)" to TMapPoint(35.892801, 128.610700),
            "글로벌플라자(103)" to TMapPoint(35.891929, 128.611240), "인문대학(104)" to TMapPoint(35.891194, 128.610692), "대학원동(107)" to TMapPoint(35.889717, 128.610220),
            "정보전산원(110)" to TMapPoint(35.891446, 128.613594), "중앙도서관(111)" to TMapPoint(35.891663, 128.612049), "향토관(113)" to TMapPoint(35.890720, 128.615168),
            "약학대학(118)" to TMapPoint(35.892659, 128.612352), "예술대학(120)" to TMapPoint(35.893545, 128.611348), "농생대1호관(201)" to TMapPoint(35.891280, 128.609536),
            "생명공학관(207)" to TMapPoint(35.889795, 128.609061), "자연과학대학(209)" to TMapPoint(35.890271, 128.606557), "생물관(217)" to TMapPoint(35.886866, 128.606068),
            "사범대학(301)" to TMapPoint(35.890419, 128.614199), "경상대학(308)" to TMapPoint(35.889098, 128.615822), "공대9호관(406)" to TMapPoint(35.886915, 128.608497),
            "IT대학2호관(416)" to TMapPoint(35.886915, 128.608497), "IT대학융복합공학관(415)" to TMapPoint(35.888065, 128.611215), "수의과대학(420)" to TMapPoint(35.886794, 128.613232),
            "정보센터식당(116)" to TMapPoint(35.892296, 128.613262), "복지관식당(305)" to TMapPoint(35.889052, 128.614423), "첨성관식당(114)" to TMapPoint(35.891443, 128.614908),
            "공식당(교직원)(408)" to TMapPoint(35.888322, 128.609688), "공식당(학생)(408)" to TMapPoint(35.888322, 128.609688))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 권한 설정정보
        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1); //위치권한 탐색 허용 관련 내용
            }
            return
        }

        // TMap Layout 설정
        var linearLayoutTmap = findViewById<LinearLayout>(R.id.linearlayoutTmap)
        tmapview = TMapView(this)
        linearLayoutTmap.addView(tmapview)

        // TMap Key 인증
        tmapview.setSKTMapApiKey(mApiKey)

        // 현 위치 아이콘 표시
        tmapview.setIconVisibility(true)

        // 기본 설정
        tmapview.zoomLevel = 15
        tmapview.mapType = TMapView.MAPTYPE_STANDARD
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN)

        // GPS 설정
        tmapGps = TMapGpsManager(this)
        tmapGps.minTime = 1000
        tmapGps.minDistance = 5f
        tmapGps.provider = TMapGpsManager.NETWORK_PROVIDER  // 인터넷으로 현 위치 받음
//        tmapGps.provider = TMapGpsManager.GPS_PROVIDER // GPS 위성으로 현 위치 받음
        tmapGps.OpenGps()

        // 화면 중심을 단말의 현재위치로 이동
        tmapview.setTrackingMode(true)
        tmapview.setSightVisible(true)

        // 현재 보는 방향 표시
        tmapview.setCompassMode(false)

        // 현위치 보기 온오프
        var btnLoc : ImageButton = findViewById(R.id.btnLoc)
        btnLoc.setOnClickListener{ view ->
            if (isLoc){ // 현위치 보기 중이면
                tmapview.setTrackingMode(false)
                tmapview.setCompassMode(false)
                tmapview.setSightVisible(false)
                tmapview.invalidate()
                btnLoc.setImageResource(R.drawable.myloc_on)
                isLoc = false
            }
            else{ // 현위치 보기 중이 아니면
                tmapview.setTrackingMode(true)
                tmapview.setCompassMode(true)
                tmapview.setSightVisible(true)
                tmapview.invalidate()
                btnLoc.setImageResource(R.drawable.myloc_off)
                isLoc = true
            }
        }

        // 플로팅 버튼
        var fab_open = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        var fab_close = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)

        var fab = findViewById<FloatingActionButton>(R.id.fab_btnMain)
        var fab1 = findViewById<FloatingActionButton>(R.id.fab_btn1)
        var fab2 = findViewById<FloatingActionButton>(R.id.fab_btn2)
        var fab3 = findViewById<FloatingActionButton>(R.id.fab_btn3)

        FB = FloatingButton(fab_open, fab_close, fab, fab1, fab2, fab3, this)

        fab3.setOnClickListener {
            showDialog()
        }

        setMarker()             // 마커 설정

        setSearchMapItems()     // searchMapitems 설정

        setRestaurantMenu(1)    // 각 식당 메뉴 설정 (정보센터식당으로 테스트)
    }

    // 길찾기
    fun searchRoute(){
        // 출발지 : 현위치, 목적지 : map에서 받아옴
        var myLoc :TMapPoint = tmapGps.location
        var destLoc = searchMap.get(destText)

        // 경로 검색 (보행자)
        TMapData().findPathDataWithType(
            TMapData.TMapPathType.CAR_PATH, myLoc, destLoc,
            TMapData.FindPathDataListenerCallback { polyLine ->
                polyLine.lineColor = Color.BLUE
                polyLine.lineWidth = 5f
                tmapview.addTMapPath(polyLine)
            })
    }

    fun setMarker() {
        // 1: 정보센터식당, 2: 복지관 교직원식당, 3: 카페테리아 첨성, 4: 공식당(교직원), 5: 공식당(학생) : 항상 마커로 표시
        // 정보센터식당 : 35.892376545752455, 128.6131707177347
        // 복지관 교직원식당 : 35.88916342021079, 128.6144599690982
        // 카페테리아 첨성 : 35.88898483101483, 128.61447321501393
        // 공식당(교직원) : 35.88828732332779, 128.60960810892362
        // 공식당(학생) : 35.888268723784684, 128.6098106243137
        var markerItem: ArrayList<TMapMarkerItem>    // MarkerItem 선언
        var item_point: ArrayList<TMapPoint>    // 식당 Point(좌표) 선언

        item_point = ArrayList()
        item_point.add(0, TMapPoint(35.892376545752455, 128.6131707177347)) // 정보센터식당 좌표
        item_point.add(1, TMapPoint(35.88916342021079, 128.6144599690982))  // 복지관 교직원식당 좌표
        item_point.add(2, TMapPoint(35.88898483101483, 128.61447321501393))  // 카페테리아 첨성 좌표
        item_point.add(3, TMapPoint(35.88828732332779, 128.60960810892362)) // 공식당(교직원) 좌표
        item_point.add(4, TMapPoint(35.888268723784684, 128.6098106243137)) // 공식당(학생) 좌표

        // 마커 아이콘 "Bitmap"으로 불러오기
        var bitmap: Bitmap
        var balBitmap: Bitmap
        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.marker_map_icon)
        bitmap = bitmap.scale(100, 100, false)
        balBitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ballon_arrow)
        balBitmap = balBitmap.scale(61, 100, false)

        markerItem = ArrayList()
        var item: TMapMarkerItem
        for(idx: Int in 0..4) {
            item = TMapMarkerItem()
            item.icon = bitmap
            item.setPosition(0.5f, 1.0f)    // 마커 중심점 중앙 하단으로 설정
            item.tMapPoint = item_point[idx]  // 마커 좌표 설정
            setBalloonView(item, idx)   // 풍선뷰 설정
            item.calloutRightButtonImage = balBitmap

            markerItem.add(idx, item)   // markerItem에 item 추가

            tmapview.addMarkerItem("merkerItem$idx", markerItem[idx])   // tmapview에 markerItem 추가
        }
    }

    fun setBalloonView(item: TMapMarkerItem, idx: Int) {
        // 풍선뷰 사용 여부 설정
        item.canShowCallout = true

        // 풍선뷰에 표시될 주된 메시지 내용 설정
        when(idx) {
            0 -> item.calloutTitle = "정보센터식당"
            1 -> item.calloutTitle = "복지관 교직원식당"
            2 -> item.calloutTitle = "카페테리아 첨성"
            3 -> item.calloutTitle = "공식당(교직원)"
            4 -> item.calloutTitle = "공식당(학생)"
        }
    }

    fun setRestaurantMenu(idx: Int) {
        // 오늘 날짜(요일) 받아오기
        getTodayCalendar()

        // crawling.kt 에서 식당 메뉴 가져오기 - Cafeteria 객체 생성
        // idx - 1: 정보센터식당, 2: 복지관 교직원식당, 3: 카페테리아 첨성, 4: 공식당(교직원), 5: 공식당(학생)
        // 메뉴 가져오기 - Cafeteria.Result 객체 생성
        // readMenu(날짜, 시간) 메소드 사용
        // 날짜 - 1: 월요일, 2: 화요일, 3: 수요일, 4: 목요일, 5: 금요일
        /* ----------------------------------- 시간 ----------------------------------- */
        // 정보센터식당, 카페테리아 첨성, 공식당(교직원), 공식당(학생) - 1: 중식, 2: 석식
        // 복지관 교직원식당 - 1: 중식
        /* --------------------------------------------------------------------------- */
        var cafe : Cafeteria
        var result1: Cafeteria.Result
        var result2: Cafeteria.Result
        object: Thread() {
            override fun run() {
                cafe = Cafeteria(idx)
                result1 = cafe.readMenu(dayOfWeek, 1)!!
                result2 = cafe.readMenu(dayOfWeek, 2)!!

                Log.e("result1_menu", result1.toString())
                Log.e("result2_menu", result2.toString())
            }
        }.start()
    }

    fun getTodayCalendar() {
        var cal: Calendar
        cal = Calendar.getInstance()

        // 오늘 요일 가져오기 (1~7 : 일~토)
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    }

    fun setSearchMapItems() {
        // searchMap의 key{건물이름(번호)}를 searchMapitems ArrayList에 저장
        searchMapitems = ArrayList()
        for (i in searchMap.keys){
            searchMapitems.add(i)
            Log.e("searchMapitems", i)
        }
    }

    fun showDialog() {
        var dialogView = View.inflate(this, R.layout.dialog_search, null)
        var dlg = AlertDialog.Builder(this)

        autoEditDestinationDlg = dialogView.findViewById(R.id.autoEdit)     // 자동완성텍스트뷰 설정
        var tempAdapter = ArrayAdapter<String>(this,
            android.R.layout.simple_dropdown_item_1line, searchMapitems)    // 어댑터 설정
        autoEditDestinationDlg.setAdapter(tempAdapter)                      // 자동완성텍스트뷰에 적용

        dlg.setView(dialogView)

        dlg.setPositiveButton("확인") { dialog, which ->
            destText = autoEditDestinationDlg.text.toString()
            Toast.makeText(this, "목적지 : $destText", Toast.LENGTH_SHORT).show()
            searchRoute()
            Log.e("destinationText", destText)
        }
        dlg.setNegativeButton("취소", null)

        var alertDialog: AlertDialog = dlg.create()
        alertDialog.show()
    }

    override fun onCalloutRightButton(item: TMapMarkerItem?) {
        var dialogView = View.inflate(this, R.layout.diet, null)
        var dlg = AlertDialog.Builder(this)
        dlg.setView(dialogView)
        dlg.show()
    }

    override fun onLocationChange(location: Location?) {
        if(m_bTrackingMode) {
            myLong = location!!.longitude
            myLat = location.latitude
            tmapview.setLocationPoint(myLong, myLat)
        }
    }
}
