package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.ZuoyetijiaoEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 作业提交
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("zuoyetijiao")
public class ZuoyetijiaoView extends ZuoyetijiaoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	* 作业成绩的值
	*/
	@ColumnInfo(comment="作业成绩的字典表值",type="varchar(200)")
	private String zuoyetijiaoValue;

	//级联表 学生
		/**
		* 学生姓名
		*/

		@ColumnInfo(comment="学生姓名",type="varchar(200)")
		private String yonghuName;
		/**
		* 学生头像
		*/

		@ColumnInfo(comment="学生头像",type="varchar(200)")
		private String yonghuPhoto;
		/**
		* 身份证号
		*/

		@ColumnInfo(comment="身份证号",type="varchar(200)")
		private String yonghuIdNumber;
		/**
		* 联系方式
		*/

		@ColumnInfo(comment="联系方式",type="varchar(200)")
		private String yonghuPhone;
		/**
		* 电子邮箱
		*/

		@ColumnInfo(comment="电子邮箱",type="varchar(200)")
		private String yonghuEmail;
		/**
		* 逻辑删除
		*/

		@ColumnInfo(comment="逻辑删除",type="int(11)")
		private Integer yonghuDelete;
	//级联表 作业
		/**
		* 作业标题
		*/

		@ColumnInfo(comment="作业标题",type="varchar(200)")
		private String zuoyeName;
		/**
		* 作业照片
		*/

		@ColumnInfo(comment="作业照片",type="varchar(200)")
		private String zuoyePhoto;
		/**
		* 作业类型
		*/
		@ColumnInfo(comment="作业类型",type="int(11)")
		private Integer zuoyeTypes;
			/**
			* 作业类型的值
			*/
			@ColumnInfo(comment="作业类型的字典表值",type="varchar(200)")
			private String zuoyeValue;
		/**
		* 作业
		*/

		@ColumnInfo(comment="作业",type="varchar(200)")
		private String zuoyeFile;
					 
		/**
		* 作业 的 教师
		*/
		@ColumnInfo(comment="教师",type="int(11)")
		private Integer zuoyeJiaoshiId;
		/**
		* 作业详情
		*/

		@ColumnInfo(comment="作业详情",type="text")
		private String zuoyeContent;
		/**
		* 逻辑删除
		*/

		@ColumnInfo(comment="逻辑删除",type="int(11)")
		private Integer zuoyeDelete;

	//重复字段
			/**
			* 重复字段 的types
			*/
			@ColumnInfo(comment="重复字段 的types",type="Integer")
			private Integer sexTypes;
				@ColumnInfo(comment="重复字段 的值",type="varchar(200)")
				private String sexValue;
			/**
			* 重复字段 的types
			*/
			@ColumnInfo(comment="重复字段 的types",type="Integer")
			private Integer banjiTypes;
				@ColumnInfo(comment="重复字段 的值",type="varchar(200)")
				private String banjiValue;


	public ZuoyetijiaoView() {

	}

	public ZuoyetijiaoView(ZuoyetijiaoEntity zuoyetijiaoEntity) {
		try {
			BeanUtils.copyProperties(this, zuoyetijiaoEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//当前表的
	/**
	* 获取： 作业成绩的值
	*/
	public String getZuoyetijiaoValue() {
		return zuoyetijiaoValue;
	}
	/**
	* 设置： 作业成绩的值
	*/
	public void setZuoyetijiaoValue(String zuoyetijiaoValue) {
		this.zuoyetijiaoValue = zuoyetijiaoValue;
	}


	//级联表的get和set 学生

		/**
		* 获取： 学生姓名
		*/
		public String getYonghuName() {
			return yonghuName;
		}
		/**
		* 设置： 学生姓名
		*/
		public void setYonghuName(String yonghuName) {
			this.yonghuName = yonghuName;
		}

		/**
		* 获取： 学生头像
		*/
		public String getYonghuPhoto() {
			return yonghuPhoto;
		}
		/**
		* 设置： 学生头像
		*/
		public void setYonghuPhoto(String yonghuPhoto) {
			this.yonghuPhoto = yonghuPhoto;
		}

		/**
		* 获取： 身份证号
		*/
		public String getYonghuIdNumber() {
			return yonghuIdNumber;
		}
		/**
		* 设置： 身份证号
		*/
		public void setYonghuIdNumber(String yonghuIdNumber) {
			this.yonghuIdNumber = yonghuIdNumber;
		}

		/**
		* 获取： 联系方式
		*/
		public String getYonghuPhone() {
			return yonghuPhone;
		}
		/**
		* 设置： 联系方式
		*/
		public void setYonghuPhone(String yonghuPhone) {
			this.yonghuPhone = yonghuPhone;
		}

		/**
		* 获取： 电子邮箱
		*/
		public String getYonghuEmail() {
			return yonghuEmail;
		}
		/**
		* 设置： 电子邮箱
		*/
		public void setYonghuEmail(String yonghuEmail) {
			this.yonghuEmail = yonghuEmail;
		}

		/**
		* 获取： 逻辑删除
		*/
		public Integer getYonghuDelete() {
			return yonghuDelete;
		}
		/**
		* 设置： 逻辑删除
		*/
		public void setYonghuDelete(Integer yonghuDelete) {
			this.yonghuDelete = yonghuDelete;
		}
	//级联表的get和set 作业

		/**
		* 获取： 作业标题
		*/
		public String getZuoyeName() {
			return zuoyeName;
		}
		/**
		* 设置： 作业标题
		*/
		public void setZuoyeName(String zuoyeName) {
			this.zuoyeName = zuoyeName;
		}

		/**
		* 获取： 作业照片
		*/
		public String getZuoyePhoto() {
			return zuoyePhoto;
		}
		/**
		* 设置： 作业照片
		*/
		public void setZuoyePhoto(String zuoyePhoto) {
			this.zuoyePhoto = zuoyePhoto;
		}
		/**
		* 获取： 作业类型
		*/
		public Integer getZuoyeTypes() {
			return zuoyeTypes;
		}
		/**
		* 设置： 作业类型
		*/
		public void setZuoyeTypes(Integer zuoyeTypes) {
			this.zuoyeTypes = zuoyeTypes;
		}


			/**
			* 获取： 作业类型的值
			*/
			public String getZuoyeValue() {
				return zuoyeValue;
			}
			/**
			* 设置： 作业类型的值
			*/
			public void setZuoyeValue(String zuoyeValue) {
				this.zuoyeValue = zuoyeValue;
			}

		/**
		* 获取： 作业
		*/
		public String getZuoyeFile() {
			return zuoyeFile;
		}
		/**
		* 设置： 作业
		*/
		public void setZuoyeFile(String zuoyeFile) {
			this.zuoyeFile = zuoyeFile;
		}
		/**
		* 获取：作业 的 教师
		*/
		public Integer getZuoyeJiaoshiId() {
			return zuoyeJiaoshiId;
		}
		/**
		* 设置：作业 的 教师
		*/
		public void setZuoyeJiaoshiId(Integer zuoyeJiaoshiId) {
			this.zuoyeJiaoshiId = zuoyeJiaoshiId;
		}

		/**
		* 获取： 作业详情
		*/
		public String getZuoyeContent() {
			return zuoyeContent;
		}
		/**
		* 设置： 作业详情
		*/
		public void setZuoyeContent(String zuoyeContent) {
			this.zuoyeContent = zuoyeContent;
		}

		/**
		* 获取： 逻辑删除
		*/
		public Integer getZuoyeDelete() {
			return zuoyeDelete;
		}
		/**
		* 设置： 逻辑删除
		*/
		public void setZuoyeDelete(Integer zuoyeDelete) {
			this.zuoyeDelete = zuoyeDelete;
		}

	//重复字段
			/**
			* 获取： 重复字段 的types
			*/
			public Integer getSexTypes() {
			return sexTypes;
			}
			/**
			* 设置： 重复字段 的types
			*/
			public void setSexTypes(Integer sexTypes) {
			this.sexTypes = sexTypes;
			}
				public String getSexValue() {
				return sexValue;
				}
				public void setSexValue(String sexValue) {
				this.sexValue = sexValue;
				}
			/**
			* 获取： 重复字段 的types
			*/
			public Integer getBanjiTypes() {
			return banjiTypes;
			}
			/**
			* 设置： 重复字段 的types
			*/
			public void setBanjiTypes(Integer banjiTypes) {
			this.banjiTypes = banjiTypes;
			}
				public String getBanjiValue() {
				return banjiValue;
				}
				public void setBanjiValue(String banjiValue) {
				this.banjiValue = banjiValue;
				}

	@Override
	public String toString() {
		return "ZuoyetijiaoView{" +
			", zuoyetijiaoValue=" + zuoyetijiaoValue +
			", yonghuName=" + yonghuName +
			", yonghuPhoto=" + yonghuPhoto +
			", yonghuIdNumber=" + yonghuIdNumber +
			", yonghuPhone=" + yonghuPhone +
			", yonghuEmail=" + yonghuEmail +
			", yonghuDelete=" + yonghuDelete +
			", zuoyeName=" + zuoyeName +
			", zuoyePhoto=" + zuoyePhoto +
			", zuoyeFile=" + zuoyeFile +
			", zuoyeContent=" + zuoyeContent +
			", zuoyeDelete=" + zuoyeDelete +
			"} " + super.toString();
	}
}
