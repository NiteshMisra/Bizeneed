package `in`.bizeneed.extras

import `in`.bizeneed.R
import `in`.bizeneed.activity.ServiceDetail
import `in`.bizeneed.model.OrderModel
import `in`.bizeneed.response.OrderData
import `in`.bizeneed.response.SubCategoryData
import `in`.bizeneed.rest.Coroutines
import android.app.*
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

fun hideKeyBoard(fragmentActivity: FragmentActivity) {
    val inm = fragmentActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = fragmentActivity.currentFocus
    view?.apply {
        inm.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

@Suppress("DEPRECATION")
fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(contentUri, proj, null, null, null)
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        cursor.getString(column_index)
    } finally {
        cursor?.close()
    }
}

fun saveImageToFile(context: Context, bitmap: Bitmap): File? {
    val myPath = File(getRootDirPath(context), "profile.png")
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(myPath)
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos)
        fos.close()
    } catch (e: Exception) {
        Log.e("SAVE_IMAGE", e.message, e)
    }
    return myPath
}

fun getRootDirPath(context: Context): String? {
    if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        val file = ContextCompat.getExternalFilesDirs(context, null)[0]
        return file.absolutePath
    }
    return context.filesDir.absolutePath
}

fun generateOTP(): Int {
    var otp: Int = Random().nextInt(999999)
    if (otp < 100000) {
        otp = when (otp.toString().length) {
            5 -> "0$otp".toInt()
            4 -> "00$otp".toInt()
            3 -> "000$otp".toInt()
            2 -> "0000$otp".toInt()
            1 -> "00000$otp".toInt()
            else -> generateOTP().toInt()
        }
    }
    return otp;
}

@Suppress("DEPRECATION")
fun isConnected(context: Context): Boolean {
    val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivity.allNetworkInfo
    for (networkInfo in info) if (networkInfo.state == NetworkInfo.State.CONNECTED) {
        return true
    }
    return false
}

fun getBitmap(context: Context, imageUri: Uri): Bitmap? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

        ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(
                context.contentResolver,
                imageUri
            )
        )

    } else {

        context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }

    }
}

fun getStates(): ArrayList<String> {
    val states = arrayOf(
        "Andhra Pradesh",
        "Arunachal Pradesh",
        "Assam",
        "Bihar",
        "Chhattisgarh",
        "Goa",
        "Gujarat",
        "Haryana",
        "Himachal Pradesh",
        "Jharkhand",
        "Karnataka",
        "Kerala",
        "Madhya Pradesh",
        "Maharashtra",
        "Manipur",
        "Meghalaya",
        "Mizoram",
        "Nagaland",
        "Odisha",
        "Punjab",
        "Rajasthan",
        "Sikkim",
        "Tamil Nadu",
        "Telangana",
        "Tripura",
        "Uttar Pradesh",
        "Uttarakhand",
        "West Bengal",
        "Andaman & Nicobar Islands",
        "Chandigarh",
        "Dadra & Nagar  Haveli",
        "Daman & Diu",
        "Lakshadweep",
        "Puducherry",
        "Delhi",
        "Jammu and Kashmir"
    )
    val statesList: ArrayList<String> = ArrayList()
    statesList.addAll(states)
    statesList.sort()
    statesList.add(0, "Select State")
    return statesList
}

fun getUserReferCode(): String {
    val value = 1000 + AppPrefData.user()!!.id
    return "CMY$value"
}

fun extractDigits(`in`: String?): String? {
    val p = Pattern.compile("(\\d{6})")
    val m = p.matcher(`in`)
    return if (m.find()) {
        m.group(0)
    } else ""
}

fun isEmailValid(emailId: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return emailId.trim { it <= ' ' }.matches(emailPattern.toRegex())
}

fun formattedRating(ratingCount: Int): String {
    return if (ratingCount > 1000) {
        val thousandPlace = ratingCount / 1000
        val decimalPlace = ratingCount % 1000
        ("$thousandPlace.${String.format(Locale.getDefault(), "%02d", decimalPlace)}k")
    } else {
        ratingCount.toString()
    }
}

fun noInterConnection() {
    Coroutines.main {
        Toast.makeText(
            MyApplication.applicationContext(),
            "No Internet Connection",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun slowInternetConnection() {
    Coroutines.main {
        Toast.makeText(
            MyApplication.applicationContext(),
            "Slow Internet Connection Try again later",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun errorOccurred() {
    Coroutines.main {
        Toast.makeText(
            MyApplication.applicationContext(),
            "Something went wrong, Try again later",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun createOrderData(orderModel: OrderModel, subCategoryData: List<SubCategoryData>): OrderData {
    return OrderData(
        orderModel.orderId,
        orderModel.subCategoryName,
        orderModel.userId,
        orderModel.name,
        orderModel.address,
        orderModel.state,
        orderModel.mobile,
        orderModel.totalPrice,
        orderModel.timeOfOrder,
        orderModel.dateOfOrder,
        "0",
        orderModel.paymentType,
        subCategoryData
    )
}

@Suppress("DEPRECATION")
fun createOrderNotification(
    context: Context,
    subCategoryData: SubCategoryData,
    orderData: OrderData,
    subCategoryName: String
) {

    val intent = Intent(context, ServiceDetail::class.java)
    intent.putExtra(Constants.DATA, Gson().toJson(subCategoryData))
    intent.putExtra(Constants.IS_PURCHASED, true)
    intent.putExtra(Constants.ORDER_DATA, Gson().toJson(orderData))

    val channelId = "companify_channel_id"

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel(channelId, "Companify", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }

    val pendingIntent =
        PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val builder = NotificationCompat.Builder(context)

    val s = "Thanks for booking our service - " + subCategoryData.name;
    builder.setAutoCancel(true)
        .setDefaults(Notification.DEFAULT_ALL)
        .setWhen(System.currentTimeMillis())
        .setSmallIcon(R.drawable.c_logo)
        .setTicker("Companify")
        .setChannelId(channelId)
        .setContentTitle("Order Successful")
        .setContentText(s)
        .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
        .setContentIntent(pendingIntent)
        .setContentInfo("Info")

    notificationManager.notify(1, builder.build())

}

fun getDate(date: String): String {
    val year = date.substring(0, 4)
    val day = date.substring(8, 10)
    val mon = date.substring(5, 7).toInt()
    val month = arrayOf(
        " ", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
        "Aug", "Sep", "Oct", "Nov", "Dec"
    )
    return day + "-" + month[mon] + "-" + year
}

fun getTime(time: String): String {
    var a = time.substring(11, 13).toInt()
    var s = " AM"
    if (a >= 12) {
        a -= 12
        s = " PM"
    }
    if (a == 0) {
        a = 12
    }
    return """$a${time.substring(13, 16)}$s"""
}
