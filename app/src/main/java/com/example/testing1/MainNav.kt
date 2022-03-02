package com.example.testing1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainNav:AppCompatActivity() {


    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_nav)

        var bnv_main=findViewById(R.id.bnv_main) as BottomNavigationView //바텀 네비게이션 객체

        bnv_main.run{setOnNavigationItemSelectedListener { //menu에서 설정한 각 아이템의 id를 통해서 적절한 fragment로 이동동
           when(it.itemId){
                R.id.tab_myGroup->{
                    val fragMyGroup=FragMyGroup()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container,fragMyGroup).commit()
                    title="My 단체"
                }
                R.id.tab_share->{
                    val fragShare=FragShare()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container,fragShare).commit()
                    title="나눔 리스트"
                }
                R.id.tab_about->{
                    val fragAbout=FragAbout()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container,fragAbout).commit()
                }
                R.id.tab_myInform->{
                    val fragMyInform=FragMyInform()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container,fragMyInform).commit()
                }
            }
            true
        }
            selectedItemId=R.id.tab_share
        }

    }
}