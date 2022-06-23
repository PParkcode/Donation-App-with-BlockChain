package com.example.testing1.model

data class ExchangeResponseData(var Header:ResponseHeader) {
}

data class ResponseHeader(
        var Trtm:String,
        var Rsms:String,
        var ApiNm:String,
        var IsTuno:String,
        var Tsymd:String,
        var FintechApsno:String,
        var Iscd:String,
        var Rpcd:String,
        var ApiSvcCd:String)