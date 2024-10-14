package engine.assets;

import android.content.Context;

import java.util.ArrayList;

public class BulkLoader {
    ArrayList<Loadable> loadables = new ArrayList<>();

    public void register(Loadable loadable) {
        loadables.add(loadable);
    }

    public void load(Context context) {
        for (Loadable loadable : loadables) {
            loadable.load(context);
        }
    }
}
