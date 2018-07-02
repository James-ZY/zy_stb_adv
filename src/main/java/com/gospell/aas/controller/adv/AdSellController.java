package com.gospell.aas.controller.adv;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.gospell.aas.common.config.Global;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.common.utils.adv.PrimaryGenerater;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.*;
import com.gospell.aas.entity.adv.*;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.service.ServiceException;
import com.gospell.aas.service.adv.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/adv/sell")
public class AdSellController extends BaseController {

	@Autowired
	private AdSellService thisService;
	
	@Autowired
	private AdComboService comboService;
	@Autowired
	private AdvertiserService advertiserService;
	@Autowired
	private AdelementService adelementService;
	@Autowired
	private AdTypeService adTypeService;

	@ModelAttribute
	public AdSell get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdSell();
		}
	}
	
 

	@RequiresPermissions("sys:sell:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdSell entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdSell> page = thisService.find(new Page<AdSell>(request, response), entity);
		User user = UserUtils.getUser();
		if(user.getAdvertiser() == null){
			model.addAttribute("isAdvtiser", false);
		}else{
			model.addAttribute("isAdvtiser", true);
		}
		model.addAttribute("page", page);
		if(null != entity.getCreateStartDate()){
		  model.addAttribute("beginCreateDate", DateUtils.formatDate(entity.getCreateStartDate(), "yyyy-MM-dd"));
		}
		if(null != entity.getCreateEndDate()){
 	 	  model.addAttribute("endCreateDate",  DateUtils.formatDate(entity.getCreateEndDate(), "yyyy-MM-dd"));
		}
		
		if(null != entity.getStartDate()){
				model.addAttribute("beginDate",  DateUtils.formatDate(entity.getStartDate(), "yyyy-MM-dd"));
		}
		if(null != entity.getEndDate()){
				model.addAttribute("endDate", DateUtils.formatDate(entity.getEndDate(), "yyyy-MM-dd"));
		}
		 
		return "/adsell/sellList";
	}

	@RequiresPermissions("sys:sell:view")
	@RequestMapping(value = "/form")
	public String form(AdSell entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		String id = entity.getId();
		boolean b  = true;
		Date maxDate = null;
		if(StringUtils.isNotBlank(id)){
		  b = thisService.isCanDeleteSell(id);
		  if(!b){
			  maxDate = thisService.getAdvMaxDateInSell(id);
		  }
			
		}
		model.addAttribute("adSell", entity);
		model.addAttribute("isHaveAdv", b);
		String maxDateStr = null;
		if(null != maxDate){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			maxDateStr = format.format(maxDate);
		}
		model.addAttribute("maxDate", maxDateStr);
		if(StringUtils.isBlank(entity.getId())){
			model.addAttribute("contractNumber", PrimaryGenerater.getInstance().generaterNextNumber());

		}else{
			model.addAttribute("contractNumber",entity.getContractNumber());
			model.addAttribute("typeId", entity.getAdCombo().getAdType().getId());
		}
		if(null != entity.getStartDate()){
			model.addAttribute("startDate", DateUtils.formatDate(entity.getStartDate(), "yyyy-MM-dd"));
			
		}else{
			model.addAttribute("startDate", "");
		}
		if(null != entity.getEndDate()){
			model.addAttribute("endDate", DateUtils.formatDate(entity.getEndDate(), "yyyy-MM-dd"));
		}else{
			model.addAttribute("endDate", "");
		}
		String json = "";
		if(entity.getAdCombo() != null){
			AdComboCanSellQueryDTO dto = comboService.getTypeAndNetworkByCombo(entity.getAdCombo());
			model.addAttribute("netWorkStr",dto.getNetWork());
			json = JsonMapper.toJsonString(dto);
		}
		
		model.addAttribute("typeAndNetwork", json);
		return "/adsell/sellForm";
	}
	
	 

	@RequiresPermissions("sys:sell:edit")
	@RequestMapping(value = "/save") 
	public String save(AdSell entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		
		if (!beanValidator(model, entity)) {
			return form(entity, request,model,redirectAttributes);
		}
		String name= entity.getId();
		if(entity.getAdCombo() != null && entity.getAdCombo().getComboName() != null){
			name=entity.getAdCombo().getComboName();
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("adv.sell"), name);
	 
		try {
			Boolean b = thisService.judgeAdTypeIsRepeat(entity);
			if(b) {
				comboService.clear();
				thisService.clear();
				thisService.save(entity);
				logService.save(request, logInfo, null);
				logger.info(UserUtils.getUser().getLoginName() + "保存或者修改套餐销售记录：" + entity.getAdCombo().getComboName() + "成功");
				addMessage(redirectAttributes, "msg.save.success");
				return "redirect:/adv/sell/?repage";
			}else{
				thisService.clear();
				addMessage(redirectAttributes,  "comid.already.select");
				return form(entity, request,model,redirectAttributes);
			}
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存或者修改套餐销售记录："+entity.getAdCombo().getComboName()+"失败",e.getMessage());
			addMessage(redirectAttributes,  "msg.save.fail");
			return "redirect:/adv/sell/?repage";
		}
	}
	
	@RequiresPermissions("sys:sell:edit")
	@RequestMapping(value = "/delete") 
	public String delete(String id, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		AdSell sell = get(id);
		String name= id;
		if(sell.getAdCombo() != null && sell.getAdCombo().getComboName() != null){
			name=sell.getAdCombo().getComboName();
		}
		String logInfo=logService.getLogInfo(id, 1, getMessage("adv.sell"), name);
		 
		try {
			Boolean b = thisService.isCanDeleteSell(id);
			if(b){
				thisService.delete(id);
				logService.save(request, logInfo, null);
				logger.info(UserUtils.getUser().getLoginName()+"删除套餐销售记录："+name+"成功");
				addMessage(redirectAttributes, "msg.del.success");
			}else{
				addMessage(redirectAttributes, "del.sell.have.adv");
				logService.save(request, logInfo, new Exception(getMessage("del.sell.have.adv")));
				logger.error(UserUtils.getUser().getLoginName()+"删除套餐销售记录："+name+"失败",getMessage("del.sell.have.adv"));
			}

			return "redirect:/adv/sell/?repage";
		} catch (ServiceException e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"删除套餐销售记录："+name+"失败",e.getMessage());
			addMessage(redirectAttributes,  "msg.del.fail");
			return "redirect:/adv/sell/?repage";
		}
	}
	
	/**
	 * 提前终止合约
	 * @param id
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:sell:edit")
	@RequestMapping(value = "/stop") 
	public String stop(String id, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		AdSell sell = get(id);
		String name= id;
		AdCombo adCombo = null;
		if(sell.getAdCombo() != null && sell.getAdCombo().getComboName() != null){
			adCombo = sell.getAdCombo();
			name=sell.getAdCombo().getComboName();
		}
		String logInfo=logService.getLogInfo(id, 0, getMessage("adv.sell"), name);
			try {
				//设置套餐状态为未生效
				/*adCombo.setStatus(AdCombo.ADCOMOBO_DENY_STATUS);
				adCombo.setValidEndTime(new Date());
				adCombo.setRemarks(getMessage("adv.sell.stop"));
				comboService.save(adCombo);*/
				//查询该销售时间段下的所有广告并停播
				List<Adelement> adeList = thisService.fingAdvInSell(id);
				 String deriction = getMessage("adv.adId");
					for (Adelement adelement : adeList) {
						String logInfo1=getMessage("close.down.log",new Object[]{deriction,adelement.getAdId()});
						try {	
							if(adelement.getStatus() == Adelement.ADV_STATUS_SHOW){//停播正在投放的广告
								adelementService.closeDown(adelement.getId(), Adelement.ADV_DELETE_NOW_YES);
								addMessage(redirectAttributes, "close.down.success");
								logService.save(request, logInfo1, null);
								logger.info(UserUtils.getUser().getLoginName()+"停播广告Id："+adelement.getAdId()+"成功");
							}
							//广告类型变为合同终止状态
							adelement.setStatus(Adelement.ADV_STATUS_STOP);
							Integer jud = DateUtils.judgeDateSize(adelement.getEndDate(),new Date());
							if(jud<0){
								adelement.setEndDate(new Date());
							}
							adelementService.updateStatus(adelement);
						} catch (Exception e) {
							e.printStackTrace();
							addMessage(redirectAttributes, "close.down.fail");
							logService.save(request, logInfo1, e);
							logger.error(UserUtils.getUser().getLoginName()+"停播广告Id："+adelement.getAdId()+"失败",e.getMessage());
						}
					}
					//更新销售记录的状态为失效
					sell.setEndDate(new Date());
					sell.setStatus(AdSell.ADSELL_NO_VALID);
					thisService.clear();
					thisService.save(sell);
					logService.save(request, logInfo, null);
					logger.info(UserUtils.getUser().getLoginName()+"提前终止合约："+name+"成功");
					addMessage(redirectAttributes, "adv.sell.stop.success");
			} catch (Exception e) {
				logService.save(request, logInfo, e);
				logger.info(UserUtils.getUser().getLoginName()+"提前终止合约："+name+"失败");
			}
			return "redirect:/adv/sell/?repage";
	}
	
    @ResponseBody
    @RequiresPermissions("sys:sell:edit")
    @RequestMapping(value = "/checkContractNumber")
    public String checkContractNumber(String oldContractNumber, String contractNumber) {
    	try{
        if (contractNumber != null && contractNumber.equals(oldContractNumber)) {
            return "true";
        } else if (contractNumber != null && thisService.findSellByContractNumber(contractNumber) == null) {
            return "true";
        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return "false";
    }
    

	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/comboSellNumber")
	public String comboSellNumber(AdSell entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(entity.getAdCombo() == null || entity.getAdCombo().getAdType() == null){
			model.addAttribute("page", new Page<>(request, response));
			return "/statistics/comboSellToAdvtiser";
		}
		Page<AdSell> page = thisService.findAdvertiserBuyTime(new Page<AdSell>(request, response), entity);
		model.addAttribute("page", page);
		model.addAttribute("beginDate", DateUtils.formatDate(entity.getStartDate(),"yyyy-MM-dd"));
		model.addAttribute("endDate", DateUtils.formatDate(entity.getEndDate(),"yyyy-MM-dd"));
		return "/statistics/comboSellToAdvtiser";
	}

	/**
	 * 广告商购买套餐时长比例
	 * @author zhw
	 * @param entity
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/comboSellTime")
	public String comboSellTime(AdSell entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(entity.getAdCombo() == null || entity.getAdCombo().getAdType() == null){
			model.addAttribute("page", new Page<>(request, response));
			return "/statistics/comboSellToAdvtiser";
		}
		Page<AdSell> page = thisService.findAdvertiserBuyNumber(new Page<AdSell>(request, response), entity);
		model.addAttribute("page", page);
		model.addAttribute("beginDate", DateUtils.formatDate(entity.getStartDate(),"yyyy-MM-dd"));
		model.addAttribute("endDate", DateUtils.formatDate(entity.getEndDate(),"yyyy-MM-dd"));
		return "/statistics/comboSellToAdvtiser";
	}
	
	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/comboSellCount")
	public String comboSellToAdvTiser(String startDate,String endDate,String typeId, Model model) {
	  
		Page<AdSell> page = new Page<AdSell>(1, Integer.valueOf(Global.getConfig("page.pageSize")));
		
		try {
			AdSell sell = new AdSell();
			Date queryStartDate = DateUtils.getDateFromString(startDate);
			Date queryEndDate = DateUtils.getDateFromString(endDate);
			AdCombo combo = new AdCombo();
			AdType type = new AdType();
			type.setId(typeId);
			combo.setAdType(type);
			 
			sell.setAdCombo(combo);
			sell.setPage(page);
			sell.setStartDate(queryStartDate);
			sell.setEndDate(queryEndDate);
		 page = thisService.findAdvertiserBuyCombo(page, sell);
		 int history = -1;
		 sell.setHistory(history);
		 model.addAttribute("page", page);
		 model.addAttribute("adSell", sell);
		 model.addAttribute("beginDate", DateUtils.formatDate(queryStartDate, "yyyy-MM-dd"));
		 model.addAttribute("endDate", DateUtils.formatDate(queryEndDate, "yyyy-MM-dd"));
		 String typeName ="";
		 AdType paramtype = AdTypeUtils.get(typeId);
		 if(paramtype != null){
			 typeName = paramtype.getTypeName();
		 }
		 model.addAttribute("typeName", typeName);
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		return "/statistics/comboSellList";
	}
	
	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/comboSellToAdvTiser")
	public String comboSellToAdvTiser1(AdSell sell,HttpServletRequest request, HttpServletResponse response, Model model) {
		 
		Page<AdSell> page = new Page<AdSell>(request, response);
		try {
		 sell.setPage(page);
		 page = thisService.findAdvertiserBuyCombo(page, sell);
		 int history = sell.getHistory();
		 history --;
		 sell.setHistory(history);
		 model.addAttribute("page", page);
		 model.addAttribute("adSell", sell);
		 model.addAttribute("beginDate", DateUtils.formatDate(sell.getStartDate(), "yyyy-MM-dd"));
		 model.addAttribute("endDate", DateUtils.formatDate(sell.getEndDate(), "yyyy-MM-dd"));
		 String typeName ="";
		 if(sell.getAdCombo() != null && sell.getAdCombo().getAdType() != null){
			 AdType paramtype = AdTypeUtils.get(sell.getAdCombo().getAdType().getId());
			 if(paramtype != null){
				 typeName = paramtype.getTypeName();
			 }
		 }
		 model.addAttribute("typeName", typeName);
		 model.addAttribute("count", -2);
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		return "/statistics/comboSellList";
	}
	
	
	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/advtiserBuyCombo")
	public String advtiserBuyComboList(String startDate,String endDate,String typeId,String advId,String advName, Model model) {
	  
		Page<AdSell> page = new Page<AdSell>(1, Integer.valueOf(Global.getConfig("page.pageSize")));
		
		try {
			AdSell sell = new AdSell();
			Date queryStartDate = DateUtils.getDateFromString(startDate);
			Date queryEndDate = DateUtils.getDateFromString(endDate);
			AdCombo combo = new AdCombo();
			Advertiser adv =new Advertiser();
			adv.setAdvertiserId(advId);
			//adv.setName(advName);
			sell.setAdvertiser(adv);
		
			AdType type = new AdType();
			type.setId(typeId);
			combo.setAdType(type);
			 
			sell.setAdCombo(combo);
			sell.setPage(page);
			sell.setStartDate(queryStartDate);
			sell.setEndDate(queryEndDate);
		 //page = thisService.getSellAdvTiserComboDetail(page, sell);
			page = thisService.findAdSellByAdType(page, sell);
		 String typeName ="";
		 String name = ""; 
		 Advertiser a = null;
		 if(sell.getAdvertiser() != null && StringUtils.isNotBlank(sell.getAdvertiser().getAdvertiserId())){
			   a = advertiserService.findAdvertiserById(sell.getAdvertiser().getAdvertiserId());
			   if(a != null){
				   name = a.getName();
			   }
		 }

		 AdType paramtype = AdTypeUtils.get(sell.getAdCombo().getAdType().getId());
		 if(paramtype != null){
			 typeName = paramtype.getTypeName();
		 }
		 int history = -1;
		 sell.setHistory(history);
		 model.addAttribute("typeName", typeName);
		 model.addAttribute("advName", name);
		 model.addAttribute("page", page);
		 model.addAttribute("adSell", sell);
		 model.addAttribute("beginDate", DateUtils.formatDate(queryStartDate, "yyyy-MM-dd"));
		 model.addAttribute("endDate", DateUtils.formatDate(queryEndDate, "yyyy-MM-dd"));
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		return "/statistics/advtiserBuyComboList";
	}
	
	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/advtiserBuyComboList")
	public String advtiserBuyComboList(AdSell sell,HttpServletRequest request, HttpServletResponse response, Model model) {
	  
		Page<AdSell> page = new Page<AdSell>(request, response);
		try {
		 sell.setPage(page);
		 page = thisService.getSellAdvTiserComboDetail(page, sell);
		 int history = sell.getHistory();
		 history --;
		 sell.setHistory(history);
		 model.addAttribute("page", page);
		 model.addAttribute("adSell", sell);
		 model.addAttribute("beginDate", DateUtils.formatDate(sell.getStartDate(), "yyyy-MM-dd"));
		 model.addAttribute("endDate", DateUtils.formatDate(sell.getEndDate(), "yyyy-MM-dd"));
		 String typeName ="";
		 String advName = ""; 
		 Advertiser a = null;
		 if(sell.getAdvertiser() != null && StringUtils.isNotBlank(sell.getAdvertiser().getAdvertiserId())){
			   a = advertiserService.findAdvertiserById(sell.getAdvertiser().getAdvertiserId());
			   if(a != null){
				   advName = a.getName();
			   }
		 }

		 if(sell.getAdCombo() != null && sell.getAdCombo().getAdType() != null){
			 AdType paramtype = AdTypeUtils.get(sell.getAdCombo().getAdType().getId());
			 if(paramtype != null){
				 typeName = paramtype.getTypeName();
			 }
		 }
		 model.addAttribute("typeName", typeName);
		 model.addAttribute("advName", advName);
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		
		return "/statistics/advtiserBuyComboList";
	}
	/**
	 * 销售时段分析
	 * @param sell
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("channelCombo:sell:view")
	@RequestMapping(value = "/channleComboSellList")
	public String channleComboSellList(AdSell sell,HttpServletRequest request, HttpServletResponse response, Model model) {
		String json ="";
		try{
			
		if(null != sell.getStartDate() && null != sell.getEndDate() ){
		 
	       json=thisService.getSellDayTimeNumberBymemory(sell);
	       System.out.println(json);
			//model.addAttribute("beginDate", DateUtils.formatDate(sell.getStartDate(), "yyyy-MM-dd"));
//			model.addAttribute("endDate", DateUtils.formatDate(sell.getEndDate(), "yyyy-MM-dd"));
			model.addAttribute("beginDate", DateUtils.formatDate(sell.getStartDate(), "yyyy-MM-dd"));
			model.addAttribute("endDate", DateUtils.formatDate(sell.getEndDate(), "yyyy-MM-dd"));
//		
		}else{
			model.addAttribute("beginDate", null);
			model.addAttribute("endDate", null);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		String tyeName="";
		if(sell.getAdCombo() != null){
			if(sell.getAdCombo().getAdType() != null){
				String id = sell.getAdCombo().getAdType().getId();
				AdType type = AdTypeUtils.get(id);
				if(type != null){
					tyeName = type.getTypeName();
				}
				 
			}
		}
		model.addAttribute("sellCount", json);
		model.addAttribute("tyeName", tyeName);
		return "/statistics/channelcomboSell";
	}
	
	/**
	 * 统计一段时间内套餐的销售数量
	 * @param dto
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("combo:sell:count:view")
	@RequestMapping(value = "/getAdComboSellCount")
	public String getAdComboSellCount(AdComboSellCountDto dto,String startDate,String endDate,String typeId,HttpServletRequest request, HttpServletResponse response, Model model) {
		String json ="";	 
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)){
				return "/statistics/comboSellCount";
			}
			int days = DateUtils.dateBetweenDay(dto.getStartDate(),dto.getEndDate());
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("typeId", dto.getAdType().getId());
			map.put("startDate", startDate);
			map.put("advertiserid", dto.getAdvertiser().getId());
			map.put("days", days);
			json=thisService.getAdComboSellCount(map);
			model.addAttribute("beginDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("sellCount", json);
			System.out.println(json);			
		}catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		return "/statistics/comboSellCount";
	}
	
	
	/**
	 * 
	 * 套餐发布统计
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("combo:statistics:release:view")
	@RequestMapping(value = "/comboReleaseList")
	public String comboReleaseList(AdComboPublishNumberDto entity,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdComboPublishNumberDto> page = thisService.findComboRelease(new Page<AdComboPublishNumberDto>(request, response), entity);
		model.addAttribute("page", page);
		String startDate = null;
		String endDate = null;
		if(entity.getStartDate() != null){
			startDate = DateUtils.formatDate(entity.getStartDate(),"yyyy-MM-dd");
		}
		if(entity.getEndDate() != null){
			endDate = DateUtils.formatDate(entity.getEndDate(),"yyyy-MM-dd");
		}
 
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
 
		return "/statistics/comboReleaseList";
	}
	
	@RequiresPermissions("combo:statistics:release:view")
	@RequestMapping(value = "/comboReleaseCount")
	public String comboReleaseCount(AdComboPublishNumber entity,HttpServletRequest request, HttpServletResponse response,String startDate,String endDate,String typeId, String selectType,Model model) {
		Page<AdComboPublishNumber> page = new Page<AdComboPublishNumber>(request, response);
		Date queryStartDate = null;
		Date queryEndDate = null;
		AdComboPublishNumberDto dto = new AdComboPublishNumberDto();
		try {
			if(StringUtils.isNotBlank(startDate)){
				queryStartDate = DateUtils.getDateFromString(startDate);
				model.addAttribute("beginDate", DateUtils.formatDate(queryStartDate, "yyyy-MM-dd"));
			}
			if(StringUtils.isNotBlank(endDate)){
				queryEndDate = DateUtils.getDateFromString(endDate);
				model.addAttribute("endDate", DateUtils.formatDate(queryEndDate, "yyyy-MM-dd"));
			}
			AdType type = new AdType();
			type.setId(typeId);
			 
			dto.setAdType(type);
			dto.setStartDate(queryStartDate);
			dto.setEndDate(queryEndDate);
		 page = thisService.findComboReleaseList(page, dto,selectType.equals("release")?">":"=");
		 model.addAttribute("page", page);
		 model.addAttribute("selectType", selectType);
		 model.addAttribute("adComboPublishNumber", dto);
		 String typeName ="";
		 AdType paramtype = AdTypeUtils.get(typeId);
		 if(paramtype != null){
			 typeName = paramtype.getTypeName();
		 }
		 model.addAttribute("typeName", typeName);
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		return "/statistics/comboReleaseCount";
	}
	
	/**
	 * 修改销售记录需要调用的接口
	 * @param dto
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "check_alter_sell",method=RequestMethod.POST)
	public boolean check(@RequestBody AdSelAlterlDTO dto,HttpServletRequest request, HttpServletResponse response) {
		boolean b = false;
		try {
			  String id =dto.getId();
				 
			  String newComboId  = dto.getNewComboId();
				
		      String newAdvertiserId  =dto.getNewAdvertiserId();
				
	          String newStartDate =dto.getNewStartDate();
				
		 	  String newEndDate =dto.getNewEndDate();
				 
		     String oldComboId =dto.getOldComboId();	
			 
			 String oldAdvertiserId = dto.getOldAdvertiserId();

			 String oldStartDate  = dto.getOldStartDate();
			 
			 String oldEndDate  = dto.getOldEndDate();
			 Boolean isHaveAdv = dto.getIsHaveAdv();
			 if(isHaveAdv == null ||StringUtils.isBlank(id) || StringUtils.isBlank(newComboId)||
					 StringUtils.isBlank(newAdvertiserId)||StringUtils.isBlank(newStartDate)||StringUtils.isBlank(newEndDate)
					 ||StringUtils.isBlank(oldComboId)||StringUtils.isBlank(oldAdvertiserId)||StringUtils.isBlank(oldEndDate)||StringUtils.isBlank(oldStartDate)){
				 b =false;
			 }else{
				  b= thisService.checkAlterSell(dto);
			 }
			
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		return b;
		
	}


	/**
	 * 广告商统计
	 * 		1.各类型广告 广告商占比
	 * 		2.广告商列表
	 * @param entity
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/comboSellStats")
	public String advertiserStats(AdSell entity, HttpServletRequest request, HttpServletResponse response, Model model) {

		//查询各类型广告广告商购买时长占比
		List<AdvtiserRateDTO> rateList = thisService.findAdvtiserRateInAdTypes(entity.getStartDate(),entity.getEndDate());

		//广告类型id 名称map
		Map<String,String> typeMap = new HashMap<>(rateList.size());
		for (AdvtiserRateDTO dto : rateList){
			AdType adType = adTypeService.getAdType(dto.getAdTypeId());
			typeMap.put(dto.getAdTypeId(),adType.getTypeName());
			dto.setAdTypeName(adType.getTypeName());
		}

		Multimap<String, AdvtiserRateDTO> multiMap = ArrayListMultimap.create() ;
		for(AdvtiserRateDTO dto : rateList) {
			multiMap.put(dto.getAdTypeName(), dto) ;
		}

		List<AdvtiserRateChartDTO> rateChartList = new ArrayList<AdvtiserRateChartDTO>();
		for (Map.Entry<String, String> entry : typeMap.entrySet()) {
			Collection<AdvtiserRateDTO> advList = multiMap.get(entry.getValue());
			AdvtiserRateChartDTO childChartDTO = new AdvtiserRateChartDTO(entry.getValue(),advList);
			rateChartList.add(childChartDTO);
		}

		String rateChartJson = JSON.toJSONString(rateChartList);
		model.addAttribute("rateChartJson", rateChartJson);
		if(null!=entity.getStartDate()) {
			model.addAttribute("beginDate", DateUtils.formatDate(entity.getStartDate(), "yyyy-MM-dd"));
		}
		if(null!=entity.getStartDate()) {
			model.addAttribute("endDate", DateUtils.formatDate(entity.getEndDate(), "yyyy-MM-dd"));
		}
		return "/statistics/comboSellStats";
	}

	/**
	 * 广告商购买套餐详情
	 *	zhw 111
	 * @param startDate
	 * @param endDate
	 * @param typeId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/comboSellStatsDetail")
	public String advtiserBuyComboDetail(String startDate,String endDate,String typeId,String advId,Integer startHour,Integer endHour,Model model) {

		Page<AdSell> page = new Page<AdSell>(1, Integer.valueOf(Global.getConfig("page.pageSize")));
		Date queryStartDate=null,queryEndDate = null;
		try {
			AdSell sell = new AdSell();
			if(StringUtils.isNotEmpty(startDate)){
				queryStartDate = DateUtils.getDateFromString(startDate,"yyyy-MM-dd");
			}
			if(StringUtils.isNotEmpty(endDate)) {
				queryEndDate = DateUtils.getDateFromString(endDate,"yyyy-MM-dd");
			}
			AdCombo combo = new AdCombo();
			Advertiser adv =new Advertiser();
			adv.setAdvertiserId(advId);
			sell.setAdvertiser(adv);

			AdType type = new AdType();
			type.setId(typeId);
			combo.setAdType(type);
            combo.setStartHour(startHour);
            combo.setEndHour(endHour);

			sell.setAdCombo(combo);
			sell.setPage(page);
			sell.setStartDate(queryStartDate);
			sell.setEndDate(queryEndDate);

			page = thisService.findAdSellByAdType(page, sell);
			String typeName ="";
			String name = "";
			AdType paramtype = AdTypeUtils.get(sell.getAdCombo().getAdType().getId());
			if(paramtype != null){
				typeName = paramtype.getTypeName();
			}
			int history = -1;
			sell.setHistory(history);
			model.addAttribute("typeName", typeName);
			model.addAttribute("page", page);
			model.addAttribute("adSell", sell);
			model.addAttribute("beginDate", startDate);
			model.addAttribute("endDate", endDate);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "/statistics/comboSellStatsDetail";
	}

	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/comboSellStatsDetailList")
	public String advtiserBuyComboDetailList(AdSell sell,HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<AdSell> page = new Page<AdSell>(request, response);
		try {
			sell.setPage(page);
			page = thisService.findAdSellByAdType(page, sell);
			int history = sell.getHistory();
			history --;
			sell.setHistory(history);
			model.addAttribute("page", page);
			model.addAttribute("adSell", sell);
			String beginDateStr = "";
			String endDateStr = "";
			if(null!=sell.getStartDate()){
				beginDateStr = DateUtils.formatDate(sell.getStartDate(), "yyyy-MM-dd");
			}
			if(null!=sell.getEndDate()){
				endDateStr = DateUtils.formatDate(sell.getEndDate(), "yyyy-MM-dd");
			}
			model.addAttribute("beginDate", beginDateStr);
			model.addAttribute("endDate", endDateStr);
			String typeName ="";
			String advName = "";
			Advertiser a = null;

			if(sell.getAdCombo() != null && sell.getAdCombo().getAdType() != null){
				AdType paramtype = AdTypeUtils.get(sell.getAdCombo().getAdType().getId());
				if(paramtype != null){
					typeName = paramtype.getTypeName();
				}
			}
			model.addAttribute("typeName", typeName);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "/statistics/comboSellStatsDetail";
	}

	/**
	 * 各类型广告 分播放时段统计 详情
	 * zhw
	 * @param startDate
	 * @param endDate
	 * @param typeId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/channleComboSellDetail")
	public String channleComboSellByTypesDetail(String startDate,String endDate,String typeId,Model model) {

		Page<AdSell> page = new Page<AdSell>(1, Integer.valueOf(Global.getConfig("page.pageSize")));
		Date queryStartDate=null,queryEndDate = null;
		try {
			AdSell sell = new AdSell();
			if(StringUtils.isNotEmpty(startDate)){
				queryStartDate = DateUtils.getDateFromString(startDate,"yyyy-MM-dd");
			}
			if(StringUtils.isNotEmpty(endDate)) {
				queryEndDate = DateUtils.getDateFromString(endDate,"yyyy-MM-dd");
			}
			AdCombo combo = new AdCombo();

			AdType type = new AdType();
			type.setId(typeId);
			combo.setAdType(type);

			sell.setAdCombo(combo);
			sell.setPage(page);
			sell.setStartDate(queryStartDate);
			sell.setEndDate(queryEndDate);
			page = thisService.findAdSellByAdType(page, sell);
			String typeName ="";
			String name = "";
			AdType paramtype = AdTypeUtils.get(sell.getAdCombo().getAdType().getId());
			if(paramtype != null){
				typeName = paramtype.getTypeName();
			}
			int history = -1;
			sell.setHistory(history);
			model.addAttribute("page", page);
			model.addAttribute("adSell", sell);
			model.addAttribute("beginDate", startDate);
			model.addAttribute("endDate", endDate);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "/statistics/channelcomboSellDetail";
	}

	/**
	 * 售出时间和可运营时间占比报表
	 * @param entity
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/comboSellValidTime")
	public String sellAndValidTimeRate(AdSell entity, HttpServletRequest request, HttpServletResponse response, Model model) {

		//查询各类型广告广告商占比
		List<AdComboSellTimeDTO> rateList = thisService.findAdComboSellAndValidTime(entity.getStartDate(),entity.getEndDate());

		//广告类型id 名称map
		Map<String,String> typeMap = new HashMap<>(rateList.size());
		for (AdComboSellTimeDTO dto : rateList){
			AdType localetype = AdTypeUtils.get(dto.getTypeId());
			dto.setTypeName(localetype.getTypeName());
		}

		String rateChartJson = JSON.toJSONString(rateList);
		System.out.println(rateChartJson);
		model.addAttribute("rateChartJson", rateChartJson);
		if(null!=entity.getStartDate()) {
			model.addAttribute("beginDate", DateUtils.formatDate(entity.getStartDate(), "yyyy-MM-dd"));
		}
		if(null!=entity.getEndDate()) {
			model.addAttribute("endDate", DateUtils.formatDate(entity.getEndDate(), "yyyy-MM-dd"));
		}
		return "/statistics/comboSellValidTime";
	}

	/**
	 * 查询可运营套餐详情
	 * @param startDate
	 * @param endDate
	 * @param typeId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("advtiser:buy:view")
	@RequestMapping(value = "/comboSellValidTimeDetail")
	public String comboSellValidTimeDetail(String startDate,String endDate,String typeId,Model model) {

		Page<AdCombo> page = new Page<AdCombo>(1, Integer.valueOf(Global.getConfig("page.pageSize")));
		Date queryStartDate=null,queryEndDate = null;
		try {
			if(StringUtils.isNotEmpty(startDate)){
				queryStartDate = DateUtils.getDateFromString(startDate,"yyyy-MM-dd");
			}
			if(StringUtils.isNotEmpty(endDate)) {
				queryEndDate = DateUtils.getDateFromString(endDate,"yyyy-MM-dd");
			}
			AdCombo combo = new AdCombo();

			AdType type = new AdType();
			type.setId(typeId);
			combo.setAdType(type);
			combo.setValidStartTime(queryStartDate);
			combo.setValidEndTime(queryEndDate);
			combo.setPage(page);
			page = comboService.findPageListByTimeIntersection(page, combo);
			String typeName ="";
			String name = "";
			AdType paramtype = AdTypeUtils.get(combo.getAdType().getId());
			if(paramtype != null){
				typeName = paramtype.getTypeName();
			}
			int history = -1;
			model.addAttribute("page", page);
			model.addAttribute("adCombo", combo);
			model.addAttribute("beginDate", startDate);
			model.addAttribute("endDate", endDate);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "/statistics/comboSellValidTimeDetail";
	}

	/**
	 * 可运营套餐详情翻页
	 * @param entity
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:combo:view")
	@RequestMapping(value ="/comboSellValidTimeDetailList")
	public String listCombo(AdCombo entity, HttpServletRequest request,
					   HttpServletResponse response, Model model) {
		Page<AdCombo> page = comboService.findPageListByTimeIntersection(new Page<AdCombo>(request,
				response), entity);
		model.addAttribute("page", page);
		if(null != entity.getValidStartTime()){
			model.addAttribute("validStartTime",DateUtils.formatDate(entity.getValidStartTime(), "yyyy-MM-dd"));
		}
		if(null != entity.getValidEndTime()){
			model.addAttribute("validEndTime",DateUtils.formatDate(entity.getValidEndTime(), "yyyy-MM-dd"));
		}

		return "/statistics/comboSellValidTimeDetail";
	}

	/**
	 * 销售时段分析
	 * @param sell
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("channelCombo:sell:view")
	@RequestMapping(value = "/comboSellTimeSlot")
	public String ComboSellTimeSlot(AdSell sell,HttpServletRequest request, HttpServletResponse response, Model model) {
		String json ="";
		try{

				//json=thisService.getSellDayTimeNumberBymemory(sell);
				json=thisService.getAdSellTimeSlot(sell);
				//System.out.println(thisService.getSellDayTimeNumberByTypes(sell));
				System.out.println(thisService.getAdSellTimeSlot(sell));
				if(sell.getStartDate()!=null&&sell.getEndDate()!=null){
					model.addAttribute("beginDate", DateUtils.formatDate(sell.getStartDate(), "yyyy-MM-dd"));
					model.addAttribute("endDate", DateUtils.formatDate(sell.getEndDate(), "yyyy-MM-dd"));
				}else{
					model.addAttribute("beginDate", null);
					model.addAttribute("endDate", null);
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		String tyeName="";
		if(sell.getAdCombo() != null){
			if(sell.getAdCombo().getAdType() != null){
				String id = sell.getAdCombo().getAdType().getId();
				AdType type = AdTypeUtils.get(id);
				if(type != null){
					tyeName = type.getTypeName();
				}

			}
		}
		model.addAttribute("sellCount", json);
		model.addAttribute("tyeName", tyeName);
		return "/statistics/comboSellTimeSlot";
	}
}
