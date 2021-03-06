package com.boby.livinghelper.api;

import com.boby.livinghelper.app.example.entity.ExampleEntity;
import com.boby.livinghelper.app.home.entity.MainResponseEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * API 接口统一管理类
 *
 * @author zbobin.com
 * @date 2017/8/28.
 */

public interface ApiService {

    String BASE_URL="http://www.zbobin.com/";

    @GET("app/kaifadaohang/{page}")
    Observable<ExampleEntity> getExampleEntity(@Path("page") String page);

    //首页模块
    @GET("app/livinghelper/{page}")
    Observable<MainResponseEntity> getMainEntity(@Path("page") String page);

    /**
     * new api
     */
    //新闻头条接口
    public static final String URL_MAIN_NEWS = "http://v.juhe.cn/toutiao/index";
    //微信精选接口
    public static final String URL_MAIN_WECHAT_HANDPICK = "http://v.juhe.cn/weixin/query";
    //笑话大全接口
    public static final String URL_MAIN_JOKE = "http://japi.juhe.cn/joke/content/list.from";
    //万年历
    public static final String URL_MAIN_CALENDAR = "http://v.juhe.cn/calendar/day";
    //H5在线电影票
    public static final String URL_MAIN_MOVIE = "http://v.juhe.cn/wepiao/query";
    //星座运势
    public static final String URL_MAIN_HOROSCOPE = "http://web.juhe.cn:8080/constellation/getAll";
    //股票数据
    public static final String URL_MAIN_STOCK = "http://web.juhe.cn:8080/finance/stock/hs";

}
