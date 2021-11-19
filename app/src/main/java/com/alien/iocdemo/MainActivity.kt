package com.alien.iocdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.Button
import com.alien.ioclib.BaseActivity
import com.alien.ioclib.annotation.ContentView
import com.alien.ioclib.annotation.InjectView

import android.widget.TextView
import com.alien.ioclib.annotation.OnClick
import com.alien.ioclib.annotation.OnLongClick


@ContentView(R.layout.activity_main)
class MainActivity : BaseActivity() {

    @InjectView(R.id.tv_message)
    private val tv: TextView? = null

    @InjectView(R.id.btn_click)
    private val btn: Button? = null

    val TAG = "IOC_TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, tv?.text.toString())
        Log.d(TAG, btn?.text.toString())
    }

    @OnClick(R.id.btn_click, R.id.btn_click2)
    fun click(view: View) {
        when (view.id) {
            R.id.btn_click -> {
                Log.d(TAG, "第一个按钮点一下")
            }
            R.id.btn_click2 -> {
                Log.d(TAG, "第二个按钮点一下")
            }
        }
    }

    @OnLongClick(R.id.btn_click, R.id.btn_click2)
    fun longClick(view: View): Boolean {
        when (view.id) {
            R.id.btn_click -> {
                Log.d(TAG, "第一个按钮长按一下")
                return true
            }
            R.id.btn_click2 -> {
                Log.d(TAG, "第二个按钮长按一下")
                return true
            }

        }
        return false
    }
}