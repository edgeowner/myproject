package com.huboot.business.mybatis;

/**
 * 文件服务类型
 *
 * @author Tory.li
 * @create 2017-02-28 14:25
 **/
public enum FileServiceTypeEnum {

			
		 		 
		/**********
		 * http://img1.zchz.com 多规格图片.
		 * 
		 * 
		 * 公共图片
		 */
		 PublicImage(0, "公共图片")
			
		 ,		 
		/**********
		 * http://img2.zchz.com
		 * 
		 * 私有图片
		 */
		 PrivateImage(1, "私有图片")
			
		 ,		 
		/**********
		 * 
		 * http://img3.zchz.com
		 * 公共文件
		 */
		 PublicFile(2, "公共文件")
			
		 ,		 
		/**********
		 * 
		 * http://img4.zchz.com
		 * 
		 * 私密文件
		 */
		 PrivateFile(3, "私有文件")
		;

	private int value;

	private String showName;

	FileServiceTypeEnum(int value, String showName) {
		this.value = value;
		this.showName = showName;
	}

	public static FileServiceTypeEnum valueOf(int value) {
		for (FileServiceTypeEnum s : FileServiceTypeEnum.values()) {
			if (s.value == value)
				return s;
		}
		throw new IllegalArgumentException(String.format("值%s不能转换成枚举", value));
	}

	public String getShowName() {
		return showName;
	}
	
	public int getValue() {
		return value;
	}
}
