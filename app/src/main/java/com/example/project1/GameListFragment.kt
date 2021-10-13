package com.example.project1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private const val TAG = "GameListFragment"

class GameListFragment : Fragment() {

    private lateinit var gameRecyclerView: RecyclerView
    private var adapter: GameAdapter? = null
//    var isFoodInFridgeList: Boolean = true

//    private val bbViewModel: BBViewModel by lazy {
//        ViewModelProviders.of(this).get(BBViewModel::class.java)
//    }

    private val foodViewModel: FoodViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var currentList = foodViewModel.currentList

        if(currentList.equals("Food In My Fridge")){
            Log.d(TAG, "Total foods: ${foodViewModel.foodsInFridge.size}")
        } else if(currentList.equals("Shopping List")){
            Log.d(TAG, "Total foods: ${foodViewModel.foodsInShoppingList.size}")
        }
    }

    companion object {
        fun newInstance(): GameListFragment {
            return GameListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_list, container, false)
        gameRecyclerView =
            view.findViewById(R.id.game_recycler_view) as RecyclerView
        gameRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI() {
        Log.i(TAG, "updateUI in called")
        var currentList = foodViewModel.currentList
        var foods = foodViewModel.foodsInFridge
        if(currentList.equals("Food In My Fridge")){
            foods = foodViewModel.foodsInFridge
        } else if(currentList.equals("Shopping List")){
            foods = foodViewModel.foodsInShoppingList
        } else{
            foods = foodViewModel.foodsInFridge
        }
        adapter = GameAdapter(foods)
        gameRecyclerView.adapter = adapter
    }

    private inner class GameHolder(view: View)
        : RecyclerView.ViewHolder(view) {

//        private lateinit var game: Game
        private lateinit var food: Food

        private val checkBoxImageView: ImageView = itemView.findViewById(R.id.check_box)
        private val foodNameTextView: TextView = itemView.findViewById(R.id.food_name)
        private val foodImageImageView: ImageView = itemView.findViewById(R.id.food_image)
        private val foodExpirationTextView: TextView = itemView.findViewById(R.id.food_expiration)

        fun bind(food: Food) {
            this.food = food
            Log.i(TAG, "In bind food")
            val foodImageString = this.food.imageString
            if(this.food.isChecked){
                checkBoxImageView.setImageResource(R.drawable.checkbox)
            } else{
                checkBoxImageView.setImageResource(R.drawable.blank_check_box)
            }
            foodNameTextView.text = this.food.name
            foodImageImageView.setImageResource(R.drawable.salad)
            foodExpirationTextView.text = this.food.expiration
            checkBoxImageView.setOnClickListener { view: View ->
                foodViewModel.flipChecked(food)
                if(this.food.isChecked){
                    checkBoxImageView.setImageResource(R.drawable.checkbox)
                } else{
                    checkBoxImageView.setImageResource(R.drawable.blank_check_box)
                }
//            val food_name_input = food_name_input.text.toString()
//            val exp_date_input = exp_date_input.text.toString()
                Log.i(TAG, "onClickListener for checkBoxImageView")
            }
        }
//        checkBoxImageView.setOnClickListener { view: View ->
////            val food_name_input = food_name_input.text.toString()
////            val exp_date_input = exp_date_input.text.toString()
//            Log.i(TAG, "onClickListener for checkBoxImageView")
//        }
    }

    private inner class GameAdapter(var foods: List<Food>)
        : RecyclerView.Adapter<GameHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : GameHolder {
            val view = layoutInflater.inflate(R.layout.list_item_game, parent, false)
            return GameHolder(view)
        }

        override fun getItemCount() = foods.size

        override fun onBindViewHolder(holder: GameHolder, position: Int) {
            val food = foods[position]
            holder.bind(food)
        }
    }

}