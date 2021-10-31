
package com.example.tahaweyplccontrol

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    var context: Context? = null
    var listView: ListView? = null
    var adapter: ListAdapter? = null
    var dialog: Dialog? = null
    var tahaweyDataSource: ArrayList<RegisterValue>? = null
    var connect: Button? = null
    var writeBtn: Button? = null
    lateinit var port: String
    var ip:kotlin.String? = null
    var address:kotlin.String? = null
    var length:kotlin.String? = null
    var slaveId:kotlin.String? = null
    var txt1:EditText?=null
    var txt2:EditText?=null
    var head: ModHead? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt1=findViewById(R.id.eT1) as EditText
        txt2=findViewById(R.id.eT2) as EditText
        port = "502" as String
        ip = txt1!!.text.toString()
        length = "10" as String
        address = "4029" as String
        slaveId = txt2!!.text.toString()
        connect = findViewById(R.id.connectBtn) as Button
        tahaweyDataSource = ArrayList()
        listView = findViewById(R.id.ListView1) as ListView
        adapter = ListAdapter(this, R.layout.recyclerview_item, tahaweyDataSource)
        context = this
        listView!!.adapter = adapter


        connect!!.setOnClickListener {
            tV1.text=ip.toString()
            tV2.text=slaveId.toString()
            try {val endpoint = EndPoint(
                ip.toString(),
                port.toInt(),
                address.toString().toInt(),
                length.toString().toInt(),
                slaveId.toString().toInt()
            )
                Mod.getInstance().config(endpoint)
                head = ModHead(tahaweyHandler)
                head!!.connect()
                head!!.startPolling()
                closeKeyBored()
            } catch (e: Exception) {
                e.printStackTrace()
            }
           // tV1.text=ip.toString() + "port "  +port.toString().toInt() +"add "+ address.toString().toInt() +"len "+length.toString().toInt() +"id "+ slaveId.toString().toInt()
            object : CountDownTimer(2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // do something after 1s
                }

                override fun onFinish() {
                    // do something end times 5s
                    tV1.text=( tahaweyDataSource!![0].regValue.toFloat()/10).toString() +" V"
                    tV2.text=( tahaweyDataSource!![1].regValue.toFloat()/10).toString()+" V"
                    tV3.text=( tahaweyDataSource!![2].regValue.toFloat()/10).toString()+" V"
                    tV4.text=( tahaweyDataSource!![3].regValue.toFloat()/10).toString()+" V"
                    tV5.text=( tahaweyDataSource!![4].regValue.toFloat()/10).toString()+" V"
                    tV6.text=( tahaweyDataSource!![5].regValue.toFloat()/10).toString()+" V"

                    get_item()

                }
            }.start()
            object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // do something after 1s
                }

                override fun onFinish() {
                    tV7.text=tahaweyDataSource!![0].regValue.toString()+" A"
                    tV8.text=tahaweyDataSource!![1].regValue.toString()+" A"
                    tV9.text=tahaweyDataSource!![2].regValue.toString()+" A"
                }
            }.start()

        }
      //  listView!!.onItemClickListener =
        //button2!!.setOnClickListener {        }

    }

    var tahaweyHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val vals = msg.data.getParcelableArrayList<RegisterValue>("regs")
            if (vals != null && vals.size != 0) {
                tahaweyDataSource!!.clear()
                tahaweyDataSource!!.addAll(vals)
                adapter!!.notifyDataSetChanged()
            }
        }
    }
    var connectionHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            val connected = msg.arg1
            if (connected == 0) {
                connect!!.text = "Connect"
            } else {
                connect!!.text = "Disconnect"
            }
        }
    }

    fun closeKeyBored() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    fun get_item(){
        /* port = "502" as String
        ip = "192.168.11.1" as String
        length = "10" as String
        slaveId = "4" as String

         */
        address = "4019" as String
        try {val endpoint = EndPoint(
            ip.toString(),
            port.toString().toInt(),
            address.toString().toInt(),
            length.toString().toInt(),
            slaveId.toString().toInt()
        )



            Mod.getInstance().config(endpoint)
            head = ModHead(tahaweyHandler)
            head!!.connect()
            head!!.startPolling()
            closeKeyBored()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
