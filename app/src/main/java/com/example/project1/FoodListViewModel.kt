package com.example.project1

import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*

class FoodListViewModel : ViewModel() {
    private val foodRepository = FoodRepository.get()
    val foodListLiveData = foodRepository.getFoods()
    val foodsInFridge = mutableListOf<Food>()
    val foodsInShoppingList = mutableListOf<Food>()
    var currentList: String = ""

    private val food = Food(UUID.randomUUID(),false, "food", "", "")

    var currentIndex = 0


    init {
        for (i in 0 until 20) {
            val food1 = Food()
            food1.name = "Food FIMF #$i"
            food1.expiration = "Expiration FIMF #$i"
            val food2 = Food()
            food2.name = "Food SL #$i"
            food2.expiration = "Expiration SL #$i"
            foodsInFridge += food1
            foodsInShoppingList += food2
        } }

    fun addFoodItem(food: Food): Void? {
        if(currentList.equals("Food In My Fridge")){
            foodsInFridge += food
        } else if(currentList.equals("Shopping List")){
            foodsInShoppingList += food
        }
        return null
    }

    fun getPhotoFile(food: Food): File {
        return foodRepository.getPhotoFile(food)
    }

    fun setFood(name: String): Void? {
        food.name = name
        return null
    }

    fun getFood(): String {
        return food.name
    }

    fun flipChecked(food: Food): Void? {
        food.isChecked = !food.isChecked
        return null
    }

    fun setCurrentList(list: String): Void? {
        currentList = list
        return null
    }
}