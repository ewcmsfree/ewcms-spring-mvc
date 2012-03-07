/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.deploy.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.publication.deploy.DeployOperatorable;

/**
 * 发布指定的资源到ftp服务器上
 * 
 * @author wangwei
 */
public class FtpDeployOperator extends DeployOperatorBase{
    
    private static final Logger logger = LoggerFactory.getLogger(FtpDeployOperator.class);
    protected static final String DEFAULT_PORT = "21";
    
    public static class Builder extends BuilderBase{
        
        public Builder(String hostname,String path){
            this.hostname = hostname;
            this.path = path;
        }
        
        @Override
        public DeployOperatorable build() {
            return new FtpDeployOperator(this);
        }
    }
    
    public FtpDeployOperator(Builder builder) {
        super(builder);
    }
    
    @Override
    protected FileObject getRootFileObject(FileSystemOptions opts,BuilderBase builder,FileSystemManager manager) throws FileSystemException {
        
        FtpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false);
        
        String port = StringUtils.isBlank(builder.getPort()) ? DEFAULT_PORT :builder.getPort();
        
        StringBuilder connBuilder = new StringBuilder();
        connBuilder.append("ftp://");
        connBuilder.append(builder.getHostname()).append(":").append(port);
        connBuilder.append(":").append(builder.getPath());
        
        String conn = connBuilder.toString();
        logger.debug("uri is  {}",conn);
        
        return manager.resolveFile(conn, opts);
    }
}
