package com.pandica_zoo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.pandica_zoo.models.AnimalList;
import com.pandica_zoo.models.EventList;
import com.pandica_zoo.models.JsonFile;
import com.pandica_zoo.models.PackageList;
import com.pandica_zoo.models.UserList;

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

    private static Gson gson = new Gson();
    public static String readJsonFromAssetsFile(Context context, String filename) {
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

    public static JsonFile readJsonFromFile(Context context)
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

                String json = new String(buffer, "UTF-8");
                UserList userList = gson.fromJson(json, UserList.class);
                EventList eventList = gson.fromJson(json, EventList.class);
                PackageList packageList = gson.fromJson(json, PackageList.class);
                AnimalList animalList = gson.fromJson(json, AnimalList.class);

                return new JsonFile(userList,eventList,packageList,animalList);

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

                String json = new String(buffer, "UTF-8");
                UserList userList = gson.fromJson(json, UserList.class);
                EventList eventList = gson.fromJson(json, EventList.class);
                PackageList packageList = gson.fromJson(json, PackageList.class);
                AnimalList animalList = gson.fromJson(json, AnimalList.class);

                return new JsonFile(userList,eventList,packageList,animalList);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static String prepareJsonString(JsonFile jsonFile)
    {
        //get all the model lists from the jsonFile
        String json1 = gson.toJson(jsonFile.getUsersList());
        String json2 = gson.toJson(jsonFile.getPackagesList());
        String json3 = gson.toJson(jsonFile.getEventsList());
        String json4 = gson.toJson(jsonFile.getAnimalsList());

        //remove the unnecessary {} and add , for correct json format
        return json1.substring(0,json1.length()-1) + ","
                + json2.substring(1,json2.length()-1) + ","
                + json3.substring(1,json3.length()-1) + ","
                + json4.substring(1,json4.length());
    }

    public static boolean writeJsonFile(Context context, String jsonData) {
        try {
            File file = new File(context.getFilesDir(), "database.json");
            if(file.exists()) {
                FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
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

    public static void updateJsonFile(JsonFile jsonFile,Context context)
    {
        String json = AssetsUtils.prepareJsonString(jsonFile);
        if (AssetsUtils.isValidJson(json)) {
            boolean success = AssetsUtils.writeJsonFile(context, json);
            if (success) {
                Log.d("WriteJsonFile", "Write success: " + success);
            } else {
                Log.e("WriteJsonFile", "Write success: " + success);
            }
        }
    }
}
