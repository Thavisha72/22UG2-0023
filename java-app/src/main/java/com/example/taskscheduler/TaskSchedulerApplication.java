package com.example.taskscheduler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskSchedulerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskSchedulerApplication.class, args);
    }

    // Create table on startup (id, description, scheduled_at)
    @Bean
    CommandLineRunner initDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS tasks (
                    id SERIAL PRIMARY KEY,
                    description TEXT NOT NULL,
                    scheduled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
        };
    }
}
