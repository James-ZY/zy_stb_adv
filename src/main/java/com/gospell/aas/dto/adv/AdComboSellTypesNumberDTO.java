package com.gospell.aas.dto.adv;

import java.util.List;

/**
 * 套餐播放时段分广告类型统计
 *
 * @author zhaohw
 * @date 2018-02-28 14:53
 */
public class AdComboSellTypesNumberDTO {

    /**
     * 广告类型id
     */
    private String typeId;

    /**
     * 广告类型名称
     */
    private String typeName;

    /**
     * 时段数量统计
     */
    private List<ChannelAdComboSellNumberDTO> sellNumber;

    public AdComboSellTypesNumberDTO() {
    }

    public AdComboSellTypesNumberDTO(String typeId, List<ChannelAdComboSellNumberDTO> sellNumber) {
        this.typeId = typeId;
        this.sellNumber = sellNumber;
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

    public List<ChannelAdComboSellNumberDTO> getSellNumber() {
        return sellNumber;
    }

    public void setSellNumber(List<ChannelAdComboSellNumberDTO> sellNumber) {
        this.sellNumber = sellNumber;
    }

    @Override
    public String toString() {
        return "AdComboSellTypesNumberDTO{" +
                "typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", sellNumber=" + sellNumber +
                '}';
    }
}
