package net.format_tv.test.fragments.users.swipe

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class SwipeHelper: RecyclerView.OnItemTouchListener {

    private val START_SWIPE_Y = 50F
    private val MIN_SWIPE_Y = 0F
    private val MAX_SWIPE_Y = 150F

    private var recyclerView: RecyclerView? = null
    private var pb: CircularProgressBar? = null
    private var updateListener: SwipeListener? = null

    private var initY = 0f

    fun attachRecyclerView(recyclerView: RecyclerView){
        this.recyclerView = recyclerView
        this.recyclerView!!.addOnItemTouchListener(this)
    }

    fun attachProgressBar(pb: CircularProgressBar){
        this.pb = pb
        this.pb!!.progressMax = MAX_SWIPE_Y
    }

    fun setSwipeListener(updateListener: SwipeListener){
        this.updateListener = updateListener
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN->{
                initY = event.rawY
                return false
            }
            MotionEvent.ACTION_MOVE->{
                return event.rawY-initY > START_SWIPE_Y && (rv.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 0
            }
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        when(e.action){
            MotionEvent.ACTION_MOVE -> {
                val dY = e.rawY - initY

                if(pb != null)
                    if(!pb!!.indeterminateMode)
                        if(dY in MIN_SWIPE_Y..MAX_SWIPE_Y+START_SWIPE_Y) {
                            animateY(rv, 0, rv.translationY,dY - START_SWIPE_Y)
                        }
                        else
                            animateY(rv, 0, rv.translationY, MAX_SWIPE_Y)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if(rv.translationY == MAX_SWIPE_Y) {
                    if(pb != null) {
                        if (!pb!!.indeterminateMode)
                            indeterminateProgress(true)
                    }else
                        animateY(rv, 500, rv.translationY, 0f)
                }
                else if(rv.translationY != 0f)
                    animateY(rv, 500, rv.translationY, 0f)
            }
        }
    }

    fun indeterminateProgress(flag: Boolean){
        this.pb?.indeterminateMode = flag

        if(!flag)
            animateY(recyclerView!!, 500, recyclerView!!.translationY, 0f)

        if(flag)
            updateListener?.onUpdate()
    }

    private fun updateProgress(newValue: Float){
        this.pb?.progress = newValue
        this.pb?.alpha = ((newValue*100)/MAX_SWIPE_Y)/100
    }

    private fun animateY(view: View, duration: Long, fromY: Float, toY: Float){
        ValueAnimator.ofFloat(fromY, toY).apply {
            this.duration = duration
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener { animation ->
                view.translationY = animation.animatedValue as Float
                updateProgress(animation.animatedValue as Float)
            }
            start()
        }
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    interface SwipeListener{
        fun onUpdate()
    }
}