package com.gsww.baselibs.adapter.recycle;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类名：com.wzm.utilslib.wedgit
 * 时间：2018/1/23 10:08
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author wangzm
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context context;
    protected List<T> datas;
    private Map<String, Object> itemMap;

    public Map<String, Object> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<String, Object> itemMap) {
        this.itemMap = itemMap;
    }

    protected int layoutId;
    protected MultiItemTypeSupportListener<T> multiItemSupportListener;
    protected Typeface customFont;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        customFont = Typeface.createFromAsset(context.getAssets(), "heavy.ttf");
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    public CommonAdapter(Context context, List<T> datas, Map<String,Object> itemMap,int layoutId) {
        customFont = Typeface.createFromAsset(context.getAssets(), "heavy.ttf");
        this.context = context;
        this.itemMap = itemMap;
        this.datas = datas;
        this.layoutId = layoutId;
    }


    public CommonAdapter(Context context, int layoutId) {
        customFont = Typeface.createFromAsset(context.getAssets(), "heavy.ttf");
        this.context = context;
        this.datas = new ArrayList<>();
        this.layoutId = layoutId;
    }

    public void setMultiItemTypeSupportListener(MultiItemTypeSupportListener<T> multiItemSupportListener) {
        this.multiItemSupportListener = multiItemSupportListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (multiItemSupportListener != null) {
            return multiItemSupportListener.getItemViewType(position, (T) datas.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (multiItemSupportListener != null) {
            layoutId = multiItemSupportListener.getLayoutId(viewType);
        }
        ViewHolder viewHolder = ViewHolder.get(context, parent, layoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, position, datas.get(position));
    }

    public abstract void convert(ViewHolder holder, int position, T t);

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setList(List<T> datas) {
        this.datas.clear();
        if (datas != null) {
            this.datas.addAll(datas);
        }
        notifyDataSetChanged();
    }


    public void addList(List<T> datas) {
        if (datas != null) {
            this.datas.addAll(datas);
            notifyDataSetChanged();
        }
    }


    public List<T> getList() {
        return datas;
    }

    /**
     * 支持多布局的item接口
     *
     * @param <T>
     * @author zlp
     */
    public interface MultiItemTypeSupportListener<T> {
        int getItemViewType(int position, T t);

        int getLayoutId(int viewType);

    }


}
