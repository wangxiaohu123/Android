package com.ykx.baselibs.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;


/*********************************************************************************
 * Project Name  : Health
 * Package       : com.sofn.agriculture.baselibs.utils
 * <p/>
 * <p/>
 * Copyright  禄康源电子商务部  Corporation 2016 All Rights Reserved
 * <p/>
 * <p/>
 * <Pre>
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 16/1/28 上午10:14.
 * <p/>
 * <p/>
 * ********************************************************************************
 */
public class BitmapUtils {

    /**
     * get the orientation of the bitmap {@link ExifInterface}
     *
     * @param path
     * @return
     */
    public final static int getDegress(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * rotate the bitmap
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * caculate the bitmap sampleSize
     *
     * @return
     */
    public final static int caculateInSampleSize(Options options, int rqsW, int rqsH) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (rqsW == 0 || rqsH == 0) return 1;
        if (height > rqsH || width > rqsW) {
            final int heightRatio = Math.round((float) height / (float) rqsH);
            final int widthRatio = Math.round((float) width / (float) rqsW);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public final static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }


    public final static Drawable getDrawable(Context context, int drawable){

        return ContextCompat.getDrawable(context,drawable);

    }

    /**
     * 压缩指定路径的图片，并得到图片对象
     *
     * @param path bitmap source path
     * @return Bitmap {@link Bitmap}
     */
    public final static Bitmap compressBitmap(String path, int rqsW, int rqsH) {
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = caculateInSampleSize(options, rqsW, rqsH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

//    /**
//     * 压缩指定路径图片，并将其保存在缓存目录中，通过isDelSrc判定是否删除源文件，并获取到缓存后的图片路径
//     * @param srcPath
//     * @param rqsW
//     * @param rqsH
//     * @param isDelSrc
//     * @return
//     */
//    public final static String compressBitmap( String srcPath, int rqsW, int rqsH, boolean isDelSrc) {
//        Bitmap bitmap = compressBitmap(srcPath, rqsW, rqsH);
//        File srcFile = new File(srcPath);
//        String desPath = getImageCacheDir(context) + srcFile.getName();
//        int degree = getDegress(srcPath);
//        try {
//            if (degree != 0) bitmap = rotateBitmap(bitmap, degree);
//            File file = new File(desPath);
//            FileOutputStream  fos = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 70, fos);
//            fos.close();
//            if (isDelSrc) srcFile.deleteOnExit();
//        } catch (Exception e) {
//            // TODO: handle exception
//            Log.e("BitmapHelper-->compressBitmap", e.getMessage()+"");
//        }
//        return desPath;
//    }

    /**
     * 此方法过期，该方法可能造成OutOfMemoryError，使用不含isAdjust参数的方法
     *
     * @param is
     * @param reqsW
     * @param reqsH
     * @param isAdjust
     * @return
     */
    @Deprecated
    public final static Bitmap compressBitmap(InputStream is, int reqsW, int reqsH, boolean isAdjust) {
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return compressBitmap(bitmap, reqsW, reqsH, isAdjust);
    }

    /**
     * 压缩某个输入流中的图片，可以解决网络输入流压缩问题，并得到图片对象
     *
     * @return Bitmap {@link Bitmap}
     */
    public final static Bitmap compressBitmap(InputStream is, int reqsW, int reqsH) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ReadableByteChannel channel = Channels.newChannel(is);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) baos.write(buffer.get());
                buffer.clear();
            }
            byte[] bts = baos.toByteArray();
            Bitmap bitmap = compressBitmap(bts, reqsW, reqsH);
            is.close();
            channel.close();
            baos.close();
            return bitmap;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    /**
     * File to Bitmap
     *
     * @param filePath
     * @param inSampleSize
     * @return
     */
    public static Bitmap getBitmapWithOption(String filePath, int inSampleSize) {

        Options options = new Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        return bitmap;
    }

    /**
     * 压缩指定byte[]图片，并得到压缩后的图像
     *
     * @param bts
     * @param reqsW
     * @param reqsH
     * @return
     */
    public final static Bitmap compressBitmap(byte[] bts, int reqsW, int reqsH) {
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
        options.inSampleSize = caculateInSampleSize(options, reqsW, reqsH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
    }

    /**
     * 此方法已过期，该方法可能造成OutOfMemoryError，使用不含isAdjust参数的方法
     *
     * @param bitmap
     * @param reqsW
     * @param reqsH
     * @return
     */
    @Deprecated
    public final static Bitmap compressBitmap(Bitmap bitmap, int reqsW, int reqsH, boolean isAdjust) {
        if (bitmap == null || reqsW == 0 || reqsH == 0) return bitmap;
        if (bitmap.getWidth() > reqsW || bitmap.getHeight() > reqsH) {
            float scaleX = new BigDecimal(reqsW).divide(new BigDecimal(bitmap.getWidth()), 4, RoundingMode.DOWN).floatValue();
            float scaleY = new BigDecimal(reqsH).divide(new BigDecimal(bitmap.getHeight()), 4, RoundingMode.DOWN).floatValue();
            if (isAdjust) {
                scaleX = scaleX < scaleY ? scaleX : scaleY;
                scaleY = scaleX;
            }
            Matrix matrix = new Matrix();
            matrix.postScale(scaleX, scaleY);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    /**
     * 压缩已存在的图片对象，并返回压缩后的图片
     *
     * @param bitmap
     * @param reqsW
     * @param reqsH
     * @return
     */
    public final static Bitmap compressBitmap(Bitmap bitmap, int reqsW, int reqsH) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 100, baos);
            byte[] bts = baos.toByteArray();
            Bitmap res = compressBitmap(bts, reqsW, reqsH);
            baos.close();
            return res;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return bitmap;
        }
    }

    /**
     * 此方法过期，该方法可能造成OutOfMemoryError，使用不含isAdjust参数的方法
     * get bitmap form resource dictory, and then compress bitmap according to reqsW and reqsH
     *
     * @param res   {@link Resources}
     * @param resID
     * @param reqsW
     * @param reqsH
     * @return
     */
    @Deprecated
    public final static Bitmap compressBitmap(Resources res, int resID, int reqsW, int reqsH, boolean isAdjust) {
        Bitmap bitmap = BitmapFactory.decodeResource(res, resID);
        return compressBitmap(bitmap, reqsW, reqsH, isAdjust);
    }

    /**
     * 压缩资源图片，并返回图片对象
     *
     * @param res   {@link Resources}
     * @param resID
     * @param reqsW
     * @param reqsH
     * @return
     */
    public final static Bitmap compressBitmap(Resources res, int resID, int reqsW, int reqsH) {
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resID, options);
        options.inSampleSize = caculateInSampleSize(options, reqsW, reqsH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resID, options);
    }

