<%@ include file="header.jsp" %>
    <div class="container-fluid pin-to-center auth-form">
        <h1>
            <c:choose>
                <c:when test="${sessionScope.user.role.name().equals('ADMIN')}">
                    <fmt:message key="request.text.title.admin"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="request.text.title"/>
                </c:otherwise>
            </c:choose>
        </h1>
    </div>

    <div  class="container-fluid">
        <div>
            <table>
                <tr>
                    <th><fmt:message key="request.column.date"/></th>
                    <th><fmt:message key="request.column.service.name"/></th>
                    <th><fmt:message key="request.column.service.price"/></th>
                    <c:if test="${!sessionScope.user.role.name().equals('HAIRDRESSER')}">
                        <th><fmt:message key="request.column.hairdresser.name"/></th>
                        <th><fmt:message key="request.column.hairdresser.rating"/></th>
                    </c:if>
                    <th><fmt:message key="request.column.status"/></th>
                    <th><fmt:message key="request.column.action"/></th>
                    <c:if test="${sessionScope.user.role.name().equals('CUSTOMER')}">
                        <th><fmt:message key="request.column.feedback"/></th>
                    </c:if>
                </tr>

                <c:forEach var="request" items="${sessionScope.requests}">
                    <tr>
                        <td>${request.date}

                        <c:if test="${sessionScope.user.role.name().equals('ADMIN') and (request.status.name().equals('ACTIVE'))}">
                            <form method="POST" action="/commands?command=StatusChange&requestId=${request.id}&lang=${current}">
                                <input type="hidden" name="clientId" value="${request.client.id}">
                                <input type="hidden" name="lang" value="${current}">
                                <div>
                                    <label>
                                        <input id="datefield" name="date" type="datetime-local" min="datefield" max="datefield" required />
                                    </label>
                                </div>
                                <button type="submit" class="btn btn-info">
                                    <fmt:message key="request.column.date.change"/>
                                </button>
                            </form>
                        </c:if>

                        </td>
                        <td>${request.serviceHairdresser.service.name}</td>
                        <td>
                            ${request.serviceHairdresser.service.price}
                            <div>
                                <c:choose>
                                    <c:when test="${(sessionScope.user.role.name().equals('ADMIN')) and (request.status.name().equals('DONE') and (request.paid != true))}">
                                        <form method="POST" action="/commands?command=StatusChange&requestId=${request.id}&lang=${current}">
                                            <input type="hidden" name="requestStatus" value="paid">
                                            <button type="submit" class="btn btn-info">
                                                <fmt:message key="request.column.action.button.pay"/>
                                            </button>
                                        </form>
                                    </c:when>
                                    <c:when test="${(sessionScope.user.role.name().equals('ADMIN'))}">
                                        Was paid: ${request.paid}
                                    </c:when>
                                </c:choose>
                            </div>
                        </td>
                        <c:if test="${!sessionScope.user.role.name().equals('HAIRDRESSER')}">
                            <td>${request.serviceHairdresser.hairdresser.name}</td>
                            <td>${request.serviceHairdresser.hairdresser.rating}</td>
                        </c:if>
                        <td>${request.status}</td>
                        <td>
                            <c:choose>
                                <c:when test="${(sessionScope.user.role.name().equals('HAIRDRESSER')) and (request.status.name().equals('ACTIVE'))}">
                                    <form method="POST" action="/commands?command=StatusChange&requestId=${request.id}&lang=${current}">
                                        <input type="hidden" name="requestStatus" value="done">
                                        <button type="submit" class="btn btn-info">
                                            <fmt:message key="request.column.action.button.done"/>
                                        </button>
                                    </form>
                                </c:when>
                                <c:when test="${((sessionScope.user.role.name().equals('ADMIN')) || (sessionScope.user.role.name().equals('CUSTOMER'))) and (request.status.name().equals('ACTIVE'))}">
                                    <form method="POST" action="/commands?command=StatusChange&requestId=${request.id}&lang=${current}">
                                        <input type="hidden" name="requestStatus" value="cancel">
                                        <button type="submit" class="btn btn-info">
                                            <fmt:message key="request.column.action.button.cancel"/>
                                        </button>
                                    </form>
                                </c:when>
                            </c:choose>
                        </td>

                        <c:if test="${(sessionScope.user.role.name().equals('CUSTOMER')) and (request.status.name().equals('DONE'))}">
                            <td>
                                <a href="/commands?command=Feedback&requestId=${request.id}&lang=${current}" class="h6">
                                    <fmt:message key="request.column.feedback.button"/>
                                </a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

<%@ include file="footer.jsp" %>
