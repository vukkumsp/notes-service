package com.vukkumsp.notes_service.config;

import jakarta.annotation.PostConstruct;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DataSeeder {

    private final DatabaseClient databaseClient;

    public DataSeeder(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    private void configTables(){
        databaseClient.sql("DROP TABLE IF EXISTS note")
                .then()
                .doOnSuccess(v -> System.out.println("✅ Deleted 'note' table"))
                .doOnError(e -> System.err.println("❌ Failed to delete table: " + e.getMessage()))
                .subscribe();

        databaseClient.sql("""
            CREATE TABLE IF NOT EXISTS note (
            id SERIAL PRIMARY KEY,
            title TEXT NOT NULL,
            content TEXT NOT NULL,
            created_at TIMESTAMP DEFAULT NOW(),
            updated_at TIMESTAMP DEFAULT NOW())
        """).then()
                .doOnSuccess(v -> System.out.println("✅ Created 'note' table"))
                .doOnError(e -> System.err.println("❌ Failed to create table: " + e.getMessage()))
                .subscribe();

        databaseClient.sql("DROP TABLE IF EXISTS auth")
                .then()
                .doOnSuccess(v -> System.out.println("✅ Deleted 'auth' table"))
                .doOnError(e -> System.err.println("❌ Failed to delete table: " + e.getMessage()))
                .subscribe();

    }

    @PostConstruct
    public void seed() {

        configTables();

        databaseClient.sql("DELETE FROM note")
                .then()
                .doOnSuccess(v -> System.out.println("✅ Deleted 'note' table data"))
                .doOnError(e -> System.err.println("❌ Failed to delete table data: " + e.getMessage()))
                .subscribe();

        databaseClient.sql("SELECT COUNT(*) AS cnt FROM note")
                        .map(row -> row.get("cnt", Long.class))
                        .one().flatMap(count -> {
                            if(count != null && count == 0){
                                databaseClient.sql("""
                                        INSERT INTO note (title, content) VALUES(
                                            'How to deploy Spring boot app on render.com',
                                            'a. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.'
                                        )
                                        """)
                                        .then()
                                        .doOnSuccess(v -> System.out.println("✅ Inserted into 'note' table"))
                                        .doOnError(e -> System.err.println("❌ Failed to insert into table: " + e.getMessage()))
                                        .subscribe();

                                databaseClient.sql("""
                                        INSERT INTO note (title, content) VALUES(
                                            'How to deploy ReactJS App on Github Pages',
                                            'b. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.'
                                        )
                                        """)
                                        .then()
                                        .doOnSuccess(v -> System.out.println("✅ Inserted into 'note' table"))
                                        .doOnError(e -> System.err.println("❌ Failed to insert into table: " + e.getMessage()))
                                        .subscribe();

                                databaseClient.sql("""
                                        INSERT INTO note (title, content) VALUES (
                                            'How to deploy postgreSQL on render.com',
                                            'c. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.'
                                        )
                                        """)
                                        .then()
                                        .doOnSuccess(v -> System.out.println("✅ Inserted into 'note' table"))
                                        .doOnError(e -> System.err.println("❌ Failed to insert into table: " + e.getMessage()))
                                        .subscribe();
                            }
                            return Mono.just(count!=null?count:0);
                        })
                        .subscribe(result -> {
                            System.out.println("Result: " + result);
                        });
    }
}