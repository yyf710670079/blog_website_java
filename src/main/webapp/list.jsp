<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <title>Post List</title>
</head>
<style type="text/css">
    th {
          
          font-size: 18px;
          
          
    }


</style>
<body style="padding-top: 80px">
        <div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <ul class="nav navbar-nav">
                    <li><a>&nbsp;&nbsp;</a></li>
                    <li class="active"><a>List Page</a></li>
                    <li><a>Yufeng Yang</a></li>
                    <li><a>305268266</a></li>
                    <li><a>CS 144</a></li>
                    <li><a>Web Application</a></li>
                </ul>
            </div>
        </div>


	<div style="width: 85%;margin:auto">
        <form action="post" id="0" method="POST">
            <input type="hidden" name="username" value=<%= request.getParameter("username") %>>
            <input type="hidden" name="postid" value=<%= request.getAttribute("postid") %>>

                

            

                
                <button class="btn btn-success btn-lg" type="submit" name="action" value="open" id="NewPost">New Post</button>
  
        </form>
    </div>
    <br>
    <br>
    
    <div  style="text-align:center" class="table-responsive"> 
        <table class="table table-striped table-bordered table-hover" style="width: 85%;margin:auto">
        	<tr><th class="text-center">Title</th><th class="text-center">Created</th><th class="text-center">Modified</th><th>&nbsp;</th></tr>
        	<% String data = (String)request.getAttribute("show_data");
               boolean dz = false; 
               if(data.equals("")) dz=true;%>
            <% String[] data_array = data.split(",");%>
        	<% for(int i=0;i<data_array.length;i+=4){ 
               if(dz) break;%>
        		<% int j = (i+1)/4; %>
                <tr>
            		<form id=<% out.println(j);%> action="post" method="POST">
                        
            			<input type="hidden" name="username" value=<%= request.getParameter("username") %>>
                        <input type="hidden" name="postid" value=<% out.println(data_array[i+3]);%>>
                        <td style="vertical-align: middle;"><% out.println(data_array[i]);%></td>
                        <td style="vertical-align: middle;"><% out.println(data_array[i+1]);%></td>
                        <td style="vertical-align: middle;"><% out.println(data_array[i+2]);%></td>
                        
                        <td>


                            <div class="btn-group-vertical" role="group">
                                <button class="btn btn-primary btn-group-sm" type="submit" name="action" value="open" style="width: 6.5rem">Open</button>
                                <button class="btn btn-danger btn-group-sm" type="submit" name="action" value="delete" style="width: 6.5rem">Delete</button>
                            </div>

                            
                        </td>
                        
            		</form>

                </tr>

        	<% }%>
        </table>

    </div>
    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>