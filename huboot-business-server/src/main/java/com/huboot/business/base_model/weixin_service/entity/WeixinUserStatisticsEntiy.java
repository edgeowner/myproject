package com.huboot.business.base_model.weixin_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户实体
 * ***/
@Document(collection="weixin_user_statistics")
public class WeixinUserStatisticsEntiy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	//ID
	@Id
	private String id;
	//店铺shopUid
	private String shopUid;
	//数据的日期
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date refDate;
	//用户的渠道，数值代表的含义如下： 0代表其他合计 1代表公众号搜索 17代表名片分享
	// 30代表扫描二维码 43代表图文页右上角菜单 51代表支付后关注（在支付完成页）
	// 57代表图文页内公众号名称 75代表公众号文章广告 78代表朋友圈广告
	private Integer userSource;
	//新增的用户数量
	private Integer newUser;
	//取消关注的用户数量
	private Integer cancelUser;
	//净增用户数量=new_user减去cancel_user
	private Integer addUser;
	//总用户量
	private Integer cumulateUser;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime = LocalDateTime.now();

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopUid() {
		return shopUid;
	}

	public void setShopUid(String shopUid) {
		this.shopUid = shopUid;
	}

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public Integer getUserSource() {
		return userSource;
	}

	public void setUserSource(Integer userSource) {
		this.userSource = userSource;
	}

	public Integer getNewUser() {
		return newUser;
	}

	public void setNewUser(Integer newUser) {
		this.newUser = newUser;
	}

	public Integer getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(Integer cancelUser) {
		this.cancelUser = cancelUser;
	}

	public Integer getAddUser() {
		return addUser;
	}

	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}

	public Integer getCumulateUser() {
		return cumulateUser;
	}

	public void setCumulateUser(Integer cumulateUser) {
		this.cumulateUser = cumulateUser;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public enum UserSource{

		//用户的渠道，数值代表的含义如下： 0代表其他合计 1代表公众号搜索 17代表名片分享
		// 30代表扫描二维码 43代表图文页右上角菜单 51代表支付后关注（在支付完成页）
		// 57代表图文页内公众号名称 75代表公众号文章广告 78代表朋友圈广告

		other(0),

		BusinessCardSearch(1),

		Share(17),

		QrCode(30),

		Menu(43),

		AfterPay(51),

		NoPublic(57),

		Article(75),

		Cycle(78);

		int type;

		UserSource(int type) {
			this.type = type;
		}

	}

	public enum IndexType{

		refDate(0),

		newUser(1),

		cancelUser(2),

		addUser(3),

		cumulateUser(4);

		private int type;

		IndexType(int type) {
			this.type = type;
		}

	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	public enum FindDateTypeEnum {

		None(0, "无"),
		Week(1, "近一周"),
		OneMonth(2, "一个月"),
		Trimester(3, "三个月"),
		HalfYear(4, "半年"),
		Year(5, "一年"),
		;

		private Integer value;

		private String showName;
	}

}
