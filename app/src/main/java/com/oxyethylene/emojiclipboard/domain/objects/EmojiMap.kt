package com.oxyethylene.emojiclipboard.domain.objects

import android.content.Context
import com.oxyethylene.emojiclipboard.domain.base.Thumbnail
import com.oxyethylene.emojiclipboard.domain.model.EGroup
import com.oxyethylene.emojiclipboard.domain.model.ESubGroup
import com.oxyethylene.emojiclipboard.domain.model.EmojiThumbnail
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Hashtable
import java.util.TreeMap

/**
 * 从文件中构建的一整个 emoji 集合，层级分为：分组(Group) → 子分组(Sub Group) → Emoji
 * @property applicationContext 用于获取 AssetManager 对象
 * @property emojiGroups 用于保存初始化的所有 emoji
 * @property favorites 收藏夹
 * @property emojiNameMap 保存所有 emoji 名字，关联其所属的分组和子分组名字
 * @property groupNameMap 保存 Group 的名字
 * @property subGroupNameMap 保存 Sub Group 的名字
 */
@ActivityScoped
class EmojiMap @Inject constructor(@ApplicationContext val applicationContext: Context) {

    // 嵌套地狱
    private val emojiGroups: TreeMap<String, EGroup> = TreeMap()

    // 保存所有 emoji 名字，关联其所属的分组和子分组名字
    val emojiNameMap = Hashtable<String, Pair<String, String>>()

    val favorites = ArrayList<EmojiThumbnail>()

    // 保存 Group 的名字
    val groupNameSet = HashSet<String>()

    // 保存 Sub Group 的名字
    val subGroupNameSet = HashSet<String>()

    init {

        // 读取 emoji 规范化文件
        // TODO 这里暂时固定为中文
        val manager = applicationContext.resources.assets

        println(AppSettings.favorites.size)

        // 初始化收藏夹
        AppSettings.favorites.forEach { entry ->
            favorites.add(EmojiThumbnail(name = entry.value, content = entry.key))
        }

        CoroutineScope(Dispatchers.IO).launch {

            var reader: BufferedReader? = null

            var index = 0

            var line = ""

            try {
                reader = BufferedReader(InputStreamReader(manager.open("emojis/emoji_normalized_zh_cn.txt")))

                line = reader.readLine()

                // 记录当前访问的组别和子分组
                var currentGroup: EGroup? = null
                var currentSubGroup: ESubGroup? = null

                // 循环读取文件内容，初始化整个 map
                do {

                    // 以 “g” 开头，说明遇到了新的一个组别
                    if (line.startsWith("g")) {
                        val groupNameIndex = line.indexOf("g") + 2
                        val groupName = line.substring(groupNameIndex).trim()
                        // 初始化一个新的 group
                        currentGroup = EGroup(groupName = groupName, itemGroupPosition = index, itemPosition = index)
                        emojiGroups[groupName] = currentGroup
                        // 保存记录
                        groupNameSet.add(currentGroup.groupName)
                        index++
                    }
                    // “sg” 开头，是一个子分组
                    else if (line.startsWith("sg")) {
                        val subGroupNameIndex = line.indexOf("sg") + 3
                        val subGroupName = line.substring(subGroupNameIndex).trim()
                        // 初始化一个新的 subgroup map
                        currentSubGroup = ESubGroup(subGroupName)
                        currentGroup!!.addSubGroup(currentSubGroup)
                        // 保存记录
                        subGroupNameSet.add(currentSubGroup.subGroupName)
                    }
                    // 非空行，按格式来说一定是 emoji 行
                    else if (line.isNotBlank()) {
                        // 提取 emoji 要素
                        val separatorIndex = line.indexOf("=")
                        val emojiText = line.substring(0, separatorIndex)
                        val emojiName = line.substring(separatorIndex + 1)

                        currentSubGroup!!.addEmoji(emojiName, emojiText)

                        // 保存记录
                        emojiNameMap[emojiName] = Pair(currentGroup!!.groupName, currentSubGroup.subGroupName)
                    }

                    // 继续读下一行，按格式来编写文件，这里可能会遇到结束行 “EOF”，条件判断复核后退出循环
                    line = reader.readLine()
                } while (!line.equals("EOF"))

            } catch (ex: Exception) {
                ex.printStackTrace()
                println(line)
            } finally {
                reader?.close()
            }

        }

    }

    /**
     * 获取整个列表
     */
    fun getGroups() = emojiGroups.values.toList()

    fun notifyFavoritesInsert(newEmoji: EmojiThumbnail) {
        favorites.add(newEmoji)
    }

    fun notifyFavoritesDelete(index: Int) {
        favorites.removeAt(index)
    }

}