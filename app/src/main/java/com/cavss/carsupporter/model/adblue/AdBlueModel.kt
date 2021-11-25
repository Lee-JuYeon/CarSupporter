package com.cavss.carsupporter.model.adblue

data class AdBlueModel(
    val code : String, //코드
    val title : String, // 명칭
    val address : String, // 주소
    val digit : String, // 전화번호
    val workTime : String, // 영업시간
    val stock : Double, // 재고량
    val price : String, // 가격
    val latitude : String, // 위도
    val longitude : String, // 경도
    val updateDate : String // 데이터 기준일
) {
}