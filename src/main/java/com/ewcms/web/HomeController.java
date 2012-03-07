package com.ewcms.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Site;
import com.ewcms.security.manage.SecurityFacable;
import com.ewcms.web.context.EwcmsContextHolder;

@Controller
public class HomeController {
	
	@Autowired
	private SiteFac siteFac;
	@Autowired
	private SecurityFacable securityFac;

	/**
     * 得到操作站点
     * 
     * @param id 站点编号
     * @return 操作站点
     */
    private Site getSite(Integer id) {
        Site site = null;
        if(id != null){
            site =  siteFac.getSite(id);
        }else{
            //TODO 得到用户所属组织，通过组织得到站点。
            List<Site> list= siteFac.getSiteListByOrgans(new Integer[]{}, true);
            if(list != null && !list.isEmpty()){
                site = list.get(0);
            }
        }
        return site ;
    }

    /**
     * 初始站点到访问上下文中当，提供全局访问
     * 
     * @param site
     */
    private void initSiteInContext(Site site){
	    EwcmsContextHolder.getContext().setSite(site);
	}
	
	@RequestMapping(value = "/home.do")
	public String index(@RequestParam(value = "siteId", required = false) Integer siteId, Model model){
		try{
			Site site = getSite(siteId);
			
			if(site != null){
				model.addAttribute("siteName", site.getSiteName());
			    initSiteInContext(site);
			}
			
			String realName = securityFac.getCurrentUserInfo().getName();
			model.addAttribute("realName", realName);
		}catch(Exception e){
			return "redirect:/login.do";
		}
		return "home";
	}
}
