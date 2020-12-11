package com.photovideoeditormaker.exclusivephotoblender.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.Scroller;

public class ExclusiveBlender_TouchImageView extends ImageView {
    private static final String DEBUG = "DEBUG";
    private static final float SUPER_MAX_MULTIPLIER = 1.25f;
    private static final float SUPER_MIN_MULTIPLIER = 0.75f;
    private Context context;
    private ZoomVariables delayedZoomVariables;
    private OnDoubleTapListener doubleTapListener = null;
    private Fling fling;
    private boolean imageRenderedAtLeastOnce;
    private float[] f4963m;
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleDetector;
    private ScaleType mScaleType;
    private float matchViewHeight;
    private float matchViewWidth;
    private Matrix matrix;
    private float maxScale;
    private float minScale;
    private float normalizedScale;
    private boolean onDrawReady;
    private float prevMatchViewHeight;
    private float prevMatchViewWidth;
    private Matrix prevMatrix;
    private int prevViewHeight;
    private int prevViewWidth;
    private State state;
    private float superMaxScale;
    private float superMinScale;
    private OnTouchImageViewListener touchImageViewListener = null;
    private OnTouchListener userTouchListener = null;
    private int viewHeight;
    private int viewWidth;

    /* renamed from: com.photolab.multiblender.view.TouchImageView$1 */
    static /* synthetic */ class C10711 {
        /* renamed from: a */
        static final /* synthetic */ int[] f4949a = new int[ScaleType.values().length];

        static {
            try {
                f4949a[ScaleType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f4949a[ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f4949a[ScaleType.CENTER_INSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f4949a[ScaleType.FIT_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f4949a[ScaleType.FIT_XY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private class CompatScroller {
        /* renamed from: a */
        Scroller f4950a;
        /* renamed from: b */
        OverScroller f4951b;
        /* renamed from: c */
        boolean f4952c;

        public CompatScroller(Context context) {
            if (VERSION.SDK_INT < 9) {
                this.f4952c = true;
                this.f4950a = new Scroller(context);
                return;
            }
            this.f4952c = false;
            this.f4951b = new OverScroller(context);
        }

        public boolean computeScrollOffset() {
            if (this.f4952c) {
                return this.f4950a.computeScrollOffset();
            }
            this.f4951b.computeScrollOffset();
            return this.f4951b.computeScrollOffset();
        }

        public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (this.f4952c) {
                this.f4950a.fling(i, i2, i3, i4, i5, i6, i7, i8);
            } else {
                this.f4951b.fling(i, i2, i3, i4, i5, i6, i7, i8);
            }
        }

        public void forceFinished(boolean z) {
            if (this.f4952c) {
                this.f4950a.forceFinished(z);
            } else {
                this.f4951b.forceFinished(z);
            }
        }

        public int getCurrX() {
            return this.f4952c ? this.f4950a.getCurrX() : this.f4951b.getCurrX();
        }

        public int getCurrY() {
            return this.f4952c ? this.f4950a.getCurrY() : this.f4951b.getCurrY();
        }

        public boolean isFinished() {
            return this.f4952c ? this.f4950a.isFinished() : this.f4951b.isFinished();
        }
    }

    private class DoubleTapZoom implements Runnable {
        private static final float ZOOM_TIME = 500.0f;
        private float bitmapX;
        private float bitmapY;
        private PointF endTouch;
        private AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        private long startTime;
        private PointF startTouch;
        private float startZoom;
        private boolean stretchImageToSuper;
        private float targetZoom;

        DoubleTapZoom(float f, float f2, float f3, boolean z) {
            ExclusiveBlender_TouchImageView.this.setState(State.ANIMATE_ZOOM);
            this.startTime = System.currentTimeMillis();
            this.startZoom = ExclusiveBlender_TouchImageView.this.normalizedScale;
            this.targetZoom = f;
            this.stretchImageToSuper = z;
            PointF a = ExclusiveBlender_TouchImageView.this.transformCoordTouchToBitmap(f2, f3, false);
            this.bitmapX = a.x;
            this.bitmapY = a.y;
            this.startTouch = ExclusiveBlender_TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            this.endTouch = new PointF((float) (ExclusiveBlender_TouchImageView.this.viewWidth / 2), (float) (ExclusiveBlender_TouchImageView.this.viewHeight / 2));
        }

        private double calculateDeltaScale(float f) {
            return ((double) (this.startZoom + ((this.targetZoom - this.startZoom) * f))) / ((double) ExclusiveBlender_TouchImageView.this.normalizedScale);
        }

        private float interpolate() {
            return this.interpolator.getInterpolation(Math.min(1.0f, ((float) (System.currentTimeMillis() - this.startTime)) / ZOOM_TIME));
        }

        private void translateImageToCenterTouchPosition(float f) {
            float f2 = this.startTouch.x + ((this.endTouch.x - this.startTouch.x) * f);
            float f3 = this.startTouch.y + ((this.endTouch.y - this.startTouch.y) * f);
            PointF a = ExclusiveBlender_TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            ExclusiveBlender_TouchImageView.this.matrix.postTranslate(f2 - a.x, f3 - a.y);
        }

        public void run() {
            float interpolate = interpolate();
            ExclusiveBlender_TouchImageView.this.scaleImage(calculateDeltaScale(interpolate), this.bitmapX, this.bitmapY, this.stretchImageToSuper);
            translateImageToCenterTouchPosition(interpolate);
            ExclusiveBlender_TouchImageView.this.fixScaleTrans();
            ExclusiveBlender_TouchImageView.this.setImageMatrix(ExclusiveBlender_TouchImageView.this.matrix);
            if (ExclusiveBlender_TouchImageView.this.touchImageViewListener != null) {
                ExclusiveBlender_TouchImageView.this.touchImageViewListener.onMove();
            }
            if (interpolate < 1.0f) {
                ExclusiveBlender_TouchImageView.this.compatPostOnAnimation(this);
            } else {
                ExclusiveBlender_TouchImageView.this.setState(State.NONE);
            }
        }
    }

    private class Fling implements Runnable {
        /* renamed from: a */
        CompatScroller f4955a;
        /* renamed from: b */
        int f4956b;
        /* renamed from: c */
        int f4957c;

        Fling(int i, int i2) {
            int i3;
            int i4;
            int k;
            int i5;
            ExclusiveBlender_TouchImageView.this.setState(State.FLING);
            this.f4955a = new CompatScroller(ExclusiveBlender_TouchImageView.this.context);
            ExclusiveBlender_TouchImageView.this.matrix.getValues(ExclusiveBlender_TouchImageView.this.f4963m);
            int i6 = (int) ExclusiveBlender_TouchImageView.this.f4963m[2];
            int i7 = (int) ExclusiveBlender_TouchImageView.this.f4963m[5];
            if (ExclusiveBlender_TouchImageView.this.getImageWidth() > ((float) ExclusiveBlender_TouchImageView.this.viewWidth)) {
                i3 = ExclusiveBlender_TouchImageView.this.viewWidth - ((int) ExclusiveBlender_TouchImageView.this.getImageWidth());
                i4 = 0;
            } else {
                i4 = i6;
                i3 = i6;
            }
            if (ExclusiveBlender_TouchImageView.this.getImageHeight() > ((float) ExclusiveBlender_TouchImageView.this.viewHeight)) {
                k = ExclusiveBlender_TouchImageView.this.viewHeight - ((int) ExclusiveBlender_TouchImageView.this.getImageHeight());
                i5 = 0;
            } else {
                i5 = i7;
                k = i7;
            }
            this.f4955a.fling(i6, i7, i, i2, i3, i4, k, i5);
            this.f4956b = i6;
            this.f4957c = i7;
        }

        public void cancelFling() {
            if (this.f4955a != null) {
                ExclusiveBlender_TouchImageView.this.setState(State.NONE);
                this.f4955a.forceFinished(true);
            }
        }

        public void run() {
            if (ExclusiveBlender_TouchImageView.this.touchImageViewListener != null) {
                ExclusiveBlender_TouchImageView.this.touchImageViewListener.onMove();
            }
            if (this.f4955a.isFinished()) {
                this.f4955a = null;
            } else if (this.f4955a.computeScrollOffset()) {
                int currX = this.f4955a.getCurrX();
                int currY = this.f4955a.getCurrY();
                int i = currX - this.f4956b;
                int i2 = currY - this.f4957c;
                this.f4956b = currX;
                this.f4957c = currY;
                ExclusiveBlender_TouchImageView.this.matrix.postTranslate((float) i, (float) i2);
                ExclusiveBlender_TouchImageView.this.fixTrans();
                ExclusiveBlender_TouchImageView.this.setImageMatrix(ExclusiveBlender_TouchImageView.this.matrix);
                ExclusiveBlender_TouchImageView.this.compatPostOnAnimation(this);
            }
        }
    }

    private class GestureListener extends SimpleOnGestureListener {
        private GestureListener() {
        }

        /* synthetic */ GestureListener(ExclusiveBlender_TouchImageView exclusiveBlenderTouchImageView, C10711 c10711) {
            this();
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            boolean onDoubleTap = ExclusiveBlender_TouchImageView.this.doubleTapListener != null ? ExclusiveBlender_TouchImageView.this.doubleTapListener.onDoubleTap(motionEvent) : false;
            if (ExclusiveBlender_TouchImageView.this.state != State.NONE) {
                return onDoubleTap;
            }
            ExclusiveBlender_TouchImageView.this.compatPostOnAnimation(new DoubleTapZoom(ExclusiveBlender_TouchImageView.this.normalizedScale == ExclusiveBlender_TouchImageView.this.minScale ? ExclusiveBlender_TouchImageView.this.maxScale : ExclusiveBlender_TouchImageView.this.minScale, motionEvent.getX(), motionEvent.getY(), false));
            return true;
        }

        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return ExclusiveBlender_TouchImageView.this.doubleTapListener != null ? ExclusiveBlender_TouchImageView.this.doubleTapListener.onDoubleTapEvent(motionEvent) : false;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (ExclusiveBlender_TouchImageView.this.fling != null) {
                ExclusiveBlender_TouchImageView.this.fling.cancelFling();
            }
            ExclusiveBlender_TouchImageView.this.fling = new Fling((int) f, (int) f2);
            ExclusiveBlender_TouchImageView.this.compatPostOnAnimation(ExclusiveBlender_TouchImageView.this.fling);
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }

        public void onLongPress(MotionEvent motionEvent) {
            ExclusiveBlender_TouchImageView.this.performLongClick();
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return ExclusiveBlender_TouchImageView.this.doubleTapListener != null ? ExclusiveBlender_TouchImageView.this.doubleTapListener.onSingleTapConfirmed(motionEvent) : ExclusiveBlender_TouchImageView.this.performClick();
        }
    }

    public interface OnTouchImageViewListener {
        void onMove();
    }

    private class PrivateOnTouchListener implements OnTouchListener {
        private PointF last;

        private PrivateOnTouchListener() {
            this.last = new PointF();
        }

        /* synthetic */ PrivateOnTouchListener(ExclusiveBlender_TouchImageView exclusiveBlenderTouchImageView, C10711 c10711) {
            this();
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            ExclusiveBlender_TouchImageView.this.mScaleDetector.onTouchEvent(motionEvent);
            ExclusiveBlender_TouchImageView.this.mGestureDetector.onTouchEvent(motionEvent);
            PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
            if (ExclusiveBlender_TouchImageView.this.state == State.NONE || ExclusiveBlender_TouchImageView.this.state == State.DRAG || ExclusiveBlender_TouchImageView.this.state == State.FLING) {
                switch (motionEvent.getAction()) {
                    case 0:
                        this.last.set(pointF);
                        if (ExclusiveBlender_TouchImageView.this.fling != null) {
                            ExclusiveBlender_TouchImageView.this.fling.cancelFling();
                        }
                        ExclusiveBlender_TouchImageView.this.setState(State.DRAG);
                        break;
                    case 1:
                    case 6:
                        ExclusiveBlender_TouchImageView.this.setState(State.NONE);
                        break;
                    case 2:
                        if (ExclusiveBlender_TouchImageView.this.state == State.DRAG) {
                            float f = pointF.y - this.last.y;
                            ExclusiveBlender_TouchImageView.this.matrix.postTranslate(ExclusiveBlender_TouchImageView.this.getFixDragTrans(pointF.x - this.last.x, (float) ExclusiveBlender_TouchImageView.this.viewWidth, ExclusiveBlender_TouchImageView.this.getImageWidth()), ExclusiveBlender_TouchImageView.this.getFixDragTrans(f, (float) ExclusiveBlender_TouchImageView.this.viewHeight, ExclusiveBlender_TouchImageView.this.getImageHeight()));
                            ExclusiveBlender_TouchImageView.this.fixTrans();
                            this.last.set(pointF.x, pointF.y);
                            break;
                        }
                        break;
                }
            }
            ExclusiveBlender_TouchImageView.this.setImageMatrix(ExclusiveBlender_TouchImageView.this.matrix);
            if (ExclusiveBlender_TouchImageView.this.userTouchListener != null) {
                ExclusiveBlender_TouchImageView.this.userTouchListener.onTouch(view, motionEvent);
            }
            if (ExclusiveBlender_TouchImageView.this.touchImageViewListener != null) {
                ExclusiveBlender_TouchImageView.this.touchImageViewListener.onMove();
            }
            return true;
        }
    }

    private class ScaleListener extends SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        /* synthetic */ ScaleListener(ExclusiveBlender_TouchImageView exclusiveBlenderTouchImageView, C10711 c10711) {
            this();
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            ExclusiveBlender_TouchImageView.this.scaleImage((double) scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), true);
            if (ExclusiveBlender_TouchImageView.this.touchImageViewListener != null) {
                ExclusiveBlender_TouchImageView.this.touchImageViewListener.onMove();
            }
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            ExclusiveBlender_TouchImageView.this.setState(State.ZOOM);
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            super.onScaleEnd(scaleGestureDetector);
            ExclusiveBlender_TouchImageView.this.setState(State.NONE);
            boolean z = false;
            float d = ExclusiveBlender_TouchImageView.this.normalizedScale;
            if (ExclusiveBlender_TouchImageView.this.normalizedScale > ExclusiveBlender_TouchImageView.this.maxScale) {
                d = ExclusiveBlender_TouchImageView.this.maxScale;
                z = true;
            } else if (ExclusiveBlender_TouchImageView.this.normalizedScale < ExclusiveBlender_TouchImageView.this.minScale) {
                d = ExclusiveBlender_TouchImageView.this.minScale;
                z = true;
            }
            if (z) {
                ExclusiveBlender_TouchImageView.this.compatPostOnAnimation(new DoubleTapZoom(d, (float) (ExclusiveBlender_TouchImageView.this.viewWidth / 2), (float) (ExclusiveBlender_TouchImageView.this.viewHeight / 2), true));
            }
        }
    }

    private enum State {
        NONE,
        DRAG,
        ZOOM,
        FLING,
        ANIMATE_ZOOM
    }

    private class ZoomVariables {
        public float focusX;
        public float focusY;
        public float scale;
        public ScaleType scaleType;

        public ZoomVariables(float f, float f2, float f3, ScaleType scaleType) {
            this.scale = f;
            this.focusX = f2;
            this.focusY = f3;
            this.scaleType = scaleType;
        }
    }

    public ExclusiveBlender_TouchImageView(Context context) {
        super(context);
        sharedConstructing(context);
    }

    public ExclusiveBlender_TouchImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        sharedConstructing(context);
    }

    public ExclusiveBlender_TouchImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        sharedConstructing(context);
    }

    private void compatPostOnAnimation(Runnable runnable) {
        if (VERSION.SDK_INT >= 16) {
            postOnAnimation(runnable);
        } else {
            postDelayed(runnable, 16);
        }
    }

    private void fitImageToView() {
        Drawable drawable = getDrawable();
        if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0 && this.matrix != null && this.prevMatrix != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            float f = ((float) this.viewWidth) / ((float) intrinsicWidth);
            float f2 = ((float) this.viewHeight) / ((float) intrinsicHeight);
            switch (C10711.f4949a[this.mScaleType.ordinal()]) {
                case 1:
                    f2 = 1.0f;
                    f = 1.0f;
                    break;
                case 2:
                    f2 = Math.max(f, f2);
                    f = f2;
                    break;
                case 3:
                    f2 = Math.min(1.0f, Math.min(f, f2));
                    f = f2;
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
            }
            f2 = Math.min(f, f2);
            f = f2;
            float f3 = ((float) this.viewWidth) - (((float) intrinsicWidth) * f);
            float f4 = ((float) this.viewHeight) - (((float) intrinsicHeight) * f2);
            this.matchViewWidth = ((float) this.viewWidth) - f3;
            this.matchViewHeight = ((float) this.viewHeight) - f4;
            if (isZoomed() || this.imageRenderedAtLeastOnce) {
                if (this.prevMatchViewWidth == 0.0f || this.prevMatchViewHeight == 0.0f) {
                    savePreviousImageValues();
                }
                this.prevMatrix.getValues(this.f4963m);
                this.f4963m[0] = (this.matchViewWidth / ((float) intrinsicWidth)) * this.normalizedScale;
                this.f4963m[4] = (this.matchViewHeight / ((float) intrinsicHeight)) * this.normalizedScale;
                f = this.f4963m[2];
                float f5 = this.f4963m[5];
                translateMatrixAfterRotate(2, f, this.normalizedScale * this.prevMatchViewWidth, getImageWidth(), this.prevViewWidth, this.viewWidth, intrinsicWidth);
                translateMatrixAfterRotate(5, f5, this.prevMatchViewHeight * this.normalizedScale, getImageHeight(), this.prevViewHeight, this.viewHeight, intrinsicHeight);
                this.matrix.setValues(this.f4963m);
            } else {
                this.matrix.setScale(f, f2);
                this.matrix.postTranslate(f3 / 2.0f, f4 / 2.0f);
                this.normalizedScale = 1.0f;
            }
            fixTrans();
            setImageMatrix(this.matrix);
        }
    }

    private void fixScaleTrans() {
        fixTrans();
        this.matrix.getValues(this.f4963m);
        if (getImageWidth() < ((float) this.viewWidth)) {
            this.f4963m[2] = (((float) this.viewWidth) - getImageWidth()) / 2.0f;
        }
        if (getImageHeight() < ((float) this.viewHeight)) {
            this.f4963m[5] = (((float) this.viewHeight) - getImageHeight()) / 2.0f;
        }
        this.matrix.setValues(this.f4963m);
    }

    private void fixTrans() {
        this.matrix.getValues(this.f4963m);
        float f = this.f4963m[2];
        float f2 = this.f4963m[5];
        f = getFixTrans(f, (float) this.viewWidth, getImageWidth());
        f2 = getFixTrans(f2, (float) this.viewHeight, getImageHeight());
        if (f != 0.0f || f2 != 0.0f) {
            this.matrix.postTranslate(f, f2);
        }
    }

    private float getFixDragTrans(float f, float f2, float f3) {
        return f3 <= f2 ? 0.0f : f;
    }

    private float getFixTrans(float f, float f2, float f3) {
        float f4;
        float f5;
        if (f3 <= f2) {
            f4 = f2 - f3;
            f5 = 0.0f;
        } else {
            f5 = f2 - f3;
            f4 = 0.0f;
        }
        return f < f5 ? (-f) + f5 : f > f4 ? (-f) + f4 : 0.0f;
    }

    private float getImageHeight() {
        return this.matchViewHeight * this.normalizedScale;
    }

    private float getImageWidth() {
        return this.matchViewWidth * this.normalizedScale;
    }

    private void printMatrixInfo() {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        Log.d(DEBUG, "Scale: " + fArr[0] + " TransX: " + fArr[2] + " TransY: " + fArr[5]);
    }

    private void savePreviousImageValues() {
        if (this.matrix != null && this.viewHeight != 0 && this.viewWidth != 0) {
            this.matrix.getValues(this.f4963m);
            this.prevMatrix.setValues(this.f4963m);
            this.prevMatchViewHeight = this.matchViewHeight;
            this.prevMatchViewWidth = this.matchViewWidth;
            this.prevViewHeight = this.viewHeight;
            this.prevViewWidth = this.viewWidth;
        }
    }

    private void scaleImage(double d, float f, float f2, boolean z) {
        float f3;
        float f4;
        if (z) {
            f3 = this.superMinScale;
            f4 = this.superMaxScale;
        } else {
            f3 = this.minScale;
            f4 = this.maxScale;
        }
        float f5 = this.normalizedScale;
        this.normalizedScale = (float) (((double) this.normalizedScale) * d);
        if (this.normalizedScale > f4) {
            this.normalizedScale = f4;
            d = (double) (f4 / f5);
        } else if (this.normalizedScale < f3) {
            this.normalizedScale = f3;
            d = (double) (f3 / f5);
        }
        this.matrix.postScale((float) d, (float) d, f, f2);
        fixScaleTrans();
    }

    private void setState(State state) {
        this.state = state;
    }

    private int setViewSize(int i, int i2, int i3) {
        switch (i) {
            case Integer.MIN_VALUE:
                return Math.min(i3, i2);
            case 0:
                return i3;
            default:
                return i2;
        }
    }

    private void sharedConstructing(Context context) {
        super.setClickable(true);
        this.context = context;
        this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener(this, null));
        this.mGestureDetector = new GestureDetector(context, new GestureListener(this, null));
        this.matrix = new Matrix();
        this.prevMatrix = new Matrix();
        this.f4963m = new float[9];
        this.normalizedScale = 1.0f;
        if (this.mScaleType == null) {
            this.mScaleType = ScaleType.FIT_CENTER;
        }
        this.minScale = 1.0f;
        this.maxScale = 3.0f;
        this.superMinScale = 0.75f * this.minScale;
        this.superMaxScale = SUPER_MAX_MULTIPLIER * this.maxScale;
        setImageMatrix(this.matrix);
        setScaleType(ScaleType.MATRIX);
        setState(State.NONE);
        this.onDrawReady = false;
        super.setOnTouchListener(new PrivateOnTouchListener(this, null));
    }

