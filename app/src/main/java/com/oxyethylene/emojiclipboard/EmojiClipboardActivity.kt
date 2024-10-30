package com.oxyethylene.emojiclipboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.google.android.material.color.DynamicColors
import com.kongzue.dialogx.DialogX
import com.oxyethylene.emojiclipboard.application.App
import com.oxyethylene.emojiclipboard.domain.objects.AppSettings
import com.oxyethylene.emojiclipboard.ui.activity.emojiclipboardactivity.ClipBoardPage
import com.oxyethylene.emojiclipboard.ui.theme.EmojiClipboardTheme

class EmojiClipboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // 标题
        var title = "未知分类"
        intent.getStringExtra("subgroup_name")?.let { title = it }
        
        setContent {
            EmojiClipboardTheme {

                val darkTheme = isSystemInDarkTheme()

                if (darkTheme) {
                    DialogX.globalTheme = DialogX.THEME.DARK
                } else {
                    DialogX.globalTheme = DialogX.THEME.LIGHT
                }

                ClipBoardPage(subGroupName = title)
                
            }
        }
    }
}
