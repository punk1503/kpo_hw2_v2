    package org.example
    import com.google.gson.Gson
    import com.google.gson.reflect.TypeToken
    import java.io.File
    import java.io.FileReader
    import java.io.FileWriter

    class Restaurant() {
        private val menuFilePath = "menu.json"
        private val ordersFilePath = "orders.json"
        private val usersFilePath = "users.json"
        private val gson = Gson()
        var menu = loadMenu()
        var users = loadUsers()
        var orders = mutableListOf<Order>()

        public fun removeDish(dishNameToRemove: String) {
            menu.dishes.removeIf { it.name == dishNameToRemove }
            println("Блюдо удалено: $dishNameToRemove")
            saveMenu()
        }

        public fun addDish(dish: Dish) {
            menu.addDish(dish)
            println("Блюдо добавлено: $dish.name")
            saveMenu()
        }

        public fun displayMenu() {
            println("Меню: ")
            menu.displayMenu()
        }

        fun saveMenu() {
            val json = gson.toJson(menu)
            File(menuFilePath).writeText(json)
        }

        fun loadMenu(): Menu {
            return try {
                val json = File(menuFilePath).readText()
                gson.fromJson(json, Menu::class.java)
            } catch (e: Exception) {
                Menu()
            }
        }

        fun saveUsers() {
            val json = gson.toJson(users)
            File(usersFilePath).writeText(json)
        }

        fun loadUsers():MutableList<User> {
            return try {
                val json = File(usersFilePath).readText()
                gson.fromJson(json, object : TypeToken<MutableList<User>>() {}.type)
            } catch (e: Exception) {
                println()
                mutableListOf()
            }
        }

        public fun makeOrder(dishes: MutableList<Dish>, user: User) {
            orders.add(Order(dishes, user))
        }

        public fun cancelOrder(orderIndex: Int) {
            orders[orderIndex].status = -1
            println("Заказ {$orderIndex+1} отменен")
        }

        public fun payOrder(orderIndex: Int) {
            orders.removeAt(orderIndex)
            println("Заказ {$orderIndex+1} оплачен")
        }

        public fun displayOrders() {
            orders.forEach { order -> 
                order.dishes.forEach { dish -> 
                    dish.displayDish()
                }
                when(order.status) {
                    -1 -> {
                        print("Отменён\n")
                    }
                    0 -> {
                        print("Не готов\n")
                    }
                    1 -> {
                        print("Готов\n")
                    }
                }
            }
        }

        public fun registerUser(username: String, password: String, isAdmin: Boolean) {
            users.add(User(username, password, isAdmin))
        }

        public fun processOrder(orderIndex: Int) {
            orders[orderIndex].processOrder()
        }
    }