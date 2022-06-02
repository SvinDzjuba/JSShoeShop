package servlets;

import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jsontools.UserJsonBuilder;
import session.UserFacade;
import tools.PasswordProtected;

/**
 *
 * @author makso
 */
@WebServlet(name = "UserServlet", loadOnStartup = 1 , urlPatterns = {
    "/registration",
    "/getListUsers",
    "/getUser",
    "/editUser"
})
public class UserServlet extends HttpServlet {
    @EJB private UserFacade userFacade;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        JsonObjectBuilder job = Json.createObjectBuilder();
        String path = request.getServletPath();
        switch (path) {
            case "/registration":
                JsonReader jsonReader = Json.createReader(request.getReader());
                JsonObject jsonObject = jsonReader.readObject();
                String firstName = jsonObject.getString("firstName","");
                String lastName = jsonObject.getString("lastName","");
                String username = jsonObject.getString("username","");
                String password = jsonObject.getString("password","");
                String phone = jsonObject.getString("phone","");
                double money = Double.parseDouble(jsonObject.getString("money",""));
                BigDecimal bd = new BigDecimal(money).setScale(2, RoundingMode.HALF_UP);
                double decimalMoney = bd.doubleValue();
                if(firstName.isEmpty() || lastName.isEmpty() 
                        || username.isEmpty() || password.isEmpty() || phone.isEmpty()
                ){
                    job.add("info", "Заполните все поля!")
                       .add("firstName", firstName)
                       .add("lastName", lastName)
                       .add("username", username)
                       .add("password", password)
                       .add("phone", phone)
                       .add("money", decimalMoney);
                    job.add("status", false);
                    try(PrintWriter out = response.getWriter()) {
                        out.println(job.build().toString());
                    }
                    break;
                }
                if(decimalMoney == 0.0) {
                    job.add("info", "Введите сумму больше нуля!")
                    .add("firstName", firstName)
                       .add("lastName", lastName)
                       .add("username", username)
                       .add("password", password)
                       .add("phone", phone)
                       .add("money", decimalMoney);
                    job.add("status", false);
                    try(PrintWriter out = response.getWriter()) {
                        out.println(job.build().toString());
                    }
                    break;
                }
                User newUser = new User();
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
                newUser.setLogin(username);
                newUser.setPhone(phone);
                newUser.setMoney(decimalMoney);
                PasswordProtected passwordProtected = new PasswordProtected();
                String salt = passwordProtected.getSalt();
                newUser.setSalt(salt);
                String userPassword = passwordProtected.getProtectedPassword(password, salt);
                newUser.setPassword(userPassword);
                newUser.setRole("USER");
                userFacade.create(newUser);
                job.add("info", "Аккаунт" + username + "успешно создан!")
                        .add("status", true);
                try(PrintWriter out = response.getWriter()) {
                    out.println(job.build().toString());
                }
                break;
            case "/getListUsers":
                List<User> users = userFacade.findAll();
                UserJsonBuilder ujb = new UserJsonBuilder();
                if(!users.isEmpty()) {
                    job.add("status", true)
                        .add("options", ujb.getUsersJsonArray(users));
                }
                try(PrintWriter out = response.getWriter()) {
                    out.println(job.build().toString());
                }
                break;
            case "/getUser":
                jsonReader = Json.createReader(request.getReader());
                jsonObject = jsonReader.readObject();
                String userId = jsonObject.getString("id", "");
                User editingUser = userFacade.find(Long.parseLong(userId));
                ujb = new UserJsonBuilder();
                job.add("status", true)
                    .add("info", "Вы редактируете: " + editingUser.getFirstName() + " " + editingUser.getLastName())
                    .add("user", ujb.getUserJsonObject(editingUser));
                try(PrintWriter out = response.getWriter()) {
                    out.println(job.build().toString());
                }
                break;
            case "/editUser":
                jsonReader = Json.createReader(request.getReader());
                jsonObject = jsonReader.readObject();
                userId = jsonObject.getString("id", "");
                firstName = jsonObject.getString("firstName", "");
                lastName = jsonObject.getString("lastName", "");
                phone = jsonObject.getString("phone", "");
                money = Double.parseDouble(jsonObject.getString("money", ""));
                bd = new BigDecimal(money).setScale(2, RoundingMode.HALF_UP);
                decimalMoney = bd.doubleValue();
                username = jsonObject.getString("username", "");
                
                User editUser = userFacade.find(Long.parseLong(userId));
                editUser.setFirstName(firstName);
                editUser.setLastName(lastName);
                editUser.setPhone(phone);
                editUser.setMoney(decimalMoney);
                editUser.setLogin(username);
//                passwordProtected = new PasswordProtected();
//                salt = passwordProtected.getSalt();
//                editUser.setSalt(salt);
//                userPassword = passwordProtected.getProtectedPassword(password, salt);
//                editUser.setPassword(userPassword);
                userFacade.edit(editUser);
                
                ujb = new UserJsonBuilder();
                job.add("status", true)
                    .add("info", "Пользователь " + editUser.getFirstName() + " " + editUser.getLastName() + " изменен(а)")
                    .add("editedUser", ujb.getUserJsonObject(editUser));
                try(PrintWriter out = response.getWriter()) {
                    out.println(job.build().toString());
                }
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}