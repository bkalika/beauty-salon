<%--html % page style took here: https://freefrontend.com/bootstrap-sidebars/--%>
<%--https://stackoverflow.com/questions/49861851/passing-drop-down-value-from-jsp-as-parameter-to-rest-controller--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<c:set var="current" value="${param.lang}" scope="session"/>

<c:choose>
    <c:when test="${not empty current}">
        <fmt:setLocale value="${current}" scope="session"/>
    </c:when>
    <c:otherwise>
        <c:set var="current" value="en" scope="session"/>
    </c:otherwise>
</c:choose>

<c:if test="${sessionScope.lastCommand == null}">
    <c:set var="lastCommand" value="ServicePage" scope="session"/>
</c:if>

<fmt:setBundle basename="message" scope="session"/>

<!DOCTYPE html>
<html lang="en" >
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css'>
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/material-design-iconic-font/2.2.0/css/material-design-iconic-font.min.css'><link rel="stylesheet" href="./style.css">

    <link href="cssfiles/styles.css" rel="stylesheet" type="text/css">
    <title>${sessionScope.lastCommand}</title>

    <script>
        // Use Javascript
        let today = new Date();
        let dd = today.getDate();
        let mm = today.getMonth() + 1; //January is 0 so need to add 1 to make it 1!
        const yyyy = today.getFullYear();
        const timezone = today.getTimezoneOffset(); // /60 * (-1);
        if(dd<10){
            dd='0'+dd
        }
        if(mm<10){
            mm='0'+mm
        }

        today = yyyy+'-'+mm+'-'+dd+''+timezone;
        document.getElementById("datefield").setAttribute("min", today);
        document.getElementById("datefield").setAttribute("max", today+14);
    </script>
</head>
<body>
<!-- partial:index.partial.html -->
<div id="viewport">
    <!-- Sidebar -->
    <div id="sidebar">
        <header>
            <a href="/commands?command=ServicePage&lang=${current}">Beauty Salon</a>
        </header>
        <ul class="nav">
            <li>
                <a href="/commands?command=ServicePage&lang=${current}">
                    <i class="zmdi zmdi-calendar"></i>
                    <fmt:message key="header.button.services"/>
                </a>
            </li>
            <c:if test="${sessionScope.role != null}">
                <li>
                    <a href="/commands?command=RequestPage&lang=${current}">
                        <i class="zmdi zmdi-link"></i>
                        <fmt:message key="header.button.requests"/>
                    </a>
                </li>
                <c:if test="${sessionScope.user.role.name().equals('ADMIN')}">
                    <li>
                        <a href="/commands?command=Clients&lang=${current}">
                            <i class="zmdi zmdi-link"></i>
                            <fmt:message key="header.button.clients"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user.role.name().equals('ADMIN')}">
                    <li>
                        <a href="/commands?command=Hairdressers&lang=${current}">
                            <i class="zmdi zmdi-link"></i>
                            <fmt:message key="header.button.hairdressers"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user.role.name().equals('ADMIN')}">
                    <li>
                        <a href="/commands?command=FeedbackPage&lang=${current}">
                            <i class="zmdi zmdi-comment-more"></i>
                            <fmt:message key="header.button.feedbacks"/>
                        </a>
                    </li>
                </c:if>
<%--                <li>--%>
<%--                    <a href="#">--%>
<%--                        <i class="zmdi zmdi-info-outline"></i> About--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li>--%>
<%--                    <a href="#">--%>
<%--                        <i class="zmdi zmdi-settings"></i> Services--%>
<%--                    </a>--%>
<%--                </li>--%>
            </c:if>
        </ul>
    </div>
    <!-- Content -->
    <div id="content">

        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="#"><i class="zmdi zmdi-notifications text-danger"></i>
                        </a>
                    </li>
                    <li>
                        <a class="nav-link" href="commands?command=${sessionScope.lastCommand}&lang=en">Eng</a>
                    </li>
                    <li>
                        <a class="nav-link" href="commands?command=${sessionScope.lastCommand}&lang=ua">Ua</a>
                    </li>

                    <c:choose>
                        <c:when test="${sessionScope.user != null}">
                            <li><a>${sessionScope.user.name}</a></li>
                            <li><a href="/commands?command=Logout&lang=${current}"><fmt:message key="header.button.signOut"/></a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="/commands?command=Registration&lang=${current}"><fmt:message key="header.button.signUp"/></a></li>
                            <li><a href="/commands?command=Login&lang=${current}"><fmt:message key="header.button.signIn"/></a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </nav>
