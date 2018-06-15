package com.gospell.aas.dto.adv;

/**
 * 各类型广告 销售时间 可运营时间
 *
 * @author zhaohw
 * @date 2018-03-01 15:24
 */
public class AdComboSellTimeDTO {

    /**
     * 广告类型id
     */
    private String typeId;

    /**
     * 广告类型名称
     */
    private String typeName;

    /**
     * 可运营时间 单位：小时
     */
    private Integer validTime;

    /**
     * 已销售时间 单位：小时
     */
    private Integer sellTime;

    /**
     * 售出占可运营百分比
     */
    private Double percent;

    public AdComboSellTimeDTO() {
    }

    public AdComboSellTimeDTO(String typeId, Integer validTime, Integer sellTime) {
        this.typeId = typeId;
        this.validTime = validTime;
        this.sellTime = sellTime;
        this.percent = sellTime*1.0/validTime;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public Integer getSellTime() {
        return sellTime;
    }

    public void setSellTime(Integer sellTime) {
        this.sellTime = sellTime;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "AdComboSellTimeDTO{" +
                "typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", validTime=" + validTime +
                ", sellTime=" + sellTime +
                ", percent=" + percent +
                '}';
    }
}
