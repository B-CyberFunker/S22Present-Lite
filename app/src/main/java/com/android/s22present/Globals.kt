package com.android.s22present

import android.annotation.SuppressLint
import android.widget.ProgressBar
import android.widget.TextView
import com.chibde.visualizer.BarVisualizer
import com.chibde.visualizer.SquareBarVisualizer

// This class stores global variables.
class Globals
{
    @SuppressLint("StaticFieldLeak")
    companion object
    {
        // Make Presentation content variable (currently allows for multiple presets, full customization will require more work than this).
        // UI Element storage. Technically doing this can potentially create a memory leak but this doesn't.
        lateinit var datefield : TextView
        lateinit var titlefield : TextView
        lateinit var loading : ProgressBar
        lateinit var loadingtext : TextView
        lateinit var timefield : TextView
        lateinit var contentfield : TextView
        var visual : Int = 0
        lateinit var visualbar: BarVisualizer
        lateinit var visualsquare: SquareBarVisualizer
        var style = "0"
        var font = "0"
    }
}