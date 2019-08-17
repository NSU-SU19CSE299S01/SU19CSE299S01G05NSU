package com.project.jhasan

import androidx.multidex.MultiDexApplication

class SoudagorApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        //AppLocationManager.instance.initiate(this)
    }
}