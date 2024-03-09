package org.example
import java.util.Scanner

fun main() {
    val restaurant = Restaurant()
    val scanner = Scanner(System.`in`)
    var loggedInUser: User? = null

    while (true) {
        println("\nДобро пожаловать в ресторан!")

        if (loggedInUser == null) {
            println("1. Вход")
            println("2. Регистрация")
            println("3. Выйти")
            print("Выберите действие: ")
            var choice: Int
            try {
                choice = scanner.nextInt()
            }
            catch(e: java.util.InputMismatchException) {
                println("Введено неверное значение меню")
                scanner.next()
                continue
            }
            
            when (choice) {
                1 -> {
                    print("Введите имя пользователя: ")
                    val username = scanner.next()
                    print("Введите пароль: ")
                    val password = scanner.next()

                    loggedInUser = restaurant.users.find { it.username == username && it.password == password }

                    if (loggedInUser == null) {
                        println("Неверное имя пользователя или пароль!")
                    }
                }
                2 -> {
                    print("Введите имя пользователя: ")
                    val username = scanner.next()
                    print("Введите пароль: ")
                    val password = scanner.next()
                    print("Пользователь - администратор?: (y/n)")
                    val isAdmin = if(scanner.next() == "y") true else false
                    restaurant.registerUser(username, password, isAdmin = isAdmin)
                    println("Пользователь зарегистрирован!")
                }
                3 -> {
                    println("Выход из программы...")
                    break
                }
                else -> println("Неверный ввод!")
            }
        } else {
            if(loggedInUser.isAdmin) {
                println("1. Просмотр меню")
                println("2. Добавить блюдо в меню")
                println("3. Удалить блюдо из меню")
                println("4. Выйти")
            }
            else {
                println("1. Просмотр меню")
                println("2. Создать заказ")
                println("3. Отменить заказ")
                println("4. Оплатить заказ")
                println("5. Выйти")
                var choice: Int
                print("Выберите действие: ")
                try {
                    choice = scanner.nextInt()
                }
                catch(e: java.util.InputMismatchException) {
                    println("Введено неверное значение меню")
                    scanner.next()
                    continue
                }
    
                when (choice) {
                    1 -> {
                        println("\nМеню:")
                        restaurant.menu?.dishes?.forEach { println("${it.name} - ${it.price}") }
                    }
                    2 -> {
                        println("\nМеню:")
                        restaurant.menu?.dishes?.forEachIndexed { index, dish ->
                            println("${index + 1}. ${dish.name} - ${dish.price}")
                        }
    
                        val orderDishes = mutableListOf<Dish>()
                        print("Выберите блюда (номера через пробел): ")
                        val dishNumbers = scanner.nextLine().split(" ").map { it.toInt() }
                        dishNumbers.forEach { index ->
                            if (index in 1..restaurant.menu?.dishes?.size!!)
                                orderDishes.add(restaurant.menu.dishes.get(index - 1))
                        }
    
                        restaurant.makeOrder(orderDishes, loggedInUser)
                        println("Заказ успешно создан!")
                    }
                    3 -> {
                        println("\nТекущие заказы:")
                        restaurant.orders.forEachIndexed { index, order ->
                            println("${index + 1}. ${order.dishes.joinToString(", ") { it.name }}")
                        }
    
                        print("Введите номер заказа для отмены: ")
                        val orderIndex = scanner.nextInt() - 1
    
                        if (orderIndex in 0 until restaurant.orders.size) {
                            restaurant.cancelOrder(orderIndex)
                            println("Заказ отменен!")
                        } else {
                            println("Неверный номер заказа!")
                        }
                    }
                    4 -> {
                        println("\nТекущие заказы:")
                        restaurant.orders.forEachIndexed { index, order ->
                            println("${index + 1}. ${order.dishes.joinToString(", ") { it.name }}")
                        }
    
                        print("Введите номер заказа для оплаты: ")
                        val orderIndex = scanner.nextInt() - 1
    
                        if (orderIndex in 0 until restaurant.orders.size) {
                            restaurant.payOrder(orderIndex)
                            println("Заказ оплачен!")
                        } else {
                            println("Неверный номер заказа!")
                        }
                    }
                    5 -> {
                        println("Выход из программы...")
                        break
                    }
                    else -> println("Неверный ввод!")
                }
            }
        }
    }
}
