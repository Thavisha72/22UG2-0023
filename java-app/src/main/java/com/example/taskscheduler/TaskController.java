package com.example.taskscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Controller
public class TaskController {

    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/")
    @ResponseBody
    public String index() {
        List<Map<String,Object>> tasks = jdbc.queryForList(
            "SELECT id, description, scheduled_at FROM tasks ORDER BY id DESC");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>Task Scheduler</title>");
        sb.append("<meta name='viewport' content='width=device-width, initial-scale=1'/>");
        sb.append("</head><body style='font-family: sans-serif; max-width: 800px; margin: 40px auto;'>");
        sb.append("<h1>Task Scheduler</h1>");
        sb.append("<form method='post' action='/add' style='margin-bottom: 1rem;'>");
        sb.append("<input style='padding:.5rem; width:70%;' type='text' name='description' placeholder='e.g., Doctor appointment at 3 PM' required/> ");
        sb.append("<button style='padding:.55rem 1rem;' type='submit'>Add Task</button>");
        sb.append("</form><hr/>");

        sb.append("<h3>Scheduled Tasks</h3><ul>");
        for (Map<String,Object> t : tasks) {
            sb.append("<li style='margin:.4rem 0;'>")
              .append(t.get("id")).append(": ")
              .append(t.get("description"))
              .append(" <small style='color:#666'>&nbsp;(").append(t.get("scheduled_at")).append(")</small>")
              .append("</li>");
        }
        sb.append("</ul>");
        sb.append("</body></html>");
        return sb.toString();
    }

    @PostMapping("/add")
    public RedirectView addTask(@RequestParam String description) {
        jdbc.update("INSERT INTO tasks (description) VALUES (?)", description);
        return new RedirectView("/");
    }
}
