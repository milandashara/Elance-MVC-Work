<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.coe.mxcommunity.account.entity.User"%>

<% User currentUser = (User)session.getAttribute("GroupUser"); %>
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script>
$(window.parent.document).find("#avatar_180").attr("src", "<%=currentUser.getBigAvatar().replace("\\", "\\\\")%>");
$(window.parent.document).find("#avatar_50").attr("src", "<%=currentUser.getSmallAvatar().replace("\\", "\\\\")%>");
</script>