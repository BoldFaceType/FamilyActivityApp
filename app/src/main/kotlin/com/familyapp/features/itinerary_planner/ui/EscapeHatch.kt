package com.familyapp.features.itinerary_planner.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun EscapeHatchFab() {
    val context = LocalContext.current
    
    FloatingActionButton(
        onClick = {
            val gmmIntentUri = Uri.parse("geo:0,0?q=restroom")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            
            try {
                context.startActivity(mapIntent)
            } catch (e: ActivityNotFoundException) {
                // Fallback to browser or generic map intent
                val genericMapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                try {
                    context.startActivity(genericMapIntent)
                } catch (e2: Exception) {
                    Toast.makeText(context, "Could not open Maps", Toast.LENGTH_SHORT).show()
                }
            }
        }
    ) {
        Text("💩")
    }
}
