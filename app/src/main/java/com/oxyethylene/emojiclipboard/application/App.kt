package com.oxyethylene.emojiclipboard.application

import android.app.Application
import com.drake.brv.utils.BRV
import com.google.android.material.color.DynamicColors
import com.kongzue.dialogx.DialogX
import com.oxyethylene.emojiclipboard.BR
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    /**
     * 存放 Activity 间通信数据的 hashMap
     */
    private val dataMap = HashMap<String, Any>()

    companion object MyApp {

        /**
         * 全局唯一的 Application 实例
         */
        private lateinit var app: App

        /**
         * 获取 Application 实例
         */
        fun getInstance() = app

    }

    override fun onCreate() {
        super.onCreate()

        // 初始化 BRV
        BRV.modelId = BR.m

        // 初始化 DialogX
        DialogX.init(this)


        // 初始化 app 单例
        app = this

        // 启用 Material You 主题
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    /**
     * 获取数据，不存在 key 时返回 null
     * @param key 数据的键
     */
    fun getData(key: String) = dataMap[key]

    /**
     * 存放数据
     * @param key 键
     * @param data 数据
     */
    fun putData(key: String, data: Any) {
        dataMap[key] = data
    }

}