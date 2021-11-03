package ljc.example.com.nestedscrollview.scenetwo.item;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import ljc.example.com.nestedscrollview.R;
import ljc.example.com.nestedscrollview.sceneone.adapter.ChildRecyclerViewAdapter;
import ljc.example.com.nestedscrollview.scenetwo.view.SecondInnerRecyclerView;


public class SecondPagerItemView extends RelativeLayout {
    SecondInnerRecyclerView mChildRecyclerView;
    String itemType;


    public SecondPagerItemView(Context context) {
        super(context, null);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.second_pager_item_view, this);
        mChildRecyclerView = findViewById(R.id.mChildRecyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mChildRecyclerView.setLayoutManager(linearLayoutManager);
        //添加Android自带的分割线
        mChildRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    public void setData(String itemType, int position) {
        this.itemType = itemType;
        switch (position) {
            case 0:
                mChildRecyclerView.setBackgroundColor(getResources().getColor(R.color.colorA));
                break;
            case 1:
                mChildRecyclerView.setBackgroundColor(getResources().getColor(R.color.colorB));
                break;
            case 2:
                mChildRecyclerView.setBackgroundColor(getResources().getColor(R.color.colorC));
                break;
            case 3:
                mChildRecyclerView.setBackgroundColor(getResources().getColor(R.color.colorD));
                break;

        }

        final ChildRecyclerViewAdapter adapter = new ChildRecyclerViewAdapter(getContext(), 100,itemType);
        mChildRecyclerView.setAdapter(adapter);
    }

    public SecondInnerRecyclerView getChildRecyclerView() {
        return mChildRecyclerView;
    }


}
