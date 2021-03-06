package com.example.adminvansales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.adminvansales.model.Request;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.widget.LinearLayout.VERTICAL;
import static com.example.adminvansales.ImportData.listId;
import static com.example.adminvansales.ImportData.listRequest;

public class MainActivity extends AppCompatActivity {
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<Request> requestList1;
    public  static  boolean isListUpdated=false;

    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialView();
//        getData();
        fillData();
        String s="";
       /* timer = new Timer();


        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable(){

                    @Override
                    public void run(){
                        // update ui here
                        if (isNetworkAvailable()) {
//                            getData();
                            if(listId.size()!=0)
                            {

                                fillData();
//                                updateSeenOfRow();
                            }
                        }

                    }
                });
            }

        }, 0, 3000);*/




    }

    private void getData() {
        ImportData importData=new ImportData(MainActivity.this);
        importData.getListRequest();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void updateSeenOfRow() {

        ExportData exportData=new ExportData(MainActivity.this);
        exportData.updateRowSeen(listId);
    }

    private void fillData() {
        isListUpdated=false;
        fillListNotification(listRequest);
    }


    private void initialView() {
//        databaseHandler=new DatabaseHandler(RequestCheque.this);
        recyclerView = findViewById(R.id.recycler);

    }

    @SuppressLint("WrongConstant")
    private void fillListNotification(ArrayList<Request> notifications) {
//        notifiList1.clear();
//        notifiList1 = notifications;
//        Log.e("fillListNotification", "" + .size());
        layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(VERTICAL);
        runAnimation(recyclerView, 0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();


    }
    private void runAnimation(RecyclerView recyclerView, int type) {
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = null;
        if (type == 0) {
            controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_filldown);
            RequestAdapter notificationAdapter = new RequestAdapter(MainActivity.this, listRequest);
            recyclerView.setAdapter(notificationAdapter);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i=new Intent(MainActivity.this,HomeActivity.class);
        startActivity(i);
    }
}
