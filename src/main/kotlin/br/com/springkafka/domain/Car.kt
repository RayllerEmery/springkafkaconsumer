package br.com.springkafka.domain

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
class Car{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    val id: String? = ""
    var name: String? = null
    var cpf: String? = null

    var brand: String? = null

    constructor()

    constructor(name: String, cpf: String, brand: String) {
        this.name = name
        this.cpf = cpf
        this.brand = brand
    }
}
