<%@ include file="header.jsp" %>
<div class="container-fluid pin-to-center auth-form">
  <h1><fmt:message key="hairdressers.text.title"/></h1>
</div>

<div class="container-fluid">

  <div>
    <div>
      <table>
        <tr>
          <th>
            <fmt:message key="hairdressers.column.name"/>
          </th>
          <th>
            <fmt:message key="hairdressers.column.email"/>
          </th>
          <th>
            <fmt:message key="hairdressers.column.rating"/>
          </th>
          <th>
            <fmt:message key="hairdressers.column.requests"/>
          </th>
        </tr>

        <c:forEach var="hairdresser" items="${sessionScope.hairdressers}">
          <tr>
            <td>${hairdresser.name}</td>
            <td>${hairdresser.email}</td>
            <td>${hairdresser.rating}</td>
            <td>
              <a href="/commands?command=RequestPage&hairdresserId=${hairdresser.id}&lang=${current}" class="h6">
                <fmt:message key="hairdressers.column.requests.button"/>
              </a>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </div>

</div>
<%@ include file="footer.jsp" %>
