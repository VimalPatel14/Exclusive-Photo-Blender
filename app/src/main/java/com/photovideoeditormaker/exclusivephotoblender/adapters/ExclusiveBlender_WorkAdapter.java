package com.photovideoeditormaker.exclusivephotoblender.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.photovideoeditormaker.exclusivephotoblender.activities.ExclusiveBlender_ShareImageActivity;
import com.photovideoeditormaker.exclusivephotoblender.R;

import java.util.ArrayList;

public class ExclusiveBlender_WorkAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    /* renamed from: a */
    SparseBooleanArray f4545a;
    /* renamed from: b */
    ArrayList<String> f4546b = new ArrayList();
    private Activity dactivity;
    private int imageSize;

    static class ViewHolder {
        /* renamed from: a */
        ImageView f4544a;

        ViewHolder() {
        }
    }

    public ExclusiveBlender_WorkAdapter(Activity activity, ArrayList<String> arrayList) {
        this.dactivity = activity;
        this.f4546b = arrayList;
        inflater = (LayoutInflater) this.dactivity.getSystemService("layout_inflater");
        this.f4545a = new SparseBooleanArray(this.f4546b.size());
    }


    public int getCount() {
        return this.f4546b.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        int pos = i;
        ViewHolder viewHolder;
        int i2 = this.dactivity.getResources().getDisplayMetrics().widthPixels;
        if (view == null) {
            view = LayoutInflater.from(this.dactivity).inflate(R.layout.list_gallary, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.f4544a = (ImageView) view.findViewById(R.id.imgIcon);
            viewHolder.f4544a.setOnClickListener(new OnClickListener() {

                public void onClick(View view) {

                    Intent intent = new Intent(dactivity, ExclusiveBlender_ShareImageActivity.class);
                    intent.putExtra("imgPath", f4546b.get(pos));
                    dactivity.startActivity(intent);
                }
            });
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(this.dactivity).load((String) this.f4546b.get(i)).into(viewHolder.f4544a);
        System.gc();
        return view;
    }
}
