package com.gramakhata.app.ui.customers

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.gramakhata.app.data.model.Customer
import com.gramakhata.app.databinding.ActivityAddCustomerBinding
import com.gramakhata.app.viewmodel.KhataViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding
    private val viewModel: KhataViewModel by viewModels()
    private var photoPath: String? = null
    private var photoUri: Uri? = null

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            binding.ivCustomerPhoto.setImageURI(photoUri)
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            photoPath = it.toString()
            binding.ivCustomerPhoto.setImageURI(it)
        }
    }

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) launchCamera()
        else Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Customer"

        binding.btnCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                launchCamera()
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        binding.btnGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.btnSave.setOnClickListener {
            saveCustomer()
        }
    }

    private fun launchCamera() {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val photoFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        photoPath = photoFile.absolutePath
        photoUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", photoFile)
        cameraLauncher.launch(photoUri)
    }

    private fun saveCustomer() {
        val name = binding.etName.text.toString().trim()
        val mobile = binding.etMobile.text.toString().trim()

        if (name.isEmpty()) {
            binding.etName.error = "Name is required"
            return
        }
        if (mobile.isEmpty() || mobile.length < 10) {
            binding.etMobile.error = "Valid mobile number required"
            return
        }

        val customer = Customer(
            name = name,
            mobile = mobile,
            photoUri = photoPath
        )

        viewModel.addCustomer(customer) {
            Toast.makeText(this, "Customer added!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
