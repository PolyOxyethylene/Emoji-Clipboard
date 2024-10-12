package com.oxyethylene.emojiclipboard.util

import com.oxyethylene.emojiclipboard.domain.model.EmojiThumbnail
import kotlin.random.Random
import kotlin.random.nextInt

object DefaultValueForTest {

    val emojiList = mutableListOf(
        EmojiThumbnail("呵呵", "\uD83D\uDE42"),
        EmojiThumbnail("含泪的笑脸", "\uD83E\uDD72"),
        EmojiThumbnail("好吃", "\uD83D\uDE0B"),
        EmojiThumbnail("想一想", "\uD83E\uDD14"),
        EmojiThumbnail("狗", "\uD83D\uDC15"),
        EmojiThumbnail("猪", "\uD83D\uDC16"),
        EmojiThumbnail("西瓜", "\uD83C\uDF49"),
        EmojiThumbnail("面包", "\uD83C\uDF5E"),
        EmojiThumbnail("亚洲和澳洲", "\uD83C\uDF0F"),
    )

    fun getRandomEmoji () = emojiList[Random.nextInt(0, emojiList.size)]


}