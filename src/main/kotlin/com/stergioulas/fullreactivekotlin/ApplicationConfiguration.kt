package com.stergioulas.fullreactivekotlin

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import java.util.stream.Stream

@Component
class ApplicationConfiguration {


    @Bean
    fun runner(employeeRepository: EmployeeRepository, db: DatabaseClient) = ApplicationRunner {
        val initDb = db.execute {
            """
                CREATE TABLE employee (
                    id SERIAL PRIMARY KEY,
                    first_name VARCHAR(255) NOT NULL,
                    last_name VARCHAR(255) NOT NULL
                );
            """
        }

        val stream = Stream.of(
                Employee(null, "Petros", "S"),
                Employee(null, "Christos", "M")
        )
        val saveAll = employeeRepository.saveAll(Flux.fromStream(stream))


        initDb
                .then()
                .thenMany(saveAll)
                .subscribe()

    }

    @Bean
    fun route(handler: EmployeeHandler) = router {
        GET("/employees") { handler.getAll(it) }
        GET("/employees/{lastName}") { handler.getByLastName(it) }
        POST("/employees") { handler.save(it) }
        DELETE("/employees/{id}") { handler.deleteById(it) }
    }
}