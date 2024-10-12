package com.oxyethylene.emojiclipboard.domain.base

/**
 * 对象缩略图
 * @property name 对象的名字
 * @property content 对象的内容
 * @property description 可选的描述文本
 */
interface Thumbnail {

    val name: String

    val content: String

    var description: String?

}