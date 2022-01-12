package net.format_tv.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.format_tv.test.fragments.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFragment = MainFragment()

        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, mainFragment, "MainFragment").commit()
        else
            mainFragment = supportFragmentManager.findFragmentByTag("MainFragment") as MainFragment
    }

    override fun onBackPressed() {
        if(!mainFragment.onBackPressed())
            super.onBackPressed()
    }
}