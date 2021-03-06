package com.example.project1

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProviders
import java.io.File
import java.util.*

private const val TAG = "AddItemActivity"
private const val KEY_INDEX = "index"
//private const val EXTRA_LIST_NAME =
//    "com.example.project1.list_name"
private const val REQUEST_PHOTO = 2

class AddFoodActivity : AppCompatActivity() {
    private lateinit var food: Food
    private lateinit var save_string: TextView
    private lateinit var food_name_input: EditText
    private lateinit var exp_date_input: TextView
    private lateinit var food_camera: ImageButton
    private lateinit var food_photo: ImageView
    private lateinit var submit_button: Button
    private lateinit var pickDateBtn: Button
    private lateinit var photoFile: File
    private lateinit var photoUri: Uri


    private val foodViewModel: FoodViewModel by lazy {
        ViewModelProviders.of(this).get(FoodViewModel::class.java)
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        food = Food()
        setContentView(R.layout.activity_add_item)


        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        foodViewModel.currentIndex = currentIndex

        save_string = findViewById(R.id.save_string)
        food_camera = findViewById<ImageButton>(R.id.food_camera)
        food_photo = findViewById<ImageView>(R.id.food_photo)
        submit_button = findViewById(R.id.submit_button)
        food_name_input = findViewById(R.id.food_name_input)
        exp_date_input = findViewById(R.id.exp_date_input)
        pickDateBtn = findViewById(R.id.pickDateBtn)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        pickDateBtn.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
            exp_date_input.setText(""+ mMonth + "/" + mDay + "/" + mYear)}, year, month, day)

            dpd.show()
        }

        // .getString(R.string.date_placeholder, year, month, day)



        photoFile = foodViewModel.getPhotoFile(food)
        photoUri = FileProvider.getUriForFile(this@AddFoodActivity,
            "com.example.project1.fileprovider",
            photoFile)

//        var food = Food(UUID.randomUUID(),false, food_name_input.text.toString(), photoUri.toString(), exp_date_input.text.toString())
//        foodViewModel.addFoodItem(food)

        submit_button.setOnClickListener { view: View ->
            val food_name_input = food_name_input.text.toString()
            val exp_date_input = exp_date_input.text.toString()
            var food = Food(UUID.randomUUID(),false, food_name_input, photoUri.toString(), exp_date_input)
            foodViewModel.addFoodItem(food)

//            val intent = Intent(this, MainActivity::class.java)
            val intent = ListActivity.newIntent(this@AddFoodActivity, foodViewModel.currentList)
            startActivity(intent)
            val currentListInViewModel = foodViewModel.currentList.toString()
            Log.i(TAG, "onClickListener for submit_button")
            Log.i(TAG, currentListInViewModel)
        }
//        val show_save = intent.getBooleanExtra(EXTRA_SHOW_ADD, false)
//        if(show_save){
//            save_string.visibility = View.VISIBLE
//        } else{
//            save_string.visibility = View.INVISIBLE
//        }
    }

    override fun onStart() {
        super.onStart()

        food_camera.apply {
            val packageManager: PackageManager = this@AddFoodActivity.packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY)
            if (resolvedActivity == null) {
                isEnabled = false
            }
            setOnClickListener {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY)
                for (cameraActivity in cameraActivities) {
                    this@AddFoodActivity.grantUriPermission(

                cameraActivity.activityInfo.packageName,
                photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION)}
                startActivityForResult(captureImage, REQUEST_PHOTO)
            }
        }

    }
    fun onDetach() {
        this@AddFoodActivity.revokeUriPermission(photoUri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == REQUEST_PHOTO -> {
                this@AddFoodActivity.revokeUriPermission(
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                updatePhotoView()
            }
        } }

    private fun updatePhotoView() {
        if (photoFile.exists()) {
            val bitmap = getScaledBitmap(photoFile.path, this@AddFoodActivity)
            food_photo.setImageBitmap(bitmap)
        } else {
            food_photo.setImageDrawable(null)
        }
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, AddFoodActivity::class.java).apply {
//                putExtra(EXTRA_LIST_NAME, list_name)
            }
        }
    }



}