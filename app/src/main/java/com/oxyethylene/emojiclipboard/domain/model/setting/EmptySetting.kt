package com.oxyethylene.emojiclipboard.domain.model.setting

import com.oxyethylene.emojiclipboard.domain.base.Setting

/**
 * 什么也不做，只显示文字信息的设置项
 */
class EmptySetting(
    override val name: String,
    override val description: String? = null
): Setting