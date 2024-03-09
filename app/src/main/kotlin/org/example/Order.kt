package org.example

class Order(dishes: MutableList<Dish>, user: User){
    public val dishes = dishes
    public val user = user
    public var status = 0 // 0 - not ready, 1 - ready, -1 - canceled

    fun processOrder(orderNumber: Int) {
        val threads = mutableListOf<Thread>()

        // Счетчик завершенных потоков
        var finishedThreads = 0

        dishes.forEach { dish ->
            val thread = Thread {
                // Симулируем приготовление блюда
                Thread.sleep(dish.cookingTime * 1000L)

                // Выводим сообщение о готовности блюда
                println("Блюдо ${dish.name} готово!")
                
                // Увеличиваем счетчик завершенных потоков
                synchronized(this) {
                    finishedThreads++
                }

                // Проверяем, все ли блюда готовы
                if (finishedThreads == dishes.size) {
                    println("Заказ номер $orderNumber готов!")
                }
            }
            threads.add(thread)
            thread.start()
        }

        // Ждем завершения всех потоков
        threads.forEach { it.join() }
    }
}