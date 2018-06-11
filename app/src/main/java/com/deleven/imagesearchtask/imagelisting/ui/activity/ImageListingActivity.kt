package com.deleven.imagesearchtask.imagelisting.ui.activity

import android.os.Bundle
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
import com.deleven.imagesearchtask.imagelisting.mvp.ImageListingView
import com.deleven.imagesearchtask.imagelisting.ui.adapter.ImageSearchRvAdapter
import kotlinx.android.synthetic.main.activity_main.*

class ImageListingActivity : BaseActivity<ImageListingView.Presenter, ImageListingView.View>(), ImageListingView.View, ImageSearchRvAdapter.IShowListClick {

    private lateinit var imageListAdapter: ImageSearchRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initUI()
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
}