package com.example.testing1

import java.time.LocalDate


//캠페인의 데이터 클래스
data class Campaign(var campaignName:String="",
                    var charityName:String="",
                    var deadline:LocalDate?=null,
                    var currentAmount:Long=0,
                    var goalAmount:Long=0,
                    var categories:List<String>? =null,
                    var coverImagePath:String="",
                    var detailImagePath:String=""
                    ){
}
data class CampaignList(
        val campaignList:List<Campaign>
)

//데이터 수신 코드도 필요할듯
data class ResponseCode(
        val code: Int,
        val message: String
)