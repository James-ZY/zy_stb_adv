package com.gospell.aas.common.utils.adv;

import java.util.Comparator;

import com.gospell.aas.dto.adv.AdComboStatisticSellDTO;

public class MyComparator implements Comparator<AdComboStatisticSellDTO> {

 

	@Override
	public int compare(AdComboStatisticSellDTO one, AdComboStatisticSellDTO two) {
//		 int i = one.getComboId().compareTo(two.getComboId());
//		 if(i==0){
			 return one.getSellDay() - two.getSellDay();
//		 }else{
//			 return i;
//		 }
	 
	}
 

	 

}
