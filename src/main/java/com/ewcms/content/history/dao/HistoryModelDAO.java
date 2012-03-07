/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.history.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.history.model.HistoryModel;

/**
 * @author 吴智俊
 */
@Service
public class HistoryModelDAO extends JpaDAO<Long, HistoryModel> {
	public List<HistoryModel> findByHistoryModel(String className,
			String idName, String idValue, Date startDate, Date endDate) {
		String hql = "From HistoryModel h Where h.className=? And h.idName=? And h.idValue=? ";
		if (startDate != null) {
			hql += " And h.createDate>=? ";
		}
		if (endDate != null) {
			hql += " And h.createDate<=? ";
		}
		hql += " Order By h.id DESC";

		List<HistoryModel> list = new ArrayList<HistoryModel>();
		if (startDate != null && endDate != null) {
			list = this.getEntityManager().createQuery(hql, HistoryModel.class).setParameter(1, className).setParameter(2, idName).setParameter(3, idValue).setParameter(4, startDate).setParameter(5, endDate).getResultList();
		} else if (startDate != null && endDate == null) {
			list = this.getEntityManager().createQuery(hql, HistoryModel.class).setParameter(1, className).setParameter(2, idName).setParameter(3, idValue).setParameter(4, startDate).getResultList();
		} else if (startDate == null && endDate != null) {
			list = this.getEntityManager().createQuery(hql, HistoryModel.class).setParameter(1, className).setParameter(2, idName).setParameter(3, idValue).setParameter(4, endDate).getResultList();
		} else {
			list = this.getEntityManager().createQuery(hql, HistoryModel.class).setParameter(1, className).setParameter(2, idName).setParameter(3, idValue).getResultList();
		}
		if (list.isEmpty())
			return new ArrayList<HistoryModel>();
		return list;
	}

	public void deleteHistoryModelByBeforeDate(final Date createDate) {
		String hql = "Delete HistoryModel h Where h.createDate<=?";
		this.getEntityManager().createQuery(hql).setParameter(1, createDate)
				.executeUpdate();
	}
}
