<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="main_content" style="position: relative;">
	<div class="main_conent_middle">
		<div class="g_message_publish">
			<div class="title_area">
				<span>Post Blog</span>
			</div>
			<div class="message_content">
				<textarea>
				</textarea>
			</div>
			<div class="message_func">
				<div class="func_item">
					<a id="func_picUpload">
						Image
					</a>
				</div>
				<div class="func_item2" id="publish_btn">
					<a id="publish_btn">
						<span>Send out</span>
					</a>
				</div>
				<div class="func_item2" style="position: relative;" id="publish_limits">
					<a style="padding-left: 80px;" id="publish_setting">
						<span class="publish_value">View Only In Group</span>
						<span class="arrow">
							<em>◆</em>
						</span>
					</a>
					<div class="g-popup-menu" id="publish_menu" style="right: 2px;top: 20px;display: none;">
						<span class="popup-item focus-on" id="publish_menu_class">
							<span class="popup-image" style="background: url(images/base/limit_1.png) no-repeat;"></span>
							View Only In Group
						</span>
						<span class="popup-item focus-on" id="publish_menu_public">
							<span class="popup-image" style="background: url(images/base/limit_2.png) no-repeat;"></span>
							public
						</span>
					</div>
				</div>
				<div style="clear: both">
				</div>
			</div>
		</div>
		<div class="g_navi_min_2">
			<a id="navi_classroom" class="active">
				<img src="images/base/navi_min_classroom.png"/>
				<span>Group </span>
			</a>
			<a id="navi_members">
				<img src="images/base/navi_min_member.png"/>
				<span>Members</span>
			</a>

		</div>
		<div class="content_box" id="content_box">
		</div>					
	</div>
	<div class="main_conent_right" >
		<div class="myclass_avatar_box">
			<a class="class_name">My Group：${GROUP_NAME}</a>
			<a class="class_img" href="class/album/${GROUP_ID}">
				<img src="images/base/class_photo.png">
			</a>
		</div>
		<div id="class_pending_box" style="padding: 16px 0 0 20px; display: none">
			<div>Pending Members</div>
		</div>
	</div>
	<div id="picUploadDiv" style="position: absolute; left:20px; top: 116px; z-index: 1000;display: none;">
	</div>
</div>