    private PointF transformCoordBitmapToTouch(float f, float f2) {
        this.matrix.getValues(this.f4963m);
        float intrinsicWidth = f / ((float) getDrawable().getIntrinsicWidth());
        float intrinsicHeight = f2 / ((float) getDrawable().getIntrinsicHeight());
        return new PointF((intrinsicWidth * getImageWidth()) + this.f4963m[2], (intrinsicHeight * getImageHeight()) + this.f4963m[5]);
    }

    private PointF transformCoordTouchToBitmap(float f, float f2, boolean z) {
        this.matrix.getValues(this.f4963m);
        float intrinsicWidth = (float) getDrawable().getIntrinsicWidth();
        float intrinsicHeight = (float) getDrawable().getIntrinsicHeight();
        float f3 = this.f4963m[2];
        float imageWidth = ((f - f3) * intrinsicWidth) / getImageWidth();
        f3 = ((f2 - this.f4963m[5]) * intrinsicHeight) / getImageHeight();
        if (z) {
            imageWidth = Math.min(Math.max(imageWidth, 0.0f), intrinsicWidth);
            f3 = Math.min(Math.max(f3, 0.0f), intrinsicHeight);
        }
        return new PointF(imageWidth, f3);
    }

    private void translateMatrixAfterRotate(int i, float f, float f2, float f3, int i2, int i3, int i4) {
        if (f3 < ((float) i3)) {
            this.f4963m[i] = (((float) i3) - (((float) i4) * this.f4963m[0])) * 0.5f;
        } else if (f > 0.0f) {
            this.f4963m[i] = -((f3 - ((float) i3)) * 0.5f);
        } else {
            this.f4963m[i] = -((((Math.abs(f) + (((float) i2) * 0.5f)) / f2) * f3) - (((float) i3) * 0.5f));
        }
    }

