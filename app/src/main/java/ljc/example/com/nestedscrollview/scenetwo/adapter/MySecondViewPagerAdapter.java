package ljc.example.com.nestedscrollview.scenetwo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import ljc.example.com.nestedscrollview.sceneone.item.PagerItemView;
import ljc.example.com.nestedscrollview.scenetwo.item.SecondPagerItemView;

/**
 * Created by guanqinghua on 2021/6/24
 * Describe:
 */
public class MySecondViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<SecondPagerItemView> itemViews = new ArrayList<>();

    public MySecondViewPagerAdapter(Context context) {
        mContext = context;
        itemViews.clear();
        for (int i = 0; i < 4; i++) {
            itemViews.add(new SecondPagerItemView(context));
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SecondPagerItemView itemView = itemViews.get(position);
        ViewGroup parent = (ViewGroup) itemView.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        switch (position) {
            case 0:
                itemView.setData("猜你喜欢",position);
                break;
            case 1:
                itemView.setData("菜谱",position);
                break;
            case 2:
                itemView.setData("水果",position);
                break;
            case 3:
                itemView.setData("熟食",position);
                break;

        }

        container.addView(itemView);
        return itemViews.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        try {
            View destroyView = (View) object;
            if (destroyView.isFocused()) {
                container.clearChildFocus(destroyView);
            }
            container.removeView(destroyView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<SecondPagerItemView> getItems() {
        return itemViews;
    }
}
