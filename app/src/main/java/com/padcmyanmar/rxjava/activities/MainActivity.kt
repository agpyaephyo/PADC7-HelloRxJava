package com.padcmyanmar.rxjava.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.padcmyanmar.rxjava.R
import com.padcmyanmar.rxjava.RxJavaApp
import com.padcmyanmar.rxjava.network.responses.RestaurantListResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btnInCodeOne.setOnClickListener {
            helloRxJava("the", "real", "PADC", ":", "Android", "Developer", "Course")
        }

        btnInCodeThree.setOnClickListener {
            networkCallWithRxJava()
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
        tvText.text = ""
        val nameObservable = Observable.fromArray(*names)
        nameObservable.subscribe(object : Observer<String> {
            override fun onSubscribe(@NonNull d: Disposable) {

            }

            override fun onNext(@NonNull name: String) {
                Log.d(RxJavaApp.TAG, "Rx : \"" + name + "\"" + " has " + name.length + " characters.")
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
                Log.d(RxJavaApp.TAG, "Rx : \"" + name + "\"" + " has " + name.length + " characters.")
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
                //Log.d(RxJavaApp.TAG, "Rx : \"" + name + "\"" + " has " + name.length + " characters.")
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

    private fun networkCallWithRxJava() {
        tvText.text = ""
        val restaurantListResponseObservable = getRestaurantListResponseObservable()
        restaurantListResponseObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<RestaurantListResponse> {

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(restaurantListResponse: RestaurantListResponse) {
                    for (restaurant in restaurantListResponse.restaurantList) {
                        tvText.text =
                            "${tvText.text}\n Rx Api : \" ${restaurant.title} + \"  has  ${restaurant.tagList.size} special meals.\n"
                    }
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                    tvText.text = "onError :  ${e.message}"
                }

            })
    }

    private fun getRestaurantListResponseObservable(): Observable<RestaurantListResponse> {
        val rxJavaApp: RxJavaApp = application as RxJavaApp
        return rxJavaApp.theApi.getRestaurantList()
    }
}