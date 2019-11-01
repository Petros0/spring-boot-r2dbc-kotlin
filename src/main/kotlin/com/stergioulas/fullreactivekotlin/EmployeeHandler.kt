package com.stergioulas.fullreactivekotlin

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.net.URI


@Component
class EmployeeHandler(
        val repository: EmployeeRepository) {


    fun getAll(request: ServerRequest) =
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(repository.findAll())

    fun getByLastName(request: ServerRequest) =
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(repository.findEmployeeByLastName(request.pathVariable("lastName")))

    fun save(request: ServerRequest): Mono<ServerResponse> {
        return request
                .bodyToMono(Employee::class.java)
                .flatMap { this.repository.save(it) }
                .flatMap { ServerResponse.created((URI.create("/employees/" + it.id))).build() }

    }

    fun deleteById(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(repository.deleteById(request.pathVariable("id").toLong()))
    }

}