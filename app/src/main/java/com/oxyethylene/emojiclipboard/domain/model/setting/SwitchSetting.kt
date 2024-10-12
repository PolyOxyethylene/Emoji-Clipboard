package com.oxyethylene.emojiclipboard.domain.model.setting

import com.oxyethylene.emojiclipboard.domain.base.Setting
import com.oxyethylene.emojiclipboard.domain.base.SettingValue

/**
 * switch 类型的设置项，value 用于描述开关状态
 */
class SwitchSetting(
    override val name: String,
    override var value: Boolean,
    override val onValueChanged: (Boolean) -> Unit,
    override val description: String? = null
): Setting, SettingValue<Boolean>