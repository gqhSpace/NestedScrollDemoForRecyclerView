package ljc.example.com.nestedscrollview.scenetwo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import ljc.example.com.nestedscrollview.sceneone.adapter.ParentRecyclerViewAdapter;
import ljc.example.com.nestedscrollview.sceneone.view.ChildRecyclerView;

/**
 * Created by guanqinghua on 2021/6/24
 * Describe:
 */
public class SecondOuterRecyclerView extends RecyclerView {

    public SecondOuterRecyclerView(Context context) {
        super(context);
    }

    public SecondOuterRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull  RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull  RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isScrollTop = !canScrollVertically(-1);
                if(isScrollTop){ //解决嵌套刷新视图惯性滑动到顶后无法立刻刷新的问题
                    stopNestedScroll();
                    setNestedScrollingEnabled(false);
                }else{
                    if(!isNestedScrollingEnabled()){ //setNestedScrollingEnabled(true)会调用ViewCompat.stopNestedScroll(mView);不能直接调
                        setNestedScrollingEnabled(true);
                    }
                }
            }
        });
    }
}
