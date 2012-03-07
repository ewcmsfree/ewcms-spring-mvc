<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>站点切换</title>
        <script type="text/javascript" src='<c:url value="/resources/page/siteswitch.js"/>'></script>
		<script type="text/javascript">
    		var _siteSwitch = new SiteSwitch({
    		    queryUrl:'<c:url action="siteQuery"/>',
    		    switchUrl:'<c:url action="index"/>'
    		});
    		
    		$(function(){
    		    _siteSwitch.init();
    		});
    		
    		function pageSubmit(){
    		    _siteSwitch.switchSite();
    		}
		</script>
	</head>
	<body>
        <table id="tt"></table>
        <input type="hidden" name="siteId"/>
	</body>
</html>