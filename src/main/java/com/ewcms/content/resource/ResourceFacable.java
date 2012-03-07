/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.Resource.Type;
import com.ewcms.core.site.model.Site;

/**
 * 资源管理接口
 *
 * @author wangwei
 */
public interface ResourceFacable {
    
    /**
     * 上传资源资源
     * 
     * @param file      资源文件
     * @param fullName  文件名（可以是路径）
     * @param type      资源类型
     * @return          资源对象
     * @throws IOException
     */
    Resource uploadResource(File file,String fullName,Resource.Type type)throws IOException;

    /**
     * 上传资源(网络采集器使用/报表资源使用)
     * 
     * @param site 站点对象
     * @param file 资源文件
     * @param path 资源类型
     * @param type 资源对象
     * @return
     * @throws IOException
     */
    Resource uploadResource(Site site, File file, String path, Type type) throws IOException;
    /**
     * 更新资源
     * 
     * @param id        资源编号
     * @param file      资源文件
     * @param fullName  文件名（可以是路径）
     * @param type      资源类型
     * @return          资源对象
     * @throws IOException
     */
    Resource updateResource(Integer id,File file,String fullName,Resource.Type type)throws IOException;
    
    /**
     * 更新资源的引导图
     * 
     * @param id 资源编号
     * @param file 引导图文件
     * @param fullName 引导图文件名
     * @return
     * @throws IOException
     */
    Resource updateThumbResource(Integer id,File file,String fullName)throws IOException;
    
    /**
     * 保存资源
     * <br>
     * 保存后的资源状态为正常状态（state = NORMAL）
     * 
     * @param descriptions 资源描述集合(key:资源编号,value:描述)
     * @return
     */
    List<Resource> saveResource(Map<Integer,String> descriptions);
    
    /**
     * 删除资源
     * 
     * @param ids 资源编号数组
     */
    void deleteResource(int[] ids);

    /**
     * 得到资源
     *
     * @param id 资源编号
     * @return
     */
    Resource getResource(Integer id);

    /**
     * 软删除资源
     * <br>
     * 资源并没有真正删除，只是标识为删除
     *
     * @param ids 资源编号数组
     */
    void softDeleteResource(int[] ids);
    
    /**
     * 清空状态为DELETE的记录
     */
    void clearSoftDeleteResource();
    
    /**
     * 恢复删除资源
     * <br>
     * 只有软删除资源才能恢复
     * 
     * @param ids 资源编号数组
     */
    void revertResource(int[] ids);

    /**
     * 更新资源描述信息
     * 
     * @param id 
     *         资源编号
     * @param description
     *         资源描述信息
     */
    Resource updateDescriptionOfResource(Integer id,String description);
}
