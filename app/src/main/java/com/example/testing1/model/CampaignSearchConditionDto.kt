package com.example.testing1.model

data class CampaignSearchConditionDto(var startIndex:Int=0,
                    var endIndex:Int=100,
                    var charityName:String ="",
                    var subject:String="",
                    var categories:List<String>? =null,
                    var interest:Boolean=false,
                    var userId:String="") {
}