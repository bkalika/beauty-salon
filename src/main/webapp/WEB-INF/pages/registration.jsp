<%@ include file="header.jsp" %>

    <div class="container-fluid pin-to-center auth-form">
        <h1><fmt:message key="registration.title"/></h1>
    </div>

    <div  class="container-fluid">
        <div class="container-auth">
            <form method="post" action="/commands?command=Registration&lang=${current}">

                <div class="form-group">
                    <label for="name"><fmt:message key="registration.name"/> </label>
                    <input type="text" name="name" id="name" class="form-control" placeholder="Boris Johnson">
                </div>

                <div class="form-group">
                    <label for="email"><fmt:message key="registration.email"/> </label>
                    <input type="text" name="email" id="email" class="form-control" placeholder="example@example.com">
                </div>

                <div class="form-group">
                    <label for="password"><fmt:message key="registration.password"/> </label>
                    <input type="password" name="password" id="password" class="form-control" placeholder="Password">
                </div>

                <div class="form-group">
                    <label for="password"><fmt:message key="registration.passwordRepeat"/> </label>
                    <input type="password" name="passwordConfirm" id="passwordConfirm" class="form-control" placeholder="Password Confirmation">
                </div>

                <div class="form-group">
                    <p><fmt:message key="registration.role.client"/></p>
                    <input name="role_id" type="radio" value="2"/>
                    <p><fmt:message key="registration.role.hairdresser"/></p>
                    <input name="role_id" type="radio" value="3"/>
                </div>

                <button type="submit" class="btn btn-info"><fmt:message key="registration.button.signUp"/></button>
            </form><br>
            <div class="text-danger">${message}</div>
            <div>
                <p>
                    <fmt:message key="registration.text.signIn"/> <a href="/commands?command=Login&lang=${current}"><fmt:message key="login.button.signIn"/></a>.
                </p>
            </div>
        </div>
    </div>

<%--<%@ include file="footer.jsp" %>--%>
<t:colontitle/>

