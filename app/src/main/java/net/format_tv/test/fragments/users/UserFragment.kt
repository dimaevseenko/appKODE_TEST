package net.format_tv.test.fragments.users

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.*
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import net.format_tv.test.R
import net.format_tv.test.databinding.FragmentUserBinding
import net.format_tv.test.models.User

class UserFragment: Fragment(), View.OnTouchListener{

    private lateinit var binding: FragmentUserBinding

    private lateinit var user: User

    private var initY = 0f
    private var velocityY = 0f

    private lateinit var tracker: VelocityTracker

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserBinding.bind(inflater.inflate(R.layout.fragment_user, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        user = arguments?.getParcelable<User>("user") as User
        binding.imageButtonCancel.setOnClickListener { dismiss() }
        binding.name.text = user.firstName+" "+user.lastName
        binding.department.text = user.department
        binding.date.text = user.birthday
        binding.phone.text = user.phone
        binding.years.text = user.userTag
        binding.imageView4.setImageResource(R.drawable.ic_full)
        binding.root.setOnTouchListener(this)
    }

    fun dismiss(fragment: UserFragment = this): Boolean{
        ObjectAnimator.ofFloat(binding.root, View.TRANSLATION_Y, binding.root.translationY, binding.root.height.toFloat()).apply {
            interpolator = FastOutSlowInInterpolator()
            duration = 500
            addListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    parentFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).remove(fragment).commit()
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })
            start()
            return true
        }
        return false
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        tracker = VelocityTracker.obtain()

        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                tracker.clear()
                initY = event.rawY
                tracker.addMovement(event)
                return false
            }
            MotionEvent.ACTION_MOVE ->{
                tracker.addMovement(event)
                tracker.computeCurrentVelocity(1000)
                velocityY = tracker.yVelocity

                val dY = event.rawY - initY
                if(dY>=0f){
                    v.translationY = dY
                }else{
                    v.translationY = 0f
                }
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->{
                tracker.recycle()

                if(velocityY>5000f)
                    flingAnimateY(v, velocityY){
                        dismiss()
                    }
                else if(v.translationY > v.height/1.5)
                    dismiss()
                else
                    animateY(v, 0f)
            }
        }
        return false
    }

    private fun flingAnimateY(v: View, velocityY: Float, endAnimation: ()-> Unit){
        FlingAnimation(v, DynamicAnimation.TRANSLATION_Y).apply {
            setStartVelocity(velocityY)
            friction = 1.1f
            setMaxValue(0f)
            setMaxValue(10000f)
            addUpdateListener { animation, value, velocity ->
                if(v.translationY>v.height){
                    cancel()
                    dismiss()
                }
            }
            start()
        }
    }

    private fun animateY(v: View, toY: Float){
        ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, v.translationY, toY).apply {
            interpolator = FastOutSlowInInterpolator()
            this.duration = 500
            start()
        }
    }
}