@file:Suppress("UnstableApiUsage")

package com.checkstyle

import com.android.tools.lint.detector.api.*
import com.checkstyle.IssueRegistry.Companion.NoisyIssue
import com.checkstyle.IssueRegistry.Companion.NoisyIssueDescription
import org.w3c.dom.Element

class NoisyDetector : Detector(), XmlScanner {
    override fun getApplicableElements() = listOf("manifest")

    override fun visitElement(context: XmlContext, element: Element) {
    }

    override fun afterCheckFile(context: Context) {
        context.report(NoisyIssue, Location.create(context.file), NoisyIssueDescription)
    }
}
