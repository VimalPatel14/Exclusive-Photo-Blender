package com.photovideoeditormaker.exclusivephotoblender.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.photovideoeditormaker.exclusivephotoblender.R;

public class ExclusiveBlender_FontAdapter extends BaseAdapter {
    private Context context;
    private final String[] mobileValues;

    static class RecordHolder {
       
        TextView img_grid_item;
        Typeface f4531b;

        RecordHolder() {
        }
    }

    public ExclusiveBlender_FontAdapter(Context context, String[] strArr) {
        context = context;
        mobileValues = strArr;
    }

    public int getCount() {
        return mobileValues.length;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        RecordHolder recordHolder;
        if (view == null) {
            view = ((Activity) context).getLayoutInflater().inflate(R.layout.listitem_fontstyle, viewGroup, false);
            RecordHolder recordHolder2 = new RecordHolder();
            recordHolder2.img_grid_item =  view.findViewById(R.id.img_grid_item);
            view.setTag(recordHolder2);
            recordHolder = recordHolder2;
        } else {
            recordHolder = (RecordHolder) view.getTag();
        }
        recordHolder.f4531b = Typeface.createFromAsset(context.getAssets(), mobileValues[i]);
        recordHolder.img_grid_item.setTypeface(recordHolder.f4531b);
        return view;
    }
}
