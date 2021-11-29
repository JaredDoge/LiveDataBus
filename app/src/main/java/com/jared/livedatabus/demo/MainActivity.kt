package com.jared.livedatabus.demo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jared.livedatabus.LiveDataBus
import com.jared.livedatabus.SingleLiveData
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var  mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnShow: Button = findViewById(R.id.btn_show)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory())
            .get(MainViewModel::class.java)


        mainViewModel.showToast.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        btnShow.setOnClickListener {
            mainViewModel.show()
        }

//        LiveDataBus.of<SampleEvent>().message().observe(this){
//
//        }
//
//        LiveDataBus.of<SampleEvent>().message().setValue(123)



    }
}