    public boolean canScrollHorizontally(int i) {
        this.matrix.getValues(this.f4963m);
        float f = this.f4963m[2];
        return getImageWidth() < ((float) this.viewWidth) ? false : (f < -1.0f || i >= 0) ? (Math.abs(f) + ((float) this.viewWidth)) + 1.0f < getImageWidth() || i <= 0 : false;
    }

    public boolean canScrollHorizontallyFroyo(int i) {
        return canScrollHorizontally(i);
    }

    public float getCurrentZoom() {
        return this.normalizedScale;
    }

    public float getMaxZoom() {
        return this.maxScale;
    }

    public float getMinZoom() {
        return this.minScale;
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public PointF getScrollPosition() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        PointF transformCoordTouchToBitmap = transformCoordTouchToBitmap((float) (this.viewWidth / 2), (float) (this.viewHeight / 2), true);
        transformCoordTouchToBitmap.x /= (float) intrinsicWidth;
        transformCoordTouchToBitmap.y /= (float) intrinsicHeight;
        return transformCoordTouchToBitmap;
    }

    public RectF getZoomedRect() {
        if (this.mScaleType == ScaleType.FIT_XY) {
            throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
        }
        PointF transformCoordTouchToBitmap = transformCoordTouchToBitmap(0.0f, 0.0f, true);
        PointF transformCoordTouchToBitmap2 = transformCoordTouchToBitmap((float) this.viewWidth, (float) this.viewHeight, true);
        float intrinsicWidth = (float) getDrawable().getIntrinsicWidth();
        float intrinsicHeight = (float) getDrawable().getIntrinsicHeight();
        return new RectF(transformCoordTouchToBitmap.x / intrinsicWidth, transformCoordTouchToBitmap.y / intrinsicHeight, transformCoordTouchToBitmap2.x / intrinsicWidth, transformCoordTouchToBitmap2.y / intrinsicHeight);
    }

