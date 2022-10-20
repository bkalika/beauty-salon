<%@ include file="header.jsp" %>
    <div class="container-fluid pin-to-center auth-form">
        <h1><fmt:message key="service.text.title"/></h1>
    </div>

    <div class="container-fluid">
        <div>
            <p><fmt:message key="service.text.paragraph"/></p>
        </div>
        <div class="text-danger">${message}</div>

        <div>
            <div>
                <table>
                    <tr>
                        <th>
                            <fmt:message key="service.column.name"/>
                            <a href="/commands?command=ServicePage&sort=serviceName&lang=${current}" class="h6">
                                <fmt:message key="service.column.name.sort"/>
                            </a>

                            <div>
                                <select name="command=ServicePage&serviceName" form="service-name">
                                    <c:forEach var="service" items="${services}">
                                        <option value="${service.name}&lang=${current}"
                                            ${service.name == serviceName ? 'selected="selected"' : ''}>${service.name}
                                        </option>
                                    </c:forEach>
                                </select>
                                <form method="get" action="/commands" id="service-name" accept-charset="UTF-8">
                                    <input type="submit" value="Filter">
                                </form>
                            </div>
                        </th>
                        <th>
                            <fmt:message key="service.column.price"/>
                            <a href="/commands?command=ServicePage&sort=servicePrice&lang=${current}" class="h6">
                                <fmt:message key="service.column.price.sort"/>
                            </a>
                        </th>
                        <th>
                            <fmt:message key="service.column.hairdresser.name"/>
                            <a href="/commands?command=ServicePage&sort=hairdresserName&lang=${current}" class="h6">
                                <fmt:message key="service.column.hairdresser.name.sort"/>
                            </a>

                            <div>
                                <select name="command=ServicePage&hairdresserName" form="hairdresser-name">
                                    <c:forEach var="hairdresser" items="${hairdressers}">
                                        <option value="${hairdresser.name}&lang=${current}"
                                            ${hairdresser.name == hairdresserName ? 'selected="selected"' : ''}>${hairdresser.name}</option>
                                    </c:forEach>
                                </select>
                                <form method="get" action="/commands" id="hairdresser-name" accept-charset="UTF-8">
                                    <input type="submit" value="Filter">
                                </form>
                            </div>
                        </th>
                        <th>
                            <fmt:message key="service.column.hairdresser.rating"/>
                            <a href="/commands?command=ServicePage&sort=hairdresserRating&lang=${current}" class="h6">
                                <fmt:message key="service.column.hairdresser.rating.sort"/>
                            </a>
                        </th>
                        <c:if test="${sessionScope.user.role.name().equals('CUSTOMER')}">
                            <th><fmt:message key="service.column.reservation"/></th>
                        </c:if>
                    </tr>

                    <c:forEach var="sh" items="${sessionScope.serviceHairdressers}">
                        <tr>
                            <td>${sh.service.name}</td>
                            <td>${sh.service.price}</td>
                            <td>${sh.hairdresser.name}</td>
                            <td>${sh.hairdresser.rating}</td>
                            <c:if test="${sessionScope.user.role.name().equals('CUSTOMER')}">
                                <td>
                                    <form method="POST" action="/commands?command=AddRequest&serviceHairdresserId=${sh.id}">
                                        <div>
                                            <label>
                                                <input id="datefield" name="date" type="datetime-local" min="datefield" max="datefield" required />
                                            </label>
                                        </div>
                                        <button type="submit" class="btn btn-info">
                                            <fmt:message key="service.column.reservation.reserve"/>
                                        </button>
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <br>
            <c:if test="${sessionScope.user == null}">
                <div>
                    <p>
                        <a href="/commands?command=Login&lang=${current}"><fmt:message key="login.button.signIn"/></a> <fmt:message key="service.text.paragraph.or"/>
                        <a href="/commands?command=Registration&lang=${current}"><fmt:message key="registration.button.signUp"/></a>
                        <fmt:message key="service.text.paragraph.makereservation"/>.
                    </p>
                </div>
            </c:if>
        </div>
    </div>
<%@ include file="footer.jsp" %>
