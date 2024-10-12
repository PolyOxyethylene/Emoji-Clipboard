package com.oxyethylene.emojiclipboard.ui.activity.mainactivity

import android.content.Intent
import android.view.LayoutInflater
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.oxyethylene.emojiclipboard.R
import com.oxyethylene.emojiclipboard.application.App
import com.oxyethylene.emojiclipboard.domain.model.EGroup
import com.oxyethylene.emojiclipboard.domain.model.ESubGroup
import com.oxyethylene.emojiclipboard.domain.objects.AppSettings
import com.oxyethylene.emojiclipboard.domain.objects.EmojiMap

@Composable
fun EmojiPage(
    modifier: Modifier = Modifier,
    emojiMap: EmojiMap
) {

    // 获取 Application 实例
    val app = App.getInstance()

    val res = app.resources

    Column (
        modifier = modifier
    ) {

        MainTopBar(title = res.getString(R.string.emoji_page_title))

        Text(
            text = res.getString(R.string.emoji_page_hint),
            lineHeight = 16.sp,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.surfaceTint,
            modifier = Modifier.padding(start = 30.dp)
        )

        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.group_list_layout, null).apply {
                    val rv = findViewById<RecyclerView>(R.id.group_list)

                    val adapter = rv.linear().setup {
                        addType<EGroup>(R.layout.item_emoji_group)
                        addType<ESubGroup>(R.layout.item_emoji_subgroup)

                        if (AppSettings.enableAnimation) setAnimation(AnimationType.SCALE)

                        R.id.item.onFastClick {
                            when (itemViewType) {
                                R.layout.item_emoji_group -> {
                                    expandOrCollapse()
                                }
                                // 如果是子分组，打开详情界面
                                R.layout.item_emoji_subgroup -> {
                                    val model = getModel<ESubGroup>()
                                    // 传递数据
                                    app.putData("emoji_list", model.getEmojiList())
                                    val intent = Intent()
                                    intent.`package` = app.packageName
                                    intent.action = "com.oxyethylene.action.EMOJI_THUMBNAIL"
                                    intent.putExtra("subgroup_name", model.subGroupName)
                                    context.startActivity(intent)
                                }
                            }
                        }

                    }

                    // 获取列表数据
                    adapter.models = emojiMap.getGroups()

                    if (AppSettings.enableAnimation) {
                        // 设置列表动画重复
                        adapter.animationRepeat = true
                    }

                }
            },
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxSize()
        )
        
    }

}