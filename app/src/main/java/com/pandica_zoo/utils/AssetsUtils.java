package com.pandica_zoo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public static String readJsonFromFile(Context context)
    {
        File file = new File(context.getFilesDir(), "database.json");
        if(file.exists()) {
            Log.d("myTag", "This is my message");
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file.getAbsolutePath());
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                inputStream.close();
                return new String(buffer, "UTF-8");

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else { //read from json
            AssetManager assetManager = context.getAssets();
            try {
                InputStream inputStream = assetManager.open("db.json");
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                inputStream.close();
                return new String(buffer, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static boolean writeJsonFile(Context context, String jsonData) {
        try {
            File file = new File(context.getFilesDir(), "database.json");
            if(file.exists()) {
                FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
                        //context.openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(jsonData.getBytes());
                outputStream.close();
                return true;
            }
            else {
                return false;
            }
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
