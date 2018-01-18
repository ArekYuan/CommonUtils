package com.yaohl.retrofitlib.retrofit.service;


import com.yaohl.retrofitlib.retrofit.RetrofitManager;

/**
 * 作者：袁光跃
 * 日期：2018/1/17
 * 描述：所有的接口 都要继承这个类
 * 邮箱：813665242@qq.com
 */

public abstract class BaseApiService {

    protected ApiInterface apiService = RetrofitManager.getInstance().create(ApiInterface.class);
}
