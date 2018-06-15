package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
 

public class AdOperatorsDTO {
		
		@JsonProperty("operatorsId")
		private String operatorsId;// 电视运营商ID
		
		@JsonProperty("operatorsName")
	    private String operatorsName;// 电视运营商名称
				
		@JsonProperty("networkList")
	    private List<AdNetWorkDTO> networkList;// 电视运营商名称
		
/*		@JsonProperty("networkId")
		private String networkId;//广告发送器ID
		
		@JsonProperty("networkName")
		private String  networkName;//广告发送器名称
*/
		public String getOperatorsId() {
			return operatorsId;
		}

		public void setOperatorsId(String operatorsId) {
			this.operatorsId = operatorsId;
		}

		public String getOperatorsName() {
			return operatorsName;
		}

		public void setOperatorsName(String operatorsName) {
			this.operatorsName = operatorsName;
		}

		public List<AdNetWorkDTO> getNetworkList() {
			return networkList;
		}

		public void setNetworkList(List<AdNetWorkDTO> networkList) {
			this.networkList = networkList;
		}

/*		public String getNetworkId() {
			return networkId;
		}

		public void setNetworkId(String networkId) {
			this.networkId = networkId;
		}

		public String getNetworkName() {
			return networkName;
		}

		public void setNetworkName(String networkName) {
			this.networkName = networkName;
		}
		*/
		
	 
}
