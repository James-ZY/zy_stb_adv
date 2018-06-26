package com.gospell.aas.controller.adv;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.DictUtils;
import com.gospell.aas.common.utils.NetUtil;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.common.utils.adv.AdvertiserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.AdPositionDTO;
import com.gospell.aas.dto.adv.AdResourceDTO;
import com.gospell.aas.dto.adv.AdSellDateDTO;
import com.gospell.aas.dto.adv.AdelementDTO;
import com.gospell.aas.dto.adv.ChildAdTypeDTO;
import com.gospell.aas.dto.adv.Position;
import com.gospell.aas.dto.adv.TypeDTO;
import com.gospell.aas.entity.adv.AdCategory;
import com.gospell.aas.entity.adv.AdChannel;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdControll;
import com.gospell.aas.entity.adv.AdNetwork;
import com.gospell.aas.entity.adv.AdSell;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.entity.sys.Dict;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.service.adv.AdCategoryService;
import com.gospell.aas.service.adv.AdChannelService;
import com.gospell.aas.service.adv.AdComboService;
import com.gospell.aas.service.adv.AdControllService;
import com.gospell.aas.service.adv.AdNetworkService;
import com.gospell.aas.service.adv.AdPositionService;
import com.gospell.aas.service.adv.AdSellService;
import com.gospell.aas.service.adv.AdTypeService;
import com.gospell.aas.service.adv.AdelementService;
import com.gospell.aas.service.sys.SysParamService;

@Controller
@RequestMapping(value = "/adv/quickAdelement")
public class AdelementQuickController extends BaseController {

