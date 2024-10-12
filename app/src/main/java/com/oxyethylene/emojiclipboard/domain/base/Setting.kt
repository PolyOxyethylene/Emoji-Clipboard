package com.oxyethylene.emojiclipboard.domain.base

/**
 * 设置项的通用属性接口
 * @property name 设置名称
 * @property description 设置的描述，可选
 */
interface Setting {

    val name: String

    val description: String?

}