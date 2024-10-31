package com.entity.vo;

import com.entity.ZuoyetijiaoEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 作业提交
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("zuoyetijiao")
public class ZuoyetijiaoVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 作业
     */

    @TableField(value = "zuoye_id")
    private Integer zuoyeId;


    /**
     * 学生
     */

    @TableField(value = "yonghu_id")
    private Integer yonghuId;


    /**
     * 作业文件
     */

    @TableField(value = "zuoyetijiao_file")
    private String zuoyetijiaoFile;


    /**
     * 作业成绩
     */

    @TableField(value = "zuoyetijiao_types")
    private Integer zuoyetijiaoTypes;


    /**
     * 创建时间 show3 listShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：作业
	 */
    public Integer getZuoyeId() {
        return zuoyeId;
    }


    /**
	 * 获取：作业
	 */

    public void setZuoyeId(Integer zuoyeId) {
        this.zuoyeId = zuoyeId;
    }
    /**
	 * 设置：学生
	 */
    public Integer getYonghuId() {
        return yonghuId;
    }


    /**
	 * 获取：学生
	 */

    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
	 * 设置：作业文件
	 */
    public String getZuoyetijiaoFile() {
        return zuoyetijiaoFile;
    }


    /**
	 * 获取：作业文件
	 */

    public void setZuoyetijiaoFile(String zuoyetijiaoFile) {
        this.zuoyetijiaoFile = zuoyetijiaoFile;
    }
    /**
	 * 设置：作业成绩
	 */
    public Integer getZuoyetijiaoTypes() {
        return zuoyetijiaoTypes;
    }


    /**
	 * 获取：作业成绩
	 */

    public void setZuoyetijiaoTypes(Integer zuoyetijiaoTypes) {
        this.zuoyetijiaoTypes = zuoyetijiaoTypes;
    }
    /**
	 * 设置：创建时间 show3 listShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间 show3 listShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
