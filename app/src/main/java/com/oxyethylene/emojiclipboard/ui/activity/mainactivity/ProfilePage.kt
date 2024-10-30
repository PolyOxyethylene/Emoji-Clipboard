package com.oxyethylene.emojiclipboard.ui.activity.mainactivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.emojiclipboard.R
import com.oxyethylene.emojiclipboard.domain.objects.AppSettings
import com.oxyethylene.emojiclipboard.ui.components.SettingItem
import com.oxyethylene.emojiclipboard.ui.components.SettingSubList

@Composable
fun ProfilePage() {

    val context = LocalContext.current

    val res = context.resources

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item { MainTopBar(title = res.getString(R.string.profile_page_title)) }

        // 应用信息
        item { ProfileCard() }

        // 设置列表
        item {
            AppSettings.settings.forEach { settingSubList ->
                SettingSubList(title = settingSubList.first, settingList = settingSubList.second)
            }
        }

    }

}

/**
 * 应用信息
 * @param res 应用的 Resources 实例
 */
@Composable
fun ProfileCard() {

    val context = LocalContext.current

    val res= context.resources

    val versionCode = context.packageManager.getPackageInfo(context.packageName, 0).versionName

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 30.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.avator),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .wrapContentHeight()
                    .padding(vertical = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = res.getString(R.string.profile_developer),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = res.getString(R.string.profile_github),
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier.padding(top = 4.dp)
                )

            }

        }

    }


}