    /**
     * 基于质量的压缩算法， 此方法未 解决压缩后图像失真问题
     * <br> 可先调用比例压缩适当压缩图片后，再调用此方法可解决上述问题
     *
     * @param maxBytes 压缩后的图像最大大小 单位为byte
     * @return
     */
    public final static Bitmap compressBitmap(Bitmap bitmap, long maxBytes) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 100, baos);
            int options = 90;
            LogUtils.showLog("length=" + baos.toByteArray().length);
            while (baos.toByteArray().length > maxBytes) {
                baos.reset();
                bitmap.compress(CompressFormat.PNG, options, baos);
                options -= 10;
            }
            byte[] bts = baos.toByteArray();
            Bitmap bmp = BitmapFactory.decodeByteArray(bts, 0, bts.length);
            baos.close();
            return bmp;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image,long maxbytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > maxbytes) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            if (options>=20) {
                options -= 10;// 每次都减少10
            }else{
                break;
            }
        }
        byte[] bts = baos.toByteArray();
        Bitmap bmp = BitmapFactory.decodeByteArray(bts, 0, bts.length);
        return bmp;
    }

    /**
     * 图片的缩放方法
     *
     * @param bitmap  ：源图片资源
     * @param maxSize ：图片允许最大空间  单位:KB
     * @return
     */
    public static Bitmap getZoomImage(Bitmap bitmap, double maxSize) {
        if (null == bitmap) {
            return null;
        }
        if (bitmap.isRecycled()) {
            return null;
        }

        // 单位：从 Byte 换算成 KB
        double currentSize = bitmapToByteArray(bitmap, false).length / 1024;
        // 判断bitmap占用空间是否大于允许最大空间,如果大于则压缩,小于则不压缩
        while (currentSize > maxSize) {
            // 计算bitmap的大小是maxSize的多少倍
            double multiple = currentSize / maxSize;
            // 开始压缩：将宽带和高度压缩掉对应的平方根倍
            // 1.保持新的宽度和高度，与bitmap原来的宽高比率一致
            // 2.压缩后达到了最大大小对应的新bitmap，显示效果最好
            bitmap = getZoomImage(bitmap, bitmap.getWidth() / Math.sqrt(multiple), bitmap.getHeight() / Math.sqrt(multiple));
            currentSize = bitmapToByteArray(bitmap, false).length / 1024;
        }
        return bitmap;
    }

    /**
     * 图片的缩放方法
     *
     * @param orgBitmap ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap getZoomImage(Bitmap orgBitmap, double newWidth, double newHeight) {
        if (null == orgBitmap) {
            return null;
        }
        if (orgBitmap.isRecycled()) {
            return null;
        }
        if (newWidth <= 0 || newHeight <= 0) {
            return null;
        }

        // 获取图片的宽和高
        float width = orgBitmap.getWidth();
        float height = orgBitmap.getHeight();
        // 创建操作图片的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(orgBitmap, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    /**
     * bitmap转换成byte数组
     *
     * @param bitmap
     * @param needRecycle
     * @return
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap, boolean needRecycle) {
        if (null == bitmap) {
            return null;
        }
        if (bitmap.isRecycled()) {
            return null;
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bitmap.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
//            Log.e(TAG, e.toString());
        }
        return result;
    }

    public final static Bitmap imageZoom(Bitmap bitmap, double size, double maxSize) {
        //图片允许最大空间   单位：KB
//        double maxSize =400.00;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length/1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitmap = imageZoom(bitmap, bitmap.getWidth() / Math.sqrt(i),
                    bitmap.getHeight() / Math.sqrt(i));
        }
        return bitmap;
    }

    /**
     * 基于质量的压缩算法， 此方法未 解决压缩后图像失真问题
     * <br> 可先调用比例压缩适当压缩图片后，再调用此方法可解决上述问题
     *
     * @return
     */
    public final static Bitmap compressBitmap(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, null);
        return compressBitmap(bitmap, 200, 400);
    }

