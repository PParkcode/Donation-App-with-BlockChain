package com.example.testing1.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.databinding.MainNavBinding
import com.example.testing1.view.SearchActivity
import com.example.testing1.viewModel.MemberViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iamport.sdk.domain.core.Iamport

private val TAG="tag1"
class MainNav:AppCompatActivity() {
    private lateinit var binding: MainNavBinding
    private val memberViewModel by viewModels<MemberViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState:Bundle?){
        Log.d(TAG,"onCreate 실행")

        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView<MainNavBinding>(this,R.layout.main_nav)
        setContentView(binding.root)
        Iamport.init(this)

        binding.viewModel=memberViewModel
        binding.lifecycleOwner=this

        memberViewModel._member=instance.getMyData()
        Log.d(TAG,"getMyData 실행")





        binding.bnvMain.run{setOnNavigationItemSelectedListener { //menu에서 설정한 각 아이템의 id를 통해서 적절한 fragment로 이동동
           when(it.itemId){
                R.id.tab_share ->{
                    val fragShare=FragShare()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container,fragShare).commit()
                    title="나눔 리스트"
                }
                R.id.tab_about ->{
                    val fragAbout=FragAbout()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container,fragAbout).commit()
                }
                R.id.tab_myInform ->{
                    val fragMyInform=FragMyInform()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container,fragMyInform).commit()
                }
            }
            true
        }
            selectedItemId= R.id.tab_share
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG,"옵션 함수 실행")
        menuInflater.inflate(R.menu.bar_menu,menu)
        var item:MenuItem=menu!!.findItem(R.id.create_item)
       var userMode= MySharedPreferences.getUserMode(this)
        Log.d(TAG,"userMode: "+userMode)

        if(userMode.equals("CHARITY")||userMode.equals("ADMIN")){
            item.setVisible(true)
        }
        else{
            item.setVisible(false)
        }


        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.getItemId())
        {
            R.id.search_item ->{
                var intent= Intent(applicationContext, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.create_item->{
                var intent= Intent(applicationContext, RegistCampaign::class.java)
                intent.putExtra("name",memberViewModel.member!!.value!!.name)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}