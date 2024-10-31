package com.entity.model;

import com.entity.ZuoyetijiaoEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 作业提交
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class ZuoyetijiaoModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 作业
     */
    private Integer zuoyeId;


    /**
     * 学生
     */
    private Integer yonghuId;


    /**
     * 作业文件
     */
    private String zuoyetijiaoFile;


    /**
     * 作业成绩
     */
    private Integer zuoyetijiaoTypes;


    /**
     * 创建时间 show3 listShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
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
	 * 获取：创建时间 show3 listShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间 show3 listShow
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
