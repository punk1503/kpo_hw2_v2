package org.example

class Menu {
    var dishes = mutableListOf<Dish>()
    
    fun displayMenu() {
        dishes.forEach {
            it.displayDish()
        }
    }

    fun addDish(dish: Dish) {
        dishes.add(dish)
    }
}