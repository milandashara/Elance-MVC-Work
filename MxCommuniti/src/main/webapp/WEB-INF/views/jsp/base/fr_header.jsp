<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.coe.mxcommunity.account.entity.User, com.coe.mxcommunity.mygroup.entity.*,com.coe.mxcommunity.mygroup.service.*,
	java.util.*"%>
    
<%@include file="fr_header_s.jsp" %>
		<div class="main_left">
			<div class="navi_common">
				<div style="width: 100%; padding:0 20px 20px 20px;">
					<% User currentUser = (User)session.getAttribute("GroupUser"); %>
					<a href="profile" style="display: block; height: 60px; width: 60px; float: left">
						<img id="g_dom_user_avatar_img" style="float: left;width: 60px; height: 60px;" src="<%=currentUser.getBigAvatar()%>">
					</a>
					<div style="margin:0 0 0 65px; padding: 5px 0 0 0;">
						<a id="g_dom_user_name_a" style="font-size: 12px;font-weight: bold;text-decoration: none;color: #000;" href="profile"><%=currentUser.getName()%></a>
						<p>8</p>
					</div>
					<div style="clear: both;"></div>
				</div>
				<div class="navi_box_1">
					<a class="N_text" href="home" id="navi_map">
						<i class="N_icon icon_home"></i>
						Map
					</a>
				</div>
				<div class="navi_box_1">
					<a class="N_text" href="mymessage" id="navi_message">
						<i class="N_icon icon_msg"></i>
					    Message
					</a>
				</div>
			</div>
			<div class="fr-navi-left-section">
				<div class="section-title">
					<h3>my group</h3>
				</div>
				<div class="section-content">
					<%
						List<MyGroup> groupList = MyGroupService.getGroupListByUserCode(currentUser.getCode(), false);
								if (groupList.size() > 0){
									MyGroup myClass;
									for (int i = 0; i < groupList.size(); i++){		  
										myClass = groupList.get(i);
					%>
							   	<a class="section-item" href="class/<%=myClass.getCode()%>" id="navi_class_<%=myClass.getCode()%>">
									<img class="section-item-icon" src="images/base/navi_left_class.png">
									<%=myClass.getName()%>
								</a>
							 <%
					   	}
				   	}else{
						%>
						   	<a class="section-item" href="group/demo/1" id="navi_class_demo_1">
								<img class="section-item-icon" src="images/base/navi_left_class.png">
								my class
							</a>
						 <%		   			
				   	}
					%>
				</div>
			</div>

		</div>
		<script type="text/javascript">
			$("#${NAVI_FOCUS}").addGroup("N_active");
		</script>
		<div class="main_right">