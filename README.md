# CommonUtils
一些常用的请求 下拉刷新 手势、以及一些常用的自定义控件等，方便维护 及时更新。
一Volley 框架
--该套框架 使用Volley时的各种技巧封装成了一个库RequestVolly. 这个库中将构造请求的方式封装为了函数式调用。
       维持一个全局的请求队列，拓展一些方便的API，使用了filedownload来支持文件的下载，封装了一个第三方的库来支持版本的下载。

一AsyncHttpClient 框架
     该框架封装的文件上传下载，get, post，不在主线程中操作，只在主线程中请求接口，在子线程中请求数据，在handler 中刷新UI

一OkHttp 框架
     该框架 支持文件上传 下载 get post  同步，缓存等一些操作，不在主线程中操作，只在主线程中请求接口，在子线程中请求数据，在handler 中刷新UI。
     增加 get方式请求 数据，在handler中刷新ui
     一post待完善
