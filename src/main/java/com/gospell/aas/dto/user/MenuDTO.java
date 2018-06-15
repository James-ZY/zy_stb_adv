package com.gospell.aas.dto.user;


public class MenuDTO {

    private String id; // 菜单id
    private String name; // 名称
    private String href; // 链接
    private String target; // 目标（ mainFrame、_blank、_self、_parent、_top）
    private String icon; // 图标

    public MenuDTO() {
        super();
    }

    public MenuDTO(String id) {
        this();
        this.id = id;
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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}