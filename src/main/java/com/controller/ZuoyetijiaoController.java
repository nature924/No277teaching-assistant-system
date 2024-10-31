
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 作业提交
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/zuoyetijiao")
public class ZuoyetijiaoController {
    private static final Logger logger = LoggerFactory.getLogger(ZuoyetijiaoController.class);

    private static final String TABLE_NAME = "zuoyetijiao";

    @Autowired
    private ZuoyetijiaoService zuoyetijiaoService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表非注册的service
    @Autowired
    private ZuoyeService zuoyeService;
    //注册表service
    @Autowired
    private YonghuService yonghuService;
    @Autowired
    private JiaoshiService jiaoshiService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("学生".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("教师".equals(role))
            params.put("jiaoshiId",request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = zuoyetijiaoService.queryPage(params);

        //字典表数据转换
        List<ZuoyetijiaoView> list =(List<ZuoyetijiaoView>)page.getList();
        for(ZuoyetijiaoView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ZuoyetijiaoEntity zuoyetijiao = zuoyetijiaoService.selectById(id);
        if(zuoyetijiao !=null){
            //entity转view
            ZuoyetijiaoView view = new ZuoyetijiaoView();
            BeanUtils.copyProperties( zuoyetijiao , view );//把实体数据重构到view中
            //级联表 学生
            //级联表
            YonghuEntity yonghu = yonghuService.selectById(zuoyetijiao.getYonghuId());
            if(yonghu != null){
            BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setYonghuId(yonghu.getId());
            }
            //级联表 作业
            //级联表
            ZuoyeEntity zuoye = zuoyeService.selectById(zuoyetijiao.getZuoyeId());
            if(zuoye != null){
            BeanUtils.copyProperties( zuoye , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setZuoyeId(zuoye.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody ZuoyetijiaoEntity zuoyetijiao, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,zuoyetijiao:{}",this.getClass().getName(),zuoyetijiao.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("学生".equals(role))
            zuoyetijiao.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<ZuoyetijiaoEntity> queryWrapper = new EntityWrapper<ZuoyetijiaoEntity>()
            .eq("zuoye_id", zuoyetijiao.getZuoyeId())
            .eq("yonghu_id", zuoyetijiao.getYonghuId())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZuoyetijiaoEntity zuoyetijiaoEntity = zuoyetijiaoService.selectOne(queryWrapper);
        if(zuoyetijiaoEntity==null){
            zuoyetijiao.setCreateTime(new Date());
            zuoyetijiaoService.insert(zuoyetijiao);
            return R.ok();
        }else {
            return R.error(511,"请不要重复提交作业");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ZuoyetijiaoEntity zuoyetijiao, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,zuoyetijiao:{}",this.getClass().getName(),zuoyetijiao.toString());
        ZuoyetijiaoEntity oldZuoyetijiaoEntity = zuoyetijiaoService.selectById(zuoyetijiao.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("学生".equals(role))
//            zuoyetijiao.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<ZuoyetijiaoEntity> queryWrapper = new EntityWrapper<ZuoyetijiaoEntity>()
            .notIn("id",zuoyetijiao.getId())
            .andNew()
            .eq("zuoye_id", zuoyetijiao.getZuoyeId())
            .eq("yonghu_id", zuoyetijiao.getYonghuId())
            .eq("zuoyetijiao_types", zuoyetijiao.getZuoyetijiaoTypes())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZuoyetijiaoEntity zuoyetijiaoEntity = zuoyetijiaoService.selectOne(queryWrapper);
        if("".equals(zuoyetijiao.getZuoyetijiaoFile()) || "null".equals(zuoyetijiao.getZuoyetijiaoFile())){
                zuoyetijiao.setZuoyetijiaoFile(null);
        }
        if(zuoyetijiaoEntity==null){
            zuoyetijiaoService.updateById(zuoyetijiao);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<ZuoyetijiaoEntity> oldZuoyetijiaoList =zuoyetijiaoService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        zuoyetijiaoService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<ZuoyetijiaoEntity> zuoyetijiaoList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            ZuoyetijiaoEntity zuoyetijiaoEntity = new ZuoyetijiaoEntity();
//                            zuoyetijiaoEntity.setZuoyeId(Integer.valueOf(data.get(0)));   //作业 要改的
//                            zuoyetijiaoEntity.setYonghuId(Integer.valueOf(data.get(0)));   //学生 要改的
//                            zuoyetijiaoEntity.setZuoyetijiaoFile(data.get(0));                    //作业文件 要改的
//                            zuoyetijiaoEntity.setZuoyetijiaoTypes(Integer.valueOf(data.get(0)));   //作业成绩 要改的
//                            zuoyetijiaoEntity.setCreateTime(date);//时间
                            zuoyetijiaoList.add(zuoyetijiaoEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        zuoyetijiaoService.insertBatch(zuoyetijiaoList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }





    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = zuoyetijiaoService.queryPage(params);

        //字典表数据转换
        List<ZuoyetijiaoView> list =(List<ZuoyetijiaoView>)page.getList();
        for(ZuoyetijiaoView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ZuoyetijiaoEntity zuoyetijiao = zuoyetijiaoService.selectById(id);
            if(zuoyetijiao !=null){


                //entity转view
                ZuoyetijiaoView view = new ZuoyetijiaoView();
                BeanUtils.copyProperties( zuoyetijiao , view );//把实体数据重构到view中

                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(zuoyetijiao.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
                //级联表
                    ZuoyeEntity zuoye = zuoyeService.selectById(zuoyetijiao.getZuoyeId());
                if(zuoye != null){
                    BeanUtils.copyProperties( zuoye , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setZuoyeId(zuoye.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody ZuoyetijiaoEntity zuoyetijiao, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,zuoyetijiao:{}",this.getClass().getName(),zuoyetijiao.toString());
        Wrapper<ZuoyetijiaoEntity> queryWrapper = new EntityWrapper<ZuoyetijiaoEntity>()
            .eq("zuoye_id", zuoyetijiao.getZuoyeId())
            .eq("yonghu_id", zuoyetijiao.getYonghuId())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZuoyetijiaoEntity zuoyetijiaoEntity = zuoyetijiaoService.selectOne(queryWrapper);
        if(zuoyetijiaoEntity==null){
            zuoyetijiao.setCreateTime(new Date());
        zuoyetijiaoService.insert(zuoyetijiao);

            return R.ok();
        }else {
            return R.error(511,"请不要重复提交作业");
        }
    }

}
