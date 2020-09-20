package `in`.bizeneed.extras

import android.app.Application
import android.content.Context

class MyApplication : Application(){

    var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        instance = this@MyApplication
        context = this.baseContext
    }

    companion object {

        @get:Synchronized
        var instance: MyApplication? = null
            private set

        fun applicationContext() : MyApplication {
            return instance!!.applicationContext as MyApplication
        }
    }
}