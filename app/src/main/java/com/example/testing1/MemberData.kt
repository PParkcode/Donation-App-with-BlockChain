package com.example.testing1

import java.lang.reflect.Member

data class MemberData(var email:String="",
                      var password:String="",
                      var name:String="",
                      var phoneNumber:String="",
                      var nickname:String="",
                      var memberType:String="",
                      var pointAmount:Int=0,
                      var myHeart:List<Campaign>?=null
                      )
{
    /*
    private var myPoint=0
    private var myHeart: List<Campaign>?=null
    init{
        myPoint=0
        myHeart=null
    }

    constructor(){
        id=""
        pwd=""
        name=""
        phone=""
        birth=""
        sex=0
        nick=""
        mode=0
        myPoint=0
        myHeart=null

    }
    public fun getMyPoint():Int{
        return myPoint
    }
    public fun setMyPoint(point:Int){
        this.myPoint=point
    }

     */

}
data class ResponseMember(
        val memberData:MemberData,
        val status:ResponseCode
)