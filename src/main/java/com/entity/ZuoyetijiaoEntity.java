package com.entity;

import com.annotation.ColumnInfo;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;
import java.io.Serializable;
import java.util.*;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.utils.DateUtil;


/**
 * 作业提交
 *
 * @author 
 * @email
 */
@TableName("zuoyetijiao")
public class ZuoyetijiaoEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public ZuoyetijiaoEntity() {

	}

	public ZuoyetijiaoEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ColumnInfo(comment="主键",type="int(11)")
    @TableField(value = "id")

    private Integer id;


    /**
     * 作业
     */
    @ColumnInfo(comment="作业",type="int(11)")
    @TableField(value = "zuoye_id")

    private Integer zuoyeId;


    /**
     * 学生
     */
    @ColumnInfo(comment="学生",type="int(11)")
    @TableField(value = "yonghu_id")

    private Integer yonghuId;


    /**
     * 作业文件
     */
    @ColumnInfo(comment="作业文件",type="varchar(200)")
    @TableField(value = "zuoyetijiao_file")

    private String zuoyetijiaoFile;


    /**
     * 作业成绩
     */
    @ColumnInfo(comment="作业成绩",type="int(11)")
    @TableField(value = "zuoyetijiao_types")

    private Integer zuoyetijiaoTypes;


    /**
     * 创建时间  listShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="创建时间",type="timestamp")
    @TableField(value = "create_time",fill = FieldFill.INSERT)

    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }
    /**
	 * 设置：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：作业
	 */
    public Integer getZuoyeId() {
        return zuoyeId;
    }
    /**
	 * 设置：作业
	 */

    public void setZuoyeId(Integer zuoyeId) {
        this.zuoyeId = zuoyeId;
    }
    /**
	 * 获取：学生
	 */
    public Integer getYonghuId() {
        return yonghuId;
    }
    /**
	 * 设置：学生
	 */

    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
	 * 获取：作业文件
	 */
    public String getZuoyetijiaoFile() {
        return zuoyetijiaoFile;
    }
    /**
	 * 设置：作业文件
	 */

    public void setZuoyetijiaoFile(String zuoyetijiaoFile) {
        this.zuoyetijiaoFile = zuoyetijiaoFile;
    }
    /**
	 * 获取：作业成绩
	 */
    public Integer getZuoyetijiaoTypes() {
        return zuoyetijiaoTypes;
    }
    /**
	 * 设置：作业成绩
	 */

    public void setZuoyetijiaoTypes(Integer zuoyetijiaoTypes) {
        this.zuoyetijiaoTypes = zuoyetijiaoTypes;
    }
    /**
	 * 获取：创建时间  listShow
	 */
    public Date getCreateTime() {
        return createTime;
    }
    /**
	 * 设置：创建时间  listShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Zuoyetijiao{" +
            ", id=" + id +
            ", zuoyeId=" + zuoyeId +
            ", yonghuId=" + yonghuId +
            ", zuoyetijiaoFile=" + zuoyetijiaoFile +
            ", zuoyetijiaoTypes=" + zuoyetijiaoTypes +
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
