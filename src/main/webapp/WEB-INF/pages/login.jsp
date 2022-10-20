<%@ include file="header.jsp" %>
    <div class="container-fluid pin-to-center auth-form">
        <h1><fmt:message key="login.title"/></h1>
    </div>

    <div  class="container-fluid">
        <div class="container-auth">
            <form method="post" action="/commands?command=Login&lang=${current}">

                <div class="form-group">
                    <label for="email"><fmt:message key="login.email"/> </label>
                    <input type="text" name="email" id="email" class="form-control" placeholder="example@example.com">
                </div>

                <div class="form-group">
                    <label for="password"><fmt:message key="login.password"/> </label>
                    <input type="password" name="password" id="password" class="form-control" placeholder="Password">
                </div>

                <button type="submit" class="btn btn-info"><fmt:message key="login.button.signIn"/></button>
            </form>
            <div class="text-danger">${message}</div>
            <br>

            <div>
                <p>
                    <fmt:message key="login.text.registration"/> <a href="/commands?command=Registration&lang=${current}">
                    <fmt:message key="login.button.registration"/></a>.
                </p>
            </div>
        </div>
    </div>

<%@ include file="footer.jsp" %>
