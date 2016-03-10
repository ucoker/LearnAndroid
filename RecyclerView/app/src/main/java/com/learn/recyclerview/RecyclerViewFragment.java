package com.learn.recyclerview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


/**
 * create an instance of this fragment.
 */
public class RecyclerViewFragment extends Fragment {

    public enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER,
        STAGGERED_GRID_MANAGER
    }

    private LayoutManagerType mManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

    ArrayList<CustomAdapter.Bean> beans;

    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

    }

    private ArrayList<CustomAdapter.Bean>createNormalBeans() {
        ArrayList<CustomAdapter.Bean> beans = new ArrayList<CustomAdapter.Bean>();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        for (int i = 0; i<100; i++) {
            int pixelsHeight = (int) (160 * scale + 0.5f);
            int pixelswidth = (int) (160 * scale + 0.5f);

            CustomAdapter.Bean bean = new CustomAdapter.Bean();
            bean.title = "title_" + i;
            bean.subTitle = "sub_title_" + i;
            bean.width = pixelswidth;
            bean.height = pixelsHeight;
            beans.add(bean);
        }
        return beans;
    }
    private ArrayList<CustomAdapter.Bean>createStaggeredBeans() {
        ArrayList<CustomAdapter.Bean> beans = new ArrayList<CustomAdapter.Bean>();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        for (int i = 0; i<100; i++) {
            int height =(int)(60 + Math.random()*100);
            int pixelsHeight = (int) (height * scale + 0.5f);
            int pixelswidth = (int) (160 * scale + 0.5f);

            CustomAdapter.Bean bean = new CustomAdapter.Bean();
            bean.title = "title_" + i;
            bean.subTitle = "sub_title_" + i;
            bean.width = pixelswidth;
            bean.height = pixelsHeight;
            beans.add(bean);
        }
        return beans;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        setRecyclerViewLayoutManager(mManagerType);

        return rootView;
    }


    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        mManagerType = layoutManagerType;
        switch (mManagerType) {
            case LINEAR_LAYOUT_MANAGER:
                this.beans = createNormalBeans();
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;

            case GRID_LAYOUT_MANAGER:
                this.beans = createNormalBeans();
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                break;

            case STAGGERED_GRID_MANAGER:
                this.beans = createStaggeredBeans();
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
            default:
                break;
        }

        mAdapter = new CustomAdapter(beans);
        mAdapter.setOnItemClickLitener(new CustomAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),
                        "当前点击：" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),
                        "长按点击：" + position , Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}
