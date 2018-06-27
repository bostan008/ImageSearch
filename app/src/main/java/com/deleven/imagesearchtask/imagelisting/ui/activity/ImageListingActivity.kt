package com.deleven.imagesearchtask.imagelisting.ui.activity

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.util.LruCache
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.deleven.imagesearchtask.App
import com.deleven.imagesearchtask.R
import com.deleven.imagesearchtask.base.component.activity.BaseActivity
import com.deleven.imagesearchtask.base.di.AppComponent
import com.deleven.imagesearchtask.base.utils.RxSearchObservable
import com.deleven.imagesearchtask.imagelisting.di.DaggerImageListingComponent
import com.deleven.imagesearchtask.imagelisting.models.ApiResponse
import com.deleven.imagesearchtask.imagelisting.models.ImageModel
import com.deleven.imagesearchtask.imagelisting.mvp.ImageListingContract
import com.deleven.imagesearchtask.imagelisting.ui.adapter.ImageSearchRvAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.BitmapFactory



class ImageListingActivity : BaseActivity<ImageListingContract.Presenter, ImageListingContract.View>(), ImageListingContract.View, ImageSearchRvAdapter.IShowListClick {

    private lateinit var imageListAdapter: ImageSearchRvAdapter

    private lateinit var mMemoryCache: LruCache<String, Bitmap>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initUI()

        //todo
       /* // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        val maxMemory = Runtime.getRuntime().maxMemory() / 1024

        // Use 1/8th of the available memory for this memory cache.
        val cacheSize = (maxMemory/8).toInt()

        mMemoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, bitmap: Bitmap?): Int {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap!!.byteCount / 1024
            }
        }*/

    }


    private fun initUI(){
        setUpSearchObservable()

        with(imageRv){
            this.layoutManager= LinearLayoutManager(this@ImageListingActivity)
            imageListAdapter = ImageSearchRvAdapter(this@ImageListingActivity, java.util.Collections.emptyList(), this@ImageListingActivity)
            this.adapter = imageListAdapter
            this.itemAnimator = null
        }

        hideLoading()
    }

    private fun setUpSearchObservable() {
        val searchobservable = RxSearchObservable.fromView(searchImageEt)
        presenter.processSearchData(searchobservable)

    }

    override fun getPresenterView()= this

    override fun initDagger(appComponent: AppComponent) {
        DaggerImageListingComponent.builder().appComponent(appComponent).bind(App.app).build().inject(this)
    }

    override fun showImageListing(apiResponse: ApiResponse) {

        apiResponse.imageLists?.let { imageListAdapter.addItems(it)
            when(it.size){
                0 -> {
                    showNoResultsView()
                }
                else -> {
                    imageRv.visibility = View.VISIBLE
                    noResultsContainer.visibility = View.GONE
                }
            }
        }
    }


    override fun onError(errMessage: String) {
        runOnUiThread {
            Toast.makeText(this, getString(R.string.oops), Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        imageSearchClpb.show()
        noResultsContainer.visibility = View.GONE
    }

    override fun hideLoading() {
        imageSearchClpb.hide()
    }

    override fun onItemClick(imageModel: ImageModel) {
        startActivity(ImageDetailsActivity.buildIntent(this, imageModel.webFormatUrl))
    }

    override fun showNoResultsView() {
        hideLoading()
        imageRv.visibility = View.GONE
        noResultsContainer.visibility = View.VISIBLE
    }

    //todo
    fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap)
        }
    }

    //todo
    fun getBitmapFromMemCache(key: String): Bitmap? {
        return mMemoryCache.get(key)
    }

    /*fun loadBitmap(resId: Int, imageView: ImageView) {
        val imageKey = resId.toString()

        val bitmap = getBitmapFromMemCache(imageKey)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            imageView.setImageResource(R.drawable.)
            val task = BitmapWorkerTask(imageView)
            task.execute(resId)
        }
    }*/

    //todo
    fun calculateInSampleSize(
            options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    //todo
    fun decodeSampledBitmapFromResource(res: Resources, resId: Int,
                                        reqWidth: Int, reqHeight: Int): Bitmap {

        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

}