package com.alien.ioclib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alien.ioclib.utils.InjectUtil

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectUtil.inject(this)
    }
}