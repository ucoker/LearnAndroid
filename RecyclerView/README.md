# Learn RecyclerView
##目的
用于学习RecyclerView，下面介绍可能存在个人见解，请见谅。
##工具
* Android Studio 1.5.1
* Git客户端

## 说明
* RecycleView 

 ~~~java
 //必要设置，分别指定布局和数据
 mRecyclerView.setLayoutManager(mLayoutManager);
 mRecyclerView.setAdapter(mAdapter);
 
 //非必需
 //可对Item进行绘制图形，比如加下划线，DividerGridItemDecoration 非官方API带，需自己写。
 mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));  
 //设置Item动画
mRecyclerView.setItemAnimator(new DefaultItemAnimator());
 
 ~~~
* LayoutManager  
分成LinearLayoutManager，GridLayoutManager以及StaggeredGridLayoutManager。

	~~~java
	//LinearLayoutManager (Context context)
	mLayoutManager = new LinearLayoutManager(getActivity());
	
	//GridLayoutManager (Context context, int spanCount)
	mLayoutManager = new GridLayoutManager(getActivity(), 4);
	
	//StaggeredGridLayoutManager (int spanCount, int orientation)
	mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
	~~~
* Adapter  
Adapters provide a binding from an app-specific data set to views that are displayed within a RecyclerView.



##流程
> 创建工程 RecyclerView  

* 创建
    
> 加入RecyclerView依赖  

1. 在build.grade（Module: app） 加入
  
	~~~java
	compile 'com.android.support:appcompat-v7:23.2.0'
	compile 'com.android.support:recyclerview-v7:23.2.0'
	~~~
	
> 	创建菜单选项

1. 新建menu\_main\_activity.xml, 加入

	~~~java
	<?xml version="1.0" encoding="utf-8"?>
	<menu xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:app="http://schemas.android.com/apk/res-auto">
	
	    <item
	        android:id="@+id/id_action_listview"
	        android:orderInCategory="100"
	        android:title="ListView"
	        app:showAsAction="never"/>
	
	    <item
	        android:id="@+id/id_action_gridview"
	        android:orderInCategory="100"
	        android:title="GirdView"
	        app:showAsAction="never"/>
	
	
	    <item
	        android:id="@+id/id_action_staggeredgridview"
	        android:orderInCategory="100"
	        android:title="StaggeredGridView"
	        app:showAsAction="never"/>
	
	</menu>
	
	~~~
	
2. 在MainActivity 创建菜单
	
	~~~java
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }
	
	~~~
3. 对菜单按钮做相应响应处理

	~~~java
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
	~~~

> 创建Recycler View 容器     

1. 修改 res/layout/activity\_main.xml  
目的为了将 recyclerview\_content\_fragment 的fragment 作为Recycler View 的容器

	~~~java
	<?xml version="1.0" encoding="utf-8"?>
	<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    tools:context="com.learn.recyclerview.MainActivity">
	
	    <FrameLayout
	        android:id="@+id/recyclerview_content_fragment"
	        android:layout_weight="2"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent" />
	</RelativeLayout>
	~~~  
2. 在MainActivity加入Recycler View

	~~~java
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
	~~~

> 创建Recycle View fragment

1. 创建fragment\_recycler\_view.xml

	~~~java
	<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    tools:context="com.learn.recyclerview.RecyclerViewFragment">
	
	    <!-- TODO: Update blank fragment layout -->
	    <android.support.v7.widget.RecyclerView
	        android:id="@+id/recyclerView"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"/>
	
	</FrameLayout>
	~~~
2. 创建RecyclerViewFragment extends Fragment，在onCreateView加入

	~~~java
	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
		
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        
        //下面这句对 RecyclerView 设置 LayoutManager、Adapter
        setRecyclerViewLayoutManager(mManagerType);

        return rootView;
    }
   ~~~

>创建Adapter

1. 创建CustomAdapter继承RecyclerView.Adapter<CustomAdapter.ViewHolder>
	* 在res/layout 创建view\_holder\_row_item.xml  
	在xml放入要显示的元素，比如图片、文字等，并做好布局处理。xml有点类似iOS中的xib文件。
	* 创建ViewHolder  
	ViewHolder 有点类似 iOS中的tableViewCell，用于跟xml文件对应起来。
2. 完善CustomAdapter
	* CustomAdapter 需要重载三个API分别为
	
		~~~java
		//将ViewHolder与xml对应起来
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
		
		//将数据装载进ViewHolder
		public void onBindViewHolder(final ViewHolder holder, final int position)
		
		//返回多少元素
		public int getItemCount()
		~~~
	
	* CustomAdapter中重载onCreateViewHolder  
		将xml文件与ViewHolder关联
		
		~~~java
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// Create a new view.
		View v = LayoutInflater.from(parent.getContext())
		        .inflate(R.layout.view_holder_row_item, parent, false);
		return new ViewHolder(v);
		}
		~~~
	* 编写数据模型(根据xml的元素来编写需要的数据模型)  
		编写下面模型，并创建一个ArrayList装载数据给ViewHolder使用。
	
		~~~java
		public static class Bean implements Serializable{
		    public String title;
		    public String subTitle;
		    public int height;
		    public int width;
		
		    public Bean() {
		        super();
		    }
		
		    @Override
		    public String toString() {
		        return "title:" + title + "\n" +
		                "sub title:" + subTitle;
		    }
		}
		~~~
	* 编写事件监听，如点击、长按

		~~~java
		public interface OnItemClickLitener {
		    void onItemClick(View view, int position);
		    void onItemLongClick(View view, int position);
		}
		    
		private OnItemClickLitener mOnItemClickLitener;
		
		public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
		    this.mOnItemClickLitener = mOnItemClickLitener;
		}	
		~~~
	* 重载onBindViewHolder，根据数据来对ViewHolder进行赋值，例如：

		~~~java
		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position) {
			Bean bean = mBeans.get(position);
			holder.getTitle().setText(bean.title);
			holder.getSubTitle().setText(bean.subTitle);
			if (mOnItemClickLitener != null) {
            holder.getRelativeLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(v, pos);
                }
            });

            holder.getRelativeLayout().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
		~~~
		
>最后

1. 完成RecyclerViewFragment的编写  
  在RecyclerViewFragment中，装载Adapter、LayoutManager。
2. 在MainActivity使用RecyclerViewFragment
3. 具体见工程代码。

>小结

* RecyclerView 最必需的为LayoutManager来管理布局、Adapter来管理数据。
* 在Adapter中，ViewHolder就类似iOS中的tableViewCell，xml文件类似iOS中的xib文件。
* 在Adapter中，需要关联xml与ViewHolder，流畅度的处理可以在onBindViewHolder中，尽量少做耗时操作。若有数据计算，可通过事先计算并存储到数据模型内。


##参考
[官方 RecyclerView Sample](http://developer.android.com/intl/zh-cn/samples/RecyclerView/index.html)  
[Android RecyclerView 使用完全解析 体验艺术般的控件](http://blog.csdn.net/lmj623565791/article/details/45059587)  
[RecyclerView使用详解](http://frank-zhu.github.io/android/2015/01/16/android-recyclerview-part-1/)

##License
MIT