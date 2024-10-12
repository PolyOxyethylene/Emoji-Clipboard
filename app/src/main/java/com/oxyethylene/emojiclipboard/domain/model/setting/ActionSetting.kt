package com.oxyethylene.emojiclipboard.domain.model.setting

import com.oxyethylene.emojiclipboard.domain.base.Setting

/**
 * 封装了一个点击响应行为的设置项
 * @param action 点击事件
 */
class ActionSetting(
    override val name: String,
    var action: () -> Unit,
    override val description: String? = null
): Setting