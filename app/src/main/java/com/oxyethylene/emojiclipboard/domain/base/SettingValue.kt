package com.oxyethylene.emojiclipboard.domain.base

/**
 * 设置项值的接口
 * @property value 设置项控制的值
 * @property onValueChanged 设置项监听器
 */
interface SettingValue<T> {

    var value: T

    val onValueChanged: (T) -> Unit

}