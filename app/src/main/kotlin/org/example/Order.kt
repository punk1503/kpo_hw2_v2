package org.example

class Order(dishes: MutableList<Dish>, user: User){
    public val dishes = dishes
    public val user = user
    public var status = 0 // 0 - not ready, 1 - ready, -1 - canceled

    fun processOrder() {
        dishes.forEach{dish -> 
            Thread {
                // Simulate preparing the dish
                Thread.sleep(dish.cookingTime * 1000L)
    
                // Output message about dish readiness
                println("Блюдо ${dish.name} готово!")
            }.start()
        }
    }
}