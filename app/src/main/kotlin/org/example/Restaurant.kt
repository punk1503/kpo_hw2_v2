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
        val menu = loadMenu()
        val users = loadUsers()
        val orders = mutableListOf<Order>()


        fun saveMenu(menu: Menu) {
            val json = gson.toJson(menu)
            File(menuFilePath).writeText(json)
        }

        fun loadMenu(): Menu? {
            return try {
                val json = File(menuFilePath).readText()
                gson.fromJson(json, Menu::class.java)
            } catch (e: Exception) {
                null
            }
        }

        fun saveUsers(users: List<User>) {
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

        fun makeOrder(dishes: MutableList<Dish>, user: User) {
            orders.add(Order(dishes, user))
        }

        fun cancelOrder(orderIndex: Int) {
            orders[orderIndex].status = -1
        }

        fun payOrder(orderIndex: Int) {
            orders.removeAt(orderIndex)
            println("Заказ $orderIndex отменён")
        }

        fun displayOrders() {
            orders.forEach { order -> 
                order.dishes.forEach { dish -> 
                    dish.displayDish()
                }
            }
        }

        fun registerUser(username: String, password: String, isAdmin: Boolean) {
            users.add(User(username, password, isAdmin))
        }

        fun processOrder(orderIndex: Int) {
            orders[orderIndex].processOrder()
        }


    }