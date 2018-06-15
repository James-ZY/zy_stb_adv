package com.gospell.aas.common.utils.adv;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;

import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.adv.Adelement;

public class AdelementUtil {
	
 
 
	
	public static void copyAdelement(Adelement copyAdv,Adelement adv){
		adv.setAdId(copyAdv.getAdId());
		adv.setAdCombo(copyAdv.getAdCombo());
		adv.setAddText(copyAdv.getAddText());
		adv.setAdName(copyAdv.getAdName());
		adv.setAdvertiser(copyAdv.getAdvertiser());
		adv.setCreateBy(copyAdv.getCreateBy());
		adv.setCreateDate(copyAdv.getCreateDate());
		adv.setCreateDateEnd(copyAdv.getCreateDateEnd());
		adv.setCurrentUser(adv.getCurrentUser());
		adv.setDelFlag(adv.getDelFlag());
		adv.setEndDate(adv.getEndDate());
		adv.setFileSize(copyAdv.getFileSize());
		adv.setFormat(copyAdv.getFormat());
		 
	}
	
	/**
	 * 获取预览页面或者审核页面的信息
	 * @param entity
	 * @param model
	 */
	public static void preview(Adelement entity,Model model){
		try{
			Integer resourceType = AdType.TYPE_STATUS_IMAGE;
			String typeName="";
			String childTypeId="";
			model.addAttribute("resourceType", resourceType);
			model.addAttribute("categroryNameSelect",
					ApplicationContextHelper.getMessage("select.category.parent"));
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
			String sd =ApplicationContextHelper.getMessage("no");
			String hd=ApplicationContextHelper.getMessage("no");
			if(entity.getIsSd() != null){
				int isSd = entity.getIsSd();
				if(isSd == Adelement.ADV_START_SD){
					sd = ApplicationContextHelper.getMessage("yes");
				}
			}
			if(entity.getIsSd() != null){
				int ishd = entity.getIsSd();
				if(ishd == Adelement.ADV_START_HD){
					hd = ApplicationContextHelper.getMessage("yes");
				}
			}
			if(entity.getPosition()!=null){
				
			}
			model.addAttribute("isSd",sd);
			model.addAttribute("isHd",hd);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	 
	}
	
 
 

}
