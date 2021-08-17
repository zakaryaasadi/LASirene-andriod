package Utilities.Helper;

import android.content.Context;

import com.snatik.storage.Storage;

public class StorageHelper {
    private Context context;
    private static Storage storage;

    public StorageHelper(Context context) {
        this.context = context;
        storage = currentStorage();
    }

    protected Storage currentStorage() {
        if(storage != null)
            return storage;

        storage = new Storage(context);
        return storage;
    }
}
