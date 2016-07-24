package com.bobyk.channels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

/**
 * Created by bobyk on 22/07/16.
 */
public class MainActivity extends AppCompatActivity{
    private DrawerBuilder drawerBuilder;
    private Drawer drawer;
    private Toolbar toolbar;
    private ViewPager channelPager;
    private ChannelAdapter channelAdapter;
    Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buildDrawer();

        channelPager = (ViewPager) findViewById(R.id.viewPager);

        channelAdapter = new ChannelAdapter(getSupportFragmentManager());
        ArrayList<ChannelFragment> channelFragments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            channelFragments.add(ChannelFragment.newInstance(i, "tit;e " + i));
        }
        channelAdapter.setChannels(channelFragments);
        channelPager.setAdapter(channelAdapter);

        syncData();
    }

    private void buildDrawer(){
        PrimaryDrawerItem channels = new PrimaryDrawerItem().withIcon(R.drawable.tv_channel_icon).withIdentifier(1).withName("Channels");
        PrimaryDrawerItem category = new PrimaryDrawerItem().withIcon(R.drawable.category_icon).withIdentifier(2).withName("Category");
        PrimaryDrawerItem favorites = new PrimaryDrawerItem().withIcon(R.drawable.star_icon).withIdentifier(3).withName("Favorites");
        PrimaryDrawerItem programSchedule = new PrimaryDrawerItem().withIcon(R.drawable.program_schedule_icon).withIdentifier(4).withName("Program Schedule");

        drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        channels, category, favorites, programSchedule
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position){
                            case 1:
                                return false;
                            case 2:
                                return false;
                            case 3:
                                return false;
                            case 4:
                            default:
                                return false;
                        }
                    }
                });
        drawer = drawerBuilder.build();
    }

    private void syncData(){
        Intent i = new Intent(MainActivity.this, LoadService.class);
        startService(i);
    }
}
