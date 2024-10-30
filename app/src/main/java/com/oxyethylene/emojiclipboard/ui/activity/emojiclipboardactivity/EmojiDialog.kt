package com.oxyethylene.emojiclipboard.ui.activity.emojiclipboardactivity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.oxyethylene.emojiclipboard.application.App

@Composable
fun EmojiDialog (
    message: String,
    onDismissRequest: () -> Unit
) {

    val manager = App.getInstance().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    val emoji = message.substring(0, message.indexOf(' '))

    val name = message.substring(message.indexOf(' ') + 1)

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {

        Column (
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = emoji,
                fontSize = 30.sp,
                modifier = Modifier.padding(top = 20.dp)
            )

            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )

            Row (
                modifier = Modifier
                    .padding(top = 30.dp)
                    .wrapContentSize()
            ) {

                Button(
                    onClick = {
                        val newClip = ClipData.newPlainText(" ", message.subSequence(0, message.indexOf(' ')))
                        manager.setPrimaryClip(newClip)
                        PopNotification.build().setMessage("\"$message\" 已复制到剪贴板").show()
                        onDismissRequest()
                    },
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Text(text = "复制")
                }

                Button(
                    onClick = {
                        PopNotification.build().setMessage("敬请期待").show()
                        onDismissRequest()
                    },
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Text(text = "收藏")
                }

            }

        }

    }
}