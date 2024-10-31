
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
 * 教学视频
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/jiaoxueshipin")
public class JiaoxueshipinController {
    private static final Logger logger = LoggerFactory.getLogger(JiaoxueshipinController.class);

    private static final String TABLE_NAME = "jiaoxueshipin";

    @Autowired
    private JiaoxueshipinService jiaoxueshipinService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表非注册的service
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
        params.put("jiaoxueshipinDeleteStart",1);params.put("jiaoxueshipinDeleteEnd",1);
        CommonUtil.checkMap(params);
        PageUtils page = jiaoxueshipinService.queryPage(params);

        //字典表数据转换
        List<JiaoxueshipinView> list =(List<JiaoxueshipinView>)page.getList();
        for(JiaoxueshipinView c:list){
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
        JiaoxueshipinEntity jiaoxueshipin = jiaoxueshipinService.selectById(id);
        if(jiaoxueshipin !=null){
            //entity转view
            JiaoxueshipinView view = new JiaoxueshipinView();
            BeanUtils.copyProperties( jiaoxueshipin , view );//把实体数据重构到view中
            //级联表 教师
            //级联表
            JiaoshiEntity jiaoshi = jiaoshiService.selectById(jiaoxueshipin.getJiaoshiId());
            if(jiaoshi != null){
            BeanUtils.copyProperties( jiaoshi , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "jiaoshiId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setJiaoshiId(jiaoshi.getId());
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
    public R save(@RequestBody JiaoxueshipinEntity jiaoxueshipin, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,jiaoxueshipin:{}",this.getClass().getName(),jiaoxueshipin.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("教师".equals(role))
            jiaoxueshipin.setJiaoshiId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<JiaoxueshipinEntity> queryWrapper = new EntityWrapper<JiaoxueshipinEntity>()
            .eq("jiaoxueshipin_name", jiaoxueshipin.getJiaoxueshipinName())
            .eq("jiaoxueshipin_video", jiaoxueshipin.getJiaoxueshipinVideo())
            .eq("jiaoxueshipin_types", jiaoxueshipin.getJiaoxueshipinTypes())
            .eq("jiaoshi_id", jiaoxueshipin.getJiaoshiId())
            .eq("jiaoxueshipin_delete", jiaoxueshipin.getJiaoxueshipinDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        JiaoxueshipinEntity jiaoxueshipinEntity = jiaoxueshipinService.selectOne(queryWrapper);
        if(jiaoxueshipinEntity==null){
            jiaoxueshipin.setJiaoxueshipinDelete(1);
            jiaoxueshipin.setCreateTime(new Date());
            jiaoxueshipinService.insert(jiaoxueshipin);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody JiaoxueshipinEntity jiaoxueshipin, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,jiaoxueshipin:{}",this.getClass().getName(),jiaoxueshipin.toString());
        JiaoxueshipinEntity oldJiaoxueshipinEntity = jiaoxueshipinService.selectById(jiaoxueshipin.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("教师".equals(role))
//            jiaoxueshipin.setJiaoshiId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<JiaoxueshipinEntity> queryWrapper = new EntityWrapper<JiaoxueshipinEntity>()
            .notIn("id",jiaoxueshipin.getId())
            .andNew()
            .eq("jiaoxueshipin_name", jiaoxueshipin.getJiaoxueshipinName())
            .eq("jiaoxueshipin_video", jiaoxueshipin.getJiaoxueshipinVideo())
            .eq("jiaoxueshipin_types", jiaoxueshipin.getJiaoxueshipinTypes())
            .eq("jiaoshi_id", jiaoxueshipin.getJiaoshiId())
            .eq("jiaoxueshipin_delete", jiaoxueshipin.getJiaoxueshipinDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        JiaoxueshipinEntity jiaoxueshipinEntity = jiaoxueshipinService.selectOne(queryWrapper);
        if("".equals(jiaoxueshipin.getJiaoxueshipinPhoto()) || "null".equals(jiaoxueshipin.getJiaoxueshipinPhoto())){
                jiaoxueshipin.setJiaoxueshipinPhoto(null);
        }
        if("".equals(jiaoxueshipin.getJiaoxueshipinVideo()) || "null".equals(jiaoxueshipin.getJiaoxueshipinVideo())){
                jiaoxueshipin.setJiaoxueshipinVideo(null);
        }
        if("".equals(jiaoxueshipin.getJiaoxueshipinFile()) || "null".equals(jiaoxueshipin.getJiaoxueshipinFile())){
                jiaoxueshipin.setJiaoxueshipinFile(null);
        }
        if(jiaoxueshipinEntity==null){
            jiaoxueshipinService.updateById(jiaoxueshipin);//根据id更新
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
        List<JiaoxueshipinEntity> oldJiaoxueshipinList =jiaoxueshipinService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        ArrayList<JiaoxueshipinEntity> list = new ArrayList<>();
        for(Integer id:ids){
            JiaoxueshipinEntity jiaoxueshipinEntity = new JiaoxueshipinEntity();
            jiaoxueshipinEntity.setId(id);
            jiaoxueshipinEntity.setJiaoxueshipinDelete(2);
            list.add(jiaoxueshipinEntity);
        }
        if(list != null && list.size() >0){
            jiaoxueshipinService.updateBatchById(list);
        }

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
            List<JiaoxueshipinEntity> jiaoxueshipinList = new ArrayList<>();//上传的东西
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
                            JiaoxueshipinEntity jiaoxueshipinEntity = new JiaoxueshipinEntity();
//                            jiaoxueshipinEntity.setJiaoxueshipinName(data.get(0));                    //教学视频标题 要改的
//                            jiaoxueshipinEntity.setJiaoxueshipinPhoto("");//详情和图片
//                            jiaoxueshipinEntity.setJiaoxueshipinVideo(data.get(0));                    //视频 要改的
//                            jiaoxueshipinEntity.setJiaoxueshipinFile(data.get(0));                    //教学资料 要改的
//                            jiaoxueshipinEntity.setJiaoxueshipinTypes(Integer.valueOf(data.get(0)));   //教学视频类型 要改的
//                            jiaoxueshipinEntity.setJiaoshiId(Integer.valueOf(data.get(0)));   //教师 要改的
//                            jiaoxueshipinEntity.setJiaoxueshipinDelete(1);//逻辑删除字段
//                            jiaoxueshipinEntity.setJiaoxueshipinContent("");//详情和图片
//                            jiaoxueshipinEntity.setCreateTime(date);//时间
                            jiaoxueshipinList.add(jiaoxueshipinEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        jiaoxueshipinService.insertBatch(jiaoxueshipinList);
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
        PageUtils page = jiaoxueshipinService.queryPage(params);

        //字典表数据转换
        List<JiaoxueshipinView> list =(List<JiaoxueshipinView>)page.getList();
        for(JiaoxueshipinView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        JiaoxueshipinEntity jiaoxueshipin = jiaoxueshipinService.selectById(id);
            if(jiaoxueshipin !=null){


                //entity转view
                JiaoxueshipinView view = new JiaoxueshipinView();
                BeanUtils.copyProperties( jiaoxueshipin , view );//把实体数据重构到view中

                //级联表
                    JiaoshiEntity jiaoshi = jiaoshiService.selectById(jiaoxueshipin.getJiaoshiId());
                if(jiaoshi != null){
                    BeanUtils.copyProperties( jiaoshi , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setJiaoshiId(jiaoshi.getId());
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
    public R add(@RequestBody JiaoxueshipinEntity jiaoxueshipin, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,jiaoxueshipin:{}",this.getClass().getName(),jiaoxueshipin.toString());
        Wrapper<JiaoxueshipinEntity> queryWrapper = new EntityWrapper<JiaoxueshipinEntity>()
            .eq("jiaoxueshipin_name", jiaoxueshipin.getJiaoxueshipinName())
            .eq("jiaoxueshipin_video", jiaoxueshipin.getJiaoxueshipinVideo())
            .eq("jiaoxueshipin_types", jiaoxueshipin.getJiaoxueshipinTypes())
            .eq("jiaoshi_id", jiaoxueshipin.getJiaoshiId())
            .eq("jiaoxueshipin_delete", jiaoxueshipin.getJiaoxueshipinDelete())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        JiaoxueshipinEntity jiaoxueshipinEntity = jiaoxueshipinService.selectOne(queryWrapper);
        if(jiaoxueshipinEntity==null){
            jiaoxueshipin.setJiaoxueshipinDelete(1);
            jiaoxueshipin.setCreateTime(new Date());
        jiaoxueshipinService.insert(jiaoxueshipin);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}
