package org.geely.service;

import org.geely.common.model.MenuDTO;

import java.util.List;

public interface MenuService {

    /**
     * 获取应用菜单
     * @param menuType
     * @return
     */
    List<MenuDTO> getAppMenu(Integer menuType,String appId);

    /**
     * 获取用户菜单
     * @param menuType
     * @return
     */
    List<MenuDTO> getUserMenu(Integer menuType,String appId);
}
