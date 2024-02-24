package org.example

class Dish(name: String = "", cookingTime: Int = 0, price: Double = 0.0) {

    fun displayDish() {
        println("Блюдо: $name - $price : $cookingTime")
    }
}