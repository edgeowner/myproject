package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WxmpMenuTypeEnum implements BaseEnum {

    view("view"), //跳转URL
    scancode_push("scancode_push"), //扫码推事件
    scancode_waitmsg("scancode_waitmsg"), //扫码推事件且弹出“消息接收中”提示框
    pic_photo_or_album("pic_photo_or_album"), //弹出拍照或者相册发图
    pic_weixin("pic_weixin"), //弹出微信相册发图器
    location_select("location_select"), //弹出地理位置选择器
    media_id("media_id"), //下发消息（除文本消息）
    view_limited("view_limited"), //跳转图文消息URL
    miniprogram("miniprogram")//跳转小程序
    ;

    private String showName;

}
