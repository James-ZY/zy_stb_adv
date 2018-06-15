package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 广告类型点击次数/播放时长
 *
 * @author zhaohw
 * @date 2018-01-30 16:40
 */
public class AdStatisticTypePlayDTO {

    /**
     * 广告类型id
     */
    @JsonProperty("advTypeId")
    private String advTypeId;

    /**
     * 广告类型名
     */
    @JsonProperty("advTypeName")
    private String advTypeName;

    /**
     * 点击次数/播放时长
     */
    @JsonProperty("count")
    private String count;

    public String getAdvTypeId() {
        return advTypeId;
    }

    public void setAdvTypeId(String advTypeId) {
        this.advTypeId = advTypeId;
    }

    public String getAdvTypeName() {
        return advTypeName;
    }

    public void setAdvTypeName(String advTypeName) {
        this.advTypeName = advTypeName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AdStatisticTypePlayDTO{" +
                "advTypeId='" + advTypeId + '\'' +
                ", advTypeName='" + advTypeName + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
