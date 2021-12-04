package com.fantastic4.knumap

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FloatingButton {

    var fab_open : Animation
    var fab_close : Animation
    var isFabOpen = false
    var fab : FloatingActionButton
    var fab1 : FloatingActionButton
    var fab2 : FloatingActionButton
    var fab3 : FloatingActionButton
    var mainContext : Context // MainActivity context 저장 변수
    lateinit var dialogView : View // 목적지 다이얼로그 뷰
    lateinit var dlgEdtDest : EditText // 목적지 다이얼로그 뷰의 에디트텍스트
    var destText : String = "" // 목적지 문자열을 저장하는 변수

    constructor(_fab_open: Animation, _fab_close: Animation, _fab: FloatingActionButton, _fab1: FloatingActionButton, _fab2: FloatingActionButton, _fab3: FloatingActionButton, mContext: Context) {
        fab_open = _fab_open
        fab_close = _fab_close

        fab = _fab // 플로팅 액션 버튼
        fab1 = _fab1 // 플로팅 버튼 1
        fab2 = _fab2 // 플로팅 버튼 2
        fab3 = _fab3 // 플로팅 버튼 3
        fab.setOnClickListener {
            anim()
            toggleFab()
        }

        mainContext = mContext // MainActivity에 접근하기 위한 컨텍스트 저장

        // 세번째 버튼 클릭하면 목적지 다이얼로그 뜨는 이벤트
        fab3.setOnClickListener {
            showDialog()
        }
    }

    // 열고 닫을때 사라지거나 나타나는 애니메이션
    fun anim() {
        if (isFabOpen) {
            fab1.startAnimation(fab_close)
            fab2.startAnimation(fab_close)
            fab3.startAnimation(fab_close)
            fab1.isClickable = false
            fab2.isClickable = false
            fab3.isClickable = false
        } else {
            fab1.startAnimation(fab_open)
            fab2.startAnimation(fab_open)
            fab3.startAnimation(fab_open)
            fab1.isClickable = true
            fab2.isClickable = true
            fab3.isClickable = true
        }
    }

    // 플로팅 액션 버튼 클릭시 움직이는 애니메이션 효과
    fun toggleFab() {
        var dp = mainContext.resources.getDimension(R.dimen.size_1dp)

        if (isFabOpen) {
            // 플로팅 액션 버튼 닫기
            // 애니메이션 추가
            val f1_animation = ObjectAnimator.ofFloat(fab1, "translationY", 0f)
            f1_animation.start()
            val f2_animation = ObjectAnimator.ofFloat(fab2, "translationY", 0f)
            f2_animation.start()
            val f3_animation = ObjectAnimator.ofFloat(fab3, "translationY", 0f)
            f3_animation.start()
            // 메인 플로팅 이미지 변경
            fab.setImageResource(android.R.drawable.ic_input_add)
            //fab.setImageResource(android.R.drawable.ic_baseline_add_24)
        } else {
            // 플로팅 액션 버튼 열기
            val f1_animation = ObjectAnimator.ofFloat(fab1, "translationY", -65 * dp)
            f1_animation.start()
            val f2_animation = ObjectAnimator.ofFloat(fab2, "translationY", -130 * dp)
            f2_animation.start()
            val f3_animation = ObjectAnimator.ofFloat(fab3, "translationY", -195 * dp)
            f3_animation.start()

            // 메인 플로팅 이미지 변경
            fab.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
            //fab.setImageResource(R.drawable.ic_baseline_clear_24)
        }
        // 플로팅 버튼 상태 변경
        isFabOpen = !isFabOpen
    }

    fun showDialog() {
        dialogView = View.inflate(mainContext, R.layout.dialog_search, null)
        var dlg = AlertDialog.Builder(mainContext)
        dlg.setView(dialogView)
        dlg.setPositiveButton("확인") { dialog, which ->
            dlgEdtDest = dialogView.findViewById<EditText>(R.id.edtDest)
            destText = dlgEdtDest.text.toString()
            Toast.makeText(mainContext, "목적지 : $destText", Toast.LENGTH_SHORT).show()

            MainActivity().setDestinationText(this)
        }
        dlg.setNegativeButton("취소", null)
        dlg.show()
    }

}