package com.stergioulas.fullreactivekotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class FullReactiveKotlinApplication

fun main(args: Array<String>) {
    runApplication<FullReactiveKotlinApplication>(*args)
}