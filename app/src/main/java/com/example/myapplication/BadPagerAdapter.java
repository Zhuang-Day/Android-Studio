package com.example.myapplication;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class BadPagerAdapter extends PagerAdapter {
    private List<View> mPager;//管理分頁陣列
    private int childCount = 0;//取得現在分頁位置

    public BadPagerAdapter(List<View> mPager) {
        //請記得新增建構子喔！(ﾟAﾟ;)
        this.mPager = mPager;//分頁陣列要由MainActivity傳入

    }

    @Override
    public int getItemPosition(@NonNull Object object) {//取得分頁位置
        if (childCount>0){
            childCount --;
            return POSITION_NONE;
        }
        return  super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return mPager.size();
    }//填入陣列長度
    /**再加入....↓*/
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mPager.get(position).setTag(position);
        ((ViewPager) container).addView(mPager.get(position));
        return mPager.get(position);//跑回圈增加View
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;//不管他，直接照打
    }

    @Override
    public void notifyDataSetChanged() {
        childCount = getCount();//動態新增
        super.notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //設置TabLayout上面的文字標籤內容
        String food="已過期食物";
        return food;
    }

    /**再加入....↑*/
}
