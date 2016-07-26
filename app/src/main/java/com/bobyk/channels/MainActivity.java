package com.bobyk.channels;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bobyk.channels.fragments.ProgramFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import com.bobyk.channels.fragments.*;

/**
 * Created by bobyk on 22/07/16.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private DrawerBuilder drawerBuilder;
    private Drawer drawer;
    private Toolbar toolbar;

    public static final int ID_CHANNELS = 228;
    public static final int ID_CATEGORIES = 322;
    public static final int ID_PROGRAMS = 488;
    public static final int ID_FAVORITE = 577;
    public static final int DELETE_CHANNELS = 666;
    public static final int DELETE_CATEGORIES = 667;
    public static boolean doneLoadSchedule = false;

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favorite:
                syncData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buildDrawer();
        syncData();

        getSupportLoaderManager().initLoader(ID_CHANNELS, null, this);
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
                                loadChannelsFragment();
                                return false;
                            case 2:
                                loadCategoriesFragment();
                                return false;
                            case 3:
                                loadFavoritesFragment();
                                return false;
                            case 4:
                                if (doneLoadSchedule) loadScheduleFragment();
                                else Toast.makeText(getApplicationContext(),"Loading...", Toast.LENGTH_SHORT).show();
                                return false;
                            default:
                                return false;
                        }
                    }
                });
        drawer = drawerBuilder.build();
    }

    private void loadScheduleFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, ProgramFragment.newInstance());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void loadCategoriesFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, CategoriesFragment.newInstance());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void loadChannelsFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, ChannelsFragment.newInstance(""));
        ft.addToBackStack(null);
        ft.commit();
    }

    private void loadFavoritesFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, FavoritesFragment.newInstance());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void syncData(){
        doneLoadSchedule = false;
        clearDB();
        Intent i = new Intent(MainActivity.this, LoadService.class);
        startService(i);
    }

    private void clearDB(){
        getContentResolver().delete(ChannelContract.ChannelEntry.CONTENT_URI, null, null);
        getContentResolver().delete(ChannelContract.CategoryEntry.CONTENT_URI, null, null);
        getContentResolver().delete(ChannelContract.ProgramEntry.CONTENT_URI, null, null);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case ID_CHANNELS:
                return new CursorLoader(this, ChannelContract.ChannelEntry.CONTENT_URI, null, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
