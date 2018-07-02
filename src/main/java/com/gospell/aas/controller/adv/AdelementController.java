package com.gospell.aas.controller.adv;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
import com.gospell.aas.dto.adv.AdChannelNCDTO;
import com.gospell.aas.dto.adv.AdPositionDTO;
import com.gospell.aas.dto.adv.AdResourceDTO;
import com.gospell.aas.dto.adv.AdSellDateDTO;
import com.gospell.aas.dto.adv.AdelementDTO;
import com.gospell.aas.dto.adv.ChildAdTypeDTO;
import com.gospell.aas.dto.adv.Position;
import com.gospell.aas.dto.adv.TypeDTO;
import com.gospell.aas.entity.adv.AdCategory;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdControll;
import com.gospell.aas.entity.adv.AdPosition;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.entity.sys.Dict;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.service.adv.AdCategoryService;
import com.gospell.aas.service.adv.AdComboService;
import com.gospell.aas.service.adv.AdControllService;
import com.gospell.aas.service.adv.AdPositionService;
import com.gospell.aas.service.adv.AdSellService;
import com.gospell.aas.service.adv.AdelementService;
import com.gospell.aas.service.sys.SysParamService;

@Controller
@RequestMapping(value = "/adv/adelement")
@Api(value = "AdelementController",description="广告模块")
public class AdelementController extends BaseController {

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
	private SysParamService sysParamService;

