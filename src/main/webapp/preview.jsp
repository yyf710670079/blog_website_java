<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.commonmark.node.*"%>
<%@ page import="org.commonmark.parser.Parser"%>
<%@ page import="org.commonmark.renderer.html.HtmlRenderer"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Preview Post</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

</head>
<body style="padding-top: 90px">

        <div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <ul class="nav navbar-nav">
                    <li><a>&nbsp;&nbsp;</a></li>
                    <li class="active"><a>Preview Page</a></li>
                    <li><a>Yufeng Yang</a></li>
                    <li><a>305268266</a></li>
                    <li><a>CS 144</a></li>
                    <li><a>Web Application</a></li>
                </ul>
            </div>
        </div>

        <div style="width: 85%;margin:auto">
            <form action="post" method="POST">
                <input type="hidden" name="username" value=<%= request.getAttribute("username") %>>
                <input type="hidden" name="postid" value=<%= request.getAttribute("postid") %>>
                <input type="hidden" name="title" value=<%= request.getAttribute("title") %>>
                <input type="hidden" name="body" value=<%= request.getAttribute("body") %>>
                <button class="btn btn-success btn-lg" type="submit" name="action" value="open">Close Preview</button>
            </form>
        </div>
        <div style="width: 65%;margin:auto" style="font-size: 22px">
            <%  Parser parser = Parser.builder().build();
				Node document = parser.parse(request.getParameter("body"));
                Node pre_title = parser.parse("# " + (String)request.getParameter("title"));
				HtmlRenderer renderer = HtmlRenderer.builder().build();%>
            <div class="text-center">
                <%  out.println(renderer.render(pre_title));%>
            </div>
            <br>
            <%  out.println(renderer.render(document));%>
        </div>
    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>