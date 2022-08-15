package com.example.testing1.model

import java.io.Serializable

data class WithDrawData(var sender:String="유동근",
                        var receiver:String,
                        var purpose:String,
                        var amount:Int, //거래 금액
                        var certificateFile:String,
                        var blockChainTransactionId:String="",
                        var date:String=""
                        ):Serializable