    public boolean isZoomed() {
        return this.normalizedScale != 1.0f;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        savePreviousImageValues();
    }

    protected void onDraw(Canvas canvas) {
        this.onDrawReady = true;
        this.imageRenderedAtLeastOnce = true;
        if (this.delayedZoomVariables != null) {
            setZoom(this.delayedZoomVariables.scale, this.delayedZoomVariables.focusX, this.delayedZoomVariables.focusY, this.delayedZoomVariables.scaleType);
            this.delayedZoomVariables = null;
        }
        super.onDraw(canvas);
    }

    protected void onMeasure(int i, int i2) {
        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int size = MeasureSpec.getSize(i);
        int mode = MeasureSpec.getMode(i);
        int size2 = MeasureSpec.getSize(i2);
        int mode2 = MeasureSpec.getMode(i2);
        this.viewWidth = setViewSize(mode, size, intrinsicWidth);
        this.viewHeight = setViewSize(mode2, size2, intrinsicHeight);
        setMeasuredDimension(this.viewWidth, this.viewHeight);
        fitImageToView();
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.normalizedScale = bundle.getFloat("saveScale");
            this.f4963m = bundle.getFloatArray("matrix");
            this.prevMatrix.setValues(this.f4963m);
            this.prevMatchViewHeight = bundle.getFloat("matchViewHeight");
            this.prevMatchViewWidth = bundle.getFloat("matchViewWidth");
            this.prevViewHeight = bundle.getInt("viewHeight");
            this.prevViewWidth = bundle.getInt("viewWidth");
            this.imageRenderedAtLeastOnce = bundle.getBoolean("imageRendered");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("saveScale", this.normalizedScale);
        bundle.putFloat("matchViewHeight", this.matchViewHeight);
        bundle.putFloat("matchViewWidth", this.matchViewWidth);
        bundle.putInt("viewWidth", this.viewWidth);
        bundle.putInt("viewHeight", this.viewHeight);
        this.matrix.getValues(this.f4963m);
        bundle.putFloatArray("matrix", this.f4963m);
        bundle.putBoolean("imageRendered", this.imageRenderedAtLeastOnce);
        return bundle;
    }

