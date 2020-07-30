package comp3350.mealbuddy.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ChangeActivityHelper {

    public static void changeActivity(Context src, Class dest, int day, String username){
        Intent intent = new Intent(src, dest);
        intent.putExtra("dayOfYear", day);
        intent.putExtra("username", username);
        src.startActivity(intent);
    }

    public static void changeActivity(Context src, Class dest, String username) {
        Intent intent = new Intent(src, dest);
        intent.putExtra("username", username);
        src.startActivity(intent);
    }

    public static void changeActivity(Context src, Class dest){
        Intent intent = new Intent(src, dest);
        src.startActivity(intent);
    }
}
