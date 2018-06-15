package com.gospell.aas.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

/**
 * Created by wdl on 14-3-21.
 */
public class HttpAttributeHelper {

    public static void postMenuId(HttpServletRequest request, Model model) {
        String menuId = request.getParameter("id");
        model.addAttribute("menuId", menuId);
    }

}
