package com.pandica_zoo.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class AssetsUtils {
    public static String readJsonFile(Context context, String filename) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(filename);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean writeJsonFile(Context context, String filename, String jsonData) {
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write(jsonData);
            fw.close();

            /*FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonData.getBytes());
            outputStream.close();*/
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean isValidJson(String jsonData) {
        try {
            new JSONObject(jsonData);
            return true;
        } catch (JSONException e) {
            // JSON data is not valid
            e.printStackTrace();
            return false;
        }
    }
}
