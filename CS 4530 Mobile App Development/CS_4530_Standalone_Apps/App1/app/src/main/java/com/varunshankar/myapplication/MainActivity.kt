package com.varunshankar.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //Create variables to hold the three strings
    private var mFirstName: EditText? = null
    private var mMiddleName: EditText? = null
    private var mLastName: EditText? = null

    //Create variables to save and restore
    private var mStringFirstName: String? = null
    private var mStringMiddleName: String? = null
    private var mStringLastName: String? = null
    private var mIvPicSave: Bitmap? = null

    //Create variables for the UI elements that we need to control
    private var mButtonSubmit: Button? = null
    private var mButtonTakePicture: Button? = null

    //Create the variable for the ImageView that holds the profile pic
    private var mIvPic: ImageView? = null

    private var mCurrentPhotoPath: String? = null
    private val mCapturedImageURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get edit texts
        mFirstName = findViewById<EditText>(R.id.editTextFirstName)
        mMiddleName = findViewById<EditText>(R.id.editTextMiddleName)
        mLastName = findViewById<EditText>(R.id.editTextLastName)

        // Get buttons
        mButtonSubmit = findViewById(R.id.buttonSubmit)
        mButtonTakePicture = findViewById(R.id.buttonTakePicture)

        // This class itself contains the listener
        mButtonSubmit!!.setOnClickListener(this)
        mButtonTakePicture!!.setOnClickListener(this)

        // Get image view
        mIvPic = findViewById(R.id.ivProfile)

        if (savedInstanceState != null) {
            mFirstName!!.setText(savedInstanceState.getString("FN_TEXT"))
            mMiddleName!!.setText(savedInstanceState.getString("LN_TEXT"))
            mLastName!!.setText(savedInstanceState.getString("LN_TEXT"))

            mCurrentPhotoPath = savedInstanceState.getString("PIC_PATH")

            println("The path is: $mCurrentPhotoPath")

            if (mCurrentPhotoPath != null) {
                val thumbnailImage = BitmapFactory.decodeFile(mCurrentPhotoPath)
                mIvPic!!.setImageBitmap(thumbnailImage)
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonSubmit -> {
                // get inputs from TextEdits
                val firstName = mFirstName!!.text.toString()
                val middleName = mMiddleName!!.text.toString()
                val lastName = mLastName!!.text.toString()

                // If any of the entries are blank before trying to continue.
                if (firstName.isNullOrBlank()) {
                    Toast.makeText(this, "Enter a first name.", Toast.LENGTH_SHORT).show()
                } else if (middleName.isNullOrBlank()) {
                    Toast.makeText(this, "Enter a middle name", Toast.LENGTH_SHORT).show()
                } else if (lastName.isNullOrBlank()) {
                    Toast.makeText(this, "Enter a last name", Toast.LENGTH_SHORT).show()
                }
                //else if (mIvPic.isNullOrBlank()) {
                //    Toast.makeText(this, "Take a picture.", Toast.LENGTH_SHORT).show()
                //}
                else{

                    // Send data to fragment
                    val intent = Intent(this@MainActivity, Login::class.java)
                    intent.putExtra("firstName",firstName)
                    intent.putExtra("middleName",middleName)
                    intent.putExtra("lastName",lastName)
                    startActivity(intent)
                }
            }

            R.id.buttonTakePicture -> {

            //The button press should open a camera
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try{
                cameraActivity.launch(cameraIntent)
            }catch(ex:ActivityNotFoundException){
                //Do error handling here
            }
        }
        }
    }
    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(result.resultCode == RESULT_OK) {
            mIvPic = findViewById<View>(R.id.ivProfile) as ImageView
            val extras = result.data!!.extras
            val thumbnailImage = extras!!["data"] as Bitmap?

            //Open a file and write to it
            if (isExternalStorageWritable) {
                mCurrentPhotoPath = saveImage(thumbnailImage)
            } else {
                Toast.makeText(this, "External storage not writable.", Toast.LENGTH_SHORT).show()
            }

            if (Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                mIvPic!!.setImageBitmap(thumbnailImage)
            }
            else{
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                mIvPic!!.setImageBitmap(thumbnailImage)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Get the strings
        mStringFirstName = mFirstName!!.text.toString()
        mStringMiddleName = mMiddleName!!.text.toString()
        mStringLastName = mLastName!!.text.toString()

        // Put them in the outgoing Bundle
        outState.putString("FN_TEXT", mStringFirstName)
        outState.putString("MN_TEXT", mStringMiddleName)
        outState.putString("LN_TEXT", mStringLastName)
        outState.putString("PIC_PATH", mCurrentPhotoPath)
    }

    private fun saveImage(finalBitmap: Bitmap?): String {
        val root = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val myDir = File("$root/saved_images")
        myDir.mkdirs()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fname = "Thumbnail_$timeStamp.jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            Toast.makeText(this, "file saved!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    private val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }
}