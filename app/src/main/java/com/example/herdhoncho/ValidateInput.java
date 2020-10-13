package com.example.herdhoncho;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

public class ValidateInput {
    Context context;

    ValidateInput(Context context)
    {
        this.context = context;
    }

    // Method 1: Validate email
    boolean checkIfEmailIsValid(String email)
    {
        if(email.length()==0)
        {
            Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) // Validate email format
        {
            Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            // Email is valid
            return true;
        }
    }

    // Method 2: Validate password
    boolean checkIfPasswordIsValid(String password)
    {
        if(password.length()==0)
        {
            Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(password.length()<6)
        {
            Toast.makeText(context, "Please enter a password with at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            // Password is valid
            return true;
        }
    }
}
