package com.example.ytz.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 叶 on 2016/11/18.
 */
public class CommonViewHolder {

    //保存控件的map集合
    private Map<Integer, View> cacheView = new HashMap<Integer, View>();
    public final View convertView;

    private CommonViewHolder(View convertView) {
        this.convertView = convertView;
    }

    public static CommonViewHolder createCVH(View convertView, ViewGroup parent, int resId) {
        CommonViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), resId, null);
            //找到控件，封装到holder身上
            holder = new CommonViewHolder(convertView);
            //设置holder
            convertView.setTag(holder);
        }
        holder = (CommonViewHolder) convertView.getTag();
        return holder;
    }

    //提供一个存值的方法
    public void putView(int id, View view) {
        cacheView.put(id, view);
    }

    //提供一个获取值（View）的方法
    public View getView(int id) {
        if (cacheView.get(id) == null) {
            putView(id, convertView.findViewById(id));
        }
        return cacheView.get(id);
    }

    public TextView getTv(int id) {
        return (TextView) getView(id);
    }

    public ImageView getIv(int id) {
        return (ImageView) getView(id);
    }
}
