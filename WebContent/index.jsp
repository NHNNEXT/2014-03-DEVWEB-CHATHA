<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${empty sessionScope.user}">
		<c:redirect url="/support/signin" />
	</c:when>
	<c:otherwise>
		<c:redirect url="/support/userinfo" />
	</c:otherwise>
</c:choose>