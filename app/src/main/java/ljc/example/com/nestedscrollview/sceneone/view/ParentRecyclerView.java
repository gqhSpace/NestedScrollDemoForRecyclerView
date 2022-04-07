package ljc.example.com.nestedscrollview.sceneone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ljc.example.com.nestedscrollview.sceneone.FlingHelper;
import ljc.example.com.nestedscrollview.sceneone.FlingHelper2;
import ljc.example.com.nestedscrollview.sceneone.adapter.ParentRecyclerViewAdapter;

/**
 * Created by guanqinghua on 2021/6/24
 * Describe:
 */
public class ParentRecyclerView extends RecyclerView implements NestedScrollingParent2 {
    Context mContext;
    FlingHelper2 flingHelper;


    private NestedScrollingParentHelper mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);


    public ParentRecyclerView(Context context) {
        this(context,null);
    }

    public ParentRecyclerView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        flingHelper = new FlingHelper2(mContext);
        addScrollListener();
    }


    int status;

    private void addScrollListener() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE) {
                    Log.i("-----111", "SCROLL_STATE_IDLE ");
                    dispatchParentFling();
                }
                super.onScrollStateChanged(recyclerView, newState);
                status = newState;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.i("-----", "scrollBy Y = " + dy + "  state =" + status);
            }
        });
    }



    //是否滚动到底
    private boolean isScrollEnd() {
        return !canScrollVertically(1);
    }

    private float oldX = 0;
    private float oldY = 0;
    private float newX = 0;
    private float newY = 0;

    private float lastY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (!(ev == null || ev.getAction() == MotionEvent.ACTION_MOVE)) {
            //在非ACTION_MOVE的情况下，将lastY置为0
            lastY = 0;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = ev.getX();
                oldY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                newX = ev.getX();
                newY = ev.getY();

                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    public void initManager(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
            @Override
            public int scrollVerticallyBy(int dy, Recycler recycler, State state) {
                return super.scrollVerticallyBy(dy, recycler, state);
            }
        };
        linearLayoutManager.setOrientation(VERTICAL);
        setLayoutManager(linearLayoutManager);
    }

    private int mFlingVelocityY;
    private boolean isStartFling;

    @Override
    public boolean fling(int velocityX, int velocityY) {
        boolean fling = super.fling(velocityX, velocityY);
        if (!fling || velocityY <= 0) {
            mFlingVelocityY = 0;
        } else {
            isStartFling = true;
            mFlingVelocityY = velocityY;

        }
//        Log.i("-----111", "fling Y = " + velocityY + "  !fling =" + !fling);
        return fling;
    }

    private void dispatchParentFling() {
        if (isScrollEnd() && mFlingVelocityY != 0) {
//            Log.i("-----111", "fling Y = " + "dispatchParentFling");
            ChildRecyclerView childRecyclerView = findChildRecyclerView();
            if (childRecyclerView != null) {
                double splineFlingDistance = flingHelper.getSplineFlingDistance(mFlingVelocityY);
                childRecyclerView.fling(0, flingHelper.getVelocityByDistance(splineFlingDistance));
//                Log.i("-----111", "mFlingVelocityY = " + mFlingVelocityY);
            }
        }
        mFlingVelocityY = 0;
    }

        @Override
    public boolean onTouchEvent(MotionEvent e) {

        if(lastY ==0){
            lastY = e.getY();
        }
        if(isScrollEnd() &&  e.getAction() == MotionEvent.ACTION_MOVE){
            ChildRecyclerView childRecyclerView = findChildRecyclerView();
            if(childRecyclerView!=null){
//                childRecyclerView.stopScroll();
//                Log.i("-----", "moveY = "+ (lastY-e.getY() ));
                childRecyclerView.scrollBy(0,(int) (lastY-e.getY()));
            }
        }
        lastY = e.getY();
        return super.onTouchEvent(e);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean b = super.onInterceptTouchEvent(e);
        Log.d("--test--", "  parent onTouchEvent -- " + e.getAction() + "   b= " + b);
        return b;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        stopScroll();
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
//        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type);

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
//        Log.i("-----", Log.getStackTraceString(new Throwable()) );
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        ChildRecyclerView childRecyclerView = findChildRecyclerView();
        boolean  parentCanScrollOnSlideDown =  dy < 0 && childRecyclerView != null && !childRecyclerView.canScrollVertically(-1);
        boolean parentCanScrollOnSlideUp = dy > 0 && !isScrollEnd();
        if (parentCanScrollOnSlideDown || parentCanScrollOnSlideUp) { //下滑&&子列表滑动到顶 || 上滑&&父列表未滑动到底
            Log.i("-----", "onNestedPreScroll Y = " + dy );
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    private ChildRecyclerView findChildRecyclerView() {
        if (getAdapter() instanceof ParentRecyclerViewAdapter) {
            return ((ParentRecyclerViewAdapter) getAdapter()).getChildRecyclerView();
        }
        return null;
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return super.dispatchNestedFling(velocityX, velocityY, consumed);
    }
}
