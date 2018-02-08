package cn.yaohl.MayorOnline.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.yaohl.MayorOnline.MayorApplication;
import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.home.HomeFragment;
import cn.yaohl.MayorOnline.ui.personal.PersonalFragment;
import cn.yaohl.MayorOnline.ui.service.ServiceFragment;

public class HomeFragmentActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 主页
     */
    private HomeFragment mainFragment;

    /**
     * 服务
     */
    private ServiceFragment serviceFragment;

    /**
     * 我的
     */
    private PersonalFragment personFragment;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    /**
     * Fragment事物
     */
    private FragmentTransaction fragmentTransaction;

    /**
     * 主页布局
     */
    private RelativeLayout homeMenuMainRelative;

    /**
     * 服务单布局
     */
    private RelativeLayout homeMenuServiceRelative;

    /**
     * 我的布局
     */
    private RelativeLayout homeMenuMyRelative;

    /**
     * 主页标签的图片
     */
    private ImageView homeMainImg;

    /**
     * 工作台标签的图片
     */
    private ImageView homeServiceImg;

    /**
     * 我的标签的图片
     */
    private ImageView homeMyImg;

    /**
     * 主页标签
     */
    private TextView homeMainTxt;

    /**
     * 工作台标签
     */
    private TextView homeServiceTxt;

    /**
     * 我的标签
     */
    private TextView homeMyTxt;

    /**
     * Tab标签
     */
    private String[] tags;

    /**
     * 当前的Fragment Tag
     */
    private String curFragmentTag;

    /**
     * 是否满足退出
     */
    private boolean isBack = false;

    /**
     * 上次退出的时间
     */
    private long downTime;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSearchVisi(View.VISIBLE);
        initView();
        setOnClick();
        init();
    }

    private void initView() {
        homeMenuMainRelative = (RelativeLayout) findViewById(R.id.homeMenuMainRelative);
        homeMenuServiceRelative = (RelativeLayout) findViewById(R.id.homeMenuWorkbenchRelative);
        homeMenuMyRelative = (RelativeLayout) findViewById(R.id.homeMenuMyRelative);
        homeMainImg = (ImageView) findViewById(R.id.homeMainImg);
        homeServiceImg = (ImageView) findViewById(R.id.homeWorkbenchImg);
        homeMyImg = (ImageView) findViewById(R.id.homeMyImg);
        homeMainTxt = (TextView) findViewById(R.id.homeMainTxt);
        homeServiceTxt = (TextView) findViewById(R.id.homeWorkbenchTxt);
        homeMyTxt = (TextView) findViewById(R.id.homeMyTxt);
    }

    @Override
    protected void onSearchClick() {
        super.onSearchClick();
        showShortToast("搜索");
        ARouter.getInstance().build("/live_broad/live").navigation();
    }

    private void init() {
        tags = new String[]{getString(R.string.home_menu_main_string), getString(R.string.home_menu_workbench_string),
                getString(R.string.home_menu_my_string)};
        fragmentManager = getSupportFragmentManager();
        // 第一次启动时默认选中第一个tab
        setTabSelection(getString(R.string.home_menu_main_string));
        curFragmentTag = getString(R.string.home_menu_main_string);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(mainFragment);
    }


    /**
     * <获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件>
     * <功能详细描述>
     * 返 回 类 型：void
     */
    private void setOnClick() {
        homeMenuMainRelative.setOnClickListener(this);
        homeMenuServiceRelative.setOnClickListener(this);
        homeMenuMyRelative.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeMenuMainRelative:
                setSearchVisi(View.VISIBLE);
                setTitleTxt("");
                setTabSelection(getString(R.string.home_menu_main_string));
                break;
            case R.id.homeMenuWorkbenchRelative:
                setSearchVisi(View.GONE);
                setTitleTxt(getString(R.string.home_menu_workbench_string));
                setTabSelection(getString(R.string.home_menu_workbench_string));
                break;
            case R.id.homeMenuMyRelative:
                setSearchVisi(View.GONE);
                setTitleTxt(getString(R.string.home_menu_my_string));
                setTabSelection(getString(R.string.home_menu_my_string));
                break;
            default:
                break;
        }
    }

    /**
     * <设置选中的Tab>
     * <功能详细描述>
     *
     * @param tag 返 回 类 型：void
     */
    public void setTabSelection(String tag) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        fragmentTransaction = fragmentManager.beginTransaction();

        if (TextUtils.equals(tag, getString(R.string.home_menu_main_string))) {
            // 当点击了消息tab时，改变控件的图片和文字颜色
            setSelection(R.id.homeMenuMainRelative);
            if (mainFragment == null) {
                mainFragment = new HomeFragment();
            }
        }
        if (TextUtils.equals(tag, getString(R.string.home_menu_workbench_string))) {
            setSelection(R.id.homeMenuWorkbenchRelative);
            if (serviceFragment == null) {
                serviceFragment = new ServiceFragment();

            }
        }
        if (TextUtils.equals(tag, getString(R.string.home_menu_my_string))) {
            setSelection(R.id.homeMenuMyRelative);
            if (personFragment == null) {
                personFragment = new PersonalFragment();
            }
        }

        switchFragment(tag);
    }

    /**
     * <根据传入的tag切换fragment>
     * <功能详细描述>
     *
     * @param tag 返 回 类 型：void
     */
    private void switchFragment(String tag) {
        detachFragment(tag);
        //getSupportFragmentManager().popBackStack();
        attachFragment(R.id.content, getFragment(tag), tag);
        curFragmentTag = tag;
        commitTransactions();
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     *
     * @param tag tag
     * @return 返 回 类 型：Fragment
     */
    private Fragment getFragment(String tag) {

        Fragment f = fragmentManager.findFragmentByTag(tag);

        if (f == null) {
            if (TextUtils.equals(tag, getString(R.string.home_menu_main_string))) {
                if (mainFragment == null) {
                    mainFragment = new HomeFragment();
                }
                return mainFragment;
            }
            if (TextUtils.equals(tag, getString(R.string.home_menu_workbench_string))) {
                if (serviceFragment == null) {
                    serviceFragment = new ServiceFragment();
                }
                return serviceFragment;
            }
            if (TextUtils.equals(tag, getString(R.string.home_menu_my_string))) {
                if (personFragment == null) {
                    personFragment = new PersonalFragment();
                }
                return personFragment;
            }
        }
        return f;

    }

    /**
     * <加入Fragment>
     * <功能详细描述>
     *
     * @param layout 显示布局
     * @param f      fragment
     * @param tag    tag
     * @see [类、类#方法、类#成员]
     */
    private void attachFragment(int layout, Fragment f, String tag) {
        if (f != null) {
            if (f.isHidden()) {
                fragmentTransaction.show(f);
            } else if (f.isDetached()) {
                fragmentTransaction.attach(f);
            } else if (!f.isAdded()) {
                fragmentTransaction.add(layout, f, tag);
            }
        }
    }

    /**
     * <从UI中装卸指定的Fragment>
     * <功能详细描述>
     *
     * @param tagCurrent 返回类型:void
     */
    private void detachFragment(String tagCurrent) {
        ensureTransaction();
        for (int i = 0; i < tags.length; i++) {
            Fragment fragment = fragmentManager.findFragmentByTag(tags[i]);
            if (fragment != null && !fragment.isDetached() && !TextUtils.equals(tagCurrent, tags[i])) {
                fragmentTransaction.hide(fragment);
            }
        }
    }

    /**
     * <开始事务>
     * <功能详细描述>
     *
     * @return 返 回 类 型：FragmentTransaction
     */
    private FragmentTransaction ensureTransaction() {
        if (fragmentTransaction == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        }
        return fragmentTransaction;

    }

    /**
     * <提交fragment 事务>
     * <功能详细描述>
     * 返 回 类 型：void
     */
    private void commitTransactions() {
        if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
            fragmentTransaction.commitAllowingStateLoss();
            //fragmentTransaction = null;
        }
    }

    /**
     * <清楚所有选中状态>
     * <功能详细描述>
     * 返 回 类 型：void
     */
    private void clearSelection() {
        homeMainImg.setImageResource(R.mipmap.navi_home1);
        homeMainTxt.setTextColor(getResources().getColor(R.color.black));
        homeServiceImg.setImageResource(R.mipmap.navi_service1);
        homeServiceTxt.setTextColor(getResources().getColor(R.color.black));
        homeMyImg.setImageResource(R.mipmap.navi_my1);
        homeMyTxt.setTextColor(getResources().getColor(R.color.black));
    }

    /**
     * <设置控件的选择状态>
     * <功能详细描述>
     *
     * @param id 传入父视图的id
     *           返 回 类 型：void
     */
    private void setSelection(int id) {
        switch (id) {
            case R.id.homeMenuMainRelative:
                homeMainImg.setImageResource(R.mipmap.navi_home2);
                homeMainTxt.setTextColor(getResources().getColor(R.color.tab_sel_color));
                break;
            case R.id.homeMenuWorkbenchRelative:
                homeServiceImg.setImageResource(R.mipmap.navi_service2);
                homeServiceTxt.setTextColor(getResources().getColor(R.color.tab_sel_color));
                break;
            case R.id.homeMenuMyRelative:
                homeMyImg.setImageResource(R.mipmap.navi_my2);
                homeMyTxt.setTextColor(getResources().getColor(R.color.tab_sel_color));
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (curFragmentTag.equals(getString(R.string.home_menu_main_string))) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (!isBack) {
                    Toast.makeText(HomeFragmentActivity.this, getString(R.string.home_back), Toast.LENGTH_SHORT).show();
                    downTime = event.getDownTime();
                    isBack = true;
                    return true;
                } else {
                    if (event.getDownTime() - downTime <= 2000) {
                        MayorApplication.getApplication().exit();
                    } else {
                        Toast.makeText(HomeFragmentActivity.this, getString(R.string.home_back), Toast.LENGTH_SHORT)
                                .show();
                        downTime = event.getDownTime();
                        return true;
                    }
                }

            }
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                setTabSelection(getString(R.string.home_menu_main_string));
                return true;
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

}
