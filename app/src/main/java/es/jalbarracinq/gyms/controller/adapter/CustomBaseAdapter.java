/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CustomBaseAdapter<T, VH> extends BaseAdapter {

    List<T> list;
    Activity activity;
    int itemResource;
    Class<VH> holderClass;

    public CustomBaseAdapter(Activity activity, List<T> list, int itemResource, Class<VH> holderClass) {
        this.activity = activity;
        this.list = list;
        this.itemResource = itemResource;
        this.holderClass = holderClass;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH holder = null;

        if (view == null) {
            view = LayoutInflater.from(activity).inflate(itemResource, viewGroup, false);
            try {
                holder = holderClass.getConstructor(View.class).newInstance(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
            view.setTag(holder);
        } else {
            holder = (VH) view.getTag();
        }

        onBindViewHolder(holder, i);
        return view;
    }

    public abstract void onBindViewHolder(VH holder, int position);

}
