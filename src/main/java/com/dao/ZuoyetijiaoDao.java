package com.dao;

import com.entity.ZuoyetijiaoEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.ZuoyetijiaoView;

/**
 * 作业提交 Dao 接口
 *
 * @author 
 */
public interface ZuoyetijiaoDao extends BaseMapper<ZuoyetijiaoEntity> {

   List<ZuoyetijiaoView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
