package ljc.example.com.nestedscrollview.sceneone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ljc.example.com.nestedscrollview.sceneone.FlingHelper;
import ljc.example.com.nestedscrollview.sceneone.FlingHelper2;

/**
 * Created by guanqinghua on 2021/6/24
 * Describe:
 */
public class ChildRecyclerView extends RecyclerView {
    ParentRecyclerView parentRecyclerView;
    FlingHelper2 flingHelper;
    boolean isStartFling;
    int totalDy;
    int mVelocity;

    public ChildRecyclerView(Context context) {
        this(context,null);
    }

    public ChildRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        totalDy = 0;
        isStartFling = false;
        flingHelper = new FlingHelper2(context);
        getParentRecyclerView();
        initScrollListener();
    }

    private void initScrollListener() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE) {
                    dispatchParentFling();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull  RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isStartFling) {
                    totalDy = 0;
                    isStartFling = false;
                }
                totalDy += dy;
            }
        });
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        if (!isAttachedToWindow()) {
            return false;
        }
        boolean fling = super.fling(velocityX, velocityY);
        if (!fling || velocityY >= 0) {
            mVelocity = 0;
        } else {
            isStartFling = true;
            mVelocity = velocityY;
        }
        return fling;
    }

    private void dispatchParentFling() {
        if(parentRecyclerView==null){
            getParentRecyclerView();
        }
        if (!canScrollVertically(-1) && mVelocity != 0) {
            double flingDistance = flingHelper.getSplineFlingDistance(mVelocity);
            parentRecyclerView.fling(0, -flingHelper.getVelocityByDistance(flingDistance + totalDy));
        }
        totalDy = 0;
        mVelocity = 0;

    }


    private void getParentRecyclerView() {
        ViewParent parent = this;
        while (parent != null && !(parent instanceof ParentRecyclerView)) {
            parent = parent.getParent();
        }
        if (parent != null) {
            parentRecyclerView = (ParentRecyclerView) parent;
        }

    }



//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//
//        // TODO: 2021/9/22 test
//        stopScroll();
//        boolean isChildScrollTop = !canScrollVertically(-1);//子滑动到顶
//        if(isChildScrollTop){
//            getParent().requestDisallowInterceptTouchEvent(false);
//        }else{
//            getParent().requestDisallowInterceptTouchEvent(true);
//        }
//        return super.onInterceptTouchEvent(e);
//    }
}
