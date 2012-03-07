/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.acls.model.Permission;

import com.ewcms.core.site.ChannelNode;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.web.vo.TreeNode;

/**
 * @author 周冬初
 * 
 */
public class TreeNodeConvert {
	public static List<TreeNode> channelNodeConvert(List<ChannelNode> cnList) {
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (cnList == null)
			return tnList;
		for (ChannelNode vo : cnList) {
			TreeNode tnVo = new TreeNode();
			tnVo.setId(vo.getId().toString());
			tnVo.setText(vo.getName());
			if (vo.isChildren()) {
				tnVo.setState("closed");
			} else {
				tnVo.setState("open");
			}
			Map<String, String> attributes = new HashMap<String, String>();
			int max = treeNodePermission(attributes, vo.getPermissions());
			tnVo.setAttributes(attributes);
			switch(max){
				case 1:tnVo.setIconCls("icon-table-refresh");break;
				case 2:tnVo.setIconCls("icon-table-edit");break;
				case 4:tnVo.setIconCls("icon-table-pub");break;
				case 8:tnVo.setIconCls("icon-note-add");break;
				case 16:tnVo.setIconCls("icon-note-edit");break;
				case 32:tnVo.setIconCls("icon-note-delete");break;
				case 64:tnVo.setIconCls("icon-note");break;
				default:tnVo.setIconCls("icon-note-error");
			}
			tnList.add(tnVo);
		}
		return tnList;
	}

	public static int treeNodePermission(Map<String, String> attributes,
			Set<Permission> permissions) {
		String permission = "";
		int max = -1;
		if (permissions != null &&!permissions.isEmpty()) {
			for (Permission pm : permissions) {
				if (pm.getMask() > max)
					max = pm.getMask();
				permission += pm.getMask() + ",";
			}
			permission = permission.substring(0, permission.length() - 2);
		}

		attributes.put("permission", permission);
		attributes.put("maxpermission", String.valueOf(max));
		return max;
	}

	public static List<TreeNode> templateConvert(List<Template> tplList) {
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (tplList == null)
			return tnList;
		for (Template vo : tplList) {
			TreeNode tnVo = new TreeNode();
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("path", vo.getPath());
			tnVo.setAttributes(attributes);
			tnVo.setId(vo.getId().toString());
			tnVo.setText(vo.getName());
			if (vo.getTemplateEntity() == null) {
				if (vo.hasChildren()) {
					tnVo.setState("closed");
				} else {
					tnVo.setState("open");
				}
			} else {
				tnVo.setState("open");
				tnVo.setIconCls("");
			}
			tnList.add(tnVo);
		}
		return tnList;
	}

	public static List<TreeNode> templateSourceConvert(
			List<TemplateSource> srcList) {
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (srcList == null)
			return tnList;
		for (TemplateSource vo : srcList) {
			TreeNode tnVo = new TreeNode();
			tnVo.setId(vo.getId().toString());
			tnVo.setText(vo.getName());
			if (vo.getSourceEntity() == null) {
				if (vo.hasChildren()) {
					tnVo.setState("closed");
				} else {
					tnVo.setState("open");
				}
			} else {
				tnVo.setState("open");
				tnVo.setIconCls("");
			}
			tnList.add(tnVo);
		}
		return tnList;
	}
}
