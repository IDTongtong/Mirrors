package com.lanou.ourteam.mirrors.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseApplication;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZHDelete on 16/3/29.
 */
public class NetHelper {

    private static NetHelper netHelperInstance;
    private RequestQueue requestQueue;
    private String diskPath;
    private ImageLoader imageLoader;
    private Context context;

    private NetHelper() {
        requestQueue = Volley.newRequestQueue(BaseApplication.getContext());
        this.context = BaseApplication.getContext();
        initImageLoader();
    }

    public static NetHelper getInstance() {
        if (netHelperInstance == null) {
            synchronized (NetHelper.class) {
                if (netHelperInstance == null) {
                    netHelperInstance = new NetHelper();
                }
            }
        }
        return netHelperInstance;
    }

    /**
     * @param url_body
     * @param params
     * @param netListener
     */

    public void volleyPostTogetNetData(String url_body, final Map<String, String> params, final VolleyNetListener netListener) {

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, Content.URL_HEAD + url_body,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        netListener.onSuccess(response);

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        netListener.onFail("请求失败");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void okHttpPost(String url_body, RequestBody requestBody, Callback responseCallback) {

        Request request = new Request.Builder()
                .url(Content.URL_HEAD + url_body)
                .post(requestBody)
                .build();

        OkHttpTools.enqueue(request, responseCallback);
    }

    /**
     * 图片缓存根路径
     */
    private void initImageLoader() {
        //定义硬盘图片缓存的根路径
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            diskPath = file.getAbsolutePath();
            //记得加读写存储卡的权限
            Log.d("NetHelper", "外置sd卡:" + diskPath);


        } else {
            File file = BaseApplication.getContext().getFilesDir();
            diskPath = file.getAbsolutePath();
            Log.d("NetHelper", "内置sd卡:" + diskPath);

        }
        File file = new File(diskPath + "/ourteammirror");//文件夹名
        if (!file.exists()) {
            file.mkdir();
        }
        diskPath = file.getAbsolutePath();
        Log.i("android", diskPath);

        imageLoader = new ImageLoader(requestQueue, new DoubleCache());
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }


    //慎用
    public void loadOriginImageWithVolley(ImageView imageView, String image_rul) {
        ImageLoader.ImageListener imageListener = imageLoader.getImageListener(imageView,
                R.mipmap.loading,
                R.mipmap.fail);
        imageLoader.get(image_rul, imageListener);
    }

    public void loadImageWithVolley(ImageView imageView, String image_rul) {
        ImageLoader.ImageListener imageListener = imageLoader.getImageListener(imageView,
                R.mipmap.loading,
                R.mipmap.fail);
        imageLoader.get(image_rul, imageListener, 480, 800, ImageView.ScaleType.FIT_XY);

    }

    public void loadImageWithVolley(ImageView imageView, String image_rul, int targetWidth, int targetHeight) {
        ImageLoader.ImageListener imageListener = imageLoader.getImageListener(imageView,
                R.mipmap.loading,
                R.mipmap.fail);
        imageLoader.get(image_rul, imageListener, targetWidth, targetHeight, ImageView.ScaleType.FIT_XY);

    }

    //有问题,不可调用 ,裁剪 添上 就好

