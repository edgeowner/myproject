/**
  * Copyright 2018 bejson.com 
  */
package com.huboot.business.base_model.ali_service.dto.batch.req;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-06-04 15:42:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BatchSendReqDTO implements Serializable {

    private String account;
    private String password;
    private List<Data> data;
    public void setAccount(String account) {
         this.account = account;
     }
     public String getAccount() {
         return account;
     }

    public void setPassword(String password) {
         this.password = password;
     }
     public String getPassword() {
         return password;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

}