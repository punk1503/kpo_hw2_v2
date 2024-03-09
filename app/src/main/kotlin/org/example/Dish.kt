package org.example

class Dish(name: String = "", cookingTime: Int = 0, price: Double = 0.0, amount: Int = 0) {
    public val name = name
    public val cookingTime = cookingTime
    public val price = price
    public var amount = 0
    
    fun displayDish() {
        println("Блюдо: $name - $price рублей : $cookingTime секунд, $amount осталось")
    }
}