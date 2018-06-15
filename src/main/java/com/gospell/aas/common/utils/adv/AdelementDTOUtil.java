package com.gospell.aas.common.utils.adv;

import java.util.List;

import com.gospell.aas.entity.adv.*;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.dto.adv.AdelementDTO;
import com.gospell.aas.repository.mybatis.adv.IAdControlDao;

/**
 * 广告工具类
 * 
 * @author Administrator
 * 
 */
public class AdelementDTOUtil {

	private static IAdControlDao mybatisDao = ApplicationContextHelper
			.getBean(IAdControlDao.class);

	/**
	 * 根据广告以及其对应的广告套餐和广告位置进行广告预览数据的回传
	 * 
	 * @param adelement
	 * @param combo
	 * @return
	 */

	public static AdelementDTO getAdelementDTO(Adelement adelement,
											   AdCombo combo, AdPosition sdposition, AdPosition hdPosition, AdRange sdRange, AdRange hdRange, AdTrack sdTrack, AdTrack hdTrack) {
		AdelementDTO dto = new AdelementDTO();

		List<AdControll> path_list = getAdControl(adelement);
		String file_path = getControlFilePath(path_list);
		List<AdControll> hd_path_list = getHdAdControl(adelement);
		String hd_file_path = getControlFilePath(hd_path_list);
		if (StringUtils.isBlank(file_path) && StringUtils.isBlank(hd_file_path)) {
			return null;
		}
		dto.setPath(file_path);
		dto.setHdPath(hd_file_path);
		dto.setAdId(adelement.getAdId());
		dto.setAdName(adelement.getAdName());
		
		AdType adType = combo.getAdType();
		if (adType != null) {
			dto.setTypeId(adType.getId());
			dto.setStatus(adType.getStatus());
			dto.setIsPosition(adType.getIsPosition());
			dto.setIsMove(adType.getIsMove());
			if(adType.getId().equals(AdType.TYPE_ROLL_ADV_ID)){
				dto.setSdVelocity(adelement.getVelocity()==null?5:adelement.getVelocity());	
				dto.setHdVelocity(adelement.getVelocity()==null?5:adelement.getVelocity());
			}

			if (null != sdposition) {
				if(sdposition.getBeginPointX() != null && sdRange != null){
					if(adType.getId().equals("5")){
						dto.setSdFx(0);
						if(sdposition.getEndPointY().equals(sdposition.getBeginPointY())){
							if(sdposition.getBeginPointX() < sdposition.getEndPointX()){
								dto.setSdFx(1);
								dto.setSdX(sdposition.getBeginPointX()-sdRange.getBeginX());
							}else{
								dto.setSdFx(0);
								dto.setSdX(sdposition.getEndPointX()-sdRange.getBeginX());
							}
							dto.setSdY(sdposition.getEndPointY()-sdRange.getBeginY());
						}else if(sdposition.getBeginPointX().equals(sdposition.getEndPointX())){
							if(sdposition.getBeginPointY() < sdposition.getEndPointY()){
								dto.setSdFx(3);
								dto.setSdY(sdposition.getBeginPointY() -sdRange.getBeginY());
							}else{
								dto.setSdFx(2);
								dto.setSdY(sdposition.getEndPointY() -sdRange.getBeginY());
							}
							dto.setSdX(sdposition.getBeginPointX()-sdRange.getBeginX());
						}
					}else{
						if(sdposition.getBeginPointX() < sdRange.getBeginX()){
							dto.setSdX(0);					
						}else{
							if(sdposition.getEndPointX() !=null){
								if(sdposition.getBeginPointX() < sdposition.getEndPointX()){
									dto.setSdX(sdposition.getBeginPointX()-sdRange.getBeginX());											
								}else{
									dto.setSdX(sdposition.getEndPointX()-sdRange.getBeginX());
								}						
							}else{
								dto.setSdX(sdposition.getBeginPointX()-sdRange.getBeginX());	
							}
						}
						if(sdposition.getBeginPointY() < sdRange.getBeginY()){
							dto.setSdY(0);
						}else{
							dto.setSdY(sdposition.getBeginPointY()-sdRange.getBeginY());					
						}
					}

					if (null != adType.getIsMove()) {
						int ismove = adType.getIsMove();
						if(ismove == AdType.TYPE_CAN_MOVE){
							dto.setSdVelocity(adelement.getVelocity()==null?5:adelement.getVelocity());
							dto.setEndSdX(sdposition.getEndPointX());
							dto.setEndSdY(sdposition.getEndPointY());
						}else{
							dto.setEndSdX(sdposition.getEndPointX());
							dto.setEndSdY(sdposition.getEndPointY());						
						}
					}
				}
				
			}else{
				if(sdRange != null){
					dto.setSdX(0);	
					dto.setSdY(0);
					dto.setSdFx(adelement.getSdFx());
				}
			}
			if (null != hdPosition) {
				if(null != hdPosition.getBeginPointX() && null != hdRange){
					if(adType.getId().equals("5")){
						dto.setHdFx(0);
						if(hdPosition.getEndPointY().equals(hdPosition.getBeginPointY())){
							if(hdPosition.getBeginPointX() < hdPosition.getEndPointX()){
								dto.setHdFx(1);
								dto.setHdX(hdPosition.getBeginPointX()-hdRange.getBeginX());
							}else{
								dto.setHdFx(0);
								dto.setHdX(hdPosition.getEndPointX()-hdRange.getBeginX());
							}
							dto.setHdY(hdPosition.getEndPointY()-hdRange.getBeginY());
						}else if(hdPosition.getBeginPointX().equals(hdPosition.getEndPointX())){
							if(hdPosition.getBeginPointY() < hdPosition.getEndPointY()){
								dto.setHdFx(3);
								dto.setHdY(hdPosition.getBeginPointY() -hdRange.getBeginY());
							}else{
								dto.setHdFx(2);
								dto.setHdY(hdPosition.getEndPointY() -hdRange.getBeginY());
							}
							dto.setHdX(hdPosition.getBeginPointX()-hdRange.getBeginX());
						}
					}else{
						if(hdPosition.getBeginPointX() < hdRange.getBeginX()){
							dto.setHdX(0);					
						}else{
							if(hdPosition.getEndPointX() !=null){
								if(hdPosition.getBeginPointX() < hdPosition.getEndPointX()){
									dto.setHdX(hdPosition.getBeginPointX()-hdRange.getBeginX());											
								}else{
									dto.setHdX(hdPosition.getEndPointX()-hdRange.getBeginX());
								}
							}else{
								dto.setHdX(hdPosition.getBeginPointX()-hdRange.getBeginX());		
							}
						}
						if(hdPosition.getBeginPointY() < hdRange.getBeginY()){
							dto.setHdY(0);
						}else{
							dto.setHdY(hdPosition.getBeginPointY()-hdRange.getBeginY());					
						}
					}
				
				if (null != adType.getIsMove()) {
					int ismove = adType.getIsMove();
					if(ismove == AdType.TYPE_CAN_MOVE){
						dto.setHdVelocity(adelement.getVelocity()==null?5:adelement.getVelocity());
						dto.setEndHdX(hdPosition.getEndPointX());
						dto.setEndHdY(hdPosition.getEndPointY());
					}else{
						dto.setEndHdX(hdPosition.getEndPointX());
						dto.setEndHdY(hdPosition.getEndPointY());						
					}
				}
			}
		}else{
			if(hdRange != null){
				dto.setHdX(0);	
				dto.setHdY(0);
				dto.setHdFx(adelement.getHdFx());
			}
		}
		}
		if(null !=sdRange){
			dto.setSdStartX(sdRange.getBeginX());
			dto.setSdStartY(sdRange.getBeginY());
			dto.setHeight(sdRange.getEndY()-sdRange.getBeginY());
			dto.setWidth(sdRange.getEndX()-sdRange.getBeginX());
		}
		if(null !=hdRange){
			dto.setHdStartX(hdRange.getBeginX());
			dto.setHdStartY(hdRange.getBeginY());
			dto.setHeightHd(hdRange.getEndY()-hdRange.getBeginY());
			dto.setWidthHd(hdRange.getEndX()-hdRange.getBeginX());
		}
		
		if(null != adelement.getSdFx()){
			dto.setSdFx(adelement.getSdFx());
		}
		if(null != adelement.getHdFx()){
			dto.setHdFx(adelement.getHdFx());
		}
		if (null != combo.getShowCount()) {
			dto.setShowCount(combo.getShowCount());
		} else {
			dto.setShowCount(0);
		}

		if (null != combo.getShowTime()) {
			dto.setShowTime(combo.getShowTime());
		} else {
			dto.setShowTime(0);
		}

		//设置插屏轨迹信息
		if(adType.getId().equals("4")){
			dto.setShowMode(combo.getTrackMode());
			if(null != sdTrack){
				dto.setSdTrackName(sdTrack.getTrackName());
				dto.setSdCoordinates(sdTrack.getCoordinates());
				dto.setSdCpShowTime(sdTrack.getShowTime());
				dto.setSdBgWidth(sdTrack.getBgWidth());
				dto.setSdBgHeight(sdTrack.getBgHeight());
				dto.setSdStartX(sdTrack.getRange().getBeginX());
				dto.setSdStartY(sdTrack.getRange().getBeginY());
				dto.setHeight(sdTrack.getRange().getEndY()-sdTrack.getRange().getBeginY());
				dto.setWidth(sdTrack.getRange().getEndX()-sdTrack.getRange().getBeginX());
			}
			if(null != hdTrack){
				dto.setHdTrackName(hdTrack.getTrackName());
				dto.setHdCoordinates(hdTrack.getCoordinates());
				dto.setHdCpShowTime(hdTrack.getShowTime());
				dto.setHdBgWidth(hdTrack.getBgWidth());
				dto.setHdBgHeight(hdTrack.getBgHeight());
				dto.setHdStartX(hdTrack.getRange().getBeginX());
				dto.setHdStartY(hdTrack.getRange().getBeginY());
				dto.setHeightHd(hdTrack.getRange().getEndY()-hdTrack.getRange().getBeginY());
				dto.setWidthHd(hdTrack.getRange().getEndX()-hdTrack.getRange().getBeginX());

			}
		}

		return dto;

	}

