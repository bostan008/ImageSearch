package com.deleven.imagesearchtask.imagelisting

import com.deleven.imagesearchtask.TestUtil.dummyApiResponse
import com.deleven.imagesearchtask.base.utils.BaseSchedulerProvider
import com.deleven.imagesearchtask.capture
import com.deleven.imagesearchtask.imagelisting.domain.repository.ImageListingRepository
import com.deleven.imagesearchtask.imagelisting.mvp.ImageListingContract
import com.deleven.imagesearchtask.imagelisting.mvp.ImageListingPresenter
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.ArgumentMatchers.matches

class ImageListingPresenterTest {

    lateinit var mImageListingPresenter : ImageListingPresenter

    @Mock
    lateinit var schedulerProvider: BaseSchedulerProvider


    @Mock
    private lateinit var mImageListingRepository: ImageListingRepository

    @Mock
    private lateinit var mView: ImageListingContract.View


    @Captor
    lateinit var getListFromRemoteCaptur : ArgumentCaptor<ImageListingRepository.ImageListingCallback>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        //mImageListingRepository = FakeImageRespositoryImpl()

        mImageListingPresenter = ImageListingPresenter(schedulerProvider, mImageListingRepository)
        mImageListingPresenter.onViewAdded(mView)
    }

    @Test
    fun getListing(){

        // Mockito.verify(mView)?.showLoading()
        val rose = "rose"
        mImageListingPresenter.getImageListing(rose)

        Mockito.verify(mImageListingRepository).getImageListingFromRemote(matches(rose), capture(getListFromRemoteCaptur))
        getListFromRemoteCaptur.value.showImageListingFromRemote(dummyApiResponse())

        val inOrder = Mockito.inOrder(mView)
        inOrder.verify<ImageListingContract.View>(mView).hideLoading()
        inOrder.verify<ImageListingContract.View>(mView).showImageListing(dummyApiResponse())
    }





}