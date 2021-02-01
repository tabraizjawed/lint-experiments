package com.checkstyle

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestFiles.java
import com.android.tools.lint.checks.infrastructure.TestFiles.manifest

object Stubs {

    val EMPTY_MANIFEST: TestFile = manifest(
            """
               <manifest xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:tools="http://schemas.android.com/tools"
                  package="com.example">
                  <application>

                  </application>
                </manifest>
            """).indented()


    val WORK_MANAGER_CONFIGURATION_INTERFACE: TestFile = kotlin(
            "androidx/work/Configuration.kt",
            """
                 package androidx.work
                 interface Configuration {
                   interface Provider {
                     fun getWorkManagerConfiguration(): Configuration
                   } 
                 }  
            """)
            .indented().within("src")

    val ANDROID_APP_CLASS: TestFile = kotlin(
            "android/app/Application.kt",
            """
                package android.app
                open class Application {
                  fun onCreate() {
                      
                  }
                }
            """)
            .indented().within("src")

    val APP_IMPLEMENTS_CONFIGURATION_PROVIDER: TestFile = kotlin(
            "com/example/App.kt",
            """
                package com.example
                
                import android.app.Application
                import androidx.work.Configuration
                
                class Config : Configuration
                
                class App : Configuration.Provider, Application() {
                  override fun onCreate() {
                  }
                  override fun getWorkManagerConfiguration(): Configuration = Config()
                }
            """)
            .indented().within("src")

    val OTHER_CLASS_IMPLEMENTS_CONFIGURATION_PROVIDER: TestFile = kotlin(
            "com/example/App.kt",
            """
                package com.example
                import androidx.work.Configuration
                
                class Config : Configuration
                
                class App : Configuration.Provider {
                  override fun onCreate() {
                  }
                  override fun getWorkManagerConfiguration(): Configuration = Config()
                }
            """)
            .indented().within("src")


    /**
     * [TestFile] containing Log.java from the Android SDK.
     *
     * This is a hacky workaround for the Android SDK not being included on the Lint test harness
     * classpath. Ideally, we'd specify ANDROID_HOME as an environment variable.
     */
    val ANDROID_LOG_JAVA = java(
            """
                package android.util;
                
                public class Log {
                    public static void wtf(String tag, String msg) {
                        // Stub!
                    }
                }
            """.trimIndent())

    val LOG_WTF_KT = kotlin(
            "com.checkstyle/app/WhatATerribleFailure.kt",
            """
                package com.checkstyle.app
                
                import android.util.Log
                
                class WhatATerribleFailure {
                    fun <T> logAsWtf(clazz: Class<T>, message: String) {
                        Log.wtf(clazz.name, message)

                        wtf(message)
                    }
                
                    fun wtf(message: String) {
                        Log.d("TAG", message)
                    }
                }
            """).indented().within("src")

    val LOG_WTF_JAVA = java(
            "com.checkstyle/app/WhatATerribleFailureJava.java",
            """
                package com.checkstyle.app;
                
                import android.util.Log;
                
                class WhatATerribleFailureJava {
                    void logAsWtf(Class<?> clazz, String message) {
                        Log.wtf(clazz.getName(), message);
                
                        wtf(message);
                    }
                
                    void wtf(String message) {
                        Log.d("TAG", message);
                    }
                }
            """).indented().within("src")
}
