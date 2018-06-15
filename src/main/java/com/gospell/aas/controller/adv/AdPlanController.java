package com.gospell.aas.controller.adv;

import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.adv.AdvertiserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.service.adv.AdComboService;
import com.gospell.aas.service.adv.AdelementService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 广告播出计划
 *
 * @author zhaohw
 * @date 2018-04-18 14:17
 */
@Controller
@RequestMapping(value = "/adv/plan")
public class AdPlanController extends BaseController{

    @Autowired
    private AdelementService adelementService;

    @Autowired
    private AdComboService adComboService;

    @ModelAttribute
    public Adelement get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return adelementService.get(id);
        } else {

            return new Adelement();
        }
    }

    @RequestMapping(value = { "/list", "" })
    @ApiOperation(value="获取广告播出计划",notes="跳转到首页")
    public String list(Adelement entity, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        try{

            //默认查询当前时间往后
            if(null == entity.getStartDate()){
                entity.setStartDate(new Date());
            }
            model.addAttribute("startDate", DateUtils.formatDate(entity.getStartDate(), "yyyy-MM-dd"));
            if(null != entity.getEndDate()){
                model.addAttribute("endDate",  DateUtils.formatDate(entity.getEndDate(), "yyyy-MM-dd"));
            }
            Page<Adelement> page = adelementService.find(new Page<Adelement>(request,
                    response), entity);
            model.addAttribute("page", page);

            if (AdvertiserUtils.checkIdAdv()) {
                model.addAttribute("isAdv", true);
            } else {
                model.addAttribute("isAdv", false);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "/adPlan/adPlanList";
    }
}
