package com.gospell.aas.controller.adv;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.DistrictCategoryModel;
import com.gospell.aas.dto.adv.SelectDistrictDTO;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdNetwork;
import com.gospell.aas.entity.adv.AdNetworkDistrict;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.service.adv.AdNetworkService;
import com.gospell.aas.service.adv.AdSellService;
import com.gospell.aas.service.adv.AdelementService;
import com.gospell.aas.webservice.netty.client.Client;
import com.gospell.aas.webservice.netty.dto.AdvNetWorkDistrictDTO;

@Controller
@RequestMapping(value = "/adv/network")
public class AdNetworkController extends BaseController {

	@Autowired
	private AdNetworkService thisService;
	@Autowired
	private AdSellService sellService;
	@Autowired
	private AdelementService elementService;

	@ModelAttribute
	public AdNetwork get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdNetwork();
		}
	}

	@RequiresPermissions("sys:network:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdNetwork entity, HttpServletRequest request, HttpServletResponse response, Model model) {
 
		try {
			Page<AdNetwork>   page = thisService.findAll(new Page<AdNetwork>(request, response), entity);
			model.addAttribute("page", page);
		} catch (Exception e) {
			 e.printStackTrace();
			 
		}

		return "/network/networkList";
	}

	@RequiresPermissions("sys:network:view")
	@RequestMapping(value = "/form")
	public String form(AdNetwork entity, Model model) {
		List<AdNetworkDistrict> adNetworkCategorys = entity.getAdNetworkCategorys();
		List<DistrictCategoryModel> districtCategoryModels = Lists.newArrayList();
		SelectDistrictDTO dto = new SelectDistrictDTO();
		if(adNetworkCategorys != null &&adNetworkCategorys.size() > 0 ){
			Map<String,String> selMap = new HashMap<String, String>();
			for (AdNetworkDistrict adNetworkDistrict : adNetworkCategorys) {
				DistrictCategoryModel dcModel = new DistrictCategoryModel();
				dcModel.setCategoryId(adNetworkDistrict.getDistrict().getId());
				dcModel.setCategoryName(adNetworkDistrict.getDistrict().getCategoryName());
				if(StringUtils.isNotBlank(adNetworkDistrict.getSelfDistrictId())){
					dcModel.setSelfCategoryId(adNetworkDistrict.getSelfDistrictId());
					selMap.put(adNetworkDistrict.getDistrict().getId(), adNetworkDistrict.getSelfDistrictId());
				}else{
					dcModel.setSelfCategoryId("");	
					selMap.put(adNetworkDistrict.getDistrict().getId(), "");
				}
				districtCategoryModels.add(dcModel);
			}
			dto.setAdDistrictCategorys(districtCategoryModels);
			model.addAttribute("selAreaInfo", JsonMapper.toJsonString(dto));
		}
		model.addAttribute("adNetwork", entity);
		return "/network/networkForm";
	}
	
	
	@RequiresPermissions("sys:network:edit")
	@RequestMapping(value = "/save")
	public String save(AdNetwork entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("network"), entity.getId());
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		try {
			thisService.save(entity);
			//推送发送器区域信息
			AdvNetWorkDistrictDTO disDto = thisService.getNetDisDto(entity);
			Client.getInstance().putAdDistrictInfo(entity.getNetworkId(), disDto);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存发送器区域信息："+entity.getId()+"成功！");
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/network/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存发送器区域信息："+entity.getId()+"失败！",e);
			addMessage(redirectAttributes,  "msg.save.fail");
			return "redirect:/adv/network/?repage";
		}
	}
	
