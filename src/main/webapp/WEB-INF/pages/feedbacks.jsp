<%@ include file="header.jsp" %>
<div class="container-fluid pin-to-center auth-form">
  <h1><fmt:message key="feedbacks.text.title"/></h1>
</div>

<div  class="container-fluid">
    <c:set var="amount" value="${fn:length(feedbacks)}"/>
    <c:set var="page" value="${page == null? 1 : page}"/>
    <c:set var="itemsAmount" value="${amount == null? 1 : amount}"/>
    <c:set var="itemsPerPage" value="5"/>
    <c:set var="endPageNumber" value="${itemsAmount % itemsPerPage == 0? itemsAmount / itemsPerPage : itemsAmount / itemsPerPage + 1}"/>
    <div style="min-height: 100%">
      <table class="table table-striped">
        <thead>
        <tr>
          <th><fmt:message key="feedbacks.id"/></th>
          <th><fmt:message key="feedbacks.name"/></th>
          <th><fmt:message key="feedbacks.rate"/></th>
          <th><fmt:message key="feedbacks.request.serviceHairdresser.hairdresser.name"/></th>
          <th><fmt:message key="feedbacks.request.status"/></th>
          <th><fmt:message key="feedbacks.request.client.email"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="feedback" items="${feedbacks}" begin="${(page-1) * itemsPerPage}" end="${page * itemsPerPage - 1}">
          <tr>
            <td>${feedback.id}</td>
            <td>${feedback.name}</td>
            <td>${feedback.rate}</td>
            <td>${feedback.request.serviceHairdresser.hairdresser.name }</td>
            <td>${feedback.request.status}</td>
            <td>${feedback.request.client.email}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>

      <div class="row" style="margin-left: 0; margin-right: 0;">
        <div class="col-xs-6 col-xs-offset-5">
          <nav aria-label="Page navigation example" >
            <ul class="pagination">
              <li class="page-item">
                <a class="page-link" href="/commands?command=FeedbackPage&page=${page > 1? page - 1 : page}&lang=${current}">
                  Previous
                </a>
              </li>

              <c:choose>
                <c:when test="${amount / itemsPerPage <= 10}">
                  <c:forEach var="i"
                             begin="1"
                             end="${endPageNumber}">
                    <li class="page-item"><a class="page-link" href="/commands?command=FeedbackPage&page=${i}&lang=${current}">${i}</a></li>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <li class="page-item"><a class="page-link" href="#">...</a></li>
                </c:otherwise>
              </c:choose>

              <li class="page-item">
                <a class="page-link" href="/commands?command=FeedbackPage&page=${page >= 1 and page < endPageNumber - 1 ? page + 1 : page}&lang=${current}">
                  Next
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>

</div>

<%@ include file="footer.jsp" %>