    public void resetZoom() {
        this.normalizedScale = 1.0f;
        fitImageToView();
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        savePreviousImageValues();
        fitImageToView();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        savePreviousImageValues();
        fitImageToView();
    }

    public void setImageResource(int i) {
        super.setImageResource(i);
        savePreviousImageValues();
        fitImageToView();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        savePreviousImageValues();
        fitImageToView();
    }

    public void setMaxZoom(float f) {
        this.maxScale = f;
        this.superMaxScale = SUPER_MAX_MULTIPLIER * this.maxScale;
    }

    public void setMinZoom(float f) {
        this.minScale = f;
        this.superMinScale = 0.75f * this.minScale;
    }

    public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
        this.doubleTapListener = onDoubleTapListener;
    }

    public void setOnTouchImageViewListener(OnTouchImageViewListener onTouchImageViewListener) {
        this.touchImageViewListener = onTouchImageViewListener;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.userTouchListener = onTouchListener;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType == ScaleType.FIT_START || scaleType == ScaleType.FIT_END) {
            throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
        } else if (scaleType == ScaleType.MATRIX) {
            super.setScaleType(ScaleType.MATRIX);
        } else {
            this.mScaleType = scaleType;
            if (this.onDrawReady) {
                setZoom(this);
            }
        }
    }

    public void setScrollPosition(float f, float f2) {
        setZoom(this.normalizedScale, f, f2);
    }

    public void setZoom(float f) {
        setZoom(f, 0.5f, 0.5f);
    }

    public void setZoom(float f, float f2, float f3) {
        setZoom(f, f2, f3, this.mScaleType);
    }

    public void setZoom(float f, float f2, float f3, ScaleType scaleType) {
        if (this.onDrawReady) {
            if (scaleType != this.mScaleType) {
                setScaleType(scaleType);
            }
            resetZoom();
            scaleImage((double) f, (float) (this.viewWidth / 2), (float) (this.viewHeight / 2), true);
            this.matrix.getValues(this.f4963m);
            this.f4963m[2] = -((getImageWidth() * f2) - (((float) this.viewWidth) * 0.5f));
            this.f4963m[5] = -((getImageHeight() * f3) - (((float) this.viewHeight) * 0.5f));
            this.matrix.setValues(this.f4963m);
            fixTrans();
            setImageMatrix(this.matrix);
            return;
        }
        this.delayedZoomVariables = new ZoomVariables(f, f2, f3, scaleType);
    }

    public void setZoom(ExclusiveBlender_TouchImageView exclusiveBlenderTouchImageView) {
        PointF scrollPosition = exclusiveBlenderTouchImageView.getScrollPosition();
        setZoom(exclusiveBlenderTouchImageView.getCurrentZoom(), scrollPosition.x, scrollPosition.y, exclusiveBlenderTouchImageView.getScaleType());
    }
}
