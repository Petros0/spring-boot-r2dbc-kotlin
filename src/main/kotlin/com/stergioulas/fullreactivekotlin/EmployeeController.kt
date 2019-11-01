package com.stergioulas.fullreactivekotlin

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/employees")
class EmployeeController(val repo: EmployeeRepository) {

    @GetMapping
    fun getAll() = repo.findAll()

    @GetMapping("/{lastName}")
    fun getByLastName(@PathVariable lastName: String) = repo.findEmployeeByLastName(lastName)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody employee: Employee) = repo.save(employee)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun save(@PathVariable id: Long) = repo.deleteById(id)
}