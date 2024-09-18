package com.android.s22present

import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Clue's in the name.
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        // On start
        Log.i("S22PresMain", "Hello!")
        Log.i("S22PresMainInit", "Tasks associated with initial setup will be appended with Init")
        super.onCreate(savedInstanceState)
        // Create UI
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        {
            v, insets -> val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Grab the loading bar and reset the progress
        val progress : ProgressBar = findViewById(R.id.progressBar)
        val progresstext : TextView = findViewById(R.id.textViewProgress)
        progress.progress = 0
        progresstext.text = "Starting..."
        // Try to find display [1]
        val displaymanager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        try {
            Log.i("S22PresMainInit", "Looking for second display...")
            val display1 = displaymanager.displays[1]
            // If the display isn't found.
            if(display1 == null)
            {
                // Notify the user.
                progresstext.text = "No second display found!"
                Log.w("S22PresMainInit", "No second display found. Are you on the stock ROM?")
                Toast.makeText(this, "No second display found. Are you on the stock ROM?", 2500).show()
            }
            // If the display is found.
            else
            {
                // Push displays and UI elements to globals (referencing globals without a second display causes a crash which is why it's done here)
                Globals.loading = progress
                Globals.loadingtext = progresstext
                Globals.loading.progress = 1
                Globals.loadingtext.text = "Got displays"
                // Identify the service
                val serviceintent = Intent(this, ListenerService::class.java)
                // Kill existing service
                Log.i("S22PresMainInit", "I'm killing any existing services to prevent duplicates")
                stopService(serviceintent)
                // Run service and update progress
                Log.i("S22PresMainInit", "Asking Services to Run.")
                startService(serviceintent)
                Globals.loading.progress = 2
                Globals.loadingtext.text = "Starting ListenerService"
                val white =  ContextCompat.getColor(this, R.color.grey)
                Log.i("S22PresMainInit", "White = $white")
            }
        }
        // If a crash occurs whilst looking for a second display.
        catch (e: Exception)
        {
            // Notify user.
            Log.e("S22PresMainInit", "An Exception occurred trying to find the second screen. It is likely because the second screen isn't activated.")
            Toast.makeText(this, "No second display found. Run the commands!", 2500).show()
            progress.progress = 0
            progresstext.text = "No second display found!"
        }
        // When the user pushes the commands button
        // When user pushes the notification settings button.
        findViewById<Button>(R.id.buttonNotify).setOnClickListener{
            // Navigate the user to the settings menu for notifications permissions.
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        }
        findViewById<Button>(R.id.buttonSetting).setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume()
    {
        super.onResume()
    }
    override fun onPause()
    {
        super.onPause()
    }
    override fun onStop()
    {
        super.onStop()
    }
}

