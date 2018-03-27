package cn.yaohl.MayorOnline.ui.personal.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.MayorApplication;
import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseActivity;
import cn.yaohl.MayorOnline.ui.personal.fragment.AnswerFragment;
import cn.yaohl.MayorOnline.ui.personal.fragment.HistoryFragment;
import cn.yaohl.MayorOnline.util.Constant;

public class MyAnswerActivity extends BaseActivity {

    private int flag = -1;

    private String[] titleStr = new String[]{"历史", "回复"};

    private TabLayout myTabLayout;

    private ViewPager myViewpager;

    private List<Fragment> fragmentList = new ArrayList<>();

    private MyAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_answer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleTxt("我的回复");
        setLeftImgBg(R.mipmap.back_white);
        flag = getIntent().getIntExtra(Constant.MINE_ANSWER_FLAG, -1);
        initView();
        initData();
    }

    @Override
    protected void onLeftClick() {
        super.onLeftClick();
        finish();
        MayorApplication.getInstance().deleteActivity(this);
    }

    private void initView() {
        myTabLayout = (TabLayout) findViewById(R.id.myTabLayout);
        myViewpager = (ViewPager) findViewById(R.id.myViewpager);
    }

    private void initData() {
        fragmentList.add(new AnswerFragment());
        fragmentList.add(new HistoryFragment());

        for (int i = 0; i < titleStr.length; i++) {
            myTabLayout.addTab(myTabLayout.newTab().setText(titleStr[i]));
        }
        adapter = new MyAdapter(getSupportFragmentManager(), titleStr,
                fragmentList);
        myViewpager.setAdapter(adapter);
        if (flag != -1) {
            switch (flag) {
                case 1001:
                    myViewpager.setCurrentItem(0);
                    break;
                case 1002:
                    myViewpager.setCurrentItem(1);
                    break;
            }
        }
        myTabLayout.setTabMode(TabLayout.MODE_FIXED);
        myTabLayout.setupWithViewPager(myViewpager);
        myTabLayout.setTabsFromPagerAdapter(adapter);
    }


    class MyAdapter extends FragmentPagerAdapter {

        private String[] titleStr;
        private List<Fragment> fragmentList;

        public MyAdapter(FragmentManager fm, String[] titleSt, List<Fragment> fragmentList) {
            super(fm);
            this.titleStr = titleSt;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList != null ? fragmentList.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleStr[position];
        }
    }
}
