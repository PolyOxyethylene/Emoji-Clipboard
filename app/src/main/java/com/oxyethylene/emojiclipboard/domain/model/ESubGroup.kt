package com.oxyethylene.emojiclipboard.domain.model

import com.oxyethylene.emojiclipboard.domain.base.Thumbnail
import java.util.TreeMap

/**
 * emoji 的子分组
 * @property subGroupName 子分组的名称
 * @property emojiMap emoji 的映射集合
 */
class ESubGroup(
    val subGroupName: String
) {

    private val emojiMap = TreeMap<String, Thumbnail>()

    /**
     * 添加一个 emoji 到集合中
     * @param emojiName 从文件中读取到的 emoji 的全名
     * @param emojiText emoji 符号
     */
    fun addEmoji(emojiName: String, emojiText: String) {
        // 如果 emoji 是从一个基础版本衍生出的
        if (emojiName.contains(":")) {
            // 基础版本的名字
            val baseName = emojiName.substring(0, emojiName.indexOf(":"))
            // 如果子分组中还没有这个基础版本，那就以第一个衍生版本为列表头
            if (!emojiMap.containsKey(baseName)) {
                // 用基础名字作为列表名称
                val newList = EmojiTnList(baseName, emojiText)
                // 把自己添加进去
                newList.addEmoji(EmojiThumbnail(emojiName, emojiText))
                emojiMap[baseName] = newList
            } else {
                // 就算有基础版本，也可能是 EmojiThumbnail 类型，需要先转换
                if (emojiMap[baseName] is EmojiThumbnail) {
                    val newList = (emojiMap[baseName] as EmojiThumbnail).toEmojiTnList()
                    emojiMap[baseName] = newList
                }
                // 添加衍生 emoji
                (emojiMap[baseName] as EmojiTnList).addEmoji(EmojiThumbnail(emojiName, emojiText))
            }
        } else {
            // 如果是基础 emoji，直接添加即可
            emojiMap[emojiName] = EmojiThumbnail(emojiName, emojiText)
        }
    }

    /**
     * 返回一个 emoji列表
     */
    fun getEmojiList () = emojiMap.values.toList()

}