package com.boby.livinghelper.app.movie.entity;

/**
 * desc
 *
 * @author zbobin.com
 * @date 2017/9/19.
 */

public class MovieEntity {

    private String h5url;        //H5页面URL，永久有效
    private String h5weixin;    //微信公众号专用h5

    public String getH5url() {
        return h5url == null?"":h5url;
    }

    public String getH5weixin() {
        return h5weixin == null?"":h5weixin;
    }
}
