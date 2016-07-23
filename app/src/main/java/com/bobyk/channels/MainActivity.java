package com.bobyk.channels;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewParent;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("first");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("second");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("third");
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        drawerBuilder = new DrawerBuilder()
                .withActivity(this)
           //     .withToolbar(toolbar)
                .addDrawerItems(
                        item1, item2, item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position){
                            case 1:
                                return true;
                            case 2:
                                return true;
                            case 3:
                                return true;
                            default:
                                return false;
                        }
                    }
                });
        drawer = drawerBuilder.build();

        channelPager = (ViewPager) findViewById(R.id.viewPager);

        channelAdapter = new ChannelAdapter(getSupportFragmentManager());
        ArrayList<ChannelFragment> channelFragments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            channelFragments.add(ChannelFragment.newInstance(i, "tit;e " + i));
        }
        channelAdapter.setChannels(channelFragments);
        channelPager.setAdapter(channelAdapter);
    }
}
