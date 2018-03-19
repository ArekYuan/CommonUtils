package cn.yaohl.MayorOnline.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.MayorApplication;
import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.chart.ChartFragment;
import cn.yaohl.MayorOnline.ui.home.HomeFragment;
import cn.yaohl.MayorOnline.ui.lmessage.LMessageFragment;

public class HomeActivity extends BaseActivity implements View.OnClickListener {


    /**
     * 是否满足退出
     */
    private boolean isBack = false;

    /**
     * 上次退出的时间
     */
    private long downTime;

    private String[] titleStr = new String[]{"市长大讲堂", "参城议事厅", "给市长留言"};

    private TabLayout mainTabLayout;

    private ViewPager mainViewpager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSearchVisi(View.GONE);
        setTitleTxt("市长在线");
        setRightImgBg(R.mipmap.more);
        initView();
        initData();
    }


    private void initView() {
        mainTabLayout = (TabLayout) findViewById(R.id.mainTabLayout);
        mainViewpager = (ViewPager) findViewById(R.id.mainViewpager);

    }


    private void initData() {
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ChartFragment());
        fragmentList.add(new LMessageFragment());

        for (int i = 0; i < titleStr.length; i++) {
            mainTabLayout.addTab(mainTabLayout.newTab().setText(titleStr[i]));
        }
        adapter = new MyAdapter(getSupportFragmentManager(), titleStr,
                                fragmentList);
        mainViewpager.setAdapter(adapter);
        mainTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mainTabLayout.setupWithViewPager(mainViewpager);
        mainTabLayout.setTabsFromPagerAdapter(adapter);
    }


    @Override
    protected void onSearchClick() {
        super.onSearchClick();
        showShortToast("搜索");
        ARouter.getInstance().build("/live_broad/live").navigation();
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isBack) {
                Toast.makeText(HomeActivity.this, getString(R.string.home_back),
                               Toast.LENGTH_SHORT).show();
                downTime = event.getDownTime();
                isBack = true;
                return true;
            } else {
                if (event.getDownTime() - downTime <= 2000) {
                    MayorApplication.getApplication().exit();
                } else {
                    Toast.makeText(HomeActivity.this, getString(R.string.home_back),
                                   Toast.LENGTH_SHORT)
                            .show();
                    downTime = event.getDownTime();
                    return true;
                }
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onRightClick() {
        super.onRightClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
