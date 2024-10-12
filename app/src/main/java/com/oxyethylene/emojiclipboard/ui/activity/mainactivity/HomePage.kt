package com.oxyethylene.emojiclipboard.ui.activity.mainactivity

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
import com.kongzue.dialogx.dialogs.PopNotification
import com.oxyethylene.emojiclipboard.R

@Composable
fun HomeFragment() {

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
                        PopNotification.build().setMessage("敬请期待").show()
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
