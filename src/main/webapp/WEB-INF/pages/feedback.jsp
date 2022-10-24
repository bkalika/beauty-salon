<%@ include file="header.jsp" %>
<div class="container-fluid pin-to-center auth-form">
<h1><fmt:message key="feedback.text.title"/></h1>
</div>

<div  class="container-fluid">

    <c:choose>
        <c:when test="${sessionScope.feedback != null}">
            <div style="min-height: 100%">
                <p>RequestId: ${sessionScope.request.id}</p>
                <p><fmt:message key="feedback.request.serviceHairdresser.service.name"/>: ${sessionScope.feedback.request.serviceHairdresser.service.name}</p>
                <p><fmt:message key="feedback.request.serviceHairdresser.hairdresser.name"/>: ${sessionScope.feedback.request.serviceHairdresser.hairdresser.name}</p>
                <p><fmt:message key="feedback.name"/>: ${sessionScope.feedback.name}</p>
                <p><fmt:message key="feedback.rate"/>: ${sessionScope.feedback.rate}</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row" style="width: 55%">
                <div class="col-xs-8 col-xs-offset-7">
                    <form method="POST" action="/commands?command=Feedback&requestId=${sessionScope.request.id}&lang=${current}">
<%--                        <input type="hidden" name="requestId" value="111">--%>
                        <div class="form-group">
                            <label for="req"><fmt:message key="feedback.request.serviceHairdresser.service.name"/>: </label>
                            <input class="form-control" id="req" value="${sessionScope.request.serviceHairdresser.service.name}" disabled>
                        </div>
                        <div class="form-group">
                            <label for="master"><fmt:message key="feedback.request.serviceHairdresser.hairdresser.name"/>: </label>
                            <input class="form-control" id="master" value="${sessionScope.request.serviceHairdresser.hairdresser.name}" disabled>
                        </div>
                        <div class="form-group">
                            <label for="rate"><fmt:message key="feedback.rate"/></label>
                            <select id="rate" name="rate" class="form-control">
                                <option value="1"> 1 </option>
                                <option value="2"> 2 </option>
                                <option value="3"> 3 </option>
                                <option value="4" selected> 4 </option>
                                <option value="5"> 5 </option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="name"><fmt:message key="feedback.name"/></label>
                            <textarea name="name" id="name" class="form-control" rows="7"></textarea>
                        </div>
                        <button type="submit" class="btn btn-info"><fmt:message key="feedback.button.create"/></button>
                    </form>
                </div>
                <div>${message}</div>
            </div>
        </c:otherwise>
    </c:choose>

</div>

<%--<%@ include file="footer.jsp" %>--%>
<t:colontitle/>
