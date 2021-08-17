package Utilities.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.shahbatech.apiwrapper.connection.RequestFactory;
import com.lasirene.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

public class ImageProcessing  extends StorageHelper{

    Context context;


    public ImageProcessing(Context context){
        super(context);
        this.context = context;

    }


    public void load(ImageView imageView, String path){

        path = getPathByScreenSize(path);

        if(isExist(path)){
            String filePath = getLocalPath(path);
            File file = currentStorage().getFile(filePath);
            Picasso.get().load(file)
                    .into(imageView);
        }else{
            String finalPath = path;
            Picasso.get()
                    .load(RequestFactory.IMAGES + path)
                    .placeholder(R.color.black_opacity)
                    .error(R.drawable.no_image_available)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            imageView.setImageBitmap(bitmap);
                            save(bitmap, finalPath);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            imageView.setImageDrawable(errorDrawable);
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            imageView.setImageDrawable(placeHolderDrawable);
                        }
                    });
        }
    }


    private void save(Bitmap bitmap, String path){
        String filePath = getLocalPath(path);
        String dirPath = currentStorage().getFile(filePath).getParent();

        if(!currentStorage().isDirectoryExists(dirPath)){
            currentStorage().createDirectory(dirPath);
        }

        currentStorage().createFile(filePath, bitmap);
    }


    private boolean isExist(String path){

        String filePath = getLocalPath(path);
        return currentStorage().isFileExist(filePath);
    }

    private String getLocalPath(String path){
        return currentStorage().getInternalCacheDirectory() + "/" + path;
    }



    private String getPathByScreenSize(String path){
        String fileName = getFileName(path);
        String ext = getExt(path);
        String screenSize = getScreenSize();
        String parent = getParent(path);

        if(!screenSize.equals(""))
            return  parent + "/"+ fileName + "-" + screenSize + "." + ext;

         return path;
    }



    private String getFileName(String path){
        String fileName = path.substring(path.lastIndexOf("/")+1);

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(0, i);
        }

        return "";
    }


    private String getParent(String path){
        return path.substring(0, path.lastIndexOf("/"));
    }


    private String getScreenSize(){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);

        if(densityDpi <= 160)
            return ScreenSize.MD.getValue();

        if(densityDpi <= 240)
            return ScreenSize.HD.getValue();

        if(densityDpi <= 320)
            return ScreenSize.XHD.getValue();

        if(densityDpi <= 480)
            return ScreenSize.XXHD.getValue();

        return "";
    }




    private String getExt(String path){

        int i = path.lastIndexOf('.');
        if (i > 0) {
            return path.substring(i+1);
        }
        return "";
    }
}


enum ScreenSize{
    MD("md"),

    HD("hd"),

    XHD("xhd"),

    XXHD("xxhd");


    private String value;

    ScreenSize(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}