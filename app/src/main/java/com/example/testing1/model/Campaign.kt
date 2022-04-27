package com.example.testing1.model
import java.time.LocalDate


//캠페인의 데이터 클래스
data class Campaign(var campaignId:Int=0,
                    var subject:String="",
                    var campaignName:String="",
                    var charityName:String="",
                    var deadline:LocalDate?=null,
                    var currentAmount:Int=0,
                    var goalAmount:Int=0,
                    var categories:List<String>? =null,
                    var coverImagePath:String="",
                    var detailImagePath:String="",
                    var reviewImagePath:String="",
                    var description:String=""

                    ){
}
data class CampaignFullDto(
        val campaignList:List<Campaign>
)

//데이터 수신 코드도 필요할듯
data class ResponseCode(
        var code: Int,
        var message: String
)