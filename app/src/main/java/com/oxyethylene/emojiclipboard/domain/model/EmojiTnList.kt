package com.oxyethylene.emojiclipboard.domain.model

import android.util.Log
import com.oxyethylene.emojiclipboard.domain.base.Thumbnail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 默认展示一个 emoji，另外还包含了以这个 emoji 为基础的衍生 emoji 列表
 * @property name emoji 的名字
 * @property content 内容，即一个 emoji 符号
 * @property description 可选的描述
 * @property emojiList 衍生 emoji 列表
 */
class EmojiTnList(
    override val name: String,
    override val content: String,
    override var description: String? = null,
    private val emojiList: ArrayList<EmojiThumbnail> = ArrayList()
): Thumbnail {

    private val stringifyEmojiList = ArrayList<CharSequence>()

    /**
     * 添加一个衍生 emoji，添加成功返回 true
     * @param emoji 新增的 emoji
     */
    fun addEmoji(emoji: EmojiThumbnail) = emojiList.add(emoji) && stringifyEmojiList.add("${emoji.content} ${emoji.name}")

    /**
     * 获取衍生 emoji 列表
     */
    fun getEmojiList() = emojiList.toList()

    fun getEmojiListInString() = stringifyEmojiList

}