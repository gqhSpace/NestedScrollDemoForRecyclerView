package ljc.example.com.nestedscrollview.sceneone.item;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import java.util.List;

import androidx.viewpager.widget.ViewPager;
import ljc.example.com.nestedscrollview.R;
import ljc.example.com.nestedscrollview.sceneone.adapter.MyViewPagerAdapter;
import ljc.example.com.nestedscrollview.sceneone.view.ParentRecyclerView;
import ljc.example.com.nestedscrollview.utils.AndroidUtil;
import ljc.example.com.nestedscrollview.sceneone.view.ChildRecyclerView;


public class LastRecyclerViewItem extends RelativeLayout {
    ViewPager mViewPager;
    int viewPagerCount;
    MyViewPagerAdapter myViewPagerAdapter;
    ChildRecyclerView mChildRecyclerView;

    public LastRecyclerViewItem(Context context) {
        super(context, null);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.last_recycler_view_item, this);
        mViewPager = findViewById(R.id.mViewPager);
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mViewPager.getLayoutParams();
        layoutParams.height = AndroidUtil.getScreenHeight(getContext());
        mViewPager.setLayoutParams(layoutParams);
        myViewPagerAdapter = new MyViewPagerAdapter(getContext());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("----", position + "  onPageSelected");
                if (myViewPagerAdapter != null) {
                    List<PagerItemView> items = myViewPagerAdapter.getItems();
                    mChildRecyclerView = items.get(position).getChildRecyclerView();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public void setData(int viewPagerCount) {
        this.viewPagerCount = viewPagerCount;
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        if (myViewPagerAdapter != null) {
            List<PagerItemView> items = myViewPagerAdapter.getItems();
            mChildRecyclerView = items.get(0).getChildRecyclerView();
        }
    }

    public ChildRecyclerView getChildRecyclerView() {

        return mChildRecyclerView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) { //内层recyclerview可以滑动的处理，不放在内层recyclerview处理是因为那样会拦截横滑
        getParent().requestDisallowInterceptTouchEvent(true);//不允许父拦截
        return super.onInterceptTouchEvent(e);
    }
}
