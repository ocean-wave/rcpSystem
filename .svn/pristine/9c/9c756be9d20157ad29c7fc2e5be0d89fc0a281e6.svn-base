package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_b_menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 3066798196738237158L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 菜单名
     */
    @Column(name = "menu_title")
    private String menuTitle;

    /**
     * 父级菜单ID
     */
    @Column(name = "p_menu_id")
    private Long pMenuId;

    /**
     * 菜单图标
     */
    @Column(name = "menu_icon")
    private String menuIcon;

    /**
     * 菜单URL
     */
    @Column(name = "menu_url")
    private String menuUrl;

    /**
     * 是否末级（0-否，1-是）
     */
    @Column(name = "is_last")
    private Integer isLast;

    /**
     * 排序
     */
    @Column(name = "sort_no")
    private Integer sortNo;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 是否有效（0-无效，1-有效）
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

    /**
     * 描述
     */
    private String description;

    /**
     * 级别编码
     */
    @Column(name = "level_code")
    private String levelCode;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取菜单ID
     *
     * @return menu_id - 菜单ID
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单ID
     *
     * @param menuId 菜单ID
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取菜单名
     *
     * @return menu_title - 菜单名
     */
    public String getMenuTitle() {
        return menuTitle;
    }

    /**
     * 设置菜单名
     *
     * @param menuTitle 菜单名
     */
    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    /**
     * 获取父级菜单ID
     *
     * @return p_menu_id - 父级菜单ID
     */
    public Long getpMenuId() {
        return pMenuId;
    }

    /**
     * 设置父级菜单ID
     *
     * @param pMenuId 父级菜单ID
     */
    public void setpMenuId(Long pMenuId) {
        this.pMenuId = pMenuId;
    }

    /**
     * 获取菜单图标
     *
     * @return menu_icon - 菜单图标
     */
    public String getMenuIcon() {
        return menuIcon;
    }

    /**
     * 设置菜单图标
     *
     * @param menuIcon 菜单图标
     */
    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    /**
     * 获取菜单URL
     *
     * @return menu_url - 菜单URL
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * 设置菜单URL
     *
     * @param menuUrl 菜单URL
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    /**
     * 获取是否末级（0-否，1-是）
     *
     * @return is_last - 是否末级（0-否，1-是）
     */
    public Integer getIsLast() {
        return isLast;
    }

    /**
     * 设置是否末级（0-否，1-是）
     *
     * @param isLast 是否末级（0-否，1-是）
     */
    public void setIsLast(Integer isLast) {
        this.isLast = isLast;
    }

    /**
     * 获取排序
     *
     * @return sort_no - 排序
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * 设置排序
     *
     * @param sortNo 排序
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人
     *
     * @return create_user_id - 创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人
     *
     * @param createUserId 创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取是否有效（0-无效，1-有效）
     *
     * @return is_enabled - 是否有效（0-无效，1-有效）
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否有效（0-无效，1-有效）
     *
     * @param isEnabled 是否有效（0-无效，1-有效）
     */
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }
}