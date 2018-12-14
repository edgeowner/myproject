/**
  * Copyright 2018 bejson.com 
  */
package com.huboot.business.base_model.ali_service.dto.batch.resp;
import java.util.List;

/**
 * Auto-generated: 2018-06-04 16:56:5
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BatchSendRespDTO {

    private String result;
    private String desc;
    private List<Data> data;
    private String req;
    public void setResult(String result) {
         this.result = result;
     }
     public String getResult() {
         return result;
     }

    public void setDesc(String desc) {
         this.desc = desc;
     }
     public String getDesc() {
         return desc;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }
}