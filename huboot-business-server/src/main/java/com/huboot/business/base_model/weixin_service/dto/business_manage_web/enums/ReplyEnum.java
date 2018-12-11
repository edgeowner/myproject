package com.huboot.business.base_model.weixin_service.dto.business_manage_web.enums;

/**
 * Created by Administrator on 2018/3/26 0026.
 */
public enum ReplyEnum {

    home(1,"首页","/{0}","http://img1.zchz.com/img/example/sy.png"),
    carList(2,"车辆列表","/{0}/Cars","http://img1.zchz.com/img/example/cllby.png"),
    bigWheel(3,"幸运大转盘","/{0}/getturntable?zsActivityExtendId={1}","http://img1.zchz.com/img/example/xydzp.png"),
    articleRecommend(4,"熟人推荐","/{0}/home?dianPing=true","http://img1.zchz.com/img/example/srtj.png"),
    ShuttleService(5,"接送服务","/{0}/makeRelay", "view"),
    GoodsMall(6,"本地特产","/{0}/mallList", "view")
    ;

    private Integer id;
    private String name;
    private String url;
    private String imageUrl;

    ReplyEnum(Integer id,String name, String url, String imageUrl) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
