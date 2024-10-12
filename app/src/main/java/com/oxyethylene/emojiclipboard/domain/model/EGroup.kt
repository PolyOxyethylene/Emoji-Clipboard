package com.oxyethylene.emojiclipboard.domain.model

import com.drake.brv.item.ItemExpand
import com.drake.brv.item.ItemHover
import com.drake.brv.item.ItemPosition
import java.util.TreeMap

/**
 * emoji 的一级组别
 * @property groupName 组别名称
 * @property itemExpand 子列表是否展开，默认 false
 * @property itemHover 主分组是否使用粘性标题，默认 true
 * @property itemGroupPosition 同级别的分组的索引位置
 * @property itemPosition 分组索引位置
 * @property subGroups 存放子组别
 */
class EGroup(
    val groupName: String,
    override var itemExpand: Boolean = false,
    override var itemHover: Boolean = true,
    override var itemGroupPosition: Int,
    override var itemPosition: Int
): ItemExpand, ItemHover, ItemPosition {

    private val subGroups = TreeMap<String, ESubGroup>()

    /**
     * 添加一个子组别
     * @param subGroup 子组别
     */
    fun addSubGroup (subGroup: ESubGroup) {
        subGroups[subGroup.subGroupName] = subGroup
    }

    /**
     * 获取子组别列表
     */
    override fun getItemSublist(): List<Any?> {
        return subGroups.values.toList()
    }

}