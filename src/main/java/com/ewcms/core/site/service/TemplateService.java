/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.history.History;
import com.ewcms.core.site.dao.TemplateDAO;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * @author 周冬初
 *
 */
@Service
public class TemplateService implements TemplateServiceable{

	@Autowired
	private TemplateDAO templateDao;
	
	
	public Template getTemplate(Integer id){
		return templateDao.get(id);
	}
	@History(modelObjectIndex = 0)	
	public Integer addTemplate(Template vo){
		templateDao.persist(vo);
		return vo.getId();
	}
	
	@History(modelObjectIndex = 0)	
	public Integer updTemplate(Template vo){
		templateDao.merge(vo);	
		updPubPath(vo);
		return vo.getId();
	}
	/**
	 * 模板目录发生修改，需要更新模板发布路径，并且包括其子模板路径
	 * 
	 * @param channel
	 *            模板
	 */
	private void updPubPath(final Template vo) {
		for (Template child : templateDao.getTemplateChildren(vo.getId(), getCurSite().getId(), null)) {
			child.setPath(null);
			updPubPath(child);
		}
		templateDao.merge(vo);
	}


	
	public void delTemplateBatch(List<Integer> idList){
		for(Integer id :idList){
			delTemplate(id);
		}		
	}
	
	public void delTemplate(Integer id){
		templateDao.removeByPK(id);
	}
	
	public List<Template> getTemplateList(){
		return templateDao.getTemplateList(getCurSite().getId());
	}
	/**
	 * 获取跟模板集
	 * 
	 */ 	
	public List<Template> getTemplaeTreeList(Boolean channelEnable){
		return getTemplateChildren(null,channelEnable,null);
	}
	
	/**
	 * 获取模板子模板集
	 * 
	 */ 	
	public List<Template> getTemplaeTreeList(Integer parentId,Boolean channelEnable){
		return getTemplateChildren(parentId,channelEnable,null);
	}

	/**
	 * 获取模板子模板集及 某个专栏模板
	 * 
	 */ 	
	public List<Template> getTemplaeTreeList(Integer parentId,String channelName){
		return getTemplateChildren(parentId,true,channelName);
	}
	
	/**
	 * 获取模板子模板
	 * 
	 */ 	
    private List<Template> getTemplateChildren(Integer parentId,Boolean channelEnable,String channelName){
        List<Template> tplList = getTemplateChildren(parentId,channelName);
        List<Template> validateList = new ArrayList<Template>();
        for(Template vo:tplList){
        	if(!channelEnable&&vo.getName().equals(getSiteTplName())){//屏蔽所有专栏模板
        		continue;
        	}
        	if(channelName!=null && parentId!=null){//屏蔽其它专栏模板,只显示某个专栏模板
        		if(getTemplate(parentId).getName().equals(getSiteTplName())&&!vo.getName().equals(channelName)){
        			continue;
        		}
        	}
        	validateList.add(vo);
        }
        return validateList;
    }
	
	private List<Template> getTemplateChildren(Integer parentId,String channelName){
		if(channelName!=null && getTemplate(parentId).getName().equals(channelName))
			return templateDao.getTemplateChildren(parentId,EwcmsContextUtil.getCurrentSite().getId(),Integer.valueOf(channelName));
		return templateDao.getTemplateChildren(parentId,EwcmsContextUtil.getCurrentSite().getId(),null);
	}  
	/**
	 * 获取站点专栏模板根目录
	 * 
	 */    
    public Template channelTPLRoot(){
    	return channelTemplate(null);
    }
	/**
	 * 获取模板唯一路径
	 * 
	 */      
    public String getTemplateUniquePath(Integer siteId,Integer channelId,String templateName){
    	return siteId.toString()+"/"+ siteId.toString()+"tpl/"+channelId.toString()+"/"+templateName;
    }
    
	/**
	 * 获取站点专栏模板目录
	 * 
	 */     
    public Template channelTemplate(String tplName){
    	if(tplName==null||tplName.length()==0){
        	Template vo = templateDao.getChannelTemplate(getSiteTplName(),getCurSite().getId(),null);
        	if(vo == null){//没有站点专栏模板节点，就创建
        		vo = new Template();
        		vo.setDescribe(getCurSite().getSiteName()+"专栏模板目录");
        		vo.setName(getSiteTplName());
        		vo.setSite(getCurSite());
        		vo.setPath(getSiteTplName());
        		vo.setSize("0KB");
        		templateDao.persist(vo);
        	} 
        	return vo;
    	}else{
    		Integer parentId = channelTPLRoot().getId();
    		Template vo = templateDao.getChannelTemplate(tplName,getCurSite().getId(),parentId);
        	if(vo == null){//没有站点专栏模板节点，就创建
        		vo = new Template();
        		vo.setDescribe(tplName+"专栏模板目录");
        		vo.setName(tplName);
        		vo.setSite(getCurSite());
        		vo.setPath(getSiteTplName()+"/"+tplName);
        		vo.setSize("0KB");
        		vo.setParent(channelTPLRoot());
        		templateDao.persist(vo);
        	} 
        	return vo;
    	}
    }
    
	private Site getCurSite(){
		return EwcmsContextUtil.getCurrentSite();
	}
	private String getSiteTplName(){
		return getCurSite().getId()+"tpl";
	}
	
	@Override
	public List<Template> getTemplatesInChannel(Integer id) {
		return templateDao.getTemplatesInChannel(id);
	}
	
	@Override
	public Template getTemplateByUniquePath(String path) {
		return templateDao.getTemplateByPath(path);
	}
	
	@Override
	public String getUniquePathOfChannelTemplate(Integer siteId, Integer channelId, String name) {
		return siteId.toString()+"/"+siteId.toString()+"tpl/"+channelId.toString()+"/"+name;
	}
}
