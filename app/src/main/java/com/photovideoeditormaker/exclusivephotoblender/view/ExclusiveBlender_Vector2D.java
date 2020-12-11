package com.photovideoeditormaker.exclusivephotoblender.view;

import android.graphics.PointF;

public class ExclusiveBlender_Vector2D extends PointF {
    public ExclusiveBlender_Vector2D(float f, float f2) {
        super(f, f2);
    }

    public static float getAngle(ExclusiveBlender_Vector2D exclusiveBlenderVector2D, ExclusiveBlender_Vector2D exclusiveBlenderVector2D2) {
        exclusiveBlenderVector2D.normalize();
        exclusiveBlenderVector2D2.normalize();
        return (float) (57.29577951308232d * (Math.atan2((double) exclusiveBlenderVector2D2.y, (double) exclusiveBlenderVector2D2.x) - Math.atan2((double) exclusiveBlenderVector2D.y, (double) exclusiveBlenderVector2D.x)));
    }

    public void normalize() {
        float sqrt = (float) Math.sqrt((double) ((this.x * this.x) + (this.y * this.y)));
        this.x /= sqrt;
        this.y /= sqrt;
    }
}
