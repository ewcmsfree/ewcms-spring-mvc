<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <!-- 跳出Iframe，显示登录界面 -->
    <script type="text/javascript">
        if(parent != self) {
            top.location='<c:url value="/login.do"/>';
        }
    </script>
    <head>
        <title>Ewcms用户登录</title>
        <script type="text/javascript" src='<c:url value="/resources/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/page/login.js"/>'></script>
        <!-- <link rel="stylesheet" type="text/css" href='<c:url value="/resources/page/login.css"/>'/>-->
        <link href='<c:url value="/resources/page/login/user_login.css"/>'  type="text/css" rel="stylesheet"/>
        <script type="text/javascript">
            $(function() {
                var _login = new login('<c:url value = "/checkcode.jpg"/>');
                _login.init();
            });
        </script>
    </head>
    <body id="userlogin_body">
    	<div></div>
		<div id="user_login">
			<dl>
		  		<dd id="user_top">
			  		<ul>
			    		<li class="user_top_l"></li>
			    		<li class="user_top_c"></li>
			    		<li class="user_top_r"></li>
			    	</ul>
		    	</dd>
		    	<form action="/j_spring_security_check" method="post">
		  		<dd id="user_main">
			  		<ul>
			    		<li class="user_main_l"></li>
			    		<li class="user_main_c">
			    			<div class="user_main_box">
			    				<ul>
			      					<li class="user_main_text">用户名：</li>
			      					<%
			      						String username=(String)session.getAttribute("j_username");
										if(username== null){
                                			username="";
                            			}
                            		%>
			      					<li class="user_main_input"><input class="txtusernamecssclass" type="text" name="j_username" value="<%=username%>"/></li>
			      				</ul>
			    				<ul>
			      					<li class="user_main_text">密&nbsp;&nbsp;&nbsp;&nbsp;码：</li>
			      					<li class="user_main_input"><input class="txtpasswordcssclass" type="password" name="j_password"/></li>
			      				</ul>
			    				<ul>
			      					<li class="user_main_text">验证码：</li>
			      					<li class="user_main_input">
                            			<input class="txtvalidatecodecssclass" type="text" name="j_checkcode"/>
			        				</li>
                            		<img id="id_checkcode" width="65px" height="20px" src="<c:url value="/checkcode.jpg"/>" alt="checkcode.jpg" title="看不清,换一张" style="padding-left: 5px;" />
			        			</ul>
			    				<ul>
			      					<li class="user_main_text">记&nbsp;&nbsp;&nbsp;&nbsp;住：</li>
			      					<li class="user_main_input">
			      						<input type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me" value="false"/>
			        				</li>
			        			</ul>
			        			<ul>
									<li class="user_main_input">
			                            <span class="error">
			                                <%
			                                String error = request.getParameter("error");
			                                AuthenticationException authException = null;
			                                if (error != null) {
			                                    authException = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			                                    if (authException == null) {
			                                        authException = (AuthenticationException) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			                                    }
			                                }
			                                String errorMsg = "";
			                                if (authException != null) {
			                                    errorMsg = authException.getMessage()+"。";
			                                }
			                                %>
			                                <%=errorMsg%>
			                            </span>			 
	                            	</li>       			
			        			</ul>
			        		</div>
			        	</li>
			    		<li class="user_main_r">
			    			<input class="ibtnentercssclass" id="ibtnenter" style="border-top-width: 0px; border-left-width: 0px; border-bottom-width: 0px; border-right-width: 0px" type="image" src='<c:url value="/resources/image/login/user_botton.gif"/>' name="ibtnenter"/> 
			    		</li>
			    	</ul>
		    	</dd>
		    	</form>
			    <dd id="user_bottom">
				  	<ul>
				    	<li class="user_bottom_l"></li>
				    	<li class="user_bottom_c"></li>
					    <li class="user_bottom_r"></li>
					</ul>
				</dd>
			</dl>
		</div>
     </body>
</html>