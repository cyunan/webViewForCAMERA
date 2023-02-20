package com.bundletool.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class FileProviderActivity : AppCompatActivity() {
    //原图像 路径
    private var imgPathOri: String? = null

    //裁剪图像 路径
    private var imgPathCrop: String? = null

    //原图像 URI
    private var imgUriOri: Uri? = null

    //裁剪图像 URI
    private var imgUriCrop: Uri? = null



    companion object{
        const val TAG = "FileProviderActivity"
        const val REQUEST_OPEN_CAMERA = 0x011
        const val REQUEST_OPEN_GALLERY = 0x022
        const val REQUEST_CROP_PHOTO = 0x033
        const val REQUEST_PERMISSIONS = 0x044
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_provider)
        WebViewActivity3.checkPermission(this)
        findViewById<Button>(R.id.btn_click).setOnClickListener {
//            openCamera()
            openGallery()
        }
    }


    /**
     * 创建原图像保存的文件
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun createOriImageFile(): File? {
        val imgNameOri = "HomePic_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val pictureDirOri =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + "/OriPicture")
        if (!pictureDirOri.exists()) {
            pictureDirOri.mkdirs()
        }
        val image = File.createTempFile(
            imgNameOri,  /* prefix */
            ".jpg",  /* suffix */
            pictureDirOri /* directory */
        )
        imgPathOri = image.absolutePath
        return image
    }

    /**
     * 创建裁剪图像保存的文件
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun createCropImageFile(): File? {
        val imgNameCrop = "HomePic_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val pictureDirCrop =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + "/CropPicture")
        if (!pictureDirCrop.exists()) {
            pictureDirCrop.mkdirs()
        }
        val image = File.createTempFile(
            imgNameCrop,  /* prefix */
            ".jpg",  /* suffix */
            pictureDirCrop /* directory */
        )
        imgPathCrop = image.absolutePath
        return image
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // 打开相机
        var oriPhotoFile: File? = null
        try {
            oriPhotoFile = createOriImageFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (oriPhotoFile != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                imgUriOri = Uri.fromFile(oriPhotoFile)
            } else {
                imgUriOri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.FileProvider", oriPhotoFile)
            }
            intent.flags =
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION //私有目录读写权限
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriOri)
            startActivityForResult(intent, REQUEST_OPEN_CAMERA)
        }
    }

    /**
     * 裁剪图片
     * @param uri 需要 裁剪图像的Uri
     */
    fun cropPhoto(uri: Uri?) {
        val intent = Intent("com.android.camera.action.CROP")
        var cropPhotoFile: File? = null
        try {
            cropPhotoFile = createCropImageFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (cropPhotoFile != null) {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                imgUriCrop = Uri.fromFile(cropPhotoFile);
//            }else{
//                imgUriCrop = FileProvider.getUriForFile(this, getPackageName() + ".provider", cropPhotoFile);
//            }

            //7.0 安全机制下不允许保存裁剪后的图片
            //所以仅仅将File Uri传入MediaStore.EXTRA_OUTPUT来保存裁剪后的图像
            imgUriCrop = Uri.fromFile(cropPhotoFile)
            intent.setDataAndType(uri, "image/*")
            intent.putExtra("crop", true)
            intent.putExtra("aspectX", 1)
            intent.putExtra("aspectY", 1)
            intent.putExtra("outputX", 300)
            intent.putExtra("outputY", 300)
            intent.putExtra("return-data", false)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriCrop)
            intent.flags =
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            startActivityForResult(intent, FileProviderActivity.REQUEST_CROP_PHOTO)

            Log.i(FileProviderActivity.TAG, "cropPhoto_imgUriCrop:" + imgUriCrop.toString())
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_OPEN_CAMERA ->             //imgUriOri... Just do someThing, For example crop picture
                cropPhoto(imgUriOri)
            REQUEST_OPEN_GALLERY ->{
                if (data != null) {
                    var imgUriSel = data.data;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //相册会返回一个由相册安全策略定义的Uri，app使用这个Uri直接放入裁剪程序会不识别，抛出[暂不支持此类型：华为7.0]
                        //formatUri会返回根据Uri解析出的真实路径
                        val imgPathSel = UriUtils.formatUri(this, imgUriSel);
                        //根据真实路径转成File,然后通过应用程序重新安全化，再放入裁剪程序中才可以识别
                        imgUriSel = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.FileProvider", File(imgPathSel));
                        //7.0+ imgUriSel... Just do someThing, For example crop picture
                        cropPhoto(imgUriSel);
                    } else {
                        //7.0- imgUriSel... Just do someThing, For example crop picture
                        cropPhoto(imgUriSel);
                    }
                }
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent.action = Intent.ACTION_OPEN_DOCUMENT;
        } else {
            intent.action = Intent.ACTION_GET_CONTENT;
            //            intent.setAction(Intent.ACTION_PICK);
        }
        intent.type = "image/*";
        startActivityForResult(intent, REQUEST_OPEN_GALLERY);
    }


}