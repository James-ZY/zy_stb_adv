package com.gospell.aas.controller.adv;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.gospell.aas.common.config.Global;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.AdElementStatisticPlayDTO;
import com.gospell.aas.dto.adv.AdStatisticChartDTO;
import com.gospell.aas.dto.adv.AdStatisticChildChartDTO;
import com.gospell.aas.dto.adv.AdStatisticTypePlayDTO;
import com.gospell.aas.entity.adv.AdStatistic;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.service.adv.AdStatisticService;
import com.gospell.aas.service.adv.AdTypeService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

@Controller
@RequestMapping(value = "/adv/adStatistic")
public class AdStatisticController extends BaseController {

	@Autowired
	private AdStatisticService thisService;
	 
	@Autowired
	private AdTypeService adTypeService;

	@ModelAttribute
	public AdStatistic get(@RequestParam(required = false) String id) {
		if (org.apache.commons.lang3.StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdStatistic();
		}
	}

	@RequiresPermissions("adv:play:view")
	@RequestMapping(value = "/advPlayCount")
	public String comboSellNumber(AdStatistic entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<AdStatistic> page = thisService.findAdvPlayCount(new Page<AdStatistic>(request, response), entity);
		model.addAttribute("page", page);
		String startDate = null;
		String endDate = null;
		if(entity.getPlayStartDate() != null){
			startDate = DateUtils.formatDate(entity.getPlayStartDate(),"yyyy-MM-dd");
		}
		if(entity.getPlayEndDate() != null){
			endDate = DateUtils.formatDate(entity.getPlayEndDate(),"yyyy-MM-dd");
		}
 
		model.addAttribute("playStartDate", startDate);
		model.addAttribute("playEndDate", endDate);
 
		return "/statistics/advClickRecordFirst";
	}
	
