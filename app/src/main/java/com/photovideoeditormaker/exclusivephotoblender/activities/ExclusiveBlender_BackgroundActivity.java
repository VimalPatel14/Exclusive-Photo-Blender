package com.photovideoeditormaker.exclusivephotoblender.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.ads.AdSize;
import com.photovideoeditormaker.exclusivephotoblender.R;

public class ExclusiveBlender_BackgroundActivity extends Activity {
    public static int collage_id = 2;
    public static int[] myImages = new int[]{R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5, R.drawable.bg_6, R.drawable.bg_7, R.drawable.bg_8, R.drawable.bg_9, R.drawable.bg_10, R.drawable.bg_11, R.drawable.bg_12, R.drawable.bg_13, R.drawable.bg_14, R.drawable.bg_15};
    private Adapter mAdapter;
    private LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    ImageView startover;
    private com.facebook.ads.AdView adView;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.cardview_frames);
        initComponents();
        showfbbanner();
    }

    private void initComponents() {
        mRecyclerView =  findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter( myImages);
        mRecyclerView.setAdapter(mAdapter);

        startover = findViewById(R.id.startover);
        startover.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showfbbanner() {
        adView = new com.facebook.ads.AdView(this, getString(R.string.banner_ad_fb), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
    }

    public void nextactivity() {
        finish();
    }

    public class MyAdapter extends Adapter<MyAdapter.ViewHolder> {
        private int[] mImages;

        public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {
            public ImageView mImageView;

            public ViewHolder(View view) {
                super(view);

                mImageView =  view.findViewById(R.id.img);
                view.setOnClickListener(this);
            }

            public void onClick(View view) {
                ExclusiveBlender_BackgroundActivity.collage_id = getPosition();
                nextactivity();
            }
        }

        public MyAdapter( int[] iArr) {
            mImages = iArr;
        }

        public int getItemCount() {
            return mImages.length;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.mImageView.setImageResource(mImages[i]);
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_adapter, viewGroup, false));
        }
    }
}
