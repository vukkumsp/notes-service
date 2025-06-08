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

        databaseClient.sql("""
            CREATE TABLE IF NOT EXISTS note (
            id SERIAL PRIMARY KEY,
            content TEXT NOT NULL)
        """).then().subscribe();

        databaseClient.sql("SELECT COUNT(*) AS cnt FROM note")
                        .map(row -> row.get("cnt", Long.class))
                        .one().flatMap(count -> {
                            if(count != null && count == 0){
                                databaseClient.sql("INSERT INTO note (content) VALUES ('data1')")
                                        .then()
                                        .subscribe();

                                databaseClient.sql("INSERT INTO note (content) VALUES ('data2')")
                                        .then()
                                        .subscribe();

                                databaseClient.sql("INSERT INTO note (content) VALUES ('data3')")
                                        .then()
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