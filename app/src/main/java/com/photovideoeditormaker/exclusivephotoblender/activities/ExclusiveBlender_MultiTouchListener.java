package com.photovideoeditormaker.exclusivephotoblender.activities;

import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.photovideoeditormaker.exclusivephotoblender.view.ExclusiveBlender_ScaleGestureDetector;
import com.photovideoeditormaker.exclusivephotoblender.view.ExclusiveBlender_ScaleGestureDetector.SimpleOnScaleGestureListener;
import com.photovideoeditormaker.exclusivephotoblender.view.ExclusiveBlender_Vector2D;

class ExclusiveBlender_MultiTouchListener implements OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    public boolean isRotateEnabled = true;
    public boolean isScaleEnabled = true;
    public boolean isTranslateEnabled = true;
    private int mActivePointerId = -1;
    private float mPrevX;
    private float mPrevY;
    private ExclusiveBlender_ScaleGestureDetector mScaleGestureDetector = new ExclusiveBlender_ScaleGestureDetector(new ScaleGestureListener());
    public float maximumScale = 10.0f;
    public float minimumScale = 0.1f;

    private class ScaleGestureListener extends SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private ExclusiveBlender_Vector2D mPrevSpanVector;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new ExclusiveBlender_Vector2D(0.0f,0.0f);
        }

        public boolean onScale(View view, ExclusiveBlender_ScaleGestureDetector exclusiveBlender_ScaleGestureDetector) {
            float f = 0.0f;
            TransformInfo transformInfo = new TransformInfo();
            transformInfo.deltaScale = ExclusiveBlender_MultiTouchListener.this.isScaleEnabled ? exclusiveBlender_ScaleGestureDetector.getScaleFactor() : 1.0f;
            transformInfo.deltaAngle = ExclusiveBlender_MultiTouchListener.this.isRotateEnabled ? ExclusiveBlender_Vector2D.getAngle(this.mPrevSpanVector, exclusiveBlender_ScaleGestureDetector.getCurrentSpanVector()) : 0.0f;
            transformInfo.deltaX = ExclusiveBlender_MultiTouchListener.this.isTranslateEnabled ? exclusiveBlender_ScaleGestureDetector.getFocusX() - this.mPivotX : 0.0f;
            if (ExclusiveBlender_MultiTouchListener.this.isTranslateEnabled) {
                f = exclusiveBlender_ScaleGestureDetector.getFocusY() - this.mPivotY;
            }
            transformInfo.deltaY = f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = ExclusiveBlender_MultiTouchListener.this.minimumScale;
            transformInfo.maximumScale = ExclusiveBlender_MultiTouchListener.this.maximumScale;
            ExclusiveBlender_MultiTouchListener.move(view, transformInfo);
            return false;
        }

        public boolean onScaleBegin(View view, ExclusiveBlender_ScaleGestureDetector exclusiveBlender_ScaleGestureDetector) {
            this.mPivotX = exclusiveBlender_ScaleGestureDetector.getFocusX();
            this.mPivotY = exclusiveBlender_ScaleGestureDetector.getFocusY();
            this.mPrevSpanVector.set(exclusiveBlender_ScaleGestureDetector.getCurrentSpanVector());
            return true;
        }
    }

    private class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }

    ExclusiveBlender_MultiTouchListener() {
    }

    private static float adjustAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    private static void adjustTranslation(View view, float f, float f2) {
        float[] fArr = new float[]{f, f2};
        view.getMatrix().mapVectors(fArr);
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(fArr[1] + view.getTranslationY());
    }

    private static void computeRenderOffset(View view, float f, float f2) {
        if (view.getPivotX() != f || view.getPivotY() != f2) {
            float[] fArr = new float[]{0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr);
            view.setPivotX(f);
            view.setPivotY(f2);
            float[] fArr2 = new float[]{0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr2);
            float f3 = fArr2[1] - fArr[1];
            view.setTranslationX(view.getTranslationX() - (fArr2[0] - fArr[0]));
            view.setTranslationY(view.getTranslationY() - f3);
        }
    }

    private static void move(View view, TransformInfo transformInfo) {
        computeRenderOffset(view, transformInfo.pivotX, transformInfo.pivotY);
        adjustTranslation(view, transformInfo.deltaX, transformInfo.deltaY);
        float max = Math.max(transformInfo.minimumScale, Math.min(transformInfo.maximumScale, view.getScaleX() * transformInfo.deltaScale));
        view.setScaleX(max);
        view.setScaleY(max);
        view.setRotation(adjustAngle(view.getRotation() + transformInfo.deltaAngle));
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mScaleGestureDetector.onTouchEvent(view, motionEvent);
        if (this.isTranslateEnabled) {
            view.bringToFront();
            RelativeLayout relativeLayout = (RelativeLayout) view;
            ImageView imageView = (ImageView) relativeLayout.getChildAt(1);
            ExclusiveBlender_ExposureActivity.f4432m = imageView;
            ImageView imageView2 = (ImageView) relativeLayout.getChildAt(0);
            if (VERSION.SDK_INT <= 16) {
                ExclusiveBlender_ExposureActivity.seek.setProgress(ExclusiveBlender_ExposureActivity.seek.getMax() - ((int) ExclusiveBlender_ExposureActivity.f4432m.getAlpha()));
            } else {
                ExclusiveBlender_ExposureActivity.seek.setProgress(ExclusiveBlender_ExposureActivity.seek.getMax() - ExclusiveBlender_ExposureActivity.f4432m.getImageAlpha());
            }
            ExclusiveBlender_ExposureActivity.f4433n = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();
            ExclusiveBlender_ExposureActivity.sideblur_seek.setProgress(Integer.parseInt((String) imageView.getContentDescription()));
            ExclusiveBlender_ExposureActivity.delete.setVisibility(View.VISIBLE);
            ExclusiveBlender_ExposureActivity.seek_rel.setVisibility(View.VISIBLE);
            ExclusiveBlender_ExposureActivity.f4427h = Boolean.valueOf(false);
            ExclusiveBlender_ExposureActivity.f4422c = Boolean.valueOf(true);
            if (ExclusiveBlender_ExposureActivity.f4429j.getInt("tut", 0) == 2) {
                Editor edit = ExclusiveBlender_ExposureActivity.f4429j.edit();
                edit.putInt("tut", 3);
                edit.commit();
            }
            int action = motionEvent.getAction();
            switch (motionEvent.getActionMasked() & action) {
                case 0:
                    this.mPrevX = motionEvent.getX();
                    this.mPrevY = motionEvent.getY();
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    break;
                case 1:
                    this.mActivePointerId = -1;
                    break;
                case 2:
                    action = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (action != -1) {
                        float x = motionEvent.getX(action);
                        float y = motionEvent.getY(action);
                        if (!this.mScaleGestureDetector.isInProgress()) {
                            adjustTranslation(view, x - this.mPrevX, y - this.mPrevY);
                            break;
                        }
                    }
                    break;
                case 3:
                    this.mActivePointerId = -1;
                    break;
                case 6:
                    action = (action & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                    if (motionEvent.getPointerId(action) == this.mActivePointerId) {
                        action = action == 0 ? 1 : 0;
                        this.mPrevX = motionEvent.getX(action);
                        this.mPrevY = motionEvent.getY(action);
                        this.mActivePointerId = motionEvent.getPointerId(action);
                        break;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }
}
