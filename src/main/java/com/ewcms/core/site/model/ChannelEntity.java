/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author 周冬初
 *
 */
@Entity
@Table(name = "site_channelentity")
@SequenceGenerator(name = "seq_site_channelentity", sequenceName = "seq_site_channelentity_id", allocationSize = 1)
public class ChannelEntity implements Serializable {

	private static final long serialVersionUID = 4288841351358330546L;

	@Id
    @GeneratedValue(generator = "seq_site_channelentity", strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @Column()
    private byte[] iconEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getIconEntity() {
		return iconEntity;
	}

	public void setIconEntity(byte[] iconEntity) {
		this.iconEntity = iconEntity;
	}    
}