//  public final static Bitmap compressBitmap(InputStream is, long maxBytes) {
//      try {
//          ByteArrayOutputStream baos = new ByteArrayOutputStream();
//          byte[] bts = new byte[1024];
//          while (is.read(bts) != -1) baos.write(bts, 0, bts.length);
//          is.close();
//          int options = 100;
//          while (baos.toByteArray().length > maxBytes) {
//
//          }
//      } catch (Exception e) {
//          // TODO: handle exception
//      }
//  }

    /**
     * 得到指定路径图片的options
     *
     * @param srcPath
     * @return Options {@link Options}
     */
    public final static Options getBitmapOptions(String srcPath) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);
        return options;
    }

//    /**
//     * 获取图片缓存路径
//     * @param context
//     * @return
//     */
//    private static String getImageCacheDir(Context context) {
//        String dir = FileHelper.getCacheDir(context) + "Image" + File.separator;
//        File file = new File(dir);
//        if (!file.exists()) file.mkdirs();
//        return dir;
//    }

    /**
     * 保存文件
     *
     * @param bm
     * @param filePath
     * @throws IOException
     */
    public static boolean saveFile(Bitmap bm, String filePath) {
        try {
            File myCaptureFile = new File(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            bm.recycle();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Bitmap createBitmap(byte[] values, int picW, int picH) {
        if(values == null || picW <= 0 || picH <= 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(values, 0, values.length);
    }

//    public static boolean savefile(int z,int x,int y,byte[] data){
//        boolean issuccess=true;
//
//        int size=256;
//        Bitmap bitmap = createBitmap(data,size,size);
//
//        String dicpath= AppConstant.TEST_PATH+z+File.separator+x+File.separator;
//        File files=new File(dicpath);
//        if (!files.exists()){
//            files.mkdirs();
//        }
//        String filepath=dicpath+y+".png";
//
//        issuccess=saveFile(bitmap,filepath);
//
//        return issuccess;
//    }


    /**
     * 将两张位图拼接成一张(横向拼接)
     *
     * @param first
     * @param second
     * @param three
     * @param four
     * @return
     */
    public static Bitmap add4Bitmap(Bitmap first, Bitmap second, Bitmap three, Bitmap four, float fileNewSize) {
        int firstSize = getBitmapSmallSize(first);
        int secondSize = getBitmapSmallSize(second);
        int threeSize = getBitmapSmallSize(three);
        int fourSize = getBitmapSmallSize(four);
        int size = firstSize;
        if (size > secondSize) {
            size = secondSize;
        }
        if (size > threeSize) {
            size = threeSize;
        }
        if (size > fourSize) {
            size = fourSize;
        }
        first = ImageCrop(first, size);
        second = ImageCrop(second, size);
        three = ImageCrop(three, size);
        four = ImageCrop(four, size);
        int linewh = 1;
        int width = size * 2 + linewh;
        int height = size * 2 + linewh;
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, size + linewh, 0, null);
        canvas.drawBitmap(three, 0, size + linewh, null);
        canvas.drawBitmap(four, size + linewh, size + linewh, null);

        if (!first.isRecycled()) {
            first.recycle();
        }
        if (!second.isRecycled()) {
            second.recycle();
        }
        if (!three.isRecycled()) {
            three.recycle();
        }
        if (!four.isRecycled()) {
            four.recycle();
        }

        return changeSize(result, fileNewSize / width);
    }

    /**
     * 按正方形裁切图片
     */
    public static int getBitmapSmallSize(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长
        return wh;
    }

    /**
     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(Bitmap bitmap, int wh) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int retX = (w - wh) / 2;//基于原图，取正方形左上角x坐标
        int retY = (h - wh) / 2;
        //下面这句是关键
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
        return newBitmap;
    }

    /**
     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长
        int retX = w > h ? (w - h) / 2 : 0;//基于原图，取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;
        //下面这句是关键
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
        return newBitmap;
    }


    /**
     * bitmap缩放比例
     *
     * @param bitmap
     * @param scaleNum
     * @return
     */
    public static Bitmap changeSize(Bitmap bitmap, float scaleNum) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleNum, scaleNum); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    public static String writeBitmapToFile(Bitmap img, String name) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString());
        File f = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString(), name + ".jpg");
        FileOutputStream fOut = null;
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        img.compress(CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("-------error--------" + e.toString());
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("-------error--------" + e.toString());
        }
        return f.getAbsolutePath();
    }

    public static String writeBitmapToFile(Bitmap img, String filepath,String filename) {
        File dir = new File(filepath);
        File f = new File(filepath, filename + ".png");
        if (img!=null) {
            try {
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fOut= new FileOutputStream(f);
                img.compress(Bitmap.CompressFormat.PNG, 90, fOut);
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return f.getAbsolutePath();
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();

        return output;
    }

    /**
     *
     * @param bitmap
     * @param roundPx
     * @param wbl  宽比例
     * @param hbl  高比例
     * @param hpflag  是否横屏 ture,竖屏 false
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx,int wbl,int hbl,boolean hpflag){

        final int color = 0xff424242;
        final Paint paint = new Paint();

        Rect rect;
        if (hpflag){
            if ((bitmap.getWidth()/bitmap.getHeight())>(wbl/hbl)){
                int heigh=bitmap.getHeight();
                int width=bitmap.getWidth()*wbl/hbl;
                int x=(bitmap.getWidth()-width)/2;
                rect = new Rect(x, 0, width, heigh);
            }else{
                int width=bitmap.getWidth();
                int heigh=width*hbl/wbl;
                int y=(bitmap.getHeight()-heigh)/2;
                rect = new Rect(0, 0, width,heigh);
            }
        }else{
            if ((bitmap.getWidth()/bitmap.getHeight())>(wbl/hbl)){
                int width=bitmap.getWidth();
                int heigh=width*hbl/wbl;
                int y=(bitmap.getHeight()-heigh)/2;
                rect = new Rect(0, y, width,heigh);
            }else{
                int heigh=bitmap.getHeight();
                int width=bitmap.getWidth()*wbl/hbl;
                int x=(bitmap.getWidth()-width)/2;
                rect = new Rect(x, 0, width, heigh);
            }
        }

        Bitmap output = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();

        return createScaleBitmap(output,800,600,0);
    }
    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight, int inSampleSize) {
        // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，所以直接设置为false
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

//
//    public static Bitmap setMatrix(Bitmap b){
//        int w=b.getWidth();
//        int h=b.getHeight();
//        Matrix matrix = new Matrix();
//        matrix.postScale(3f, 3f); // 长和宽放大缩小的比例
//        Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w,h, matrix, true);
//        return resizeBmp;
//    }

}
