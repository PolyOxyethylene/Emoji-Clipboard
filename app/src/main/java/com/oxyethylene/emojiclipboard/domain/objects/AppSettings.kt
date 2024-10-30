package com.oxyethylene.emojiclipboard.domain.objects

import com.drake.serialize.serialize.annotation.SerializeConfig
import com.drake.serialize.serialize.serialLazy
import com.kongzue.dialogx.dialogs.PopNotification
import com.oxyethylene.emojiclipboard.R
import com.oxyethylene.emojiclipboard.application.App
import com.oxyethylene.emojiclipboard.domain.base.Setting
import com.oxyethylene.emojiclipboard.domain.model.setting.ActionSetting
import com.oxyethylene.emojiclipboard.domain.model.setting.SwitchSetting
import com.oxyethylene.emojiclipboard.domain.objects.AppSettings.enableAnimation
import com.oxyethylene.emojiclipboard.domain.objects.AppSettings.enableFavorite
import com.oxyethylene.emojiclipboard.domain.objects.AppSettings.enableMaterialTheme
import com.oxyethylene.emojiclipboard.domain.objects.AppSettings.favorites
import com.oxyethylene.emojiclipboard.domain.objects.AppSettings.settings

typealias SettingList = ArrayList<Pair<String, List<Setting>>>

/**
 * 保存应用的全局设置
 * @property enableAnimation 应用是否开启动画
 * @property enableMaterialTheme 启用动态主题色
 * @property enableFavorite 启用收藏功能
 * @property favorites 收藏表情包列表
 * @property settings 设置的集合，使用 key(分类名称) 来划分不同分类的设置项
 */
@SerializeConfig(mmapID = "app_settings")
object AppSettings {

    var enableAnimation by serialLazy(false)

    var enableMaterialTheme by serialLazy(true)

    var enableFavorite by serialLazy(true)

    var favorites by serialLazy(HashMap<String, String>())

    val settings = SettingList().apply {
        val app = App.getInstance()
        val res = app.resources

        add(Pair("应用信息", listOf(
            ActionSetting(
                name = app.packageManager.getPackageInfo(app.packageName, 0).versionName,
                action = {
                    PopNotification.build().setMessage("敬请期待").show()
                },
                description = res.getString(R.string.profile_version_description)
            ),
            ActionSetting(
                name = res.getString(R.string.profile_about_application),
                action = {
                    PopNotification.build().setMessage("敬请期待").show()
                }
            ),
            ActionSetting(
                name = res.getString(R.string.profile_open_source),
                action = {
                    PopNotification.build().setMessage("敬请期待").show()
                }
            )
        )))

        add(Pair("外观", listOf(
            SwitchSetting(
                name = "启用应用动画",
                value = enableAnimation,
                onValueChanged = { v ->
                    enableAnimation = v
                },
                description = "开启动画可以丰富画面效果，但是需要更高的设备性能"
            ),
//            SwitchSetting(
//                name = "启用动态颜色主题",
//                value = enableMaterialTheme,
//                onValueChanged = { v ->
//                    enableMaterialTheme = v
//                },
//                description = "开启后应用颜色随系统主题色改变，该功能仅 Android 12 及以上设备支持"
//            )
        )))

        add(Pair("功能", listOf(
            SwitchSetting(
                name = "启用收藏功能",
                value = enableFavorite,
                onValueChanged = { v ->
                    enableFavorite = v
                },
                description = "开启后，点击 emoji 可以选择加入收藏夹，关闭后仍可快捷复制收藏夹已有 emoji"
            )
        )))


    }

}