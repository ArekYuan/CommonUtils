package cn.yaohl.MayorOnline.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseActivity;
import cn.yaohl.MayorOnline.ui.home.adapter.ProListAdapter;
import cn.yaohl.MayorOnline.ui.home.beans.CityBeans;
import cn.yaohl.MayorOnline.ui.home.beans.CityUtils;
import cn.yaohl.MayorOnline.util.Constant;

public class ProListActivity extends BaseActivity {

    private ListView proListView;
    private ProListAdapter listAdapter;
    private List<String> proList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_clity_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleTxt("选择省份");
        initView();
        initData();
    }

    private void initView() {
        proListView = (ListView) findViewById(R.id.proListView);
        proListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (proList.size() != 0) {
//                    showShortToast(proList.get(position));
                    Intent intent = new Intent(mContext, CitysListActivity.class)
                            .putExtra(Constant.PROVINCE_CITY, proList.get(position));
                    startActivity(intent);
                }
            }
        });
    }

    private void initData() {
        CityBeans beans = cityList();
        if (beans != null) {
            proList = CityUtils.getProvince(beans);
        }
        listAdapter = new ProListAdapter(mContext);
        proListView.setAdapter(listAdapter);
        listAdapter.setmData(proList);
    }
}