	/**
	 * 根据广告以及其对应的广告套餐进行广告预览视频数据的回传
	 * 
	 * @param adelement
	 * @param combo
	 * @param position
	 * @return
	 */

	public static AdelementDTO getAdelementDTOForVedio(Adelement adelement,
			AdCombo adCombo) {
		AdelementDTO dto = new AdelementDTO();
		
		List<AdControll> path_list = getAdControl(adelement);
		List<AdControll> hd_path_list = getHdAdControl(adelement);
		StringBuffer file_path = new StringBuffer();
		StringBuffer file_image_path= new StringBuffer();
		StringBuffer hd_file_path = new StringBuffer();
		StringBuffer hd_file_image_path= new StringBuffer();
		getControlVedioFilePath(path_list,file_path,file_image_path);
		getControlVedioFilePath(hd_path_list,hd_file_path,hd_file_image_path);
		if(StringUtils.isBlank(file_path) && StringUtils.isBlank(hd_file_path)){
			return null;
		}
		dto.setPath(file_path.toString());
		dto.setFileImagePath(file_image_path.toString());
		dto.setHdPath(hd_file_path.toString());
		dto.setHdFileImagePath(hd_file_image_path.toString());
		AdType adType = adCombo.getAdType();
			if (adType != null) {
				dto.setTypeId(adType.getId());
				dto.setStatus(adType.getStatus());

			}
			return dto;
		 
	 

	}

