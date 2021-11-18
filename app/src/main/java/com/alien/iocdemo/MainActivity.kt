package com.alien.iocdemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.alien.ioclib.BaseActivity
import com.alien.ioclib.annotation.ContentView
import com.alien.ioclib.annotation.InjectView

import android.widget.TextView


@ContentView(R.layout.activity_main)
class MainActivity : BaseActivity() {

    @InjectView(R.id.tv_message)
    private val tv: TextView? = null

    @InjectView(R.id.btn_click)
    private val btn: Button? = null

    val TAG = "MainActivityTAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, tv?.text.toString())
        Log.d(TAG, btn?.text.toString())
    }
}