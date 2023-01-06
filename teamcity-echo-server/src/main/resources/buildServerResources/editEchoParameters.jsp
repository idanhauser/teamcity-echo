<%@ page import="jetbrains.teamcity.EchoConstants" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<c:set var="messageId" value="<%EchoConstants.MESSAGE_KEY%>"/>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean" %>

<l:settingsGroup title="My Runner settings">
    <tr>
        <th><label for="${messageId}">Message: <l:star/></label></th>
        <td>
            <div class="posRel">
                <props:textProperty name="${messageId}" size="56" maxlength="100"/>
            </div>
        </td>
    </tr>

</l:settingsGroup>