	private static List<AdControll> getAdControl(Adelement adelement) {
		List<String> paths = Lists.newArrayList();
		if (null != adelement.getControllerList()
				&& adelement.getControllerList().size() > 0) {
			List<AdControll> controllList = adelement.getControllerList();
			for (int i = 0; i < controllList.size(); i++) {
				paths.add(controllList.get(i).getId());
			}
		} else {
			return null;
		}

		try {
			return mybatisDao.getControlByIds(paths);
		} catch (Exception e) {
			return null;
		}
	}

	private static List<AdControll> getHdAdControl(Adelement adelement) {
		List<String> paths = Lists.newArrayList();
		if (null != adelement.getHdControllerList()
				&& adelement.getHdControllerList().size() > 0) {
			List<AdControll> controllList = adelement.getHdControllerList();
			for (int i = 0; i < controllList.size(); i++) {
				paths.add(controllList.get(i).getId());
			}
		} else {
			return null;
		}

		try {
			return mybatisDao.getControlByIds(paths);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getControlFilePath(List<AdControll> path_list) {
		if (null != path_list && path_list.size() > 0) {
			List<String> l = Lists.newArrayList();
			for (AdControll menu : path_list) {
				if (StringUtils.isNotBlank(menu.getFileImagePath())) {
					l.add(menu.getFileImagePath());
				} else {
					l.add(menu.getFilePath());
				}

			}
			return StringUtils.join(l, ",");
		}
		return null;
	}

	/**
	 * 获取视频资源文件的源文件路径和图片路径
	 * @param path_list
	 * @param file_path
	 * @param file_image_path
	 */
	public static void getControlVedioFilePath(List<AdControll> path_list,
		 StringBuffer file_path, StringBuffer file_image_path) {
		List<String> file_list = Lists.newArrayList();
		List<String> file_image_list = Lists.newArrayList();
		if (null != path_list && path_list.size() > 0) {
			for (int i = 0; i < path_list.size(); i++) {

				for (AdControll menu : path_list) {
					String previewVedioPath = menu.getPreviewVedioPath();
					String fileImagePath = menu.getFileImagePath();
                    if(previewVedioPath!=null){
                    	file_list.add(previewVedioPath.substring(0, previewVedioPath.lastIndexOf(".")) + ".mp4");                    	
                    }
					file_image_list.add(fileImagePath);

				}
			}
		}
		if (null != file_list && file_list.size() > 0) {
			file_path.append(StringUtils.join(file_list, ","));
			 
		}
		if (null != file_image_list && file_image_list.size() > 0) {
			file_image_path.append(StringUtils.join(file_image_list, ","));
		}
	}
	
}
