package ljc.example.com.nestedscrollview.scenetwo.view;

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
public class SecondInnerRecyclerView extends RecyclerView {

    public SecondInnerRecyclerView(Context context) {
        this(context, null);
    }

    public SecondInnerRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        stopScroll();//防止子列表嵌套外层滑动时，再触摸子列表产生外层和内层的同时滑动
        return super.onInterceptTouchEvent(e);
    }

}
