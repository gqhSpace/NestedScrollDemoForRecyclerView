package ljc.example.com.nestedscrollview.scenetwo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import ljc.example.com.nestedscrollview.sceneone.adapter.ParentRecyclerViewAdapter;
import ljc.example.com.nestedscrollview.sceneone.view.ChildRecyclerView;
import ljc.example.com.nestedscrollview.scenetwo.adapter.SecondOuterRecyclerViewAdapter;

/**
 * Created by guanqinghua on 2021/6/24
 * Describe:
 */
public class SecondParentView extends LinearLayout implements NestedScrollingParent2 {

    private SecondOuterRecyclerView outerRecyclerView;

    public SecondParentView(Context context) {
        this(context, null);
    }

    public SecondParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.i("---++", "onStartNestedScroll ");
        if (child instanceof SecondInnerRecyclerView || child instanceof SecondOuterRecyclerView) {
            return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        } else {
            return false;
        }
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

        Log.i("---++", "onStopNestedScroll ");
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.i("---++", "onNestedScroll ");
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.i("---++", "onNestedPreScroll --1");
        if (target instanceof SecondOuterRecyclerView) {
            SecondOuterRecyclerView outerRecyclerView = (SecondOuterRecyclerView) target;
            SecondInnerRecyclerView innerRecyclerView = findInnerRecyclerView();
            boolean innerNeedScrollForUpScroll = dy > 0 && innerRecyclerView != null && !outerRecyclerView.canScrollVertically(1);//上滑，并外层滑动到底

            boolean innerNeedScrollForDownScroll = dy < 0 && innerRecyclerView != null && innerRecyclerView.canScrollVertically(-1);//下滑，并内层未滑动到顶
            if (innerNeedScrollForUpScroll || innerNeedScrollForDownScroll) {
                Log.i("-----", "onNestedPreScroll --2");
                innerRecyclerView.scrollBy(0, dy);
                consumed[1] = dy;
            }
        }

        if (target instanceof SecondInnerRecyclerView) {
            SecondInnerRecyclerView innerRecyclerView = (SecondInnerRecyclerView) target;
            boolean outerNeedScrollForUpScroll = dy > 0 && outerRecyclerView.canScrollVertically(1);//上滑&&父列表未滑动到底
            boolean outerNeedScrollForDownScroll = dy < 0 && !innerRecyclerView.canScrollVertically(-1);//下滑&&子列表滑动到顶
            if (outerNeedScrollForUpScroll || outerNeedScrollForDownScroll) {
                outerRecyclerView.scrollBy(0, dy);
                consumed[1] = dy;
            }
        }
    }

    public void setOuterRecyclerView(SecondOuterRecyclerView recyclerView) {
        this.outerRecyclerView = recyclerView;
    }

    private SecondInnerRecyclerView findInnerRecyclerView() {
        if (outerRecyclerView != null && outerRecyclerView.getAdapter() instanceof SecondOuterRecyclerViewAdapter) {
            return ((SecondOuterRecyclerViewAdapter) outerRecyclerView.getAdapter()).getChildRecyclerView();
        }
        return null;
    }
}
