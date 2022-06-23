package com.example.testing1.model

import java.time.temporal.TemporalAmount

data class TransactionForm(
        var id:String,
        var from:String,
        var to:String,
        var amount: String,
        var date: String,
        var type: String
)