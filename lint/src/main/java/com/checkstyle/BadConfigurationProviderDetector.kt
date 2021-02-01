@file:Suppress("UnstableApiUsage")

package com.checkstyle

import com.android.tools.lint.detector.api.*
import com.checkstyle.IssueRegistry.Companion.BadConfigurationProviderDescription
import org.jetbrains.uast.UClass

class BadConfigurationProviderDetector : Detector(), SourceCodeScanner {
    private var correct = false

    override fun applicableSuperClasses() = listOf("androidx.work.Configuration.Provider")

    override fun visitClass(context: JavaContext, declaration: UClass) {
        correct = false
        if (context.evaluator.extendsClass(
                        declaration.javaPsi,
                        "android.app.Application",
                        false)) {
            // Application correctly extends Configuration.Provider
            correct = true
        }
    }

    override fun afterCheckRootProject(context: Context) {
        if (!correct) {
            context.report(
                    issue = IssueRegistry.BadConfigurationProviderIssue,
                    location = Location.create(context.file),
                    message = BadConfigurationProviderDescription
            )
        }
    }
}
