<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Post</title>
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
                    <li class="active"><a>Edit Page</a></li>
                    <li><a>Yufeng Yang</a></li>
                    <li><a>305268266</a></li>
                    <li><a>CS 144</a></li>
                    <li><a>Web Application</a></li>
                </ul>
            </div>
        </div>

<div style="width: 85%;margin:auto">
    <form action="post" method="POST" role="form">
        <div class="row">
            <div style="text-align:center"  class="btn-group col-xs-8">
                <button type="submit" name="action" value="save" style="width: 8rem" class="btn btn-success btn-lg">Save</button>
                <button type="submit" name="action" value="list" style="width: 8rem" class="btn btn-warning btn-lg">Close</button>
                <button type="submit" name="action" value="preview" style="width: 8rem" class="btn btn-primary btn-lg">Preview</button>
                <button type="submit" name="action" value="delete" style="width: 8rem" class="btn btn-danger btn-lg">Delete</button>
            </div>
        </div>
        <br>
        <br>
        <br>
        <input type="hidden" name="username" value=<%= request.getAttribute("username") %>>
        <input type="hidden" name="postid" value=<%= request.getAttribute("postid") %>>
        
        <div class="form-group text-center row">
            <label for="title" style="font-size: 18px">Title</label>
            <br>
            <div class="form-group text-center col-xs-6 col-xs-offset-3">
                <input type="text" id="text" name="title" class="form-control text-center input-lg" placeholder="Your Title" value=<%= request.getAttribute("title")%> >
            </div>
        </div>
        
        <div class="form-group text-center row">
            <label for="body" style="font-size: 18px">Body</label>
            <br>
            <div class="form-group text-center col-xs-8 col-xs-offset-2">
                <textarea style="height: 40rem;font-size: 17px" id="body" name="body" class="form-control"><% out.println(request.getAttribute("body"));%></textarea>
            </div>
        </div>
    </form>
</div>
    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>