	@RequiresPermissions("adv:click:view")
	@RequestMapping(value = "/advPlayDetailQuery")
	public String advtiserBuyComboList(AdStatistic entity,HttpServletRequest request, HttpServletResponse response, Model model) {
	 
		Page<AdStatistic> page = new Page<AdStatistic>(request, response);
		try {
		 entity.setPage(page);
		 entity.setIsScan(AdStatistic.SKIP_URL_NO);
		 page = thisService.findAdvPlayDetail(page, entity);
		 
		 model.addAttribute("page", page);
		 model.addAttribute("adStatistic", entity);
//		String queryParam ="advId="+entity.getAdElemet().getAdId();
		 String playStartDate="";
		 String playEndDate="";
		 if(null != entity.getPlayStartDate()){
		
			 playStartDate=DateUtils.formatDate(entity.getPlayStartDate(), "yyyy-MM-dd");
			// queryParam += "&startDate="+playStartDate;
		 }
		 if(null != entity.getPlayEndDate()){
				
			 playEndDate=DateUtils.formatDate(entity.getPlayEndDate(), "yyyy-MM-dd");
			 //queryParam += "&endDate="+playEndDate;
		 }
		 model.addAttribute("playStartDate", playStartDate);
		 model.addAttribute("playEndDate",playEndDate);
		// model.addAttribute("queryParam",queryParam);
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		return "/statistics/advPlayRecord";
	}
	
	@RequiresPermissions("adv:click:view")
	@RequestMapping(value = "/advclickDetail/query")
	public String advClickDetail(AdStatistic entity,HttpServletRequest request, HttpServletResponse response, Model model) {
	 
		Page<AdStatistic> page = new Page<AdStatistic>(request, response);
		try {
		 entity.setPage(page);
		 entity.setIsScan(AdStatistic.SKIP_URL_YES);
		 page = thisService.findAdvPlayDetail(page, entity);
		 
		 int history = entity.getHistory();
		 history--;
		 entity.setHistory(history);
		 model.addAttribute("page", page);
		 model.addAttribute("adStatistic", entity);
 		String queryParam ="advId="+entity.getAdElemet().getAdId();
		 String playStartDate="";
		 String playEndDate="";
		 if(null != entity.getPlayStartDate()){
		
			 playStartDate=DateUtils.formatDate(entity.getPlayStartDate(), "yyyy-MM-dd");
			  queryParam += "&startDate="+playStartDate;
		 }
		 if(null != entity.getPlayEndDate()){
				
			 playEndDate=DateUtils.formatDate(entity.getPlayEndDate(), "yyyy-MM-dd");
			  queryParam += "&endDate="+playEndDate;
		 }
		 model.addAttribute("playStartDate", playStartDate);
		 model.addAttribute("playEndDate",playEndDate);
		  model.addAttribute("queryParam",queryParam);
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		return "/statistics/advClcikRecordSecond";
	}
	
	@RequiresPermissions("adv:play:view")
	@RequestMapping(value = "/advClickDetail")
	public String advPlayDetail(@RequestParam(required = true)String advId,@RequestParam(required = false) String startDate,@RequestParam(required = false) String endDate, Model model) {
	  
		Page<AdStatistic> page = new Page<AdStatistic>(1, Integer.valueOf(Global.getConfig("page.pageSize")));
		
		try {
			AdStatistic statistic = new AdStatistic();
			String queryParam ="advId="+advId;
			String playStartDate="";
			String playEndDate ="";
			if(org.apache.commons.lang3.StringUtils.isNotBlank(startDate) ){
				Date queryStartDate = DateUtils.getDateFromString(startDate,"yyyy-MM-dd");
				statistic.setPlayStartDate(queryStartDate);
				playStartDate = DateUtils.formatDate(queryStartDate, "yyyy-MM-dd");
				queryParam +="&startDate="+playStartDate;
			}else{
				 model.addAttribute("playStartDate", null);
			}
			if(org.apache.commons.lang3.StringUtils.isNotBlank(endDate)){
				Date queryEndDate = DateUtils.getDateFromString(endDate,"yyyy-MM-dd");
				statistic.setPlayEndDate(queryEndDate);
				playEndDate = DateUtils.formatDate(queryEndDate, "yyyy-MM-dd");
				queryParam +="&endDate="+endDate;
				 
			} 
			
			 
			Adelement adelement = new Adelement();
			adelement.setAdId(advId);
			 
			statistic.setAdElemet(adelement);
		
			 
			statistic.setPage(page);
			statistic.setIsScan(AdStatistic.SKIP_URL_YES);
		 page = thisService.findAdvPlayDetail(page, statistic);
		 
		 model.addAttribute("page", page);
		 statistic.setHistory(-1);
		 model.addAttribute("adStatistic", statistic);
		 model.addAttribute("queryParam",queryParam);
		 model.addAttribute("playStartDate", playStartDate);
		 model.addAttribute("playEndDate", playEndDate);
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		return "/statistics/advClcikRecordSecond";
	}


    /**
     * 点击次数统计图表页面
     * @param entity
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequiresPermissions("adv:click:view")
	@RequestMapping(value = "/advClickRecordChart")
	public String advClickRecordChartView(AdStatistic entity, HttpServletRequest request, HttpServletResponse response, Model model) {

        String startDate = null;
		String endDate = null;
		if(entity.getPlayStartDate() != null){
			startDate = DateUtils.formatDate(entity.getPlayStartDate(),"yyyy-MM-dd");
		}
		if(entity.getPlayEndDate() != null){
			endDate = DateUtils.formatDate(entity.getPlayEndDate(),"yyyy-MM-dd");
		}
		model.addAttribute("playStartDate", startDate);
		model.addAttribute("playEndDate", endDate);

		return "/statistics/advClickRecordChart";
	}

    /**
     * 点击统计图表数据
     * @param req
     * @param resp
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value="/clickChartData",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String  getClickChartData(HttpServletRequest req,HttpServletResponse resp, Date startDate,Date endDate){

		Locale currentLocale = LocaleContextHolder.getLocale();
		String name = currentLocale.getLanguage();
		AdStatistic entity = new AdStatistic();
        entity.setPlayStartDate(startDate);
        entity.setPlayEndDate(endDate);
		List<AdStatisticTypePlayDTO> typeList = thisService.findAdvTypeClickCount(entity);
		//将typeId与typeName放入map，供后续使用
		Map<String,String> typeMap = new HashMap<>(typeList.size());
		for (AdStatisticTypePlayDTO dto : typeList){
			AdType adType = adTypeService.getAdType(dto.getAdvTypeId());
			typeMap.put(dto.getAdvTypeId(),adType.getTypeName());
			dto.setAdvTypeName(adType.getTypeName());
		}
		List<AdElementStatisticPlayDTO> allElementList = thisService.statisticAdvElementClickCount(entity);
		Multimap<String, AdElementStatisticPlayDTO> multiMap = ArrayListMultimap.create() ;
		for(AdElementStatisticPlayDTO dto : allElementList) {
			multiMap.put( typeMap.get(dto.getAdvTypeId()), dto) ;
		}
		List<AdStatisticChildChartDTO> elementList =  elementList = new ArrayList<>();
		for (Map.Entry<String, String> entry : typeMap.entrySet()) {
			Collection<AdElementStatisticPlayDTO> childElementList = multiMap.get(entry.getValue());
			AdStatisticChildChartDTO childChartDTO = new AdStatisticChildChartDTO(entry.getValue(),childElementList);
			elementList.add(childChartDTO);
		}
		AdStatisticChartDTO chartData = new AdStatisticChartDTO(typeList,elementList);
		chartData.setLanguage(name);
        return JSON.toJSONString(chartData);
    }

    /**
     * 广告点击详情
     * @param advId
     * @param startDate
     * @param endDate
     * @param model
     * @return
     */
    @RequiresPermissions("adv:click:view")
    @RequestMapping(value = "/advClickChartDetail")
    public String advClickRecordChartDetailView(@RequestParam(required = true)String advId,@RequestParam(required = false) String startDate,@RequestParam(required = false) String endDate, Model model) {
        Page<AdStatistic> page = new Page<AdStatistic>(1, Integer.valueOf(Global.getConfig("page.pageSize")));
        try {
            AdStatistic statistic = new AdStatistic();
            String queryParam ="advId="+advId;
            String playStartDate="";
            String playEndDate ="";
            if(org.apache.commons.lang3.StringUtils.isNotBlank(startDate) ){
                Date queryStartDate = DateUtils.getDateFromString(startDate,"yyyy-MM-dd");
                statistic.setPlayStartDate(queryStartDate);
                playStartDate = DateUtils.formatDate(queryStartDate, "yyyy-MM-dd");
                queryParam +="&startDate="+playStartDate;
            }else{
                model.addAttribute("playStartDate", null);
            }
            if(org.apache.commons.lang3.StringUtils.isNotBlank(endDate)){
                Date queryEndDate = DateUtils.getDateFromString(endDate,"yyyy-MM-dd");
                statistic.setPlayEndDate(queryEndDate);
                playEndDate = DateUtils.formatDate(queryEndDate, "yyyy-MM-dd");
                queryParam +="&endDate="+endDate;

            }
            Adelement adelement = new Adelement();
            adelement.setAdId(advId);
            statistic.setAdElemet(adelement);
            statistic.setPage(page);
            statistic.setIsScan(AdStatistic.SKIP_URL_YES);
            page = thisService.findAdvPlayDetail(page, statistic);

            model.addAttribute("page", page);
            statistic.setHistory(-1);
            model.addAttribute("adStatistic", statistic);
            model.addAttribute("queryParam",queryParam);
            model.addAttribute("playStartDate", playStartDate);
            model.addAttribute("playEndDate", playEndDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/statistics/advClickRecordChartDetail";
    }

	/**
	 * 广告点击详情翻页
	 * @param entity
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adv:click:view")
	@RequestMapping(value = "/advClickChartDetailList")
	public String advClickChartDetailList(AdStatistic entity,HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<AdStatistic> page = new Page<AdStatistic>(request, response);
		try {
			entity.setPage(page);
			entity.setIsScan(AdStatistic.SKIP_URL_YES);
			page = thisService.findAdvPlayDetail(page, entity);

			int history = entity.getHistory();
			history--;
			entity.setHistory(history);
			model.addAttribute("page", page);
			model.addAttribute("adStatistic", entity);
			String queryParam ="advId="+entity.getAdElemet().getAdId();
			String playStartDate="";
			String playEndDate="";
			if(null != entity.getPlayStartDate()){

				playStartDate=DateUtils.formatDate(entity.getPlayStartDate(), "yyyy-MM-dd");
				queryParam += "&startDate="+playStartDate;
			}
			if(null != entity.getPlayEndDate()){

				playEndDate=DateUtils.formatDate(entity.getPlayEndDate(), "yyyy-MM-dd");
				queryParam += "&endDate="+playEndDate;
			}
			model.addAttribute("playStartDate", playStartDate);
			model.addAttribute("playEndDate",playEndDate);
			model.addAttribute("queryParam",queryParam);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "/statistics/advClickRecordChartDetail";
	}

	/**
	 * 播放时长统计图表页面
	 * @param entity
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adv:play:view")
	@RequestMapping(value = "/advPlayRecordChart")
	public String advPlayRecordChartView(AdStatistic entity, HttpServletRequest request, HttpServletResponse response, Model model) {

		String startDate = null;
		String endDate = null;
		if(entity.getPlayStartDate() != null){
			startDate = DateUtils.formatDate(entity.getPlayStartDate(),"yyyy-MM-dd");
		}
		if(entity.getPlayEndDate() != null){
			endDate = DateUtils.formatDate(entity.getPlayEndDate(),"yyyy-MM-dd");
		}
		model.addAttribute("playStartDate", startDate);
		model.addAttribute("playEndDate", endDate);

		return "/statistics/advPlayRecordChart";
	}

	/**
	 * 播放统计图表数据
	 * @param req
	 * @param resp
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value="/playChartData",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String  getPlayChartData(HttpServletRequest req,HttpServletResponse resp, Date startDate,Date endDate){

		Locale currentLocale = LocaleContextHolder.getLocale();
		String name = currentLocale.getLanguage();
		AdStatistic entity = new AdStatistic();
		entity.setPlayStartDate(startDate);
		entity.setPlayEndDate(endDate);
		List<AdStatisticTypePlayDTO> typeList = thisService.findAdvTypePlayCount(entity);
		//将typeId与typeName放入map，供后续使用
		Map<String,String> typeMap = new HashMap<>(typeList.size());
		for (AdStatisticTypePlayDTO dto : typeList){
			AdType adType = adTypeService.getAdType(dto.getAdvTypeId());
			typeMap.put(dto.getAdvTypeId(),adType.getTypeName());
			dto.setAdvTypeName(adType.getTypeName());
		}
		List<AdElementStatisticPlayDTO> allElementList = thisService.statisticAdvElementPlayCount(entity);
		Multimap<String, AdElementStatisticPlayDTO> multiMap = ArrayListMultimap.create() ;
		for(AdElementStatisticPlayDTO dto : allElementList) {
			multiMap.put( typeMap.get(dto.getAdvTypeId()), dto) ;
		}
		List<AdStatisticChildChartDTO> elementList =  elementList = new ArrayList<>();
		for (Map.Entry<String, String> entry : typeMap.entrySet()) {
			Collection<AdElementStatisticPlayDTO> childElementList = multiMap.get(entry.getValue());
			AdStatisticChildChartDTO childChartDTO = new AdStatisticChildChartDTO(entry.getValue(),childElementList);
			elementList.add(childChartDTO);
		}
		AdStatisticChartDTO chartData = new AdStatisticChartDTO(typeList,elementList);
		chartData.setLanguage(name);
		return JSON.toJSONString(chartData);
	}

	/**
	 * 广告播放详情
	 * @param advId
	 * @param startDate
	 * @param endDate
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adv:play:view")
	@RequestMapping(value = "/advPlayChartDetail")
	public String advPlayRecordChartDetailView(@RequestParam(required = true)String advId,@RequestParam(required = false) String startDate,@RequestParam(required = false) String endDate, Model model) {
		Page<AdStatistic> page = new Page<AdStatistic>(1, Integer.valueOf(Global.getConfig("page.pageSize")));
		try {
			AdStatistic statistic = new AdStatistic();
			String queryParam ="advId="+advId;
			String playStartDate="";
			String playEndDate ="";
			if(org.apache.commons.lang3.StringUtils.isNotBlank(startDate) ){
				Date queryStartDate = DateUtils.getDateFromString(startDate,"yyyy-MM-dd");
				statistic.setPlayStartDate(queryStartDate);
				playStartDate = DateUtils.formatDate(queryStartDate, "yyyy-MM-dd");
				queryParam +="&startDate="+playStartDate;
			}else{
				model.addAttribute("playStartDate", null);
			}
			if(org.apache.commons.lang3.StringUtils.isNotBlank(endDate)){
				Date queryEndDate = DateUtils.getDateFromString(endDate,"yyyy-MM-dd");
				statistic.setPlayEndDate(queryEndDate);
				playEndDate = DateUtils.formatDate(queryEndDate, "yyyy-MM-dd");
				queryParam +="&endDate="+endDate;

			}
			Adelement adelement = new Adelement();
			adelement.setAdId(advId);
			statistic.setAdElemet(adelement);
			statistic.setPage(page);
			statistic.setIsScan(AdStatistic.SKIP_URL_NO);
			page = thisService.findAdvPlayDetail(page, statistic);

			model.addAttribute("page", page);
			statistic.setHistory(-1);
			model.addAttribute("adStatistic", statistic);
			model.addAttribute("queryParam",queryParam);
			model.addAttribute("playStartDate", playStartDate);
			model.addAttribute("playEndDate", playEndDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/statistics/advPlayRecordChartDetail";
	}

	/**
	 *  播放详情翻页
	 * @param entity
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adv:play:view")
	@RequestMapping(value = "/advPlayChartDetailList")
	public String advPlayChartDetailList(AdStatistic entity,HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<AdStatistic> page = new Page<AdStatistic>(request, response);
		try {
			entity.setPage(page);
			entity.setIsScan(AdStatistic.SKIP_URL_NO);
			page = thisService.findAdvPlayDetail(page, entity);

			model.addAttribute("page", page);
			model.addAttribute("adStatistic", entity);
//		String queryParam ="advId="+entity.getAdElemet().getAdId();
			String playStartDate="";
			String playEndDate="";
			if(null != entity.getPlayStartDate()){

				playStartDate=DateUtils.formatDate(entity.getPlayStartDate(), "yyyy-MM-dd");
				// queryParam += "&startDate="+playStartDate;
			}
			if(null != entity.getPlayEndDate()){

				playEndDate=DateUtils.formatDate(entity.getPlayEndDate(), "yyyy-MM-dd");
				//queryParam += "&endDate="+playEndDate;
			}
			model.addAttribute("playStartDate", playStartDate);
			model.addAttribute("playEndDate",playEndDate);
			// model.addAttribute("queryParam",queryParam);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "/statistics/advPlayRecordChartDetail";
	}
}
