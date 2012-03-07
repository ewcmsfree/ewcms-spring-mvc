/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.page;

import org.apache.commons.lang.StringUtils;

import com.ewcms.publication.freemarker.directive.page.SkipBaseDirective.GeneratorUrl;
import com.ewcms.publication.uri.UriRuleable;

import freemarker.template.TemplateException;

/**
 * "上一页"跳转
 * 
 * @author wangwei
 */
class SkipPagePrevious implements SkipPageable{

    private static final String DEFAULT_LABEL="上一页";

    @Override
    public PageOut skip(Integer count,Integer number,String label,UriRuleable rule)throws TemplateException{
        
        label = StringUtils.isBlank(label) ? DEFAULT_LABEL : label;
        --number;
        boolean active =(number >= 0);
        int prev = number <= 0 ? 0 : (number);
        GeneratorUrl generatorUrl = new GeneratorUrl(rule,number);
        String   url = generatorUrl.getUriValue(prev);
        
        return new PageOut(count,prev,label,url,active);
    }
}
