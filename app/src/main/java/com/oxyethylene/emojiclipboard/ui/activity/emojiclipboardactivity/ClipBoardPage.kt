package com.oxyethylene.emojiclipboard.ui.activity.emojiclipboardactivity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup
import com.kongzue.dialogx.dialogs.BottomMenu
import com.kongzue.dialogx.dialogs.PopNotification
import com.oxyethylene.emojiclipboard.R
import com.oxyethylene.emojiclipboard.application.App
import com.oxyethylene.emojiclipboard.domain.base.Thumbnail
import com.oxyethylene.emojiclipboard.domain.model.EmojiThumbnail
import com.oxyethylene.emojiclipboard.domain.model.EmojiTnList
import com.oxyethylene.emojiclipboard.domain.objects.AppSettings
import com.oxyethylene.emojiclipboard.ui.components.TitleBar

@Composable
fun ClipBoardPage(subGroupName: String) {

    val app = App.getInstance()

    val manager = app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    Column (
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {

        TitleBar(title = subGroupName)

        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.thumbnail_list_layout, null).apply {
                    val rv = findViewById<RecyclerView>(R.id.thumbnail_list)

                    val adapter = rv.grid(4).setup {
                        addType<EmojiThumbnail>(R.layout.item_emoji_thumbnail)
                        addType<EmojiTnList>(R.layout.item_emoji_with_list)

                        if (AppSettings.enableAnimation) setAnimation(AnimationType.SCALE)

                        R.id.thumbnail_card.onFastClick {
                            when(val model = getModel<Thumbnail>()) {
                                // 如果存在衍生 emoji，应该展开
                                is EmojiTnList -> {
                                    BottomMenu.build()
                                        .setTitle("所有 emoji")
                                        .setMenuList(model.getEmojiListInString())
                                        .setOnMenuItemClickListener { _, text, _ ->
                                            val newClip = ClipData.newPlainText(" ", text.subSequence(0, text.indexOf(' ')))
                                            manager.setPrimaryClip(newClip)
                                            PopNotification.build().setMessage("\"$text\" 已复制到剪贴板").show()
                                            return@setOnMenuItemClickListener false
                                        }
                                        .show()
                                }
                                is EmojiThumbnail -> {
                                    val newClip = ClipData.newPlainText(" ", model.content)
                                    manager.setPrimaryClip(newClip)
                                    PopNotification.build().setMessage("\"${model.content} ${model.name}\" 已复制到剪贴板").show()
                                }
                            }
                        }

                    }

                    val list = app.getData("emoji_list")

                    // 获取列表数据
                    adapter.models = if (list != null) list as List<Thumbnail> else emptyList()

                }
            },
            modifier = Modifier.navigationBarsPadding().fillMaxSize()
        )

    }

}
