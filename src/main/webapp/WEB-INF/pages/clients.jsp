<%@ include file="header.jsp" %>
<div class="container-fluid pin-to-center auth-form">
    <h1><fmt:message key="clients.text.title"/></h1>
</div>

<div class="container-fluid">

    <div>
        <div>
            <table>
                <tr>
                    <th>
                        <fmt:message key="clients.column.name"/>
                    </th>
                    <th>
                        <fmt:message key="clients.column.email"/>
                    </th>
                    <th>
                        <fmt:message key="clients.column.requests"/>
                    </th>
                </tr>

                <c:forEach var="client" items="${sessionScope.clients}">
                    <tr>
                        <td>${client.name}</td>
                        <td>${client.email}</td>
                        <td>
                            <a href="/commands?command=RequestPage&clientId=${client.id}&lang=${current}" class="h6">
                                <fmt:message key="clients.column.requests.button"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

</div>
<%@ include file="footer.jsp" %>
