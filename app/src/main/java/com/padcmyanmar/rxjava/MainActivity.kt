package com.padcmyanmar.rxjava

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btnInCodeOne.setOnClickListener {
            tvText.text = ""
            helloRxJava("the", "real", "PADC", ":", "Android", "Developer", "Course")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun helloRxJava(vararg names: String) {
        val nameObservable = Observable.fromArray(*names)
        nameObservable.subscribe(object : Observer<String> {
            override fun onSubscribe(@NonNull d: Disposable) {

            }

            override fun onNext(@NonNull name: String) {
                Log.d("Hello-RxJava", "Rx : \"" + name + "\"" + " has " + name.length + " characters.")
                tvText.text = "${tvText.text}\n Rx : \"$name\"  has ${name.length}  characters."
            }

            override fun onError(@NonNull e: Throwable) {

            }

            override fun onComplete() {

            }
        })

        /*
        nameObservable.subscribe(object : Observer<String> {
            override fun onSubscribe(@NonNull d: Disposable) {
                tvText.text = "${tvText.text}\n onSubscribe"
            }

            override fun onNext(@NonNull name: String) {
                Log.d("Hello-RxJava", "Rx : \"" + name + "\"" + " has " + name.length + " characters.")
                tvText.text = "${tvText.text}\n Rx : \"$name\"  has ${name.length}  characters."
            }

            override fun onError(@NonNull e: Throwable) {
                tvText.text = "${tvText.text}\n onError ${e.message}"
            }

            override fun onComplete() {
                tvText.text = "${tvText.text}\n onComplete"
            }
        })
        */

        /*
        val nameObserver = object : Observer<String> {
            override fun onSubscribe(@NonNull d: Disposable) {

            }

            override fun onNext(@NonNull name: String) {
                tvText.text = "${tvText.text}\n Rx : \"$name\"  has ${name.length}  characters."
            }

            override fun onError(@NonNull e: Throwable) {

            }

            override fun onComplete() {

            }
        }

        val nameLogObserver = object : Observer<String> {
            override fun onSubscribe(@NonNull d: Disposable) {

            }

            override fun onNext(@NonNull name: String) {
                //Log.d("Hello-RxJava", "Rx : \"" + name + "\"" + " has " + name.length + " characters.")
                tvText.text = "${tvText.text}\n Logging"
            }

            override fun onError(@NonNull e: Throwable) {

            }

            override fun onComplete() {

            }
        }

        nameObservable.subscribe(nameObserver)
        nameObservable.subscribe(nameLogObserver)
        */
    }
}
