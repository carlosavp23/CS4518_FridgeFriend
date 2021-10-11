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
//import com.example.project1.com.example.project1.Game
import com.example.project1.com.example.project1.Food

private const val TAG = "GameListFragment"

class GameListFragment : Fragment() {

    private lateinit var gameRecyclerView: RecyclerView
    private var adapter: GameAdapter? = null

//    private val bbViewModel: BBViewModel by lazy {
//        ViewModelProviders.of(this).get(BBViewModel::class.java)
//    }

    private val itemViewModel: ItemViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total games: ${itemViewModel.foods.size}")
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
        val foods = itemViewModel.foods
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
            val foodImageString = this.food.imageString
            if(this.food.isChecked){
                checkBoxImageView.setImageResource(R.drawable.checkbox)
            } else{
                checkBoxImageView.setImageResource(R.drawable.blank_check_box)
            }
            foodNameTextView.text = this.food.name
            foodImageImageView.setImageResource(R.drawable.salad)
            foodExpirationTextView.text = this.food.expiration
        }
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