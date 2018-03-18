package com.example.dingjie.elderlauncher;

/**
 * Created by dingjie on 2018/3/15.
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class AppList extends AppCompatActivity {

    private PackageManager manager;
    private List<Item> apps;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.app_list);
        loadApps();
        loadList();
        addClickListener();


    }
    private void loadApps(){
        manager = getPackageManager();
        apps = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> available = manager.queryIntentActivities(intent,0);
        for(ResolveInfo ri :available){
            Item app = new Item();
            app.label = ri.activityInfo.packageName;
            app.name = ri.loadLabel(manager);
            app.icon = ri.loadIcon(manager);
            apps.add(app);
        }
    }
    private  void loadList(){
        list = (ListView)findViewById(R.id.list);
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this,R.layout.item,apps){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.item,null);

                }
                ImageView appIcon = (ImageView) convertView.findViewById(R.id.icon);
                appIcon.getLayoutParams().height = MainActivity.getScreenHeight()/5;
                appIcon.getLayoutParams().width = MainActivity.getScreenHeight()/5;
                Drawable drawable=  apps.get(position).icon;

                appIcon.setImageDrawable(drawable);

                TextView appName = (TextView)convertView.findViewById(R.id.name);
                appName.setText(apps.get(position).name);

                return convertView;
            }
        };

        list.setAdapter(adapter);

    }
    private void addClickListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = manager.getLaunchIntentForPackage(apps.get(i).label.toString());
                startActivity(intent);
            }
        });
    }
}
