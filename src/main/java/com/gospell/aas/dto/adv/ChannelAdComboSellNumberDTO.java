package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* @author zhaohw
* @date 2018/3/29 14:50
*/
public class ChannelAdComboSellNumberDTO {

	/**
	 * 0表示0-1,1表示1-2,2表示2-3,...23表示23-24点
	 */
	private Integer time;

	/**
	 * 每个时间段的销售个数
	 */
	@JsonProperty("count")
	private Integer count;

	/**
	 * 时间占比
	 */
	private Double rate;

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "ChannelAdComboSellNumberDTO{" +
				"time=" + time +
				", count=" + count +
				", rate=" + rate +
				'}';
	}
}
