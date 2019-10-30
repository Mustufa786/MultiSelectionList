package com.codixlab.multiselectionlist;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Util {

    public static List<Inbox> getInboxData(Context ctx) {
        List<Inbox> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.people_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);
        String date_arr[] = ctx.getResources().getStringArray(R.array.general_date);

        for (int i = 0; i < drw_arr.length(); i++) {
            Inbox obj = new Inbox();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.from = name_arr[i];
            obj.email = "abc@gmail.com";
            obj.message = ctx.getResources().getString(R.string.lorem_ipsum);
            obj.date = date_arr[randInt(date_arr.length - 1)];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        Collections.shuffle(items);
        return items;
    }

    public static int randInt(int max) {
        Random r = new Random();
        int min = 0;
        return r.nextInt((max - min) + 1) + min;
    }

    public static void toggleStatusBarColor(Activity activity, int color) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(activity.getResources().getColor(color));
    }
}
