/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.component;


/**
 *当使用callback参数时，elementId参数将失效。
 *
 * @author wangwei
 */
//TODO 是否可以通过统计分析替代
public class CountDirective {

//    private static final Log log = LogFactory.getLog(CountDirective.class);
//
//    private static final String PARAM_ELEMENT_ID_VALUE = "elementId";
//    private static final String PARAM_CALLBACK_VALUE = "callback";
//    private static final String PARAM_URL_VALUE = "url";
//    private static final String DEFAULT_CALLBACK_NAME = "browse_count_callback";
//    private static final String DEFAULT_ELEMENT_ID = "browse_count_element_id";
//    private static final String DEFAULT_URL = "/component/counter";
//    private String elementId;
//    private String callback;
//    private String url;
//
//    public void execute(final Environment env,
//            final Map params, final TemplateModel[] loopVars,
//            final TemplateDirectiveBody body) throws TemplateException, IOException {
//
//        try {
//            elementId = getParamValue(params, PARAM_ELEMENT_ID_VALUE);
//            callback = getParamValue(params, PARAM_CALLBACK_VALUE);
//            url  = getParamValue(params, PARAM_URL_VALUE);
//       } catch (DirectiveException e) {
//            e.render(env.getOut());
//        } catch(Exception e){
//            DirectiveException ex = new DirectiveException(e);
//            ex.render(env.getOut());
//        }
//        super.execute(env, params, loopVars, body);
//    }
//
//    @Override
//    protected String constructOutValue(Article articleRmc) {
//
//        StringBuilder builder = new StringBuilder();
//
//        if (elementId == null && callback == null) {
//            elementId = DEFAULT_ELEMENT_ID;
//            builder.append("<span id='").append(DEFAULT_ELEMENT_ID).append("'></span>");
//        }
//
//        if (callback == null) {
//            callback = DEFAULT_CALLBACK_NAME;
//            builder.append(createDefaultCallBackFunction(elementId));
//        }
//
//        String src = getSrc(articleRmc, callback);
//        builder.append("<script type='text/javascript' src='").append(src).append("'>");
//        builder.append("</script>");
//        return builder.toString();
//    }
//
//    private String createDefaultCallBackFunction(final String elementId) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("<script type=\"text/javascript\">\n");
//        builder.append("function ").append(DEFAULT_CALLBACK_NAME).append("(count){\n");
//        builder.append("document.getElementById('").append(elementId).append("').innerHTML = count;\n");
//        builder.append("}\n");
//        builder.append("</script>\n");
//
//        return builder.toString();
//    }
//
//    private String getParamValue(final Map params, final String name) throws TemplateModelException, DirectiveException {
//        return FreemarkerUtil.getString(params, name);
//    }
//
//    private String getSrc(Article articleRmc, final String callback) {
//        url = (url== null ? DEFAULT_URL : url);
//        int id = articleRmc.getId().intValue();
//        return String.format("%s?article_id=%d&callback=%s", url,id,callback);
//    }
//
//    @Override
//    protected String defaultVariable() {
//        return GlobalVariable.Article.toString();
//    }
}
