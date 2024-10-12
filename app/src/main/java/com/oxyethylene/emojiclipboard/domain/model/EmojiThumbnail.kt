package com.oxyethylene.emojiclipboard.domain.model

import com.drake.brv.item.ItemPosition
import com.oxyethylene.emojiclipboard.domain.base.Thumbnail

/**
 * 单个 emoji 的缩略图
 * @property name emoji 的名字
 * @property content 内容，即一个 emoji 符号
 * @property description 可选的描述
 */
class EmojiThumbnail(
    override val name: String,
    override val content: String,
    override var description: String? = null
): Thumbnail {

    /**
     * 转换成带衍生 emoji 列表的 emoji 类型
     */
    fun toEmojiTnList () = EmojiTnList(name, content, description)

}