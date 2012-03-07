package com.ewcms.content.document.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.query.cache.CacheResultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.Category;

@Controller
@RequestMapping(value = "/document/category")
public class CategoryController {

	@Autowired
	private DocumentFacable documentFac;
	@Autowired
	protected QueryFactory queryFactory;
	
//	private List<Long> updSelections = new ArrayList<Long>();
	
//	@RequestMapping(value = "/input.do")
//    public String input(@RequestParam(value = "selections", required = false) List<Long> selections, Model model) throws Exception{
//    	if (selections != null && !selections.isEmpty()){
//    		updSelections = selections;
//    		Long categoryId = updSelections.get(0);
//            return "redirect:edit/" + categoryId + ".do";//重定向到@RequestMapping中value的值
//    	}else{
//    		model.addAttribute("category", new Category());
//    		return "document/category/edit";//返回页面
//    	}
//    }  
	
	@RequestMapping(value = "/edit.do",method = RequestMethod.GET)
	public String edit(@RequestParam(value = "selections", required = false) List<Long> selections, Model model) throws Exception{
		Category category = new Category();
		if (selections != null && !selections.isEmpty()){
			Long categoryId = selections.get(0);
			category = documentFac.findCategory(categoryId);
			selections.remove(0);
    	}
		model.addAttribute("selections", selections);
		model.addAttribute("category", category);
		return "document/category/edit";
	}
    
    @RequestMapping(value = "/save.do",method = RequestMethod.POST)
    public String save(@RequestParam(value = "selections", required = false) List<Long> selections, @ModelAttribute("category")Category category, Model model) throws Exception{
    	if(category.getId() == null){
    		documentFac.addCategory(category); 
    	}else{
    		documentFac.updCategory(category);
    	}
    	model.addAttribute("selections", selections);
    	return "redirect:edit.do";
    } 

    
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String delete(@RequestParam("selections") List<Long> selections) throws Exception{
    	for (Long categoryId : selections){
    		documentFac.delCategory(categoryId);
    	}
    	return "document/category/index";
    }
    
	@RequestMapping(value = "/query.do")
	@ResponseBody
	public Map<String, Object> query(@RequestParam(value = "page") int page, @RequestParam(value = "rows") int rows,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "selections", required = false) String selections,
			@RequestParam(value = "cacheKey", required = false) String cacheKey,
			@ModelAttribute(value = "category")Category category) {
		
		page = page - 1;
		
		EntityQueryable query = queryFactory.createEntityQuery(Category.class).setPage(page).setRow(rows);
		
		if (isStringNotEmpty(order)) {
			if (order.equals("asc")) {
				query.orderAsc(sort);
			} else {
				query.orderDesc(sort);
			}
		}
		if (isStringNotEmpty(selections)) {
			String[] selectArr = selections.split(",");
			List<Integer> selectionArr = new ArrayList<Integer>();
			for (String arr : selectArr) {
				selectionArr.add(Integer.valueOf(arr));
			}
			query.in("id", selectionArr);
		}
		if(category != null) {
			if (isNotNull(category.getId())) query.eq("id", category.getId());
			if (isStringNotEmpty(category.getCategoryName())) query.likeAnywhere("categoryName", category.getCategoryName());
		}
		
		CacheResultable result = query.queryCacheResult(cacheKey);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", result.getCount());
		resultMap.put("cacheKey", result.getCacheKey());
		resultMap.put("rows", result.getResultList());
		return resultMap;
	}
}