    /**
     * 毕加索
     *
     * @param imageView
     * @param image_url
     */
    public void loadImageWithPicasso(ImageView imageView, String image_url) {
        Picasso.with(imageView.getContext())
                .load(image_url)
                .resize(480, 800)
                .centerCrop()
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.fail)
                .into(imageView);
//        Picasso.with( imageView.getContext() )
//         .load(url)
//         .resize(dp2px(250), dp2px(250))
//         .centerCrop()
//         .into(imageView);


    }

    public void loadImageWithPicasso(ImageView imageView, String image_url, int targetWidth, int targetHeight) {
        Picasso.with(imageView.getContext())
                .load(image_url)
                .resize(targetWidth, targetHeight)
                .centerCrop()//缩放方式
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.fail)
                .into(imageView);

    }

    public void loadBigImageWithPicasso(ImageView imageView, String image_url) {

        /**
         * Indicate that this action should not use the memory cache for attempting to load or save the
         * image. This can be useful when you know an image will only ever be used once (e.g., loading
         * an image from the filesystem and uploading to a remote server).
         * 不用 memory cache load 或者 save
         * 常用于,已经知道,一个图片,将只被使用一次,
         * 不如 从 文件系统加载一张图,然后上传到远端服务器
         */

        Picasso.with(imageView.getContext())
                .load(image_url)
                .skipMemoryCache()
//                .placeholder(R.mipmap.loading)
                .error(R.mipmap.fail)
                .into(imageView);
    }

    public void loadImageWithFresco(SimpleDraweeView simpleDraweeView, String image_url) {
        /**
         *  //创建SimpleDraweeView对象
         simpleDraweeView = (SimpleDraweeView) findViewById(R.id.main_sdv);
         //创建将要下载的图片的URI
         Uri imageUri = Uri.parse("http://my.csdn.net/uploads/avatar/4/E/8/1_y1scp.jpg");
         //开始下载
         simpleDraweeView.setImageURI(imageUri);
         */

        Uri image_uri = Uri.parse(image_url);
        simpleDraweeView.setImageURI(image_uri);

    }


    public class MemoryCache implements
            ImageLoader.ImageCache {
        private LruCache<String, Bitmap> cache;

        public MemoryCache() {
            long maxSize = Runtime.getRuntime().maxMemory() / 1024;

            int cacheSize = (int) (maxSize / 4);

            cache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() *
                            value.getHeight() / 1024;
                }
            };

        }

        @Override
        public Bitmap getBitmap(String url) {
            //url = CommonUtils.stringToMD5(url);
            return cache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            //url = CommonUtils.stringToMD5(url);
            cache.put(url, bitmap);
        }
    }

    public class DiskCache implements ImageLoader.ImageCache {

        @Override
        public Bitmap getBitmap(String url) {
//            //获取url中的图片名称
//            String fileName = url.substring(
//                    url.lastIndexOf("/") + 1,
//                    url.length()
//            );
            //MD5 加密网址整体作为文件名:
            String fileName = CommonUtils.stringToMD5(url);
            Log.d("NetHelper", "get___MD5的文件名:" + fileName);

            //用文件名拼接出实际文件存储路径
            String filePath = diskPath + "/" + fileName;

            //
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(filePath, options);

            //想要得到缩放的值
            final int REQUIRED_SIZE = 800;
            int width_temp = options.outWidth;
            int height_temp = options.outHeight;
            Log.d("DiskCache", "width_temp上:" + width_temp);
            Log.d("DiskCache", "height_temp上:" + height_temp);
            int scale = 1;
            while (true) {
                if (width_temp / 2 < REQUIRED_SIZE | height_temp / 2 < REQUIRED_SIZE) {
                    Log.d("DiskCache", "if里走没走");
                    break;
                }
                width_temp /= 2;
                height_temp /= 2;
                Log.d("DiskCache", "width_temp:" + width_temp);
                Log.d("DiskCache", "height_temp:" + height_temp);
                scale *= 2;
            }
            Log.d("DiskCache", "缩放比例为:" + scale);

            BitmapFactory.Options options1 = new BitmapFactory.Options();
            options1.inSampleSize = scale;


            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options1);
            return bitmap;
        }

        /**
         * 将Bitmap写入文件
         * @param url
         * @param bitmap
         */
        @Override
        public void putBitmap(String url, Bitmap bitmap) {

            String fileName = CommonUtils.stringToMD5(url);
            Log.d("NetHelper", "put___MD5的文件名:" + fileName);

            //用文件名拼接出实际文件存储路径
            String filePath = diskPath + "/" + fileName;
            Log.d("NetHelper", "put执行___文件路径:" + filePath);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(filePath);
                //将bitmap对象写入文件中
                bitmap.compress(Bitmap.CompressFormat.PNG,
                        10, fos);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                //关闭文件流
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }

    public class DoubleCache implements
            ImageLoader.ImageCache {
        private MemoryCache memoryCache;
        private DiskCache diskCache;

        public DoubleCache() {
            memoryCache = new MemoryCache();
            diskCache = new DiskCache();
        }

        /**
         * 读取Bitmap
         * @param url
         * @return
         */
        @Override
        public Bitmap getBitmap(String url) {
            Bitmap bitmap = memoryCache.getBitmap(url);
            if (bitmap == null)
                bitmap = diskCache.getBitmap(url);
            return bitmap;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            memoryCache.putBitmap(url, bitmap);
            diskCache.putBitmap(url, bitmap);
        }
    }


}
