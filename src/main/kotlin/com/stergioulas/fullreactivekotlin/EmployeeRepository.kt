package com.stergioulas.fullreactivekotlin

import org.springframework.data.r2dbc.repository.query.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface EmployeeRepository : ReactiveCrudRepository<Employee, Long> {

    @Query("select e.* from Employee e where e.last_name = :lastName")
    fun findEmployeeByLastName(lastName: String): Mono<Employee>
}