	@Autowired
	private AdelementService thisService;
	@Autowired
	private AdPositionService positionService;
	@Autowired
	private AdSellService sellService;
	@Autowired
	private AdComboService comboService;
	@Autowired
	private AdControllService controllService;
	@Autowired
	private AdCategoryService categoryService;
	@Autowired
	private AdTypeService adTypeService;
	@Autowired
	private AdChannelService channelService;
	@Autowired
	private SysParamService sysParamService;
	@Autowired
	private AdNetworkService networkService;
	@ModelAttribute
	public Adelement get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {

			return new Adelement();
		}
	}

	@RequiresPermissions("sys:adv:quick:view")
	@RequestMapping(value = "/form")
	public String form(Adelement entity, Model model) {
		String advId = "";
		if (AdvertiserUtils.checkIdAdv()) {
			advId = UserUtils.getUser().getAdvertiser().getId();
			model.addAttribute("isNotAdv", false);
		} else {
			model.addAttribute("isNotAdv", true);
		}
		model.addAttribute("advId", advId);
		if(StringUtils.isNotBlank(entity.getId())){
			AdType type = entity.getAdCombo().getAdType();
			AdTypeUtils.getLocaleAdType(type);
			model.addAttribute("isPosition",type.getIsPosition());//是否需要坐标
			entity.setAdTypeId(entity.getAdCombo().getAdType().getTypeId());
		}
		Integer resourceType = AdType.TYPE_STATUS_IMAGE;

		if (null != entity.getAdCombo()) {
			if (null != entity.getAdCombo().getAdType()) {
				resourceType = entity.getAdCombo().getAdType().getStatus();
			}
			AdCombo adcombo = entity.getAdCombo();
			if(null != entity.getIsFlag()){
				if(adcombo.getIsFlag().equals(AdCombo.ADCOMOBO_NETWORK_ISFLAG)){
					if(adcombo.getSendMode() == 2){
						List<AdNetwork> list = adcombo.getNetworkList();
						String netIds = "";
						String ops = "";
						for (AdNetwork adNetwork : list) {
							adNetwork = networkService.get(adNetwork.getId());
							netIds += adNetwork.getId()+"-";
							ops += adNetwork.getAdOperators().getId()+"-";
						}
						model.addAttribute("netIds", netIds);
						model.addAttribute("ops", ops);
					}
				}else{
					if(adcombo.getSendMode() == 2){
						List<AdChannel> list = adcombo.getChannelList();
						List<String> nets = Lists.newArrayList();
						String ops = "";
						for (AdChannel adChannel : list) {
							adChannel = channelService.get(adChannel.getId());
							if(!nets.contains(adChannel.getAdNetWork().getAdOperators().getId())){
								ops += adChannel.getAdNetWork().getAdOperators().getId()+"-";
								nets.add(adChannel.getAdNetWork().getAdOperators().getId());							
							}
						}
						model.addAttribute("ops", ops);
					}
				}
			}

		}else{
			AdCombo combo = new AdCombo();
			combo.setTrackMode(1);
			entity.setAdCombo(combo);
		}
		model.addAttribute("adCombo", entity.getAdCombo());
		AdCategory category =null;
		if (null != entity.getAdCategory()) {
			category = entity.getAdCategory();
			
			String categoryId = category.getId();
			category = categoryService.get(categoryId);
			entity.setAdCategory(category);
			if (StringUtils.isNotBlank(categoryId)) {
				entity.setOldAdCategoryId(categoryId);
			}
		}else{
			category = categoryService.get("0001");
			entity.setAdCategory(category);
		}
		
		AdSellDateDTO dto = sellService.getSellDateByAdvPlayDate(entity);
		model.addAttribute("sellTime", JsonMapper.toJsonString(dto));
		model.addAttribute("resourceType", resourceType);
		if (null != entity.getControllerList()
				&& entity.getControllerList().size() > 0) {
			List<AdControll> list = entity.getControllerList();

			List<String> nameIdList = Lists.newArrayList();
			for (AdControll menu : list) {
				nameIdList.add(menu.getFileSize());
			}
			String fileSize = StringUtils.join(nameIdList, ",");

			entity.setFileSize(fileSize);
		}
		
		if (null != entity.getHdControllerList()
				&& entity.getHdControllerList().size() > 0) {
			List<AdControll> list = entity.getHdControllerList();

			List<String> nameIdList = Lists.newArrayList();
			for (AdControll menu : list) {
				nameIdList.add(menu.getFileSize());
			}
			String fileSize = StringUtils.join(nameIdList, ",");

			entity.setFileSize(fileSize);
		}

		String startDate = "";
		String endDate = "";
		if (null != entity.getStartDate()) {
			startDate = DateUtils.formatDate(entity.getStartDate(),
					"yyyy-MM-dd");
		}

		if (null != entity.getEndDate()) {
			endDate = DateUtils.formatDate(entity.getEndDate(), "yyyy-MM-dd");
		}
		/*entity.setSdMaxNC(1250000);
		entity.setHdMaxNC(1250000);*/

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("adelement", entity);

		return "/adelement/advQuickForm";
	}

	@RequiresPermissions("sys:adv:quick:edit")
	@RequestMapping(value = "/save")
	public String save(Adelement entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		thisService.clear();
		String categoryId = request.getParameter("adCategory.id");
		if(StringUtils.isBlank(categoryId)){
			categoryId = "0001";
		}
		if(categoryId != null && categoryId.equals(AdCategory.getzeroAdCategoryId())){
			addMessage(redirectAttributes, "category.id.select.error");
			return "redirect:/adv/adelement/?repage";
		}
		entity.setAdCategory(new AdCategory(categoryId));
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("adv.adId"), entity.getAdId());
		try {
			// 表示用户不可以修改这条数据，只能打开预览页面查看一下，这个这个地方直接返回查询页面
			if (entity.getStatus() != null){
				int status = entity.getStatus() ;
				if (status == Adelement.ADV_STATUS_CLAIM||
								status == Adelement.ADV_STATUS_END || 
										status == Adelement.ADV_STATUS_SHOW) {
					return "redirect:/adv/adelement/?repage";
				}
			}
			String id = entity.getAdCombo().getId();//判断套餐id是否存在
			AdCombo combo = null;
			if (StringUtils.isNotBlank(id)) {
				combo = comboService.get(id);
				combo.setStatus(AdCombo.ADCOMOBO_ALREADY_STATUS);
				Integer ite = thisService.getAdelementCount(null, id, entity.getStartDate(), entity.getEndDate(), entity.getChildAdType().getId(), null);
				if(ite>0){
					logger.info(UserUtils.getUser().getLoginName()
							+ "添加或者修改广告:" + entity.getAdName() + "失败");
					logService.save(request, logInfo, null);
					addMessage(redirectAttributes, "adelement.already.exist");
					return "redirect:/adv/adelement/?repage";
				}
			}else{
				combo = entity.getAdCombo();
				if(combo.getAdType().getId()!=null){
					combo.setAdType(adTypeService.get(combo.getAdType().getId()));
				}
				combo.setComboName(entity.getAdName());
				combo.setStatus(AdCombo.ADCOMOBO_ALREADY_STATUS);				
				if(!comboService.checkIfAddCombo(combo)){
					logger.info(UserUtils.getUser().getLoginName()
							+ "添加或者修改广告:" + entity.getAdName() + "失败");
					logService.save(request, logInfo, null);
					addMessage(redirectAttributes, "adcombo.already.used");	
					return "redirect:/adv/adelement/?repage";
				}
			}
			
			comboService.save(combo);//更新套餐
			//新增销售记录
			Map<String,Object> map = new HashMap<String,Object>(); 
			map.put("adcomboId", combo.getId());
			map.put("advertiserId",entity.getAdvertiser().getId());
			map.put("startDate", entity.getStartDate());
			map.put("endDate", entity.getEndDate());
			map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			AdSell sell = sellService.fingAdSellByCombo(map);
			if(sell ==null){
				sell = new AdSell();
				sell.setAdCombo(combo);
				sell.setAdvertiser(entity.getAdvertiser());
				sell.setStartDate(entity.getStartDate());
				sell.setEndDate(entity.getEndDate());
				sellService.save(sell);				
			}
			entity.setAdCombo(combo); 
		    //生成广告
			entity.setStatus(Adelement.ADV_STATUS_WAIT);
			entity.setAuditDate(null);
			entity.setClaimDate(null);
			entity.setAuditUser(null);
			entity.setRemarks(null);
			if(entity.getPosition().getBeginPointX() == null){
				entity.setPosition(null);
			}
			
			if(entity.getHdPosition().getBeginPointX() == null){
				entity.setHdPosition(null);
			}
			thisService.save(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存广告Id："+entity.getId()+"成功");
			comboService.pushAdcomboToClient(combo);	//推送套餐	
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/adelement/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存广告Id："+entity.getId()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/adelement/?repage";
		}
	}

	@RequiresPermissions("sys:adv:quick:edit")
	@RequestMapping(value = "/preview")
	public String add(Adelement entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			thisService.clear();
			String categoryId = request.getParameter("adCategory.id");
			if(StringUtils.isBlank(categoryId)){
				categoryId = "0001";
			}
			entity.setAdCategory(new AdCategory(categoryId));
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String comboName = "";
			String typeName = "";
			String childTypeName = null;
			String adelementDto = "";
			String childTypeId="";
			AdCombo combo = null;
			if (null != entity.getAdCombo()) {
				if(StringUtils.isNotBlank(entity.getAdCombo().getId())){					
					combo = comboService.get(entity.getAdCombo().getId());
				}else{
					combo = entity.getAdCombo();
					combo.setComboName(entity.getAdName());
				}
				if(combo.getAdType().getId()!=null){
					combo.setAdType(adTypeService.get(combo.getAdType().getId()));
				}
				if (null != combo) {
					comboName = combo.getComboName();
					typeName = combo.getAdType().getTypeName();

					AdelementDTO dto = thisService.showAdelement(entity, combo);
					model.addAttribute("adeDto",dto);
					model.addAttribute("adType",combo.getAdType());
					adelementDto = JsonMapper.toJsonString(dto);
					String hdPoint = "";
					String sdPoint = "";
					if(combo.getAdType().getTypeId().equals("2") || combo.getAdType().getTypeId().equals("4") ||  combo.getAdType().getTypeId().equals("5") ||  combo.getAdType().getTypeId().equals("10")){
					if(StringUtils.isNotBlank(entity.getId())){		
						if(entity.getPosition() !=null){
							if(entity.getPosition().getBeginPointX() < dto.getSdStartX()){
								sdPoint = "start:("+dto.getSdStartX();	
								entity.getPosition().setBeginPointX(dto.getSdStartX());
							}else{
								sdPoint = "start:("+entity.getPosition().getBeginPointX();
							}
							if(entity.getPosition().getBeginPointY() < dto.getSdStartY()){
								sdPoint = sdPoint +","+dto.getSdStartY()+")";
								entity.getPosition().setBeginPointY(dto.getSdStartY());
							}else{ 
								sdPoint = sdPoint +","+entity.getPosition().getBeginPointY()+")";
							}	
							if(entity.getPosition().getEndPointX() != null){
								sdPoint = sdPoint + ",end:(" +entity.getPosition().getEndPointX();
							}
                            if(entity.getPosition().getEndPointY() != null){
                            	sdPoint = sdPoint +"," +entity.getPosition().getEndPointY()+")";
							}
						}else{
							sdPoint = "start:("+dto.getSdStartX()+","+dto.getSdStartY()+")";
						}
						if(entity.getHdPosition() !=null){
							if(entity.getHdPosition().getBeginPointX() < dto.getHdStartX()){
								hdPoint = "start:("+dto.getHdStartX();		
								entity.getHdPosition().setBeginPointX(dto.getHdStartX());
							}else{
								hdPoint = "start:("+entity.getHdPosition().getBeginPointX();
							}
							if(entity.getHdPosition().getBeginPointY() < dto.getHdStartY()){
								hdPoint = hdPoint+","+dto.getHdStartY()+")";
								entity.getHdPosition().setBeginPointY(dto.getHdStartY());
							}else{
								hdPoint = hdPoint+","+entity.getHdPosition().getBeginPointY()+")";
							}
							if(entity.getHdPosition().getEndPointX() != null){
								hdPoint = hdPoint + ",end:(" +entity.getHdPosition().getEndPointX();
							}
							if(entity.getPosition().getEndPointY() != null){
								hdPoint = hdPoint +"," +entity.getHdPosition().getEndPointY()+")";
							}
						}else{
							hdPoint = "start:("+dto.getHdStartX()+","+dto.getHdStartY()+")";
						}
					}else{
						if(combo.getAdType().getTypeId().equals("4") && dto.getShowMode().equals(2)){
							String sdTrack = dto.getSdTrackName()+"("+dto.getSdBgWidth()+"x"+dto.getSdBgHeight()+")";
							String hdTrack = dto.getHdTrackName()+"("+dto.getHdBgWidth()+"x"+dto.getHdBgHeight()+")";
							model.addAttribute("sdTrack",sdTrack);
							model.addAttribute("hdTrack",hdTrack);
							model.addAttribute("trackMode",dto.getShowMode());
						}else{
							sdPoint = "start:("+dto.getSdStartX()+","+dto.getSdStartY()+")";
							hdPoint = "start:("+dto.getHdStartX()+","+dto.getHdStartY()+")";
						}
					}
					model.addAttribute("sdPoint",sdPoint);
					model.addAttribute("hdPoint",hdPoint);
					}
				}

			}
			if (null != entity.getChildAdType()) {
				AdType type = entity.getChildAdType();
				String id = type.getId();

				if(StringUtils.isNotBlank(id)){
					childTypeId= type.getId();
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
					model.addAttribute("sdShow","yes");
				}else{
					model.addAttribute("sdShow","no");
				}
			}
			if(entity.getIsHd() != null){
				int ishd = entity.getIsHd();
				if(ishd == Adelement.ADV_START_HD){
					hd = getMessage("yes");
					model.addAttribute("hdShow","yes");
				}else{
					model.addAttribute("hdShow","no");
				}
			}
			if(entity.getPosition()!=null){
				
			}
			 
			model.addAttribute("isSd",sd);
			model.addAttribute("isHd",hd);
			model.addAttribute("startDate",
					format.format(entity.getStartDate()));
			model.addAttribute("endDate", format.format(entity.getEndDate()));
			model.addAttribute("comboName", comboName);
			String localeTypeName = AdTypeUtils.getLocaleAdTypeName(typeName);
			model.addAttribute("typeName", localeTypeName);
			String localeChildTypeName = AdTypeUtils.getLocaleAdTypeName(childTypeName);
			model.addAttribute("childTypeName", localeChildTypeName);
			model.addAttribute("childTypeId", childTypeId);
			model.addAttribute("isFlag", DictUtils.getDictLabel(
					String.valueOf(entity.getIsFlag()), "adv_isflag", ""));
	 
			model.addAttribute("playTime", entity.getPlayTime());
			model.addAttribute("adelement", entity);
			model.addAttribute("adelementDto", adelementDto);
			logger.info(UserUtils.getUser().getLoginName()+"预览广告Id："+entity.getId()+"成功");
		} catch (Exception e) {
			logger.error(UserUtils.getUser().getLoginName()+"预览广告Id："+entity.getId()+"失败",e.getMessage());
		}
		return "/adelement/advQuickPreview";
	}

	@RequiresPermissions("sys:adv:quick:edit")
	@RequestMapping(value = "/addreturn")
	public String addReturn(Adelement entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		try {

			model.addAttribute("adelement", entity);
			model.addAttribute("adelementDto", JsonMapper.toJsonString(entity));
			if(entity.getAdCombo().getIsFlag().equals(1)){
				List<String> adchannelList = Lists.newArrayList();
				for (AdChannel channel : entity.getAdCombo().getChannelList()) {
					channel = channelService.get(channel.getId());
					adchannelList.add(channel.getChannelName());
				}
				model.addAttribute("channelIds", entity.getAdCombo().getChannelIds());
				model.addAttribute("setNet", StringUtils.join(adchannelList, ","));
			}
			if (null != entity.getAdCombo()) {
				if (null != entity.getAdCombo().getAdType()) {
					AdType type = adTypeService.get(entity.getAdCombo().getAdType().getId());
					entity.getAdCombo().setAdType(type);
				}

			}
			model.addAttribute("startTime", entity.getAdCombo().getStartTime());
			model.addAttribute("endTime", entity.getAdCombo().getEndTime());
			logger.error(UserUtils.getUser().getLoginName()+"广告Id："+entity.getId()+"预览页面跳转会修改页面成功");
			return form(entity, model);
		} catch (Exception e) {
			logger.error(UserUtils.getUser().getLoginName()+"广告Id："+entity.getId()+"预览页面跳转会修改页面失败",e.getMessage());

			return "/adelement/advQuickForm";
		}
	}
	
	@ResponseBody
	@RequiresPermissions("sys:adv:quick:edit")
	@RequestMapping(value = "chekAddress", method = RequestMethod.POST)
	public String check(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String addText = (String) map.get("addText");
		Boolean b = NetUtil.isConnect(addText);
		if (null != b) {
			return String.valueOf(b);
		} else {
			return "false";
		}
	}

	@ResponseBody
	@RequiresPermissions("sys:adv:quick:edit")
	@RequestMapping(value = "getCombobyId", method = RequestMethod.POST)
	public AdPositionDTO getCombobyId(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String id = (String) map.get("id");
		AdPositionDTO dto = null;
		if (StringUtils.isNotBlank(id)) {
			try {

				dto = positionService.findAdPostionByComboId(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	/**
	 * 根据高标清和广告类型获取坐标的接口，参数是广告类型ID和分辨率
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:adv:quick:edit")
	@RequestMapping(value = "getPositionByTypeId", method = RequestMethod.POST)
	public Position getPositionbyTypeId(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String id = (String) map.get("id");
		String resolution_map = (String) map.get("resolution");
		Position dto = null;
		if (StringUtils.isNotBlank(id)) {
			try {
				Integer resolution = Integer.valueOf(resolution_map);
				dto = positionService.findAdPostionByTypeId(id,resolution);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	/**
	 * 获取套餐销售的时间
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:adv:quick:edit")
	@RequestMapping(value = "getSellDate", method = RequestMethod.POST)
	public List<AdSellDateDTO> getSellDate(
			@RequestBody Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) {
		String id = (String) map.get("id");
		String advId = (String) map.get("advId");
		if (StringUtils.isBlank(advId)) {
			User user = UserUtils.getUser();
			if (null != user.getAdvertiser()) {
				advId = user.getAdvertiser().getId();
			}
		}
		List<AdSellDateDTO> dto = null;
		if (StringUtils.isNotBlank(id)) {
			try {
				dto = sellService.getSellDate(id, advId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	/**
	 * 获取广告类型通过套餐
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:adv:quick:edit")
	@RequestMapping(value = "getAdType", method = RequestMethod.POST)
	public TypeDTO getAdType(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String id = (String) map.get("id");
		TypeDTO dto = null;
		if (StringUtils.isNotBlank(id)) {
			try {
				dto = comboService.findAdTypeByComboId(id);
		        String name = AdTypeUtils.getLocaleAdTypeName(dto.getTypeName());
			    dto.setTypeName(name);
			 
				List<ChildAdTypeDTO> childType = dto.getChildList();
				if(childType != null && childType.size()>0){
					for (int i = 0; i < childType.size(); i++) {
						ChildAdTypeDTO childDto = childType.get(i);
						String childName = AdTypeUtils.getLocaleAdTypeName(childDto.getTypeName());
						childDto.setTypeName(childName);
					 
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	
	/**
	 * 获取广告类型获取子广告类型列表
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:adv:quick:edit")
	@RequestMapping(value = "getAdChlidType", method = RequestMethod.POST)
	public TypeDTO getAdChlidType(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String id = (String) map.get("id");
		AdType type = null;
		TypeDTO dto = null;
		if (StringUtils.isNotBlank(id)) {
			try {
				type = adTypeService.get(id);		
				dto = new TypeDTO();
				dto.setAdType(String.valueOf(type.getStatus()));
				dto.setTypeId(type.getId());
				dto.setTypeName(AdTypeUtils.getLocaleAdTypeName(type.getTypeName()));
				List<AdType> child =type.getChildList();
				if (null != child && child.size() > 0) {
					List<ChildAdTypeDTO> childList = Lists.newArrayList();
					for (int i = 0; i < child.size(); i++) {
						ChildAdTypeDTO ca = new ChildAdTypeDTO();
						ca.setTypeName(AdTypeUtils.getLocaleAdTypeName(child.get(i).getTypeName()));
						ca.setId(child.get(i).getId());
						childList.add(ca);
					}
					dto.setChildList(childList);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	/**
	 * 广告发布页面获取资源文件
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:adv:quick:edit")
	@RequestMapping(value = "getResoure", method = RequestMethod.POST)
	public List<AdResourceDTO> getResoure(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = (String) map.get("id");

			User user = UserUtils.getUser();
			Map<String, Object> c_map = new HashMap<>();
			c_map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			c_map.put("typeId", id);
			if (null != user.getAdvertiser()) {
				c_map.put("advId", user.getAdvertiser().getId());
		
				return controllService.getControlByTypeId(c_map);
			} else {
				String advId = (String) map.get("advId");
				if (StringUtils.isBlank(advId)) {
					return null;
				}
				c_map.put("advId", advId);
			}
			String resolution = (String) map.get("resolution");
			if (StringUtils.isBlank(resolution)) {
				return null;
			}
			Integer reInteger = Integer.valueOf(resolution);
			c_map.put("resolution", reInteger);
			c_map.put("typeId", id);
			return controllService.getControlByTypeId(c_map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 添加或者修改广的时候根据广告的播放时间判断广告是否重复
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkAdvIsRepeat", method = RequestMethod.POST)
	public boolean checkAdvIsRepeat(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		boolean b = false;
		try {
			b = thisService.checkAdvIsRepeat(map);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return b;
	}

	/**
	 * 广告审核统计
	 * 
	 * @param entity
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adv:aduit:statistic:view")
	@RequestMapping(value = "/auditStatictis/query")
	public String auditQueryStatic(Adelement entity,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			Page<Adelement> page = thisService.findAuditQuery(
					new Page<Adelement>(request, response), entity);
			model.addAttribute("page", page);
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
			model.addAttribute("statusSelect", statusList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/statistics/advAuditStatistic/aduitStatisticList";
	}

	@RequiresPermissions("adv:aduit:statistic:view")
	@RequestMapping(value = "/auditStatictis/form")
	public String auditQueryStaticDetail(Adelement entity,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
 
		try {
			
			model.addAttribute("adelement", entity);
			Integer resourceType = AdType.TYPE_STATUS_IMAGE;
			String adelementDto = "";
			String typeName="";
			String childTypeId="";
			if (null != entity.getAdCombo()) {
				if (null != entity.getAdCombo().getAdType()) {
					typeName = entity.getAdCombo().getAdType().getTypeName();
					resourceType = entity.getAdCombo().getAdType().getStatus();
				}
				AdelementDTO dto = thisService.showAdelement(entity,
						entity.getAdCombo());
				adelementDto = JsonMapper.toJsonString(dto);
				// System.out.println("广告的预览字符串是："+adelementDto);
			}
			model.addAttribute("adelementDto", adelementDto);
			model.addAttribute("resourceType", resourceType);
			model.addAttribute("categroryNameSelect",
					getMessage("select.category.parent"));
			String localeTypeName = AdTypeUtils.getLocaleAdTypeName(typeName);
			model.addAttribute("typeName", localeTypeName);
			String childTypeName="";
			if (null != entity.getChildAdType()) {
				AdType type = entity.getChildAdType();
				String id = type.getId();
				 
				if(StringUtils.isNotBlank(id)){
					childTypeId = type.getId();
					AdType childtype = AdTypeUtils.get(entity.getChildAdType().getId());
					 
					childTypeName = childtype.getTypeName();
				}
			}
			String localeChildTypeName = AdTypeUtils.getLocaleAdTypeName(childTypeName);
			model.addAttribute("childTypeName", localeChildTypeName);
			model.addAttribute("childTypeId", childTypeId);
			model.addAttribute("playTime", entity.getPlayTime());
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
			model.addAttribute("isSd",sd);
			model.addAttribute("isHd",hd);
			return "/statistics/advAuditStatistic/aduitStatisticForm";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/statistics/advAuditStatistic/aduitStatisticForm";
	}

	@ResponseBody
	@RequiresPermissions("sys:adv:quick:edit")
	@RequestMapping(value = "checkAdvName")
	public String checkAdvName(HttpServletRequest request, String oldAdName,
			String adName,String operate) {
		try {
			request.setCharacterEncoding("utf-8");
            if(operate!=null && operate.trim().equals("copy")){
            	if (adName != null
            			&& thisService.findAdelementByAdName(adName.trim()).size()==0) {
            		return "true";
            	}    
            }else{
            	if (adName != null && adName.trim().equals(oldAdName)) {
            		return "true";
            	} else if (adName != null
            			&& thisService.findAdelementByAdName(adName.trim()).size()==0) {
            		return "true";
            	}           	
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}
	
}
