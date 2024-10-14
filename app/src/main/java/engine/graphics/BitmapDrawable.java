package engine.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.DrawableRes;

import engine.assets.BitmapResourceLoader;
import engine.assets.Loadable;
import engine.assets.ResourceLoader;

public class BitmapDrawable implements Loadable {
    Bitmap bitmap;
    ResourceLoader loader;

    public BitmapDrawable(ResourceLoader<Bitmap> loader) {
        this.loader = loader;
        loader.registerCallback(bitmap -> {
            this.bitmap = bitmap;
        });
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public double getAspect() {
        return (double)bitmap.getWidth() / (double)bitmap.getHeight();
    }

    public static BitmapDrawable fromResource(@DrawableRes int resid) {
        return new BitmapDrawable(new BitmapResourceLoader(resid));
    }

    public void load(Context context) {
        loader.loadResource(context);
    }
}
