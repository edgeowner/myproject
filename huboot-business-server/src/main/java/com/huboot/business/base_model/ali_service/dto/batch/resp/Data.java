/**
  * Copyright 2018 bejson.com 
  */
package com.huboot.business.base_model.ali_service.dto.batch.resp;

/**
 * Auto-generated: 2018-06-04 16:56:5
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private String msgid;
    private String status;
    private String desc;
    private String failPhones;
    public void setMsgid(String msgid) {
         this.msgid = msgid;
     }
     public String getMsgid() {
         return msgid;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setDesc(String desc) {
         this.desc = desc;
     }
     public String getDesc() {
         return desc;
     }

    public void setFailPhones(String failPhones) {
         this.failPhones = failPhones;
     }
     public String getFailPhones() {
         return failPhones;
     }

}