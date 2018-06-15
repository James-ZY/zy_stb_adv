package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 广告商在各广告类型中占比
 *
 * @author zhaohw
 * @date 2018-02-26 10:36
 */
public class AdvtiserRateDTO extends AdvtiserSellNumber{

    /**
     * 广告类型id
     */
    @JsonProperty("adTypeId")
    private String adTypeId;

    /**
     * 广告类型名称
     */
    @JsonProperty("adTypeName")
    private String adTypeName;

    public String getAdTypeId() {
        return adTypeId;
    }

    public void setAdTypeId(String adTypeId) {
        this.adTypeId = adTypeId;
    }

    public String getAdTypeName() {
        return adTypeName;
    }

    public void setAdTypeName(String adTypeName) {
        this.adTypeName = adTypeName;
    }

    @Override
    public String toString() {
        return "AdvtiserRateDTO{" +
                "adTypeId='" + adTypeId + '\'' +
                ", adTypeName='" + adTypeName + '\'' +super.toString()+
                '}';
    }
}