/**
 * 让当前广告发送器失效
 * @param id
 * @param request
 * @param model
 * @param redirectAttributes
 * @return
 */
	@RequiresPermissions("sys:network:edit")
	@RequestMapping(value = "/stop") 
	public String stop(AdNetwork network, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		 thisService.clear();
		 String network_log = getMessage("network");
			String logInfo=getMessage("network.stop.log",new Object[]{network_log,network.getNetworkId()});
		try {
			int count = sellService.getSellCountInNetwork(network.getId());
			if(count >0){
				//thisService.stopNetwork(network);
				addMessage(redirectAttributes,"stop.network.fail.reason");
				logService.save(request, logInfo, new Exception(getMessage("stop.network.fail.reason")));
				logger.error(UserUtils.getUser().getLoginName()+"停用广告发送器Id："+network.getNetworkId()+"失败");
			}else{
				network.setStatus(AdNetwork.NETWORK_NO_STATUS);
				 
				thisService.stopNetwork(network);
				addMessage(redirectAttributes, "network.valid");
				logService.save(request, logInfo, null);
				logger.info(UserUtils.getUser().getLoginName()+"停用广告发送器Id："+network.getNetworkId()+"成功");
			}
	
			return "redirect:/adv/network/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"停用广告发送器Id："+network.getNetworkId()+"失败",e.getMessage());
			addMessage(redirectAttributes,  "stop.network.fail");
			return "redirect:/adv/network/?repage";
		}
	}
	/**
	 * 让当前广告发送器启用
	 * @param id
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
		@RequiresPermissions("sys:network:edit")
		@RequestMapping(value = "/start") 
		public String start(AdNetwork network, HttpServletRequest request, Model model,
				RedirectAttributes redirectAttributes) {
			 thisService.clear();
			 String network_log = getMessage("network");
				String logInfo=getMessage("network.start.log",new Object[]{network_log,network.getNetworkId()});
			try {
				network.setStatus(AdNetwork.NETWORK_YES_STATUS);
				thisService.startNetwork(network);
				addMessage(redirectAttributes, "network.start.success");
				logService.save(request, logInfo, null);
				logger.error(UserUtils.getUser().getLoginName()+"启用广告发送器Id："+network.getNetworkId()+"成功");
				return "redirect:/adv/network/?repage";
			} catch (Exception e) {
				logService.save(request, logInfo, e);
				logger.error(UserUtils.getUser().getLoginName()+"启用广告发送器Id："+network.getNetworkId()+"失败",e.getMessage());
				addMessage(redirectAttributes,  "network.start.fail");
				return "redirect:/adv/network/?repage";
			}
		}
		
		/**
		 * 全网停播
		 * @param network
		 * @param request
		 * @param model
		 * @param redirectAttributes
		 * @return
		 */
		@RequiresPermissions("sys:network:edit")
		@RequestMapping(value = "/shutdown") 
		public String shutdown(AdNetwork network, HttpServletRequest request, HttpServletResponse response,Model model,
				RedirectAttributes redirectAttributes) {
			 thisService.clear();
			 String network_log = getMessage("network");
				String logInfo=getMessage("network.shutdown.log",new Object[]{network_log,network.getNetworkId()});
			try {
				Adelement entity = new Adelement();
				AdCombo combo = new AdCombo();
				combo.setNetworkId(network.getId());
				entity.setAdCombo(combo);
				entity.setStatus(Adelement.ADV_STATUS_SHOW);
				entity.setIsValid(Adelement.ADV_VALID_STATUS);				
				//查询出改发送器下所以投放中的广告
				Page<Adelement> page = elementService.find(new Page<Adelement>(request,
						response,-1 ), entity);
				List<Adelement> elements = page.getList();
				for (Adelement adelement : elements) {
					String deriction = getMessage("adv.adId");
					String logInfo1=getMessage("close.down.log",new Object[]{deriction,adelement.getAdId()});
					try {
						elementService.closeDown(adelement.getId(), Adelement.ADV_DELETE_NOW_YES);
						logService.save(request, logInfo1, null);
						logger.info(UserUtils.getUser().getLoginName()+"紧急停播广告Id："+adelement.getAdId()+"成功");
					} catch (Exception e) {
						e.printStackTrace();
						logService.save(request, logInfo1, e);
						logger.error(UserUtils.getUser().getLoginName()+"紧急停播广告Id："+adelement.getAdId()+"失败",e.getMessage());
					}
				}	
				network = thisService.get(network.getId());
				network.setPlayStatus(AdNetwork.NETWORK_CLOSEDOWN_STATUS);
				thisService.save(network);
				addMessage(redirectAttributes, "close.down.success");
				return "redirect:/adv/network/?repage";
			} catch (Exception e) {
				logService.save(request, logInfo, e);
				logger.error(UserUtils.getUser().getLoginName()+"全网停播发送器Id："+network.getNetworkId()+"失败",e.getMessage());
				addMessage(redirectAttributes,  "close.down.fail");
				return "redirect:/adv/network/?repage";
			}
		}
		
		/**
		 * 全网重启
		 * @param network
		 * @param request
		 * @param model
		 * @param redirectAttributes
		 * @return
		 */
		@RequiresPermissions("sys:network:edit")
		@RequestMapping(value = "/restart") 
		public String restart(AdNetwork network, HttpServletRequest request,  HttpServletResponse response,Model model,
				RedirectAttributes redirectAttributes) {
			 thisService.clear();
			 String network_log = getMessage("network");
				String logInfo=getMessage("network.restart.log",new Object[]{network_log,network.getNetworkId()});
			try {
				Adelement entity = new Adelement();
				AdCombo combo = new AdCombo();
				combo.setNetworkId(network.getId());
				entity.setAdCombo(combo);
				entity.setStatus(Adelement.ADV_STATUS_END);
				entity.setIsValid(Adelement.ADV_VALID_STATUS);
				//查询出改发送器下所以投放中的广告
				Page<Adelement> page = elementService.find(new Page<Adelement>(request,
						response,-1 ), entity);
				List<Adelement> elements = page.getList();
				for (Adelement adelement : elements) {
					adelement = elementService.get(adelement.getId());
					String deriction = getMessage("adv.adId");
					String logInfo1=getMessage("restart.log",new Object[]{deriction,adelement.getAdId()});
					try {
					    elementService.push(adelement,adelement.getAdCombo());		
						logService.save(request, logInfo1, null);
						logger.info(UserUtils.getUser().getLoginName()+"重启广告Id："+adelement.getAdId()+"成功");
					} catch (Exception e) {
						e.printStackTrace();
						logService.save(request, logInfo1, e);
						logger.error(UserUtils.getUser().getLoginName()+"重启广告Id："+adelement.getAdId()+"失败",e.getMessage());
					}
				}	
				network = thisService.get(network.getId());
				network.setPlayStatus(AdNetwork.NETWORK_PLAY_STATUS);
				thisService.save(network);
				addMessage(redirectAttributes, "restart.success");
				return "redirect:/adv/network/?repage";
			} catch (Exception e) {
				logService.save(request, logInfo, e);
				logger.error(UserUtils.getUser().getLoginName()+"全网重启发送器Id："+network.getNetworkId()+"失败",e.getMessage());
				addMessage(redirectAttributes,  "restart.fail");
				return "redirect:/adv/network/?repage";
			}
		}
	
    @ResponseBody
    @RequiresPermissions("sys:network:edit")
    @RequestMapping(value = "checknetworkId")
    public String checknetworkId(String oldnetworkId, String networkId) {
    	try{
        if (networkId != null && networkId.equals(oldnetworkId)) {
            return "true";
        } else if (networkId != null && thisService.findByNetworkId(networkId) == null) {
            return "true";
        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return "false";
    }

}
