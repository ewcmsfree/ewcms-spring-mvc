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
 * 跳转首页
 *
 * @author wangwei
 */
class SkipPageFirst implements SkipPageable{

    private static final String DEFAULT_LABEL="首页";

    @Override
    public PageOut skip(Integer count,Integer number,String label,UriRuleable rule)throws TemplateException{
        
        label = StringUtils.isBlank(label) ? DEFAULT_LABEL : label;
        int first = 0;
        boolean active = (number != first);
        GeneratorUrl generatorUrl = new GeneratorUrl(rule,number);
        String url = generatorUrl.getUriValue(first);
        
        return new PageOut(count,first,label,url,active);
    }
}
