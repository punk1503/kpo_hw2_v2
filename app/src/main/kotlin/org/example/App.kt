package org.example
import java.util.Scanner

fun inputChoice(prompt: String, min: Int, max: Int): Int {
    val scanner = Scanner(System.`in`)
    var number: Int? = null

    while (number == null || number !in min..max) {
        print(prompt)
        if (scanner.hasNextInt()) {
            number = scanner.nextInt()
            if (number !in min..max) {
                println("Число должно быть между $min и $max.")
                number = null
            }
        } else {
            println("Неверный ввод. Пожалуйста, введите целое число.")
            scanner.next()
        }
    }
    return number
}

fun inputDouble(prompt: String, min: Double): Double {
    val scanner = Scanner(System.`in`)
    var doubleNumber: Double? = null

    while (doubleNumber == null || doubleNumber < min) {
        print(prompt)
        if (scanner.hasNextDouble()) {
            doubleNumber = scanner.nextDouble()
            if (doubleNumber < min) {
                println("Число должно быть больше или равно $min")
                doubleNumber = null
            }
        } else {
            println("Неверный ввод. Пожалуйста, введите дробное число.")
            scanner.next()
        }
    }
    return doubleNumber
}

fun inputInteger(prompt: String, min: Int): Int {
    val scanner = Scanner(System.`in`)
    var number: Int? = null

    while (number == null || number < min) {
        print(prompt)
        if (scanner.hasNextInt()) {
            number = scanner.nextInt()
            if (number < min) {
                println("Число должно быть больше или равно $min")
                number = null
            }
        } else {
            println("Неверный ввод. Пожалуйста, введите целое число.")
            scanner.next()
        }
    }
    return number

}

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
            var choice = inputChoice("Пункт меню(1, 3): ", 1, 3)
            
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
                    restaurant.saveUsers()
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

                var choice = inputChoice("Пункт меню(1, 4): ", 1, 4)
                when(choice) {
                    1 -> {
                        restaurant.displayMenu()
                    }
                    2 -> {
                        println("Введите данные нового блюда: ")
                        print("Название: ")
                        val name = scanner.next()
                        val cookingTime = inputInteger("Время приготовления(сек): ", 0)
                        val price = inputDouble("Цена(руб): ", 0.0)
                        val amount = inputInteger("Количество: ", 0)
                        restaurant.addDish(Dish(name, cookingTime, price, amount))
                    }
                    3 -> {
                        print("Введите название блюда: ")
                        val name = scanner.next()
                        restaurant.removeDish(name)
                    }
                    4 -> {
                        break
                    }

                }
            }
            else {
                println("1. Просмотр меню")
                println("2. Создать заказ")
                println("3. Отменить заказ")
                println("4. Оплатить заказ")
                println("5. Выйти")
                val choice = inputChoice("Пункт меню(1, 5): ", 1, 5)
    
                when (choice) {
                    1 -> {
                        println("\nМеню:")
                        restaurant.menu.dishes.forEach { println("${it.name} - ${it.price}") }
                    }
                    2 -> {
                        println("\nМеню:")
                        restaurant.menu.dishes.forEachIndexed { index, dish ->
                            println("${index + 1}. ${dish.name} - ${dish.price}")
                        }
    
                        val orderDishes = mutableListOf<Dish>()
                        print("Выберите блюда (номера через пробел, неверные будут проигнорированны): ")
                        var dishNumbers: List<Int>
                        while(true) {
                            try {
                                dishNumbers = scanner.nextLine().split(" ").map { it.toInt() }
                                break
                            } catch (e: Exception) {
                                println("Неверные значения типа 'число'")
                                continue
                            }
                            

                        }
                        dishNumbers.forEach { index ->
                            if (index in 1..restaurant.menu.dishes.size)
                                orderDishes.add(restaurant.menu.dishes.get(index - 1))
                        }
    
                        restaurant.makeOrder(orderDishes, loggedInUser)
                        println("Заказ успешно создан!")
                    }
                    3 -> {
                        println("\nТекущие заказы:")
                        restaurant.orders.forEachIndexed { index: Int, order: Order ->
                            println("${index+1}. ${order.dishes.joinToString(", ") { it.name }}")
                        }
    
                        print("Введите номер заказа для отмены (-1 для выхода): ")
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
                        restaurant.orders.forEachIndexed { index: Int, order: Order ->
                            println("${index+1}. ${order.dishes.joinToString(", ") { it.name }}")
                        }
    
                        print("Введите номер заказа для оплаты (-1 для выхода): ")
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
