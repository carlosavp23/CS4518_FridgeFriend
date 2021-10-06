package com.example.project1

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import java.io.File

private const val TAG = "MainFragment"
private const val KEY_INDEX = "index"
private const val EXTRA_TEAM_A_NAME =
    "com.example.project1.team_a_name"
private const val EXTRA_TEAM_B_NAME =
    "com.example.project1.team_b_name"
private const val REQUEST_PHOTO = 2

class MainFragment : Fragment() {
    private lateinit var game: Game
    private lateinit var photoFileTeamA: File
    private lateinit var photoFileTeamB: File
    private lateinit var photoUriTeamA: Uri
    private lateinit var photoUriTeamB: Uri

    private lateinit var team_a: TextView
    private lateinit var teama_camera: ImageButton
    private lateinit var teama_photo: ImageView
    private lateinit var score_a: TextView
    private lateinit var add_3_a: Button
    private lateinit var add_2_a: Button
    private lateinit var free_throw_a: Button
    private lateinit var team_b: TextView
    private lateinit var teamb_camera: ImageButton
    private lateinit var teamb_photo: ImageView
    private lateinit var score_b: TextView
    private lateinit var add_3_b: Button
    private lateinit var add_2_b: Button
    private lateinit var free_throw_b: Button
    private lateinit var reset: Button
    private lateinit var game_over: Button
    private lateinit var save_button: Button
    private lateinit var winner_a: TextView
    private lateinit var winner_b: TextView

    private val bbViewModel: BBViewModel by activityViewModels()

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        game = Game()
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        bbViewModel.currentIndex = currentIndex
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        team_a = view.findViewById(R.id.team_a)
        teama_camera = view.findViewById<ImageButton>(R.id.teama_camera)
        teama_photo = view.findViewById<ImageView>(R.id.teama_photo)
        score_a = view.findViewById(R.id.score_a)
        add_3_a = view.findViewById(R.id.add_3_a)
        add_2_a = view.findViewById(R.id.add_2_a)
        free_throw_a = view.findViewById(R.id.free_throw_a)
        team_b = view.findViewById(R.id.team_b)
        teamb_camera = view.findViewById<ImageButton>(R.id.teamb_camera)
        teamb_photo = view.findViewById<ImageView>(R.id.teamb_photo)
        score_b = view.findViewById(R.id.score_b)
        add_3_b = view.findViewById(R.id.add_3_b)
        add_2_b = view.findViewById(R.id.add_2_b)
        free_throw_b = view.findViewById(R.id.free_throw_b)
        reset = view.findViewById(R.id.reset)
        game_over = view.findViewById(R.id.game_over)
        save_button = view.findViewById(R.id.save_button)
        winner_a = view.findViewById(R.id.winner_a)
        winner_b = view.findViewById(R.id.winner_b)

        teama_camera.setOnClickListener { view: View ->
            Log.i(TAG, "onClickListener for camer a")
        }
        teamb_camera.setOnClickListener { view: View ->
            Log.i(TAG, "onClickListener for camer b")
        }

        photoFileTeamA = bbViewModel.getPhotoFileTeamA(game)
        photoUriTeamA = FileProvider.getUriForFile(requireActivity(),
            "com.example.project1.fileprovider",
            photoFileTeamA)

        photoFileTeamB = bbViewModel.getPhotoFileTeamB(game)
        photoUriTeamB = FileProvider.getUriForFile(requireActivity(),
            "com.example.project1.fileprovider",
            photoFileTeamB)


