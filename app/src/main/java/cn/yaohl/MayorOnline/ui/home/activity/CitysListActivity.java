package cn.yaohl.MayorOnline.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.MayorApplication;
import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseActivity;
import cn.yaohl.MayorOnline.ui.HomeActivity;
import cn.yaohl.MayorOnline.ui.home.adapter.CitysListAdapter;
import cn.yaohl.MayorOnline.ui.home.beans.CityBeans;
import cn.yaohl.MayorOnline.util.Constant;

public class CitysListActivity extends BaseActivity {

    private ListView cityListView;
    private CitysListAdapter adapter;
    private List<CityBeans.ProvincesBean.CitysBean> cityList = new ArrayList<>();
    private String province = "";
    private List<CityBeans.ProvincesBean.CitysBean> citys = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_citys_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        province = getIntent().getStringExtra(Constant.PROVINCE_CITY);
        if (!province.equals("")) {
            setTitleTxt(province);
        } else {
            setTitleTxt("选择城市");
        }
        initView();
        initData();
    }


    private void initView() {
        cityListView = (ListView) findViewById(R.id.cityListView);
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName = citys.get(position).getCitysName();
                Constant.LOCATION = cityName;
                Intent intent = new Intent(mContext, HomeActivity.class)
                        .putExtra(Constant.CITY_DATA, cityName)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {
        if (!province.equals("")) {
            citys = MayorApplication.params.get(province);
            adapter = new CitysListAdapter(mContext);
            cityListView.setAdapter(adapter);
            adapter.setmData(citys);
        }
    }
}
