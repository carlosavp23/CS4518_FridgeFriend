package com.example.project1

import android.app.Application

class Project1Application : Application() {
    override fun onCreate() {
        super.onCreate()
        GameRepository.initialize(this)
    }
}