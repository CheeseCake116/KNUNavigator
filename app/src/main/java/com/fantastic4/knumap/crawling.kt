package com.fantastic4.knumap

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

// 크롤링 테스트할때 쓰는 메인 함수
/*fun main() {
    var cafe1 = Cafeteria(1)
    var res = cafe1.readMenu(1, 2)
    print (res)
}*/

class Cafeteria {
    lateinit var doc : Document
    var big_table : Element
    var menu_tables : Elements
    /*
    1) 객체 호출 방법
    var cafe = Cafeteria(식당번호)
    식당 번호는 1 : 정보센터식당, 2 : 복지관, 3 : 첨성관, 4 : 공식당(교직원), 5 : 공식당(학생)

    2) 객체로부터 크롤링 값 받는 방법
    var res = cafe.readMenu(날짜, 식단 번호)
    날짜는 1 : 월요일, 2: 화요일, 3: 수요일, 4: 목요일, 5:금요일
    식단번호는 1: 아침, 2: 점심, 3: 저녁


    ex)
    var cafe1 = Cafeteria(1) // 정보센터식당의
    var res = cafe1.readMenu(1, 2) // 월요일 점심 메뉴를 가져와줘

    반환값 : Result 클래스 (밑에 정의되어있음)
    필드는 error(Boolean), menu(String), menuSize(Int)
    error가 false인지 체크 후 menu와 price를 출력하면 됨
     */

    constructor(docNumber: Int) {
        when (docNumber) {
            1 -> doc = Jsoup.connect("https://coop.knu.ac.kr/sub03/sub01_01.html?shop_sqno=35").get()
            2 -> doc = Jsoup.connect("https://coop.knu.ac.kr/sub03/sub01_01.html?shop_sqno=36").get()
            3 -> doc = Jsoup.connect("https://coop.knu.ac.kr/sub03/sub01_01.html?shop_sqno=37").get()
            4 -> doc = Jsoup.connect("https://coop.knu.ac.kr/sub03/sub01_01.html?shop_sqno=85").get()
            5 -> doc = Jsoup.connect("https://coop.knu.ac.kr/sub03/sub01_01.html?shop_sqno=86").get()
        }

        big_table = doc.getElementById("print") // 전체 문서
        menu_tables = big_table.getElementsByClass("tstyle_me") // 조식,중식,석식
    }

    class Result {
        var error : Boolean = false // 에러 여부
        var menu : Array<String> // 식단
        var menuSize : Int // (메뉴 + 가격) 원소 갯수

        constructor(errorBoolean: Boolean, menuString: Array<String>) {
            error = errorBoolean
            menu = menuString
            menuSize = menuString.size
        }

        override fun toString(): String {
            var str = menu.joinToString("\n", "", "")
            return str
        }
    }


    fun readMenu(day: Int, meal: Int) : Result? {
        if (day < 1 || day > 5) {
            var res = Result(true, arrayOf("날짜 오류"))
            return res
        }
        if (meal < 1 || meal > 3) {
            var res = Result(true, arrayOf("식단 번호 오류"))
            return res
        }
        if (menu_tables.size < 2) {
            var res = Result(true, arrayOf("식단 정보 없음"))
            return res
        }

        var date_tables = menu_tables[meal].getElementsByTag("td") // 월~화
        var menu_sections = date_tables[day - 1].getElementsByTag("li") // 메뉴, 가격
        var menuArray = Array<String>(0){""}
        for (section in menu_sections) {
            var element = section.html().split("<p>", "</p>", "<br>")
            var str = ""
            for (el in element) {
                var menu = el.trim()
                if (!(menu.equals(""))) {
                    str += menu + '\n'
                }
            }
            str = str.trim()
            menuArray = menuArray.plus(str)
        }

        // 메뉴, 가격 텍스트 가공
        var res = Result(false, menuArray)

        return res
    }
}