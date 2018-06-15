package com.gospell.aas.common.utils.adv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.dto.adv.AdChannelForComboDTO;
import com.gospell.aas.dto.adv.AdComboByUserDTO;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.repository.hibernate.adv.AdComboDao;
import com.gospell.aas.repository.mybatis.adv.IAdComboDao;

/**
 * 字典工具类
 * 
 * @author free lance
 * @version 2013-5-29
 */
public class AdComboUtils {

	private static Logger logger = LoggerFactory.getLogger(AdComboUtils.class);
	private static AdComboDao thisDao = ApplicationContextHelper
			.getBean(AdComboDao.class);

	private static IAdComboDao mybatisDao = ApplicationContextHelper
			.getBean(IAdComboDao.class);

	/**
	 * 获取已经运营的广告套餐
	 * 
	 * @return
	 */
	public static List<AdCombo> getAdComboList() {
		return thisDao.findAlreayCombo(AdCombo.ADCOMOBO_ALREADY_STATUS);
	}

	public static List<AdComboByUserDTO> getAdComboListByCurrentUser() {

		User user = UserUtils.getUser();
		List<AdComboByUserDTO> list = Lists.newArrayList();
		// if(user.isAdmin()){
		// List<AdCombo> allList = thisDao.findAll();
		// if(null != allList && allList.size() >0){
		// for (int i = 0; i < allList.size(); i++) {
		// AdComboByUserDTO dto = new AdComboByUserDTO();
		// dto.setId(allList.get(i).getId());
		// dto.setComboName(allList.get(i).getComboName());
		// dto.setAdComboId(allList.get(i).getAdcomboId());
		// list.add(dto);
		// }
		//
		// }
		// }else
		if (null != user.getAdvertiser()) {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = format.format(date);
			list = mybatisDao.getComboByUserId(dateStr,
					AdCombo.ADCOMOBO_ALREADY_STATUS, user.getId());// 获取当前登录的广告商当前时间还生效的广告套餐
		}
		return list;
	}

	/**
	 * 比较当前用户选择的套餐时间段与已经添加的数据的时间套餐段是否重复
	 * 
	 * @return
	 */
	public static boolean isIntime(String startTime, String endTime,
			String currentStartTime, String currentEndTime) {
		try {
			String[] start = startTime.split(":");
			String[] end = endTime.split(":");
			String[] c_start = currentStartTime.split(":");
			String[] c_end = currentEndTime.split(":");
			int start0 = Integer.parseInt(start[0]);
			int start1 = Integer.parseInt(start[1]);
			int start2 = Integer.parseInt(start[2]);
			int c_start0 = Integer.parseInt(c_start[0]);
			int c_start1 = Integer.parseInt(c_start[1]);
			int c_start2 = Integer.parseInt(c_start[2]);
			int end0 = Integer.parseInt(end[0]);
			int end1 = Integer.parseInt(end[1]);
			int end2 = Integer.parseInt(end[2]);
			int c_end0 = Integer.parseInt(c_end[0]);
			int c_end1 = Integer.parseInt(c_end[1]);
			int c_end2 = Integer.parseInt(c_end[2]);

			// 当前选择的结束时间（小时） < 查询出来套餐的开始时间（小时），肯定不重复
			if (c_end0 < start0) {
				return true;
			}
			if (c_end0 == start0) {
				if (c_end1 < start1) {// 比较分钟
					return true;
				}
				if (c_end1 == start1) {// 比较秒数
					if (c_end2 < start2) {
						return true;
					}
				}
			}
			// 当前选择的开始时间（小时） 大于 查询出来套餐的结束时间（小时），肯定不重复
			if (c_start0 > end0) {
				return true;
			}
			if (c_start0 == end0) {
				if (c_start1 > end1) {// 比较分钟
					return true;
				}
				if (c_start1 == end1) {// 比较秒数
					if (c_start2 > end2) {
						return true;
					}
				}
			}

		} catch (Exception e) {
			logger.error("比较当前选择的套餐时间段是否在已有的套餐里面", e);
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	 

	public static boolean compareCombo(AdChannelForComboDTO dto,
			Map<String, Object> map) {
		String startHour = String.valueOf(dto.getStartHour());

		String startMinutes = String.valueOf(dto.getStartMinutes());

		String startSecond = String.valueOf(dto.getStartSecond());

		String endHour = String.valueOf(dto.getEndHour());

		String endMinutes = String.valueOf(dto.getEndMinutes());
		String endSecond = String.valueOf(dto.getEndSecond());
		Date startDate = dto.getValidStartTime();
		Date endDate = dto.getValidEndTime();
		String format_startDate = DateUtils.formatDate(startDate, "yyyy-MM-dd");
		String format_endDate = DateUtils.formatDate(endDate, "yyyy-MM-dd");
		String start_h = (String) map.get("startHour");
		String start_m = (String) map.get("startMinute");
		String start_s = (String) map.get("startSecond");
		String end_h = (String) map.get("endHour");
		String end_m = (String) map.get("endMinute");
		String end_s = (String) map.get("endSecond");
		String s_Date = (String) map.get("startDate");
		String e_Date = (String) map.get("endDate");
		String _typeId = (String) map.get("typeId");
		String typeId = dto.getTypeId();
		
		if(format_startDate.equals(s_Date)
				&& format_endDate.equals(e_Date)&&startHour.equals(start_h) && startMinutes.equals(start_m)
				&& startSecond.equals(start_s)&& endHour.equals(end_h)&& endMinutes.equals(end_m)
				&& endSecond.equals(end_s) && _typeId.equals(typeId)){
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		String s = "09:00:01";
		String[] s1 = s.split(":");
		for (int i = 0; i < s1.length; i++) {
			System.out.println(s1[i]);
		}
	}

}
