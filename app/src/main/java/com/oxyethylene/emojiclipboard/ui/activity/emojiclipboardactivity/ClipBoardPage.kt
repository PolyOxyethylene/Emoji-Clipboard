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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup
import com.kongzue.dialogx.dialogs.BottomMenu
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.oxyethylene.emojiclipboard.R
import com.oxyethylene.emojiclipboard.application.App
import com.oxyethylene.emojiclipboard.domain.base.Thumbnail
import com.oxyethylene.emojiclipboard.domain.model.EmojiThumbnail
import com.oxyethylene.emojiclipboard.domain.model.EmojiTnList
import com.oxyethylene.emojiclipboard.domain.objects.AppSettings
import com.oxyethylene.emojiclipboard.domain.objects.EmojiMap
import com.oxyethylene.emojiclipboard.ui.components.TitleBar

@Composable
fun ClipBoardPage(subGroupName: String) {

    val app = App.getInstance()

    val manager = app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    val emojiMap = app.getData("emoji_map") as EmojiMap

    var message by remember { mutableStateOf("") }

    var visible by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {

        TitleBar(title = subGroupName)

        if (visible) {
            EmojiDialog(message = message) {
                visible = false
            }
        }

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

                                            val emoji = text.substring(0, text.indexOf(' '))

                                            val name = text.substring(text.indexOf(' ') + 1)

                                            if (!AppSettings.enableFavorite) {
                                                val newClip = ClipData.newPlainText(" ", text.subSequence(0, text.indexOf(' ')))
                                                manager.setPrimaryClip(newClip)
                                                PopNotification.build().setMessage("\"$text\" 已复制到剪贴板").show()
                                            } else {

//                                                message = text.toString()
//                                                visible = true

                                                MessageDialog.build()
                                                    .setTitle(text)
//                                                    .setButtonOrientation(LinearLayout.VERTICAL)
                                                    .setOkButton("复制") { _, _ ->
                                                        val newClip = ClipData.newPlainText(" ", text.subSequence(0, text.indexOf(' ')))
                                                        manager.setPrimaryClip(newClip)
                                                        PopNotification.build().setMessage("\"$text\" 已复制到剪贴板").show()
                                                        return@setOkButton false
                                                    }
                                                    .setOtherButton("收藏") { _, _ ->
                                                        AppSettings.favorites[emoji] = name
                                                        AppSettings.favorites = AppSettings.favorites
                                                        emojiMap.notifyFavoritesInsert(EmojiThumbnail(name, emoji))
                                                        PopNotification.build().setMessage("\"$text\" 已添加到收藏夹").show()
                                                        return@setOtherButton false
                                                    }
                                                    .show()
                                            }
                                            return@setOnMenuItemClickListener false
                                        }
                                        .show()
                                }
                                is EmojiThumbnail -> {
                                    if (!AppSettings.enableFavorite) {
                                        val newClip = ClipData.newPlainText(" ", model.content)
                                        manager.setPrimaryClip(newClip)
                                        PopNotification.build().setMessage("\"${model.content} ${model.name}\" 已复制到剪贴板").show()
                                    } else {

//                                        message = "${model.content} ${model.name}"
//                                        visible = true

                                        MessageDialog.build()
                                            .setTitle("${model.content} ${model.name}")
//                                            .setButtonOrientation(LinearLayout.VERTICAL)
                                            .setOkButton("复制") { _, _ ->
                                                val newClip = ClipData.newPlainText(" ", model.content)
                                                manager.setPrimaryClip(newClip)
                                                PopNotification.build().setMessage("\"${model.content} ${model.name}\" 已复制到剪贴板").show()
                                                return@setOkButton false
                                            }
                                            .setOtherButton("收藏") { _, _ ->
                                                AppSettings.favorites[model.content] = model.name
                                                AppSettings.favorites = AppSettings.favorites
                                                emojiMap.notifyFavoritesInsert(EmojiThumbnail(model.name, model.content))
                                                PopNotification.build().setMessage("\"${model.content} ${model.name}\" 已添加到收藏夹").show()
                                                return@setOtherButton false
                                            }
                                            .show()
                                    }
                                }
                            }
                        }

                    }

                    val list = app.getData("emoji_list")

                    // 获取列表数据
                    adapter.models = if (list != null) list as List<Thumbnail> else emptyList()

//                    requestLayout()

                }
            },
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxSize()
        )

    }

}
