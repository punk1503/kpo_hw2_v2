package org.example
import kotlinx.coroutines.*

class Order(public val dishes: List<Dish>, private val user: User) {
    var status = 0 // 0 - not ready, 1 - ready, -1 - canceled

    fun processOrder(orderNumber: Int) {
        // Запускаем корутину для обработки заказа
        CoroutineScope(Dispatchers.Default).launch {
            val deferredJobs = mutableListOf<Deferred<Unit>>()

            // Создаем и запускаем отложенные задания для приготовления каждого блюда
            dishes.forEach { dish: Dish ->
                val deferredJob = async {
                    // Симулируем приготовление блюда
                    delay(dish.cookingTime * 1000L)

                    // Выводим сообщение о готовности блюда
                    println("Блюдо ${dish.name} готово!")
                }
                deferredJobs.add(deferredJob)
            }

            // Ожидаем завершения всех отложенных заданий
            deferredJobs.awaitAll()

            // Выводим сообщение о готовности заказа после завершения всех блюд
            println("Заказ номер ${orderNumber+1} готов!")
        }
    }
}
