package com.example.atvddarqsoftw;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MyIntentService extends IntentService {

    public MyIntentService() {

        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null){

            ActivityManager activityManager =(ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
            if(activityManager != null) {
                List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();
                String name;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    name = tasks.get(0).getTaskInfo().topActivity.getClassName();
                }else{
                    name = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
                }
                Toast.makeText(this, "Nome da Tela: " +name, Toast.LENGTH_SHORT).show();
            }
            }
        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service destruído ", Toast.LENGTH_SHORT).show();
    }
}



