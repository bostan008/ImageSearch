package com.deleven.imagesearchtask.imagelisting.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.deleven.imagesearchtask.R
import com.deleven.imagesearchtask.base.utils.getDeviceWidth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class ImageDetailsActivity: AppCompatActivity() {


    companion object {

        fun buildIntent(context: Context?, url: String?): Intent {
            return Intent(context, ImageDetailsActivity::class.java).apply {
                this.putExtra("url", url)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.extras?.let {
            val screenWidth = detailsIv.getDeviceWidth()
            Picasso.with(this).load(it.getString("url")).noFade().resize(screenWidth, 0).into(detailsIv)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

}