	@ModelAttribute
	public Adelement get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {

			return new Adelement();
		}
	}


	@RequiresPermissions("sys:adv:view")
	@RequestMapping(value = { "/list", "" })
	//方法上使用@ApiOperation
	@ApiOperation(value="获取广告列表",notes="跳转到首页")
	//参数使用@ApiParam
	public String list(Adelement entity, HttpServletRequest request,
					   HttpServletResponse response, Model model) {
		try{
			if(null == entity.getIsExpired()){
				entity.setIsExpired(Adelement.ADV_NO_STATUS);
			}
			Page<Adelement> page = thisService.find(new Page<Adelement>(request,
					response), entity);
			model.addAttribute("page", page);
			if(null != entity.getStartDate()){
				model.addAttribute("startDate", DateUtils.formatDate(entity.getStartDate(), "yyyy-MM-dd"));
			}
			if(null != entity.getEndDate()){
				model.addAttribute("endDate",  DateUtils.formatDate(entity.getEndDate(), "yyyy-MM-dd"));
			}

			if (AdvertiserUtils.checkIdAdv()) {

				model.addAttribute("isAdv", true);
			} else {
				model.addAttribute("isAdv", false);//
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/adelement/advList";
	}

	@RequiresPermissions("sys:adv:edit")
	@RequestMapping(value = "/returnList")
	public String returnList(Adelement entity, HttpServletRequest request,
							 HttpServletResponse response, Model model) {
		try{
			entity =new Adelement();
			Page<Adelement> page = thisService.find(new Page<Adelement>(request,
					response), entity);
			model.addAttribute("page", page);
			model.addAttribute("adelement", entity);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/adelement/advList";
	}

	@RequiresPermissions("sys:adv:aduit:view")
	@RequestMapping(value = "/audit/query")
	public String audit(Adelement entity, HttpServletRequest request,
						HttpServletResponse response, Model model) {
			try {
			Page<Adelement> page = thisService.findAudit(new Page<Adelement>(
					request, response), entity);
			model.addAttribute("page", page);
			List<Dict> statusList = Lists.newArrayList();
			Dict dict = new Dict();
			dict.setId("1");
			dict.setValue(String.valueOf(Adelement.ADV_STATUS_WAIT));
			dict.setLabel(getMessage("wait.audit"));

			Dict dict1 = new Dict();
			dict1.setId("2");
			dict1.setValue(String.valueOf(Adelement.ADV_STATUS_CLAIM));
			dict1.setLabel(getMessage("already.audit"));

			statusList.add(dict);
			statusList.add(dict1);
			model.addAttribute("statusSelect", statusList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/advaduit/aduitList";
	}

	@ApiOperation(value = "获取广告详细信息", notes = "获取广告详细信息")
	@RequiresPermissions("sys:adv:view")
	@RequestMapping(value = "/form")
	public String form(@ApiParam(name="adelement实体",value="json格式",required=true) Adelement entity, Model model) {
		String advId = "";
		if (AdvertiserUtils.checkIdAdv()) {
			advId = UserUtils.getUser().getAdvertiser().getId();
			model.addAttribute("isNotAdv", false);
		} else {
			model.addAttribute("isNotAdv", true);
		}

		if (StringUtils.isNoneBlank(entity.getId()) && !entity.getAdCombo()
				.getAdType().getId().equals(AdType.Type_SWITCH_ON_VEDIO)) {
			// 先判断是否超出内存限制
			AdChannelNCDTO dto1 = thisService.getCurrentNC1(entity.getAdCombo()
							.getAdType().getId(), DateUtils.getDate(entity
							.getStartDate()), DateUtils.getDate(entity.getEndDate()),
					entity.getAdCombo().getId());
			model.addAttribute("sdMaxNC", dto1.getSdMaxNC());
			model.addAttribute("hdMaxNC", dto1.getHdMaxNC());
		}
		/*model.addAttribute("sdMaxNC", 1250000);
		model.addAttribute("hdMaxNC", 1250000);*/
		
/*		if(entity.getAdCombo().getAdType().getId().equals(AdType.Type_OPEN_IMGAE)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			map.put("paramType", SysParam.BOOT_PICTURE_TIME_SET);
			SysParam param = sysParamService.getParam(map);
			model.addAttribute("bpTS", param.getParamValue());//设置开当图片时长
			
			map.put("paramType", SysParam.BOOT_PICTURE_TOTAL_TIME);
			param = sysParamService.getParam(map);
			model.addAttribute("bpTT", param.getParamValue());//设置开机画面显示总时长
		}*/

		model.addAttribute("advId", advId);
		if(StringUtils.isNotBlank(entity.getId())){
			thisService.clear();
			entity = thisService.get(entity.getId());
			Integer it  = thisService.findStatusById(entity);
			entity.setStatus(it);
			AdType type = entity.getAdCombo().getAdType();
			AdTypeUtils.getLocaleAdType(type);
			model.addAttribute("isPosition",type.getIsPosition());//是否需要坐标
			entity.setAdTypeId(entity.getAdCombo().getAdType().getTypeId());
		}
		if(entity.getStatus()==null){
			entity.setStatus(Adelement.ADV_STATUS_WAIT);
		}
		model.addAttribute("adelement", entity);

		Integer resourceType = AdType.TYPE_STATUS_IMAGE;

		if (null != entity.getAdCombo()) {
			if (StringUtils.isNotBlank(entity.getAdCombo().getId())){
				AdCombo combo = comboService.get(entity.getAdCombo().getId());

				model.addAttribute("startTime",combo.getStartTime());
				model.addAttribute("endTime",combo.getEndTime());
			}
			if (null != entity.getAdCombo().getAdType()) {
				resourceType = entity.getAdCombo().getAdType().getStatus();
			}

		}
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

		if (null != entity.getHdPosition()) {
			String positionId = entity.getHdPosition().getId();
			if (StringUtils.isNotBlank(positionId)) {
				if (StringUtils.isBlank(entity.getHdPosition().getPointId())) {
					AdPosition ad = positionService.get(positionId);
					entity.setHdPosition(ad);
					if(entity.getAdTypeId().equals("5")){
						entity.setHdFx(0);
						if(ad.getEndPointY().equals(ad.getBeginPointY())){
							if(ad.getBeginPointX() < ad.getEndPointX()){
								entity.setHdFx(1);
							}else{
								entity.setHdFx(0);
							}
						}else if(ad.getBeginPointX().equals(ad.getEndPointX())){
							if(ad.getBeginPointY() < ad.getEndPointY()){
								entity.setHdFx(3);
							}else{
								entity.setHdFx(2);
							}
						}
					}
				}
			}
		}
		if (null != entity.getPosition()) {
			String positionId = entity.getPosition().getId();
			if (StringUtils.isNotBlank(positionId)) {
				if (StringUtils.isBlank(entity.getPosition().getPointId())) {
					AdPosition ad = positionService.get(positionId);
					entity.setPosition(ad);
					if(entity.getAdTypeId().equals("5")){
						entity.setSdFx(0);
						if(ad.getEndPointY().equals(ad.getBeginPointY())){
							if(ad.getBeginPointX() < ad.getEndPointX()){
								entity.setSdFx(1);
							}else{
								entity.setSdFx(0);
							}
						}else if(ad.getBeginPointX().equals(ad.getEndPointX())){
							if(ad.getBeginPointY() < ad.getEndPointY()){
								entity.setSdFx(3);
							}else{
								entity.setSdFx(2);
							}
						}
					}
				}
			}
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
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "/adelement/advForm";
	}

	@RequiresPermissions("sys:adv:edit")
	@RequestMapping(value = "/save")
	public String save(Adelement entity, HttpServletRequest request,
					   Model model, RedirectAttributes redirectAttributes) {
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
		try {
			// 表示用户不可以修改这条数据，只能打开预览页面查看一下，这个这个地方直接返回查询页面
			if (entity.getStatus() != null){
				int status = entity.getStatus() ;
				if (status == Adelement.ADV_STATUS_CLAIM) {
					return "redirect:/adv/adelement/?repage";
				}
			}
			if (null != entity.getAdCombo()) {
				AdCombo combo = comboService.get(entity.getAdCombo().getId());
				if (null != combo) {
					entity.setAdCombo(combo);
				}

			}

			Integer ite = thisService.getAdelementCount(null, entity.getAdCombo().getId(), entity.getStartDate(), entity.getEndDate(), entity.getChildAdType().getId(), entity.getId());
			if(ite>0){
				addMessage(redirectAttributes, "adelement.already.exist");
				return "redirect:/adv/adelement/?repage";
			}

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
			String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("adv.adId"), entity.getAdId());
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存广告Id："+entity.getId()+"成功");
			comboService.pushAdcomboToClient(entity.getAdCombo());	//推送套餐
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/adelement/?repage";
		} catch (Exception e) {
			String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("adv.adId"), entity.getAdId());
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存广告Id："+entity.getId()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/adelement/?repage";
		}
	}

	@RequiresPermissions("sys:adv:aduit:view")
	@RequestMapping(value = "/auditForm")
	public String auditform(Adelement entity, Model model) {

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
			AdelementDTO dto = thisService.showAdelement(entity,
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

		return "/advaduit/aduitForm";
	}

	@RequiresPermissions("sys:adv:audit")
	@RequestMapping(value = "/saveClaim")
	public String saveClaim(Adelement entity, HttpServletRequest request,
							Model model, RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 6, getMessage("adv.adId"), entity.getAdId());
		if (!beanValidator(model, entity)) {
			addMessage(redirectAttributes, "msg.claim.fail");
			return "redirect:/adv/adelement/audit/?repage";
		}
		try {
			entity.setStatus(Adelement.ADV_STATUS_CLAIM);
			entity.setAuditUser(UserUtils.getUser());// 认领人
			entity.setClaimDate(new Date());
			thisService.updateStatus(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"认领广告Id："+entity.getId()+"成功");
			addMessage(redirectAttributes, "msg.claim.success");
			return "redirect:/adv/adelement/audit/query/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"认领广告Id："+entity.getId()+"失败");
			addMessage(redirectAttributes, "msg.claim.fail");
			return "redirect:/adv/adelement/audit/query/?repage";
		}
	}

	@RequiresPermissions("sys:adv:audit")
	@RequestMapping(value = "/saveAudit")
	public String saveAudit(Adelement entity, HttpServletRequest request,
							Model model, RedirectAttributes redirectAttributes) {

		if (!beanValidator(model, entity)) {
			return auditform(entity, model);
		}
		if (!entity.getAdCombo()
				.getAdType().getId().equals(AdType.Type_SWITCH_ON_VEDIO) && entity.getStatus().equals(Adelement.ADV_STATUS_PASS)) {
			// 先判断是否超出内存限制
			AdChannelNCDTO dto1 = thisService.getCurrentNC1(entity.getAdCombo()
							.getAdType().getId(), DateUtils.getDate(entity
							.getStartDate()), DateUtils.getDate(entity.getEndDate()),
					entity.getAdCombo().getId());
			List<AdControll> sdL = entity.getControllerList();
			Integer cursd = 0;
			for (AdControll adControll : sdL) {
				if(StringUtils.isNoneBlank(adControll.getId())){
					adControll = controllService.get(adControll.getId());
					if (adControll.getFileFormat().equals("m2v")
							|| adControll.getFileFormat().equals("ts")) {
						cursd += Integer.parseInt(adControll.getFileSize());
					} else {
						cursd += Integer.parseInt(adControll.getFileSize()) * 1000;
					}
				}
			}
			if (cursd > dto1.getSdMaxNC()) {
				addMessage(redirectAttributes, "outNCRange");
				return "redirect:/adv/adelement/audit/query/?repage";
			}
			List<AdControll> hdL = entity.getHdControllerList();
			Integer curhd = 0;
			for (AdControll adControll : hdL) {
				if(StringUtils.isNoneBlank(adControll.getId())){
					adControll = controllService.get(adControll.getId());
					if (adControll.getFileFormat().equals("m2v")
							|| adControll.getFileFormat().equals("ts")) {
						curhd += Integer.parseInt(adControll.getFileSize());
					} else {
						curhd += Integer.parseInt(adControll.getFileSize()) * 1000;
					}
				}
			}
			if (curhd > dto1.getHdMaxNC()) {
				addMessage(redirectAttributes, "outNCRange");
				return "redirect:/adv/adelement/audit/query/?repage";
			}
		}
		try {
			entity.setAuditUser(UserUtils.getUser());// 审核人
			entity.setAuditDate(new Date());
			thisService.updateStatus(entity);
			addMessage(redirectAttributes, "msg.audit.success");
			String logInfo=logService.getLogInfo(entity.getId(), 5, getMessage("adv.adId"), entity.getAdId());
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"审核广告Id："+entity.getId()+"成功");
			thisService.pushAdvToClient(entity);
			return "redirect:/adv/adelement/audit/query/?repage";
		} catch (Exception e) {
			e.printStackTrace();
			String logInfo=logService.getLogInfo(entity.getId(), 5, getMessage("adv.adId"), entity.getAdId());
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"审核广告Id："+entity.getId()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.audit.fail");
			return "redirect:/adv/adelement/audit/query/?repage";
		}
	}

	@RequiresPermissions("sys:adv:audit")
	@RequestMapping(value = "/put")
	public String putAdv(Adelement entity, HttpServletRequest request,
						 Model model, RedirectAttributes redirectAttributes) {

		try {

			thisService.pushAdvToClient(entity);
			logger.error("立即投放广告" + entity.getId() + "成功！");
			addMessage(redirectAttributes, "put.success");
			return "redirect:/adv/adelement/?repage";
		} catch (Exception e) {
			logger.error("立即投放广告" + entity.getId() + "失败！");
			addMessage(redirectAttributes, "put.fail");
			return "redirect:/adv/adelement/?repage";
		}
	}

	@RequiresPermissions("sys:adv:edit")
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
			if (null != entity.getAdCombo()) {
				AdCombo combo = comboService.get(entity.getAdCombo().getId());
				if (null != combo) {
					comboName = combo.getComboName();
					typeName = combo.getAdType().getTypeName();
					AdelementDTO dto = thisService.showAdelement(entity, combo);
					model.addAttribute("adeDto",dto);
					model.addAttribute("adType",combo.getAdType());
					adelementDto = JsonMapper.toJsonString(dto);
					String hdPoint = "";
					String sdPoint = "";
					if(combo.getAdType().getTypeId().equals("2") || combo.getAdType().getTypeId().equals("4") ||  combo.getAdType().getTypeId().equals("5") ||  combo.getAdType().getTypeId().equals("10") ){
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
								if(entity.getHdPosition().getEndPointY() != null){
									hdPoint = hdPoint +"," +entity.getHdPosition().getEndPointY()+")";
								}
							}else{
								hdPoint = "start:("+dto.getHdStartX()+","+dto.getHdStartY()+")";
							}
							if(combo.getAdType().getTypeId().equals("4") && dto.getShowMode().equals(2)){
								String sdTrack = dto.getSdTrackName()+"("+dto.getSdBgWidth()+"x"+dto.getSdBgHeight()+")";
								String hdTrack = dto.getHdTrackName()+"("+dto.getHdBgWidth()+"x"+dto.getHdBgHeight()+")";
								model.addAttribute("sdTrack",sdTrack);
								model.addAttribute("hdTrack",hdTrack);
								model.addAttribute("trackMode",dto.getShowMode());
							}
						}else{
							sdPoint = "start:("+dto.getSdStartX()+","+dto.getSdStartY()+")";
							hdPoint = "start:("+dto.getHdStartX()+","+dto.getHdStartY()+")";
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
		return "/adelement/advPreview";
	}

	@RequiresPermissions("sys:adv:edit")
	@RequestMapping(value = "/addreturn")
	public String addReturn(Adelement entity, HttpServletRequest request,
							Model model, RedirectAttributes redirectAttributes) {
		try {

			model.addAttribute("adelement", entity);
			logger.error(UserUtils.getUser().getLoginName()+"广告Id："+entity.getId()+"预览页面跳转会修改页面成功");
			return form(entity, model);
		} catch (Exception e) {
			logger.error(UserUtils.getUser().getLoginName()+"广告Id："+entity.getId()+"预览页面跳转会修改页面失败",e.getMessage());

			return "/adelement/advForm";
		}
	}

	@RequiresPermissions("sys:adv:edit")
	@RequestMapping(value = "/delete")
	public String delete(Adelement entity, HttpServletRequest request,
						 Model model, RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 1, getMessage("adv.adId"), entity.getAdId());
		try {
			entity = thisService.get(entity.getId());
			int status = thisService.isDelete(entity);
			if (status == 1) {
				logService.save(request, logInfo, null);
				logger.info(UserUtils.getUser().getLoginName()+"删除广告Id："+entity.getId()+"成功");
				addMessage(redirectAttributes, "msg.del.success");
			} else {
				logService.save(request, logInfo, new Exception(getMessage("adv.delete.not")));
				logger.error(UserUtils.getUser().getLoginName()+"删除广告Id："+entity.getId()+"失败，失败原因："+getMessage("adv.delete.not"));
				addMessage(redirectAttributes, "adv.delete.not");
			}
			return "redirect:/adv/adelement/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"删除广告Id："+entity.getId()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/adelement/?repage";
		}
	}

	/**
	 * 停播广告
	 * @param id
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:adv:edit")
	@RequestMapping(value = "/closedown")
	public String closeDown(String id, HttpServletRequest request, Model model,
							RedirectAttributes redirectAttributes) {
		Adelement a = get(id);
		String deriction = getMessage("adv.adId");
		String logInfo=getMessage("close.down.log",new Object[]{deriction,a.getAdId()});
		try {
			thisService.closeDown(id, Adelement.ADV_DELETE_NOW_NO);
			addMessage(redirectAttributes, "close.down.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"停播广告Id："+a.getAdId()+"成功");
			return "redirect:/adv/adelement/?repage";
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "close.down.fail");
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"停播广告Id："+a.getAdId()+"失败",e.getMessage());
			return "redirect:/adv/adelement/?repage";
		}
	}

	@RequiresPermissions("sys:adv:edit")
	@RequestMapping(value = "/closedownNow")
	public String closeDownNow(String id, HttpServletRequest request,
							   Model model, RedirectAttributes redirectAttributes) {
		Adelement a = get(id);
		String deriction = getMessage("adv.adId");
		String logInfo=getMessage("close.now.down.log",new Object[]{deriction,a.getAdId()});
		try {

			thisService.closeDown(id, Adelement.ADV_DELETE_NOW_YES);
			addMessage(redirectAttributes, "close.down.now.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"紧急停播广告Id："+a.getAdId()+"成功");
			return "redirect:/adv/adelement/?repage";
		} catch (Exception e) {

			addMessage(redirectAttributes, "close.down.now.fail");
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"紧急停播广告Id："+a.getAdId()+"失败",e.getMessage());
			return "redirect:/adv/adelement/?repage";
		}
	}

	@RequiresPermissions("sys:adv:edit")
	@RequestMapping(value = "/restart")
	public String restart(String id, HttpServletRequest request,
						  Model model, RedirectAttributes redirectAttributes) {
		Adelement entity = get(id);
		String deriction = getMessage("adv.adId");
		String logInfo=getMessage("restart.log",new Object[]{deriction,entity.getAdId()});
		try {
			thisService.push(entity,entity.getAdCombo());
			addMessage(redirectAttributes, "restart.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"重启广告Id："+entity.getAdId()+"成功");
			return "redirect:/adv/adelement/?repage";
		} catch (Exception e) {

			addMessage(redirectAttributes, "restart.fail");
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"重启广告Id："+entity.getAdId()+"失败",e.getMessage());
			return "redirect:/adv/adelement/?repage";
		}
	}

	@RequiresPermissions("sys:adv:edit")
	@RequestMapping(value = "/freeze")
	public String freeze(String id, HttpServletRequest request,
						 Model model, RedirectAttributes redirectAttributes) {
		Adelement entity = get(id);
		String deriction = getMessage("adv.adId");
		String logInfo=getMessage("freeze.log",new Object[]{deriction,entity.getAdId()});
		try {
			entity.setStatus(Adelement.ADV_STATUS_FREEZE);
			thisService.updateStatus(entity);
			addMessage(redirectAttributes, "freeze.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"冻结广告Id："+entity.getAdId()+"成功");
			return "redirect:/adv/adelement/?repage";
		} catch (Exception e) {

			addMessage(redirectAttributes, "freeze.fail");
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"冻结广告Id："+entity.getAdId()+"失败",e.getMessage());
			return "redirect:/adv/adelement/?repage";
		}
	}

	@ResponseBody
	@RequiresPermissions("sys:adv:edit")
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
	@RequiresPermissions("sys:adv:edit")
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
	@RequiresPermissions("sys:adv:edit")
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
	@RequiresPermissions("sys:adv:edit")
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
	@RequiresPermissions("sys:adv:edit")
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
	 * 广告发布页面获取资源文件
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:adv:edit")
	@RequestMapping(value = "getResoure", method = RequestMethod.POST)
	public List<AdResourceDTO> getResoure(@RequestBody Map<String, Object> map,
										  HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = (String) map.get("id");
			String adelementId = (String) map.get("adelementId");
			String rollFlag = (String) map.get("rollFlag");
			String version = (String) map.get("version");
			User user = UserUtils.getUser();
			Map<String, Object> c_map = new HashMap<>();
			c_map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			c_map.put("typeId", id);
			c_map.put("adelementId", adelementId);
			c_map.put("rollFlag", rollFlag);
			c_map.put("hdShowParam", map.get("hdShowParam"));
			c_map.put("sdShowParam", map.get("sdShowParam"));
			String resolution = (String) map.get("resolution");
			List<String> versionIds = Lists.newArrayList();
			if(StringUtils.isNotBlank(version)){
				versionIds = Arrays.asList(version.split(","));
				c_map.put("versionIds", versionIds);
			}
			if (StringUtils.isBlank(resolution)) {
				return null;
			}
			Integer reInteger = Integer.valueOf(resolution);
			c_map.put("resolution", reInteger);
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
			thisService.clear();
			int status = entity.getStatus();
			if (status == Adelement.ADV_STATUS_SHOW
					|| status == Adelement.ADV_STATUS_END) {
				entity.setStatus(Adelement.ADV_STATUS_PASS);
			}

			model.addAttribute("isSd",sd);
			model.addAttribute("isHd",hd);
			return "/statistics/advAuditStatistic/aduitStatisticForm";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/statistics/advAuditStatistic/aduitStatisticForm";
	}


	/**
	 * 获取所选择发送器的占用的内存
	 *
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getNCLimit", method = RequestMethod.POST)
	public String getNCLimit(@RequestBody Map<String, Object> map,
							 HttpServletRequest request, HttpServletResponse response) {
		String typeId = (String) map.get("typeId");
		String networkList = (String) map.get("networkIds");
		String channelList = (String) map.get("channelIds");
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String startHour = (String) map.get("startHour");
		String startMinutes = (String) map.get("startMinutes");
		String startSecond = (String) map.get("startSecond");
		String endHour = (String) map.get("endHour");
		String endMinutes = (String) map.get("endMinutes");
		String endSecond = (String) map.get("endSecond");
		String sendMode = (String) map.get("sendMode");
		AdChannelNCDTO dto = null;
		try {
			dto = thisService.getCurrentNC(typeId,sendMode, networkList,channelList,startDate,endDate,startHour,startMinutes,startSecond,endHour,endMinutes,endSecond);
		} catch (Exception e) {
			logger.error("获取通道内存信息失败", e);
			e.printStackTrace();
		}
		return JsonMapper.toJsonString(dto);
	}

	/**
	 * 获取所选择发送器的占用的内存
	 *
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getNCLimit1", method = RequestMethod.POST)
	public String getNCLimit1(@RequestBody Map<String, Object> map,
							  HttpServletRequest request, HttpServletResponse response) {
		String typeId = (String) map.get("typeId");
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String comboId = (String) map.get("comboId");
		AdChannelNCDTO dto = null;
		try {
			dto = thisService.getCurrentNC1(typeId,startDate,endDate,comboId);
		} catch (Exception e) {
			logger.error("获取通道内存信息失败", e);
			e.printStackTrace();
		}
		return JsonMapper.toJsonString(dto);
	}
}
