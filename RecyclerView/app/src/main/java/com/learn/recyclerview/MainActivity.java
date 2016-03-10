package com.learn.recyclerview;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private RecyclerViewFragment mRecyclerViewFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RecyclerViewFragment fragment = new RecyclerViewFragment();
            transaction.replace(R.id.recyclerview_content_fragment, fragment);
            transaction.commit();
            mRecyclerViewFragment = fragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_gridview:
                mRecyclerViewFragment.setRecyclerViewLayoutManager(RecyclerViewFragment.LayoutManagerType.GRID_LAYOUT_MANAGER);
                break;

            case R.id.id_action_listview:
                mRecyclerViewFragment.setRecyclerViewLayoutManager(RecyclerViewFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER);
                break;

            case R.id.id_action_staggeredgridview:
                mRecyclerViewFragment.setRecyclerViewLayoutManager(RecyclerViewFragment.LayoutManagerType.STAGGERED_GRID_MANAGER);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
