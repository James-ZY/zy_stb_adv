package com.gospell.aas.controller.adv;

import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.AdVersion;
import com.gospell.aas.service.adv.AdVersionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/sys/version")
public class AdVersionController extends BaseController {

    @Autowired
    private AdVersionService thisService;

    @ModelAttribute
    public AdVersion get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return thisService.get(id);
        } else {
            return new AdVersion();
        }
    }

    @RequiresPermissions("sys:version:view")
    @RequestMapping(value = "/form")
    public String form(AdVersion entity, Model model) {
        List<AdVersion> list = thisService.findAll();
        model.addAttribute("adVersion", list.get(0));
        return "/sys/versionForm";
    }

}
