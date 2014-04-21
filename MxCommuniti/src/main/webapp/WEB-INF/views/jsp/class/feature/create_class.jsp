<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form  action="group/create.do" method="post">
	<table>
		<tr>
			<td>
			${create_group_error}
			</td>
		</tr>
		<tr>
			<td>
				Type：
			</td>
			<td>
				<input type="radio" checked="checked" name="groupType" value="3"/>Music
			</td>
		</tr>

		<tr>
			<td>
				Created Date：
			</td>
			<td>
				<select id="year" name="groupYear">
				</select>
			</td>
		</tr>
		<tr>
			<td>
				Group Name：
			</td>
			<td>
				<input type="text" name="groupName"/>
			</td>
		</tr>
		<tr>
			<td>
				<input type="submit" name="SUBMIT"/>
			</td>
		</tr>
	</table>
</form>
<%@include file="../../dialog/location_selector.jsp" %>