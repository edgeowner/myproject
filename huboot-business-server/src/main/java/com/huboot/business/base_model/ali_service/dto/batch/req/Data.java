/**
  * Copyright 2018 bejson.com 
  */
package com.huboot.business.base_model.ali_service.dto.batch.req;

import java.io.Serializable;

/**
 * Auto-generated: 2018-06-04 15:42:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data implements Serializable {

    private String msgid;
    private String phones;
    private String content;
    private String sign;
    private String subcode;
    private String sendtime;
    public void setMsgid(String msgid) {
         this.msgid = msgid;
     }
     public String getMsgid() {
         return msgid;
     }

    public void setPhones(String phones) {
         this.phones = phones;
     }
     public String getPhones() {
         return phones;
     }

    public void setContent(String content) {
         this.content = content;
     }
     public String getContent() {
         return content;
     }

    public void setSign(String sign) {
         this.sign = sign;
     }
     public String getSign() {
         return sign;
     }

    public void setSubcode(String subcode) {
         this.subcode = subcode;
     }
     public String getSubcode() {
         return subcode;
     }

    public void setSendtime(String sendtime) {
         this.sendtime = sendtime;
     }
     public String getSendtime() {
         return sendtime;
     }

}