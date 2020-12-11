package com.photovideoeditormaker.exclusivephotoblender.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.photovideoeditormaker.exclusivephotoblender.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExclusiveBlender_StickerAdapter extends BaseAdapter {

    Context context;
    ArrayList<Integer> f4556b;
    ImageView img_editing;
    private LayoutInflater inflater;
    private ViewHolder viewholder;

    public class ViewHolder {
    }

    public ExclusiveBlender_StickerAdapter(Context context, ArrayList<Integer> arrayList) {
        context = context;
        f4556b = arrayList;
        inflater = ((LayoutInflater) context.getSystemService("layout_inflater"));
    }

    public int getCount() {
        return f4556b.size();
    }

    public Object getItem(int i) {
        return f4556b.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.sticker_item, null);
        }
        img_editing = view.findViewById(R.id.img_editing);
        Picasso.get().load(((Integer) f4556b.get(i)).intValue()).into(img_editing);
        System.gc();
        return view;
    }
}
