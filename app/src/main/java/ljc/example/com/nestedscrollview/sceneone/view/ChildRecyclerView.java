package ljc.example.com.nestedscrollview.sceneone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by guanqinghua on 2021/6/24
 * Describe:
 */
public class ChildRecyclerView extends RecyclerView {
    ParentRecyclerView parentRecyclerView;

    public ChildRecyclerView(Context context) {
        super(context);
        getParentRecyclerView();
    }

    public ChildRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getParentRecyclerView();
    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if(parentRecyclerView==null){
//            getParentRecyclerView();
//        }
//        boolean isParentScrollBottom = !parentRecyclerView.canScrollVertically(1);//父滑动到底
//        boolean isChildScrollTop = !canScrollVertically(-1);//子滑动到顶
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                if(isParentScrollBottom){
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }else if(isChildScrollTop){
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//
//                break;
//        }
//
//        return super.dispatchTouchEvent(ev);
//    }

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