        return view
    }

    override fun onStart() {
        super.onStart()

        teama_camera.apply {
            val packageManager: PackageManager = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY)
            if (resolvedActivity == null) {
                isEnabled = false
            }
            setOnClickListener {
                Log.i(TAG, "onClickListener for camera A")
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUriTeamA)
                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY)
                for (cameraActivity in cameraActivities) {
                    requireActivity().grantUriPermission(

                        cameraActivity.activityInfo.packageName,
                        photoUriTeamA,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION)}
                startActivityForResult(captureImage, REQUEST_PHOTO)
            }
        }

        teamb_camera.apply {
            val packageManager: PackageManager = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY)
            if (resolvedActivity == null) {
                isEnabled = false
            }
            setOnClickListener {
                Log.i(TAG, "onClickListener for camera b")
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUriTeamB)
                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY)
                for (cameraActivity in cameraActivities) {
                    requireActivity().grantUriPermission(

                        cameraActivity.activityInfo.packageName,
                        photoUriTeamB,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION)}
                startActivityForResult(captureImage, REQUEST_PHOTO)
            }
        }


        add_3_a.setOnClickListener { view: View ->
            score_a.text = bbViewModel.addPoints("A", 3)
            Log.i(TAG, "onClickListener for add_3_a")
        }

        add_2_a.setOnClickListener { view: View ->
            score_a.text = bbViewModel.addPoints("A", 2)
            Log.i(TAG, "onClickListener for add_2_a")
        }

        free_throw_a.setOnClickListener { view: View ->
            score_a.text = bbViewModel.addPoints("A", 1)
            Log.i(TAG, "onClickListener for free_throw_a")
        }


        add_3_b.setOnClickListener { view: View ->
            score_b.text = bbViewModel.addPoints("B", 3)
            Log.i(TAG, "onClickListener for add_3_b")
        }

        add_2_b.setOnClickListener { view: View ->
            score_b.text = bbViewModel.addPoints("B", 2)
            Log.i(TAG, "onClickListener for add_2_b")
        }

        free_throw_b.setOnClickListener { view: View ->
            score_b.text = bbViewModel.addPoints("B", 1)
            Log.i(TAG, "onClickListener for free_throw_b")
        }

        reset.setOnClickListener { view: View ->
            bbViewModel.setScore("A", 0)
            bbViewModel.setScore("B", 0)
            score_a.text = bbViewModel.getScore("A")
            score_b.text = bbViewModel.getScore("B")
            score_a.setTextColor(Color.parseColor("#000000"))
            score_b.setTextColor(Color.parseColor("#000000"))
            makeClickable()
            winner_a.visibility = View.INVISIBLE
            winner_b.visibility = View.INVISIBLE
            save_button.visibility = View.INVISIBLE
            bbViewModel.setIsWinner("A", false)
            bbViewModel.setIsWinner("B", false)
            bbViewModel.setIsGameOverCalled(false)
            Log.i(TAG, "onClickListener for reset")
        }

        game_over.setOnClickListener { view: View ->
            checkWinners()
            bbViewModel.setIsGameOverCalled(true)
            Log.i(TAG, "onClickListener for game_over")
        }

        save_button.setOnClickListener { view: View ->
            val show_save = bbViewModel.getIsGameOverCalled()
            val intent = SaveActivity.newIntent(this@MainFragment.requireContext(), show_save)
           startActivity(intent)
            Log.i(TAG, "onClickListener for save_button")
        }

        score_a.text = bbViewModel.getScore("A")
        score_b.text = bbViewModel.getScore("B")
  //      intent.getStringExtra(EXTRA_TEAM_A_NAME)?.let { bbViewModel.setTeamAName(it) }
   //     intent.getStringExtra(EXTRA_TEAM_B_NAME)?.let { bbViewModel.setTeamBName(it) }
        team_a.text = bbViewModel.getTeamAName()
        team_b.text = bbViewModel.getTeamBName()
        if(bbViewModel.getIsGameOverCalled()) {
            checkWinners()
        } else {
            makeClickable()
        }

        updatePhotoViewTeamA()

        updatePhotoViewTeamB()


    }

    private fun updatePhotoViewTeamA() {
        if (photoFileTeamA.exists()) {
            val bitmap = getScaledBitmap(photoFileTeamA.path, requireActivity())
            teama_photo.setImageBitmap(bitmap)
        } else {
            teama_photo.setImageDrawable(null)
        }
    }

    private fun updatePhotoViewTeamB() {
        if (photoFileTeamB.exists()) {
            val bitmap = getScaledBitmap(photoFileTeamB.path, requireActivity())
            teamb_photo.setImageBitmap(bitmap)
        } else {
            teamb_photo.setImageDrawable(null)
        }
    }

    private fun checkWinners(): Void? {
        save_button.visibility = View.VISIBLE
        if(bbViewModel.checkWinner("A", "B") && bbViewModel.checkWinner("B", "A")){
            score_a.setTextColor(Color.parseColor("#32cd32"))
            winner_a.visibility = View.VISIBLE
            bbViewModel.setIsWinner("A", true)
            score_b.setTextColor(Color.parseColor("#32cd32"))
            winner_b.visibility = View.VISIBLE
            bbViewModel.setIsWinner("B", true)
        } else if(bbViewModel.checkWinner("A", "B")){
            score_a.setTextColor(Color.parseColor("#32cd32"))
            winner_a.visibility = View.VISIBLE
            bbViewModel.setIsWinner("A", true)
            score_b.setTextColor(Color.parseColor("#ff4500"))
        } else {
            score_a.setTextColor(Color.parseColor("#ff4500"))
            score_b.setTextColor(Color.parseColor("#32cd32"))
            winner_b.visibility = View.VISIBLE
            bbViewModel.setIsWinner("B", true)
        }
        if(bbViewModel.getIsWinner("A") || bbViewModel.getIsWinner("B")){
            makeNotClickable()
        }
        return null
    }

    private fun makeClickable(): Void? {
        teama_camera.isClickable = true
        teamb_camera.isClickable = true
        add_3_a.isClickable = true
        add_2_a.isClickable = true
        free_throw_a.isClickable = true
        add_3_b.isClickable = true
        add_2_b.isClickable = true
        free_throw_b.isClickable = true
        return null
    }

    private fun makeNotClickable(): Void? {
        add_3_a.isClickable = false
        add_2_a.isClickable = false
        free_throw_a.isClickable = false
        add_3_b.isClickable = false
        add_2_b.isClickable = false
        free_throw_b.isClickable = false
        return null
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().revokeUriPermission(photoUriTeamA,Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        requireActivity().revokeUriPermission(photoUriTeamB,Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            requestCode == REQUEST_PHOTO -> {
                requireActivity().revokeUriPermission(photoUriTeamA,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                updatePhotoViewTeamA()
            }
            requestCode == REQUEST_PHOTO -> {
                requireActivity().revokeUriPermission(photoUriTeamB,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                updatePhotoViewTeamB()
            }
        } }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, bbViewModel.currentIndex)
    }

}