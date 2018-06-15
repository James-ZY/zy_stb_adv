package com.gospell.aas.controller.adv;

import com.google.common.collect.Lists;
import com.gospell.aas.common.config.Global;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.common.utils.adv.AdvertiserUtils;
import com.gospell.aas.common.utils.sms.DateUtil;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.AdelementDTO;
import com.gospell.aas.entity.adv.*;
import com.gospell.aas.entity.sys.Dict;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.service.adv.*;
import com.gospell.aas.service.sys.SysParamService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

@Controller
@RequestMapping(value = "/adv/selfAdelement")
public class AdSelfAdelementController extends BaseController {

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
	@Autowired
	private AdNetworkService networkService;
	@Autowired
	private SysParamService sysParamService;

	@ModelAttribute
	public AdSell get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdSell();
		}
	}
	
 

	@RequiresPermissions("sys:adv:self:view")
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
		 
		return "/adelement/advSelfList";
	}

	@RequiresPermissions("sys:adv:self:view")
	@RequestMapping(value = "/getAdCombo")
	public String form(AdCombo entity, Model model) {
		String id = entity.getId();

		if (StringUtils.isNotBlank(id)) {
			entity = comboService.get(entity.getId());			
		}
		model.addAttribute("adCombo", entity);

		String startDate = DateUtil.dateToStr(entity.getValidStartTime(),
				"yyyy-MM-dd");
		String endDate = DateUtil.dateToStr(entity.getValidEndTime(),
				"yyyy-MM-dd");
		
		List<AdNetwork> list = networkService.findAllByMabatis();

		model.addAttribute("networkList", list);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("startTime", entity.getStartTime());
		model.addAttribute("endTime", entity.getEndTime());
		model.addAttribute("operate", "form");
		model.addAttribute("comboId", entity.getId());
		
		return "/adelement/advSelfComboForm";
	}
	
	@RequiresPermissions("sys:adv:self:view")
	@RequestMapping(value = "/alertAdelement")
	public String alertAdelement(Adelement element,String startDate,String endDate,String comboId,Model model) {
		Page<Adelement> page = new Page<Adelement>(1, Integer.valueOf(Global.getConfig("page.pageSize")));
		Date queryStartDate=null,queryEndDate = null;
		try {
			AdCombo combo = new AdCombo();
			if(StringUtils.isNotEmpty(startDate)){
				queryStartDate = DateUtils.getDateFromString(startDate,"yyyy-MM-dd");
			}
			if(StringUtils.isNotEmpty(endDate)) {
				queryEndDate = DateUtils.getDateFromString(endDate,"yyyy-MM-dd");
			}
			combo.setId(comboId);
			element.setAdCombo(combo);
			element.setStartDate(queryStartDate);
			element.setEndDate(queryEndDate);
			page = adelementService.find(page, element);
			model.addAttribute("page", page);
			if (AdvertiserUtils.checkIdAdv()) {
				model.addAttribute("isAdv", true);
			} else {
				model.addAttribute("isAdv", false);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "/adelement/advSelfAdelementList";
	}
	
	@RequiresPermissions("sys:adv:self:view")
	@RequestMapping(value = "/adelementPreview")
	public String adelementPreview(Adelement entity,Model model) {
		entity = adelementService.get(entity.getId());
		Integer status = entity.getStatus();
		if (status == Adelement.ADV_STATUS_SHOW
				|| status == Adelement.ADV_STATUS_END) {
			entity.setStatus(Adelement.ADV_STATUS_PASS);
		}
		model.addAttribute("adelement", entity);
		Integer resourceType = AdType.TYPE_STATUS_IMAGE;
		String adelementDto = "";
		String typeName="";
		if (null != entity.getAdCombo()) {
			if (null != entity.getAdCombo().getAdType()) {
				typeName = entity.getAdCombo().getAdType().getTypeName();
				resourceType = entity.getAdCombo().getAdType().getStatus();
			}
			AdelementDTO dto = adelementService.showAdelement(entity,
					entity.getAdCombo());
			model.addAttribute("adeDto", dto);
			adelementDto = JsonMapper.toJsonString(dto);
			// System.out.println("广告的预览字符串是："+adelementDto);
		}
		model.addAttribute("adelementDto", adelementDto);
		model.addAttribute("resourceType", resourceType);
		List<Dict> statusList = Lists.newArrayList();
		Dict dict = new Dict();
		dict.setId("1");
		dict.setValue(String.valueOf(Adelement.ADV_STATUS_PASS));
		dict.setLabel(getMessage("audit.pass"));

		Dict dict1 = new Dict();
		dict1.setId("2");
		dict1.setValue(String.valueOf(Adelement.ADV_STATUS_FAIL));
		dict1.setLabel(getMessage("audit.fail"));
		statusList.add(dict);
		statusList.add(dict1);
		model.addAttribute("statusList", statusList);
		model.addAttribute("categroryNameSelect",
				getMessage("select.category.parent"));
		String localeTypeName = AdTypeUtils.getLocaleAdTypeName(typeName);
		model.addAttribute("typeName", localeTypeName);
		String childTypeName="";
		String childTypeId="";
		if (null != entity.getChildAdType()) {
			AdType type = entity.getChildAdType();
			String id = type.getId();
			if(StringUtils.isNotBlank(id)){
				childTypeId = type.getId();
				AdType childtype = AdTypeUtils.get(entity.getChildAdType().getId());
				 
				childTypeName = childtype.getTypeName();
			}
		}
		String sd =getMessage("no");
		String hd=getMessage("no");
		if(entity.getIsSd() != null){
			int isSd = entity.getIsSd();
			if(isSd == Adelement.ADV_START_SD){
				sd = getMessage("yes");
			}
		}
		if(entity.getIsHd() != null){
			int ishd = entity.getIsHd();
			if(ishd == Adelement.ADV_START_HD){
				hd = getMessage("yes");
			}
		}
		if(entity.getPosition()!=null){
			
		}
		 
		model.addAttribute("isSd",sd);
		model.addAttribute("isHd",hd);
		String localeChildTypeName = AdTypeUtils.getLocaleAdTypeName(childTypeName);
		model.addAttribute("childTypeName", localeChildTypeName);
		model.addAttribute("childTypeId", childTypeId);
		model.addAttribute("playTime", entity.getPlayTime());
		return "/adelement/advSelfPreview";
	}
	
}
