import java.io.IOException;
import java.sql.* ;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * Servlet implementation class for Servlet: ConfigurationTest
 *
 */
public class Editor extends HttpServlet {
    /**
     * The Servlet constructor
     * 
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public Editor() {}

    public static class DataTuple {
        String username;
        int postid;
        String title;
        String body;
        Timestamp modified;
        Timestamp created;


        public DataTuple(String username, int postid, String title, String body, Timestamp modified, Timestamp created){
            this.username = username;
            this.postid = postid;
            this.title = title;
            this.body = body;
            this.modified = modified;
            this.created = created;

        }
    }

    public void init() throws ServletException
    {
        /*  write any servlet initialization code here or remove this function */
    }
    
    public void destroy()
    {
        /*  write any servlet cleanup code here or remove this function */
    }

    /**
     * Handles HTTP GET requests
     * 
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
     *      HttpServletResponse response)
     */

    private static Timestamp getCurrentTimeStamp() {

    java.util.Date today = new java.util.Date();
    return new Timestamp(today.getTime());

    }

    public static DataTuple accessDB(HttpServletResponse response, String action, String username, int postid, String title, 
    String body, Timestamp modified) throws ServletException, IOException
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        DataTuple data_tuple = null;

        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", "");

            if(action.equals("save")){
                s = c.prepareStatement(
                "INSERT INTO Posts VALUES(?,?,?,?,?,?)"
                );

                s.setString(1, username);
                s.setInt(2,postid);
                s.setString(3,title);
                s.setString(4,body);
                
                s.setTimestamp(6,getCurrentTimeStamp());
                s.setTimestamp(5,getCurrentTimeStamp());

                int n0 = s.executeUpdate();
            }else if(action.equals("delete")){
                
                s = c.prepareStatement(
                "DELETE FROM Posts WHERE username=? and postid=?"
                );

                s.setString(1,username);
                s.setInt(2,postid);

                int n1 = s.executeUpdate();

            }else if(action.equals("open") || action.equals("preview")) {
                s = c.prepareStatement(
                "SELECT title,body " +
                "FROM Posts " + 
                "WHERE username=? and postid=?");

                s.setString(1,username);
                s.setInt(2,postid);

                rs = s.executeQuery();

                if(rs.next()){
                    String tuple_title = rs.getString("title");
                    String tuple_body = rs.getString("body");

                    data_tuple = new DataTuple(username,postid,tuple_title,tuple_body,null,null);
                    System.out.println("In DB and first_line is not null");
                    System.out.println(data_tuple);
                }else{
                    data_tuple = new DataTuple(username,postid,null,null,null,null);
                }

                

            }else if(action.equals("max")){
                s = c.prepareStatement(
                "SELECT postid " +
                "FROM Posts " + 
                "WHERE postid=(SELECT MAX(postid) FROM Posts)");

                rs = s.executeQuery();

                if(rs.next()){
                    int maxID = rs.getInt("postid");
                    data_tuple = new DataTuple(username,maxID,null,null,null,null);
                } else {
                    data_tuple = new DataTuple(username,0,null,null,null,null);
                }

            }else if(action.equals("update")){
                s = c.prepareStatement(
                    "UPDATE Posts " + 
                    "SET title=?,body=?,modified=? " + 
                    "WHERE username=? and postid=?"
                    );

                s.setString(1,title);
                s.setString(2,body);
                s.setString(3,modified.toString());
                s.setString(4,username);
                s.setInt(5,postid);

                int n2 = s.executeUpdate();

            }

            
        }catch(SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        }finally{
            try { s.close(); } catch (Exception e) { }
            try { c.close(); } catch (Exception e) { }
            try { rs.close();} catch (Exception e) { }
        }
        
        return data_tuple;

    }

    public static String listPage(HttpServletResponse response, String username)
    throws ServletException, IOException
    {
        String data = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            //System.out.println(ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        Connection c = null;
        PreparedStatement listPost = null;
        ResultSet rs = null;

        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", "");
            listPost = c.prepareStatement(
                "SELECT title,created,modified,postid " +
                "FROM Posts " + 
                "WHERE username=?");

                listPost.setString(1,username);
                rs = listPost.executeQuery();


                int row_count = 0;
                while(rs.next()){
                    if(row_count==0){data = rs.getString("title");}
                    else{data += ","+rs.getString("title");}
                    
                    data += ","+rs.getString("created");
                    data += ","+rs.getString("modified");
                    data += ","+rs.getString("postid");

                    row_count ++;


                }
                //System.out.println(data);



        }catch(SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        } finally {
            try { listPost.close();} catch (Exception e){}
            try { c.close(); } catch (Exception e) {}
            try { rs.close();} catch (Exception e) {}
        }

        return data;

    }

    public static boolean is_existed(HttpServletResponse response, String username, int postid)
    throws ServletException, IOException
     {
        boolean result = false;
        System.out.println("In is_existed");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;

        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", "");
            s = c.prepareStatement(
                    "SELECT * FROM Posts WHERE username=? and postid=?"
                );

            s.setString(1,username);
            s.setInt(2,postid);
            System.out.println(username + postid);

            rs = s.executeQuery();
            System.out.println(rs);

            //String first_line = (String)rs.next();



            if(rs.next()) { result = true;}
            else          {result = false;}
            //System.out.println("first_line: ",first_line);
            //System.out.println("first_line is T?"+(first_line==true));

        }catch(SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        } finally {
            try { s.close(); } catch (Exception e) { }
            try { c.close(); } catch (Exception e) { }
            try { rs.close();} catch (Exception e) { }
        }

        return result;

    }

    public static boolean is_user_existed(HttpServletResponse response, String username)
    throws ServletException, IOException
    {
        boolean outcome = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;

        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", "");
            s = c.prepareStatement(
                    "SELECT * FROM Posts WHERE username=?"
                );

            s.setString(1,username);

            rs = s.executeQuery();

            if(rs.next()) outcome = true;
            else          outcome = false;

        }catch(SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        } finally {
            try { s.close(); } catch (Exception e) { }
            try { c.close(); } catch (Exception e) { }
            try { rs.close();} catch (Exception e) { }
        }

        return outcome;
    }





    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException 
    {
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String postid_str = request.getParameter("postid");
        //System.out.println("username: "+username);
        //System.out.println("action: "+action);
        //System.out.println("postid_str: "+postid_str);
        

        //if(postid_str!=null && (!postid_str.matches("\\d+"))){
        //    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        //}

        int postid;
        if((postid_str == null || postid_str.equals("")||postid_str.equals("null"))==false){
            postid = Integer.parseInt(postid_str);
        }else{
            postid = 0;
        }
        String title = request.getParameter("title");
        String body = request.getParameter("body");

        System.out.println("postid: "+postid);
        boolean a_l = action.equals("list");
        System.out.println("action == \"list\" " + a_l);
	
        if(action.equals("open")){
            if(is_existed(response,username,postid)){
                DataTuple tp1 = accessDB(response,"open",username,postid,title,body, getCurrentTimeStamp());
                request.setAttribute("title",tp1.title);
                request.setAttribute("body",tp1.body);
                request.setAttribute("username",username);
                request.setAttribute("postid",postid);

            }else{
                request.setAttribute("username",username);
                request.setAttribute("postid",0);
                request.setAttribute("title","");
                request.setAttribute("body","");
            }
            request.getRequestDispatcher("/edit.jsp").forward(request, response);

        }else if(action.equals("preview")){
            DataTuple tp3;
            if(is_existed(response,username,postid)){
                accessDB(response,"update",username,postid,title,body, getCurrentTimeStamp());
                tp3 = accessDB(response, "preview",username,postid,title,body, getCurrentTimeStamp());
            }else{
                accessDB(response,"save",username,postid,title,body, getCurrentTimeStamp());
                tp3 = accessDB(response,"preview",username,postid,title,body, getCurrentTimeStamp());

            }

            request.setAttribute("username",username);
            request.setAttribute("postid",postid);
            request.setAttribute("title",tp3.title);
            request.setAttribute("body",tp3.body);
            request.getRequestDispatcher("/preview.jsp").forward(request, response);

        }else if(action.equals("list")){
            //System.out.println("action(in): " + action);
            //System.out.println("T or F (in): "+is_user_existed(response,username));
            if(is_user_existed(response,username)){
                String show_data0 = listPage(response,username);
                request.setAttribute("show_data",show_data0);
                request.getRequestDispatcher("/list.jsp").forward(request, response);
            }else{
                request.setAttribute("show_data","");
                request.getRequestDispatcher("/list.jsp").forward(request, response);
            }

        }else{
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    /**
     * Handles HTTP POST requests
     * 
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
	
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String postid_str = request.getParameter("postid");

        if((postid_str == null || postid_str.equals("")) && (!postid_str.matches("\\d+"))){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        int postid;
        if((postid_str == null || postid_str.equals("")||postid_str.equals("null"))==false){

            postid = Integer.parseInt(postid_str);
        }else{
            postid = 0;
        }
        //int postid = Integer.valueOf(postid_str);
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        //response.getWriter().write(action+username+postid);

        if(action.equals("save")){

            if(postid <= 0){
                int id_temp = accessDB(response,"max", username, postid, title, body, getCurrentTimeStamp()).postid;
                accessDB(response,"save", username, id_temp+1, title, body, getCurrentTimeStamp());
            } else if(postid>0 && is_existed(response,username,postid)){
                
                    accessDB(response,"update",username,postid,title,body, getCurrentTimeStamp());      

            }else{
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            request.setAttribute("username",username);
            String show_data = listPage(response,username);
            request.setAttribute("show_data",show_data);
            request.getRequestDispatcher("/list.jsp").forward(request, response);

        }else if(action.equals("open")){
            //System.out.println("in open");
            //System.out.println("while openning: postid = " + postid);
            if(is_existed(response,username,postid)){
                DataTuple tp1 = accessDB(response,"open",username,postid,title,body, getCurrentTimeStamp());
                //System.out.println("In doPost");
                //System.out.println(tp1.title+tp1.body);
                request.setAttribute("username",username);
                request.setAttribute("postid",postid);
                request.setAttribute("title",tp1.title);
                request.setAttribute("body",tp1.body);
            }else{
                request.setAttribute("username",username);
                request.setAttribute("postid",0);
                request.setAttribute("title","");
                request.setAttribute("body","");
            }

            
            request.getRequestDispatcher("/edit.jsp").forward(request, response);

            

        }else if(action.equals("delete")){
            if(is_existed(response,username,postid)){
                accessDB(response,"delete", username,postid,title,body, getCurrentTimeStamp());


                if(is_user_existed(response,username)){
                    String show_data4 = listPage(response,username);
                    request.setAttribute("show_data",show_data4);
                    request.getRequestDispatcher("/list.jsp").forward(request, response);
                }else{
                    request.setAttribute("show_data","");
                    request.getRequestDispatcher("/list.jsp").forward(request, response);

                }

            }else{
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("We cannot delete it in that we cannot find this tuple in Database");
            }

        }else if(action.equals("preview")){
            DataTuple tp3;
            if(is_existed(response,username,postid)){
                accessDB(response,"update",username,postid,title,body, getCurrentTimeStamp());
                tp3 = accessDB(response,"preview",username,postid,title,body, getCurrentTimeStamp());
            }else{
                int id_temp2 = accessDB(response,"max", username, postid, title, body, getCurrentTimeStamp()).postid;
                postid = id_temp2 + 1;
                accessDB(response,"save",username,postid,title,body, getCurrentTimeStamp());
                tp3 = accessDB(response,"preview",username,postid,title,body, getCurrentTimeStamp());
                System.out.println("=========\n");
                System.out.println("postid " + postid);
                System.out.println("=========\n");

            }


            request.setAttribute("username",username);
            request.setAttribute("postid",postid);
            //System.out.println("=========\n");
            //System.out.println("postid2 " + postid);
            //System.out.println("=========\n");
            request.setAttribute("title",tp3.title);
            request.setAttribute("body",tp3.body);
            request.getRequestDispatcher("/preview.jsp").forward(request, response);

        }else if(action.equals("list")){
            
            if(is_user_existed(response,username)){
                String show_data3 = listPage(response,username);
                request.setAttribute("show_data",show_data3);
                request.getRequestDispatcher("/list.jsp").forward(request, response);
            }else{
                request.setAttribute("show_data","");
                request.getRequestDispatcher("/list.jsp").forward(request, response);
                //response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }


        }else{
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        }
        
    }
}

