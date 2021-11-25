package com.jared.livedatabus.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import com.jared.livedatabus.LiveDataBus
import com.jared.livedatabus.BusLiveData
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSend: Button = findViewById(R.id.btn_send)

        val btnRemove: Button = findViewById(R.id.btn_remove)

        val et: EditText = findViewById(R.id.et)

        val text: TextView = findViewById(R.id.tv)


        val ob = Observer<String> { t ->text.text = t}


        LiveDataBus.get<String>("bus1").observe(this, ob)

        btnSend.setOnClickListener {
                LiveDataBus.get<String>("bus1").setValue(et.text.toString())
        }

        btnRemove.setOnClickListener {
            //正常情況不需要自行removeObserver
            //但如果想自行remove是可以的

            LiveDataBus.get<String>("bus1").removeObserver(ob)
        }


        //不用LiveDataBus，也可以單獨使用，可以確保訊息的來源是可靠的
        val b = BusLiveData<String>()
        b.observe(this, {
            Log.d("Bus", it)
        })

        b.setValue("test")

        /*
          也可以用向retrofit 的api 定義方式，避免使用get(String key) 時，key打錯的問題

         */
        LiveDataBus.of<Chat>().message().observe(this, {

            Log.d("Bus", it.toString())

        })

        LiveDataBus.of<Chat>().message().setValue(777)

    }
}