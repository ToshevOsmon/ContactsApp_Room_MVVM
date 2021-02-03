package com.uzandroid.contactapp.conventer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class ImageConverter {

    @TypeConverter
    public static byte[] fromBitmap(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();

    }

@TypeConverter
    public static Bitmap toBitmap(byte[] images) {

        return BitmapFactory.decodeByteArray(images, 0, images.length);

    }

}
