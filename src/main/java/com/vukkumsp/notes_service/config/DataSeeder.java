package com.vukkumsp.notes_service.config;

import jakarta.annotation.PostConstruct;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataSeeder {

    private final DatabaseClient databaseClient;

    public DataSeeder(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @PostConstruct
    public void seed() {
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

        databaseClient.sql("DELETE FROM note")
                .then()
                .doOnSuccess(v -> System.out.println("✅ Deleted 'note' table data"))
                .doOnError(e -> System.err.println("❌ Failed to delete table data: " + e.getMessage()))
                .subscribe();

//        databaseClient.sql("""
//            CREATE OR REPLACE FUNCTION set_updated_at()
//            RETURNS TRIGGER AS $$
//            BEGIN
//                NEW.updated_at = NOW();
//                RETURN NEW;
//            END;
//            $$ LANGUAGE plpgsql;
//        """).then().subscribe();
//
//        databaseClient.sql("""
//            CREATE TRIGGER trigger_set_updated_at
//            BEFORE UPDATE ON notes
//            FOR EACH ROW
//            EXECUTE FUNCTION set_updated_at();
//        """).then().subscribe();


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

//        databaseClient.sql("DELETE FROM note")
//                .then()
//                .subscribe();
//
//        databaseClient.sql("INSERT INTO note (content) VALUES ('data1')")
//                .then()
//                .subscribe();
//
//        databaseClient.sql("INSERT INTO note (content) VALUES ('data2')")
//                .then()
//                .subscribe();
//
//        databaseClient.sql("INSERT INTO note (content) VALUES ('data3')")
//                .then()
//                .subscribe();

//        Flux<String> data = Flux.just("data1", "data2", "data3");
//
//        databaseClient.sql("DELETE FROM note")
//                .then()
//                .thenMany(
//                        data.flatMap(content ->
//                                databaseClient.sql("INSERT INTO note (content) VALUES (:content)")
//                                        .bind("content", content)
//                                        .then()
//                        )
//                )
//                .subscribe(
//                        null,
//                        error -> {
//                            System.err.println("💥 Seeding failed: " + error);
//                            error.printStackTrace();
//                        },
//                        () -> System.out.println("✅ Seeding completed.")
//                );

    }
}