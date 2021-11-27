package com.fantastic4.knumap

import android.animation.ObjectAnimator
import android.view.animation.Animation
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FloatingButton {
    var fab_open : Animation
    var fab_close : Animation
    var isFabOpen = false
    var fab : FloatingActionButton
    var fab1 : FloatingActionButton
    var fab2 : FloatingActionButton
    var fab3 : FloatingActionButton

    constructor(_fab_open: Animation, _fab_close: Animation, _fab: FloatingActionButton, _fab1: FloatingActionButton, _fab2: FloatingActionButton, _fab3: FloatingActionButton) {
        fab_open = _fab_open
        fab_close = _fab_close

        fab = _fab
        fab1 = _fab1
        fab2 = _fab2
        fab3 = _fab3

        fab.setOnClickListener {
            anim()
            toggleFab()
        }
    }

    //
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

    // 플로팅 액션 버튼 클릭시 애니메이션 효과
    fun toggleFab() {
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
            val f1_animation = ObjectAnimator.ofFloat(fab1, "translationY", -170f)
            f1_animation.start()
            val f2_animation = ObjectAnimator.ofFloat(fab2, "translationY", -340f)
            f2_animation.start()
            val f3_animation = ObjectAnimator.ofFloat(fab3, "translationY", -510f)
            f3_animation.start()

            // 메인 플로팅 이미지 변경
            fab.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
            //fab.setImageResource(R.drawable.ic_baseline_clear_24)
        }
        // 플로팅 버튼 상태 변경
        isFabOpen = !isFabOpen
    }
}