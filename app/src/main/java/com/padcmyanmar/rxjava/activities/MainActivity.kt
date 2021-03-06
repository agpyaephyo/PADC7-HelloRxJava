package com.padcmyanmar.rxjava.activities

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.padcmyanmar.rxjava.R
import com.padcmyanmar.rxjava.RxJavaApp
import com.padcmyanmar.rxjava.data.vo.RestaurantVO
import com.padcmyanmar.rxjava.network.responses.RestaurantListResponse
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Callable

class MainActivity : AppCompatActivity() {

    private val mTestSubject: PublishSubject<Int> = PublishSubject.create()
    private var mValue = 0

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

        btnInCodeFour.setOnClickListener {
            longRunningOperationWithRxJava()
        }

        mTestSubject.subscribe(object : Observer<Int> {
            override fun onSubscribe(@NonNull d: Disposable) {

            }

            override fun onNext(integer: Int) {
                when (integer) {
                    1 -> tvText.text = "Yayyyy. We are one."
                    2 -> tvText.text = "Hmmmm, two. Still acceptable though."
                    3 -> tvText.text = "Ok people. WTF. We are dripping off. Three ?"
                    4 -> tvText.text = "Listen up, unless we keep push up in next time, we are screwed."
                    5 -> tvText.text = "We are screwed."
                    else -> tvText.text = "We are officially irrelevant now."
                }
            }

            override fun onComplete() {

            }

            override fun onError(@NonNull e: Throwable) {

            }
        })

        btnInCodeFive.setOnClickListener {
            mValue++
            mTestSubject.onNext(mValue)
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

    private fun longRunningOperationWithRxJava() {
        tvText.text = ""
        val integerSingle = Single.fromCallable(Callable<Int> { operation() })

        integerSingle
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Int> {
                override fun onSubscribe(@NonNull d: Disposable) {

                }

                override fun onSuccess(integer: Int) {
                    tvText.text = "${tvText.text}\nOperation has finished. The value is $integer\n"
                }

                override fun onError(@NonNull e: Throwable) {

                }
            })

        recursiveLogger(1)
    }

    private fun aBitComplexRxOperations() {
        tvText.text = ""
        val restaurantListResponseObservable = getRestaurantListResponseObservable()
        restaurantListResponseObservable
            .subscribeOn(Schedulers.io()) //run value creation code on a specific thread (non-UI thread)
            .map<List<RestaurantVO>> { restaurantListResponse ->
                restaurantListResponse.restaurantList
            }
            .flatMap<RestaurantVO>(object : Function<List<RestaurantVO>, ObservableSource<RestaurantVO>> {
                override fun apply(restaurantVOs: List<RestaurantVO>): ObservableSource<RestaurantVO> {
                    return Observable.fromIterable<RestaurantVO>(restaurantVOs)
                }
            })
            .filter { restaurant ->
                !TextUtils.isEmpty(restaurant.title)
            }
            .take(5)
            .doOnNext { restaurantVO ->
                Log.d(RxJavaApp.TAG, "Saving restaurant" + restaurantVO.title + " info into disk")
            }
            .map { restaurant ->
                "Rx Api : \"" + restaurant.title + "\"" + " has " + restaurant.tagList.size + " special meals.\n"
            }
            .observeOn(AndroidSchedulers.mainThread()) //observe the emitted value of the Observable on an appropriate thread
            .subscribe(object : Observer<String> {
                override fun onSubscribe(@NonNull d: Disposable) {

                }

                override fun onNext(@NonNull readableText: String) {
                    tvText.text = "${tvText.text} $readableText"
                }

                override fun onError(@NonNull e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }

    private fun getRestaurantListResponseObservable(): Observable<RestaurantListResponse> {
        val rxJavaApp: RxJavaApp = application as RxJavaApp
        return rxJavaApp.theApi.getRestaurantList()
    }

    private fun operation(): Int {
        try {
            // "Simulate" the delay.
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val result = 2345 + 1234
        return result
    }

    private fun recursiveLogger(index: Int) {
        Handler().postDelayed({
            tvText.text = "${tvText.text} + Time waited  $index ) ${(index + 500)} \n"
            if (index < 20) {
                recursiveLogger(index + 1)
            }
        }, 500)
    }
}
