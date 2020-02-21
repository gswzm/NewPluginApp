package com.gsww.baselibs.adapter.recycle;

/**
 * 类名：com.wzm.utilslib.wedgit
 * 时间：2018/1/23 11:24
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author wangzm
 */

public interface MultiTypeSupport<T> {
    /**
     * 根据不同的 itemtype 获取不同类型的布局文件
     * @param itemType
     * @return
     */
    int getLayoutId(int itemType);
    /**
     * 根据不同 position 的 bean 生成不同的的 item 布局
     * @param position
     * @param t 多种不同 item 传入的 bean 数据
     * @return
     */
    int getItemViewType(int position, T t);
}
