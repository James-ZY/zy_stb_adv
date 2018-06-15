package com.gospell.aas.dto.user;

public class RoleDTO {

    @Override
	public String toString() {
        return "RoleDTO [officeId=" + officeId + ", name=" + name + ", dataScope=" + dataScope + ", id=" + id + "]";
    }

    private String officeId; // 归属机构
    private String name; // 角色名称
    private String dataScope; // 数据范围
    private String id;

    // 数据范围（1：所有数据；2：所在机构及以下数据；3：所在机构数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
    public static final String DATA_SCOPE_ALL = "1";
    public static final String DATA_SCOPE_COMPANY_AND_CHILD = "2";
    public static final String DATA_SCOPE_COMPANY = "3";
    public static final String DATA_SCOPE_OFFICE_AND_CHILD = "4";
    public static final String DATA_SCOPE_OFFICE = "5";
    public static final String DATA_SCOPE_SELF = "8";
    public static final String DATA_SCOPE_CUSTOM = "9";

    public RoleDTO() {
        super();
        this.dataScope = DATA_SCOPE_CUSTOM;
    }

    public RoleDTO(String id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

}
