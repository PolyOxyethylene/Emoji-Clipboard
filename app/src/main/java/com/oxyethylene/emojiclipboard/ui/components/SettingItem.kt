package com.oxyethylene.emojiclipboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.emojiclipboard.domain.base.Setting
import com.oxyethylene.emojiclipboard.domain.model.setting.ActionSetting
import com.oxyethylene.emojiclipboard.domain.model.setting.EmptySetting
import com.oxyethylene.emojiclipboard.domain.model.setting.SwitchSetting

/**
 * 设置项的 UI，根据设置项的不同类型显示不同的控件
 * @param setting 设置项
 * @param modifier 设置控件的 margin
 */
@Composable
fun SettingItem(setting: Setting, modifier: Modifier = Modifier) {

    when(setting) {

        is SwitchSetting -> SwitchSettingItem(setting, modifier)

        is EmptySetting -> EmptySettingItem(setting, modifier)

        is ActionSetting -> ActionSettingItem(setting, modifier)

    }

}

/**
 * 设置列表，抬头显示一个列表的标题
 * @param title 列表的标题
 * @param settingList 设置列表
 */
@Composable
fun SettingSubList(title: String, settingList: Collection<Setting>) {

    Column (
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(top = 20.dp, bottom = 10.dp)
    ) {

        Text(
            text = title,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.surfaceTint,
            modifier = Modifier.padding(start = 20.dp)
        )

        settingList.forEach { setting ->
            SettingItem(setting = setting)
        }

    }

}

/**
 * switch 类型的设置项控件
 * @param setting 设置项
 * @param modifier 设置控件的 margin
 */
@Composable
fun SwitchSettingItem(setting: SwitchSetting, modifier: Modifier = Modifier) {

    var checked by remember { mutableStateOf(setting.value) }

    Row (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                checked = !checked
                setting.value = checked
                setting.onValueChanged(checked)
            }
            .padding(horizontal = 20.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column (
            modifier = Modifier
                .width(220.dp)
                .wrapContentHeight()
        ) {

            if (setting.description == null) {
                Spacer(modifier = Modifier.height(4.dp).background(Color.Transparent))
            }

            Text(
                text = setting.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            if (setting.description != null) {
                Text(
                    text = setting.description,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Justify,
                    lineHeight = 12.sp,
                    modifier = Modifier.padding(top = 6.dp, bottom = 16.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(20.dp).background(Color.Transparent))
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = { c ->
                checked = c
                setting.value = c
                setting.onValueChanged(c)
            },
            modifier = Modifier
                .scale(0.8f)
        )

    }

}

/**
 * 纯文本的设置项控件
 * @param setting 设置项
 * @param modifier 设置控件的 margin
 */
@Composable
fun EmptySettingItem(setting: EmptySetting, modifier: Modifier = Modifier) {

    Row (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(horizontal = 20.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column (
            modifier = Modifier
                .width(220.dp)
                .wrapContentHeight()
        ) {

            if (setting.description == null) {
                Spacer(modifier = Modifier.height(4.dp).background(Color.Transparent))
            }

            Text(
                text = setting.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            if (setting.description != null) {
                Text(
                    text = setting.description,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Justify,
                    lineHeight = 12.sp,
                    modifier = Modifier.padding(top = 6.dp, bottom = 16.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(20.dp).background(Color.Transparent))
            }

        }

    }

}

/**
 * switch 类型的设置项控件
 * @param setting 设置项
 * @param modifier 设置控件的 margin
 */
@Composable
fun ActionSettingItem(setting: ActionSetting, modifier: Modifier = Modifier) {

    Row (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                setting.action()
            }
            .padding(horizontal = 20.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column (
            modifier = Modifier
                .width(220.dp)
                .wrapContentHeight()
        ) {

            if (setting.description == null) {
                Spacer(modifier = Modifier.height(4.dp).background(Color.Transparent))
            }

            Text(
                text = setting.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            if (setting.description != null) {
                Text(
                    text = setting.description,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Justify,
                    lineHeight = 12.sp,
                    modifier = Modifier.padding(top = 6.dp, bottom = 16.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(20.dp).background(Color.Transparent))
            }
        }

    }

}