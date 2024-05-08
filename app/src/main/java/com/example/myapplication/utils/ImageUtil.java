package com.example.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.myapplication.R;
import com.example.myapplication.view.PieceImage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ImageUtil {
    /**
     * 保存所有连连看图片资源值(int类型)
     */
    private static List<Integer> imageValues = getImageValues();
    private static List<Integer> imageValuesnum = getImageValuesnum();
    private static List<Integer> imageValuesnum2 = getImageValuesnum2();
    private static List<Integer> imageValues2 = getImageValues2();
    /**
     * 获取连连看所有图片的ID（约定所有图片ID以p_开头）
     */
    public static List<Integer> getImageValues() {
        try {
            // 得到R.drawable所有的属性, 即获取drawable目录下的所有图片
            Field[] drawableFields = R.drawable.class.getFields();
            List<Integer> resourceValues = new ArrayList<Integer>();
            for (Field field : drawableFields) {
                // 如果该Field的名称以fruit_开头
                if (field.getName().indexOf("answer_") != -1) {
                    resourceValues.add(field.getInt(R.drawable.class));
                }

            }

            return resourceValues;
        } catch (Exception e) {
            return null;
        }
    }
    public static List<Integer> getImageValuesnum() {
        try {
            // 得到R.drawable所有的属性, 即获取drawable目录下的所有图片
            Field[] drawableFields = R.drawable.class.getFields();
            List<Integer> resourceValues = new ArrayList<Integer>();
            for (Field field : drawableFields) {
                // 如果该Field的名称以fruit_开头
                if (field.getName().indexOf("answer_") != -1) {

                    String a=field.getName().replace("answer_","");
                    resourceValues.add(field.getInt(a));
                }

            }

            return resourceValues;
        } catch (Exception e) {
            return null;
        }
    }
    public static List<Integer> getImageValuesnum2() {
        try {
            // 得到R.drawable所有的属性, 即获取drawable目录下的所有图片
            Field[] drawableFields = R.drawable.class.getFields();
            List<Integer> resourceValues = new ArrayList<Integer>();
            for (Field field : drawableFields) {
                // 如果该Field的名称以fruit_开头
                if (field.getName().indexOf("ques_") != -1) {

                    String a=field.getName().replace("ques_","");
                    resourceValues.add(field.getInt(a));
                }

            }

            return resourceValues;
        } catch (Exception e) {
            return null;
        }
    }
    public static List<Integer> getImageValues2() {
        try {
            // 得到R.drawable所有的属性, 即获取drawable目录下的所有图片
            Field[] drawableFields = R.drawable.class.getFields();
            List<Integer> resourceValues = new ArrayList<Integer>();
            for (Field field : drawableFields) {
                // 如果该Field的名称以fruit_开头
                if (field.getName().indexOf("ques_") != -1) {
                    resourceValues.add(field.getInt(R.drawable.class));
                }

            }

            return resourceValues;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 随机从sourceValues的集合中获取size个图片ID, 返回结果为图片ID的集合
     *
     * @param sourceValues 从中获取的集合
     * @param size         需要获取的个数
     * @return size个图片ID的集合
     */
    public static List<Integer> getRandomValues(List<Integer> sourceValues,List<Integer> sourceValues2,
                                                int size) {
        // 创建一个随机数生成器
        Random random = new Random();
        // 创建结果集合
        List<Integer> result = new ArrayList<Integer>();
        boolean[] flag=new boolean[sourceValues.size()];//重复判断句
        for (int i = 0; i < size; i++) {
            int index;
            try {
                do{
                    //从[0,redBalls.length)取
                    index = random.nextInt(sourceValues.size());
                }while(flag[index]);
                flag[index]=true;
                // 随机获取一个数字，大于、小于sourceValues.size()的数值
                // 从图片ID集合中获取该图片对象
                Integer image = sourceValues.get(index);
                Integer image2 = sourceValues2.get(index);
                // 添加到结果集中
                result.add(image);
                result.add(image2);
            } catch (IndexOutOfBoundsException e) {
                return result;
            }
        }
        return result;
    }

    /**
     * 从drawable目录中中获取size个图片资源ID(以p_为前缀的资源名称), 其中size为游戏数量
     *
     * @param size 需要获取的图片ID的数量
     * @return size个图片ID的集合
     */
    public static List<Integer> getPlayValues(int size) {
        if (size % 2 != 0) {
            // 如果该数除2有余数，将size加1
            size += 1;
        }
        // 再从所有的图片值中随机获取size的一半数量,即N/2张图片
        List<Integer> playImageValues = getRandomValues(imageValues, imageValues2,size / 2);
        // 将playImageValues集合的元素增加一倍（保证所有图片都有与之配对的图片），即N张图片

        // 将所有图片ID随机“洗牌”

        return playImageValues;
    }

    /**
     * 将图片ID集合转换PieceImage对象集合，PieceImage封装了图片ID与图片本身
     *
     * @return size个PieceImage对象的集合
     */
    public static List<PieceImage> getPlayImages(Context context, int size) {
        // 获取图片ID组成的集合
        List<Integer> resourceValues = getPlayValues(size);
        List<PieceImage> result = new ArrayList<PieceImage>();
        // 遍历每个图片ID
        for (int i=0;i<resourceValues.size();i=i+2) {
            // 加载图片

            Bitmap bm = drawableToBitmap(context.getResources().getDrawable(resourceValues.get(i)));
            // 封装图片ID与图片本身
            Bitmap bm2 = drawableToBitmap(context.getResources().getDrawable(resourceValues.get(i+1)));
            PieceImage pieceImage = new PieceImage(bm, i);
            PieceImage pieceImage2 = new PieceImage(bm2, i);
            result.add(pieceImage);
            result.add(pieceImage2);
        }
        Collections.shuffle(result);
        return result;
    }

    /**
     * 将Drawable转换为Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
//        Bitmap bitmap = Bitmap.createBitmap(
//                drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight(),
//                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
//                        : Bitmap.Config.RGB_565);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(GameConf.PIECE_WIDTH, GameConf.PIECE_HEIGHT, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, GameConf.PIECE_WIDTH, GameConf.PIECE_HEIGHT);
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 获取选中标识的图片
     *
     * @param context
     * @return 选中标识的图片
     */
    public static Bitmap getSelectImage(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//表示只获取Bitmap的尺寸,而不获取bitmap
        BitmapFactory.decodeResource(context.getResources(), R.drawable.selected,options);
        int originWidth = options.outWidth;
        int originHeight = options.outHeight;
        int sampleRatio = 1;
        float widthRatio = 1.0f;
        float heightRatio = 1.0f;
        int realWidth = 120;
        int realHeight = 120;
//分别计算宽高的比率，然后从中取最小值，注意分子和分母的位置，采样率最后是取倒数的，因此使用大值/小值的方法

            sampleRatio = (int) Math.min(2,2);

        options.inSampleSize = sampleRatio;
        options.inJustDecodeBounds = false;
//目标图片的长度和宽度，这两个值可以是手机的屏幕大小，或者是显示bitmap的ImageView的大小


        return BitmapFactory.decodeResource(context.getResources(), R.drawable.selected,options);
    }
}
