package com.fantastic4.knumap

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import com.skt.Tmap.TMapView
import android.view.View
import android.widget.LinearLayout
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapData

import com.skt.Tmap.TMapPolyLine
import java.lang.Exception
import com.skt.Tmap.TMapData.FindPathDataListenerCallback

import com.skt.Tmap.TMapData.TMapPathType
import com.skt.Tmap.poi_item.TMapPOIItem


class MainActivity : AppCompatActivity() {
    lateinit var tMapPolyLine: TMapPolyLine
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "KNU MAP"

        val linearLayoutTmap = findViewById<View>(R.id.linearLayoutTmap) as LinearLayout
        val tMapView = TMapView(this)
        val tMapPointStart = TMapPoint(35.88688, 128.60850) // 공대 9호관(출발지)
        val tMapPointEnd = TMapPoint(35.89113011991111, 128.6119321645856) // 중앙도서관(목적지)
        //val strData = "공대 9호관"

        tMapView.setSKTMapApiKey("l7xx6a347111bc9842009151e620e7301037")
        linearLayoutTmap.addView(tMapView)
        tMapView.setCenterPoint(128.611212, 35.889975) // 경북대학교 set center

        // 경로 검색 (보행자)
        TMapData().findPathDataWithType(TMapPathType.PEDESTRIAN_PATH, tMapPointStart, tMapPointEnd,
            FindPathDataListenerCallback { polyLine ->
                polyLine.lineColor = Color.BLUE
                polyLine.lineWidth = 5f
                tMapView.addTMapPath(polyLine) })

        // POI 검색
        /*TMapData().findAllPOI(strData,
            TMapData.FindAllPOIListenerCallback { itemList ->
                for(i in 0..itemList.size){
                    val item : TMapPOIItem
                    item = itemList.get(i)
                }
            })*/
    }
}