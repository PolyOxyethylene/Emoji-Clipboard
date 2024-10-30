package com.oxyethylene.emojiclipboard.ui.activity.mainactivity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.grid
import com.drake.brv.utils.mutable
import com.drake.brv.utils.setup
import com.kongzue.dialogx.dialogs.BottomDialog
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.OnBindView
import com.kongzue.dialogx.style.IOSStyle
import com.oxyethylene.emojiclipboard.R
import com.oxyethylene.emojiclipboard.application.App
import com.oxyethylene.emojiclipboard.domain.model.EmojiThumbnail
import com.oxyethylene.emojiclipboard.domain.objects.AppSettings
import com.oxyethylene.emojiclipboard.domain.objects.EmojiMap

@Composable
fun HomeFragment(emojiMap: EmojiMap) {

    val res = LocalContext.current.resources

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MainTopBar(title = res.getString(R.string.home_page_title))

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                ChatBubble(modifier = Modifier.align(Alignment.Start), message = "诶 我有一计\uD83E\uDD13\uD83D\uDC46")

                ChatBubble(modifier = Modifier.align(Alignment.End), message = "通过搜索可以更快查找想要的emoji，保存到收藏夹中更快捷使用", onLeft = false)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    onClick = {
                        PopNotification.build().setMessage("敬请期待").show()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier.size(20.dp))
                    Text(text = "搜索Emoji", modifier = Modifier.padding(start = 6.dp))
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    onClick = {
                        showFavorites(emojiMap)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(20.dp))
                    Text(text = "收藏夹", modifier = Modifier.padding(start = 6.dp))
                }

            }

        }
    }

}

/**
 * 模仿一个聊天气泡
 * @param modifier 建议只调整聊天气泡的位置
 * @param message 聊天内容
 * @param onLeft 是否在左侧，默认为 true，false 时聊天气泡在右侧显示
 */
@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    message: String,
    onLeft: Boolean = true
) {

    var _modifier = modifier
        .padding(vertical = 10.dp)
        .wrapContentWidth()
        .wrapContentHeight()

    _modifier = if (onLeft) {
        _modifier
            .padding(end = 80.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 16.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp
                )
            )
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    } else {
        _modifier
            .padding(start = 80.dp)
//            .border(1.dp, Color.Black, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomEnd = 0.dp, bottomStart = 10.dp))
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 0.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp
                )
            )
            .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f))
    }

    Column (
        modifier = _modifier
    ) {

        Text(
            text = message,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 18.dp),
            textAlign = TextAlign.Justify,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }

}

fun showFavorites(
    emojiMap: EmojiMap
) {

    val manager = App.getInstance().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    BottomDialog.build()
        .setTitle("收藏夹")
        .setMessage("点击复制 emoji，长按删除 emoji")
        .setCancelButton("取消") { _, _ ->
            return@setCancelButton false
        }
        .setCustomView(object : OnBindView<BottomDialog>(R.layout.thumbnail_list_layout) {
            override fun onBind(dialog: BottomDialog?, v: View?) {

                val rv = v!!.findViewById<RecyclerView>(R.id.thumbnail_list)

                val adapter = rv.grid(4).setup {
                    addType<EmojiThumbnail>(R.layout.item_emoji_thumbnail)

                    R.id.thumbnail_card.onFastClick {
                        val model = getModel<EmojiThumbnail>()

                        val newClip = ClipData.newPlainText(" ", model.content)
                        manager.setPrimaryClip(newClip)
                        PopNotification.build().setMessage("\"${model.content} ${model.name}\" 已复制到剪贴板").show()

                    }

                    R.id.thumbnail_card.onLongClick {
                        val model = getModel<EmojiThumbnail>()

                        val index = rv.mutable.indexOf(model)

                        if (index >= 0) {

                            MessageDialog.build()
                                .setTitle("移除 emoji")
                                .setMessage("是否移除表情 \"${model.content} ${model.name}\"")
                                .setOkButton("确认") {_, _ ->

                                    // 确认移除
                                    rv.mutable.removeAt(index)
                                    rv.bindingAdapter.notifyItemRemoved(index)
                                    AppSettings.favorites.remove(model.content)
                                    AppSettings.favorites = AppSettings.favorites

                                    PopNotification.build()
                                        .setMessage("表情 \"${model.content} ${model.name}\" 已移除")
                                        .setButton("撤销") {_, _ ->
                                            rv.mutable.add(index, model)
                                            rv.bindingAdapter.notifyItemInserted(index)
                                            AppSettings.favorites[model.content] = model.name
                                            AppSettings.favorites = AppSettings.favorites
                                            return@setButton false
                                        }.show()
                                    return@setOkButton false
                                }
                                .setCancelButton("取消")
                                .show()
                        }

                    }

                }

                adapter.models = emojiMap.favorites

            }
        })
        .show()

}