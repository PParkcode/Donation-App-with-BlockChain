package com.example.testing1.model

data class ExchangeData(
        var Header:RequestHeader,
        var Bncd:String,
        var Acno:String,
        var Tram:String,
        var DractOtlt:String="환전",
        var MractOtlt:String="환전"
) {
}

data class RequestHeader(
        var ApiNm:String,
        var Tsymd:String,
        var Trtm:String,
        var Iscd:String,
        var FintechApsno:String,
        var ApiSvcCd:String,
        var IsTuno:String,
        var AccessToken:String
)

