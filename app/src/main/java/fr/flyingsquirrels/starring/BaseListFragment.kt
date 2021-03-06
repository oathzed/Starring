package fr.flyingsquirrels.starring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import kotlinx.android.synthetic.main.fragment_list.*
import timber.log.Timber



abstract class BaseListFragment : Fragment() {

    companion object {
        const val VISIBLE_THRESHOLD = 6
        const val TYPE_KEY: String = "type"
    }

    protected var disposables = CompositeDisposable()
    protected lateinit var paginator: PublishProcessor<Int>
    protected var isLoading = false

    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(parentFragment != null && parentFragment is BaseTabsFragment)
            onScrollChangeListener = (parentFragment as BaseTabsFragment).onScrollListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    private var onScrollChangeListener: RecyclerView.OnScrollListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val grid = GridLayoutManager(context, 2)
        list.layoutManager = grid

        view.viewTreeObserver.addOnGlobalLayoutListener {

            val spanCount: Int = if(context!=null
                    && (view.width / context!!.resources.displayMetrics.density + 0.5f)>=480){
                4
            }else{
                2
            }
            grid.spanCount = spanCount
        }

        onScrollChangeListener?.let { list.addOnScrollListener(it) }
        list.adapter?.let{
            if(it.itemCount == 0) {
                loading.visibility = View.VISIBLE
            }else {
                loading.visibility = View.GONE
            }
        } ?: run {
            loading.visibility = View.VISIBLE
        }


        list.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView,
                           dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                lastVisibleItem = (list.layoutManager as? GridLayoutManager)?.findLastVisibleItemPosition() ?: 0
                if (!isLoading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    nextPage()
                    isLoading = true
                }
            }
        })

        paginator = PublishProcessor.create<Int>()


        swipe_refresh.setOnRefreshListener {
            list.adapter = null
            setPageNumber(0)
            totalItemCount = 0
            nextPage()
        }
    }

    protected abstract fun setPageNumber(pageNumber: Int)
    protected abstract fun getPageNumber(): Int

    override fun onResume() {
        super.onResume()
        loading?.visibility = View.GONE
    }

    protected fun nextPage(){
        isLoading = true
        setPageNumber(getPageNumber()+1)
        paginator.onNext(getPageNumber())
    }

    protected fun handleError(t: Throwable){
        Timber.e(t)
        finishLoading()
        isLoading = false
        setPageNumber(getPageNumber()-1)
    }

    protected fun finishLoading() {
        swipe_refresh?.isRefreshing = false
        totalItemCount = list?.adapter?.itemCount ?: 0
        isLoading = false
        loading?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}