package Utilities.Helper;

import android.annotation.TargetApi;
import android.content.Context;

import com.google.gson.Gson;
import com.shahbatech.apiwrapper.model.Customer;
import com.shahbatech.apiwrapper.model.Service;

import java.io.File;

public class JsonStorageHelper extends StorageHelper {

    private static final String DIR_JSON_STORAGE = "json_storage";
    private String path;

    public JsonStorageHelper(Context context) {
        super(context);
    }


    public void save(Object object){
        save(object, object.getClass().getName());
    }

    public  <T> T  load(Class<T> aClass) {
        String js = load(aClass.getName());
        T object = new Gson().fromJson(js, aClass);
        return object;
    }


    private void save(Object object, String name){
        path = currentStorage().getInternalCacheDirectory() + File.pathSeparator + DIR_JSON_STORAGE
                + File.pathSeparator + name + ".json";

        if(currentStorage().isFileExist(path)){
            currentStorage().deleteFile(path);
        }

        String js = new Gson().toJson(object);

        currentStorage().createFile(path, js);
    }


    private String load(String name){
        path = currentStorage().getInternalCacheDirectory() + File.pathSeparator + DIR_JSON_STORAGE
                + File.pathSeparator + name + ".json";

        if(currentStorage().isFileExist(path)){
            return currentStorage().readTextFile(path);
        }

        return "";
    }


    public void remove(Class<?> aClass) {
        path = currentStorage().getInternalCacheDirectory() + File.pathSeparator + DIR_JSON_STORAGE
                + File.pathSeparator + aClass.getName() + ".json";

        if(currentStorage().isFileExist(path)){
           currentStorage().deleteFile(path);
        }
    }
}
