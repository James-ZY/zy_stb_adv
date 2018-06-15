package com.gospell.aas.common.utils.adv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataOpretion {
	
	/**
	 * 根据天数来排序
	 * @param startDate
	 * @param endDate
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Integer>> returnListByDay(Date startDate,Date endDate,List<Map<String,Integer>> data) throws Exception{		
		//SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		//startDate = startDate.replace("-", "");
		//endDate = endDate.replace("-", "");
		Date date1 = startDate;
		Date date2 = endDate;
		List<Map<String,Integer>> list2 = new ArrayList<Map<String,Integer>>();
		while(date1.getTime()<=date2.getTime()){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			for(Map map : data){
				if(!df.format(date1).equals(map.get("date"))){
					Map<String,Integer> map1 = new HashMap<String,Integer>();
					map1.put("date",Integer.parseInt(df.format(date1)));
					map1.put("num",0);
					list2.add(map1);
				}else{
					list2.add(map);
				}
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			calendar.add(Calendar.DATE,1);
			date1 = calendar.getTime();
		}
		return list2;
	}

}
