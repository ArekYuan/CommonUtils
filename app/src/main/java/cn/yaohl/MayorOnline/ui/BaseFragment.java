package cn.yaohl.MayorOnline.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 作者：袁光跃
 * 日期：2018/1/18
 * 描述：
 * 邮箱：813665242@qq.com
 */

public abstract class BaseFragment extends Fragment {

    /**
     * 布局view
     */
    protected View rootView;

    /**
     * 上下文
     */
    protected Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getContentViewId(), container, false);
    }

    /**
     * 获取 布局内容id
     *
     * @return int
     */
    protected abstract int getContentViewId();

    protected void showShortToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        onViewInitialized(rootView, savedInstanceState);
    }

    /**
     * <初始化一些数据>
     * <功能详细描述>
     *
     * @param savedInstanceState 返回类型:void
     */
    public abstract void onViewInitialized(View view, Bundle savedInstanceState);


}
