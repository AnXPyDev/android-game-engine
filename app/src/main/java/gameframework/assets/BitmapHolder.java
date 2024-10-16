package gameframework.assets;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.DrawableRes;

public class BitmapHolder implements Loadable {
    Bitmap bitmap;
    ResourceLoader loader;

    public BitmapHolder(ResourceLoader<Bitmap> loader) {
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

    public void load(Context context) {
        loader.loadResource(context);
    }

    public static BitmapHolder fromResource(@DrawableRes int resid) {
        return new BitmapHolder(new BitmapResourceLoader(resid));
    }

}
