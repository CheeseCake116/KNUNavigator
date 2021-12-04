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
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skt.Tmap.*
import java.lang.Exception
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationListener
import android.widget.Button
import androidx.core.graphics.scale


class MainActivity : AppCompatActivity(), TMapGpsManager.onLocationChangedCallback {
    var m_bTrackingMode : Boolean = true
    lateinit var tMapGpsManager : TMapGpsManager
    lateinit var tmapGps : TMapGpsManager
    lateinit var tmapview : TMapView
    var myLong : Double = 0.0 // 현재 위치 _ 경도
    var myLat : Double = 0.0 // 현재 위치 _ 위도
    val mApiKey : String = "l7xx6a347111bc9842009151e620e7301037"
    companion object{
        var isLoc : Boolean = true
    }

    lateinit var tMapPolyLine: TMapPolyLine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 권한 설정정
       if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1); //위치권한 탐색 허용 관련 내용
            }
            return;
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
        tmapview.setCompassMode(true)

        // 현위치 보기 온오프
        var btnLoc : View = findViewById(R.id.btnLoc)
        btnLoc.setOnClickListener{ view ->
            if (isLoc){ // 현위치 보기 중이면
                tmapview.setTrackingMode(false)
                tmapview.setCompassMode(false)
                tmapview.setSightVisible(false)
                tmapview.invalidate()
                isLoc = false
            }
            else{ // 현위치 보기 중이 아니면
                tmapview.setTrackingMode(true)
                tmapview.setCompassMode(true)
                tmapview.setSightVisible(true)
                tmapview.invalidate()
                isLoc = true
            }
        }

        // POI 검색
        /*TMapData().findAllPOI(strData,
            TMapData.FindAllPOIListenerCallback { itemList ->
                for(i in 0..itemList.size){
                    val item : TMapPOIItem
                    item = itemList.get(i)
                }
            })*/

        // 플로팅 버튼
        var fab_open = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        var fab_close = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)

        var fab = findViewById<FloatingActionButton>(R.id.fab_btnMain)
        var fab1 = findViewById<FloatingActionButton>(R.id.fab_btn1)
        var fab2 = findViewById<FloatingActionButton>(R.id.fab_btn2)
        var fab3 = findViewById<FloatingActionButton>(R.id.fab_btn3)

        var FB = FloatingButton(fab_open, fab_close, fab, fab1, fab2, fab3, this)

        // 마커 설정
        setMarker()
    }

    override fun onLocationChange(location: Location?) {
        if(m_bTrackingMode) {
            myLong = location!!.longitude
            myLat = location.latitude
            tmapview.setLocationPoint(myLong, myLat)

            // 경로 설정
            val tMapPointStart = TMapPoint(myLat, myLong) //현재 위치
            val tMapPointEnd = TMapPoint(35.89113011991111, 128.6119321645856) // 중앙도서관(목적지)


            // 경로 검색 (보행자)
            TMapData().findPathDataWithType(
                TMapData.TMapPathType.PEDESTRIAN_PATH, tMapPointStart, tMapPointEnd,
                TMapData.FindPathDataListenerCallback { polyLine ->
                    polyLine.lineColor = Color.BLUE
                    polyLine.lineWidth = 5f
                    tmapview.addTMapPath(polyLine)
                })
        }
    }

    fun setMarker() {
        // 1. 정보센터식당, 2. 복지관, 3. 첨성관, 4. 공식당(교직원), 5. 공식당(학생) : 항상 마커로 표시
        // 정보센터식당 : 35.892376545752455, 128.6131707177347
        // 복지관 : 35.88916342021079, 128.6144599690982
        // 첨성관 : 35.8915902231366, 128.61487701142792
        // 공식당(교직원), 공식당(학생) : 35.88828732332779, 128.60960810892362

        var markerItem: ArrayList<TMapMarkerItem>    // MarkerItem 선언
        var item_point: ArrayList<TMapPoint>    // 식당 Point(좌표) 선언

        item_point = ArrayList()
        item_point.add(0, TMapPoint(35.892376545752455, 128.6131707177347)) // 정보센터식당 좌표
        item_point.add(1, TMapPoint(35.88916342021079, 128.6144599690982))  // 복지관 좌표
        item_point.add(2, TMapPoint(35.8915902231366, 128.61487701142792))  // 첨성관 좌표
        item_point.add(3, TMapPoint(35.88828732332779, 128.60960810892362)) // 공식당 좌표(세븐일레븐)

        // 마커 아이콘 "Bitmap"으로 불러오기
        var bitmap: Bitmap
        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.marker_map_icon)
        bitmap.scale(150, 150, true)

        markerItem = ArrayList()
        var item: TMapMarkerItem
        for(idx: Int in 0..3) {
            item = TMapMarkerItem()
            item.setPosition(0.5f, 1.0f)    // 마커 중심점 중앙 하단으로 설정
            item.tMapPoint = item_point[idx]  // 마커 좌표 설정

            markerItem.add(idx, item)   // markerItem에 item 추가

            tmapview.addMarkerItem("merkerItem$idx", markerItem[idx])   // tmapview에 markerItem 추가
        }
    }
}