package BlogWebsite;

import static spark.Spark.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class App {
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        get("/", (req, res) -> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "Mustafa Aydoğan");
            map.put("mesaj", "Site hazırlanıyor...");
            return new ModelAndView(map, "index.html");
        }, new MustacheTemplateEngine());

        get("/about", (req, res) -> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "Mustafa Aydoğan");
            map.put("mesaj", "Site hazırlanıyor...");
            return new ModelAndView(map, "about.html");
        }, new MustacheTemplateEngine());

        post("/contact", (req, res) -> {
            String name = req.queryParams("name");
            String email = req.queryParams("email");
            String subject = req.queryParams("subject");
            String message = req.queryParams("message");

            Map<String, String> map = new HashMap<String, String>();
            boolean couldWrite = true;

            try {
                File messageFile = new File("messages.txt");
                FileWriter writer = new FileWriter(messageFile, true);
                writer.append(name + "|" + email + "|" + subject + "|" + message + "\n");
                writer.close();
            } catch (Exception e) {
                map.put("error", "Mesaj gonderilemedi!");
                couldWrite = false;
            }

            if(couldWrite){
                map.put("result", "Mesajiniz basariyla gonderildi.");
            }
            return new ModelAndView(map, "contact.html");
        }, new MustacheTemplateEngine());

        get("/contact", (req, res) -> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "Mustafa Aydoğan");
            map.put("mesaj", "Site hazırlanıyor...");
            return new ModelAndView(map, "contact.html");
        }, new MustacheTemplateEngine());

        get("/blog", (req, res) -> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "Mustafa Aydoğan");
            map.put("mesaj", "Site hazırlanıyor...");
            return new ModelAndView(map, "blog.html");
        }, new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            //return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
