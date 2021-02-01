package com.checkstyle

import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.checkstyle.IssueRegistry.Companion.BadConfigurationProviderIssue
import com.checkstyle.IssueRegistry.Companion.NoisyIssue
import com.checkstyle.Stubs.ANDROID_APP_CLASS
import com.checkstyle.Stubs.ANDROID_LOG_JAVA
import com.checkstyle.Stubs.APP_IMPLEMENTS_CONFIGURATION_PROVIDER
import com.checkstyle.Stubs.EMPTY_MANIFEST
import com.checkstyle.Stubs.LOG_WTF_JAVA
import com.checkstyle.Stubs.OTHER_CLASS_IMPLEMENTS_CONFIGURATION_PROVIDER
import com.checkstyle.Stubs.WORK_MANAGER_CONFIGURATION_INTERFACE
import org.junit.Test

class DetectorTest {
    @Test
    fun testNoisyDetector() {
        lint().files(EMPTY_MANIFEST)
                .allowMissingSdk()
                .issues(NoisyIssue)
                .run()
                .expect(
                        "AndroidManifest.xml: Information: This is a noisy issue. Feel free to ignore for now. [NoisyIssueId]\n" +
                                "0 errors, 0 warnings".trimIndent()
                )
    }

    @Test
    fun testBadConfigurationProviderDetector_success() {
        lint().files(
                WORK_MANAGER_CONFIGURATION_INTERFACE,
                ANDROID_APP_CLASS,
                APP_IMPLEMENTS_CONFIGURATION_PROVIDER)
                .allowMissingSdk()
                .issues(BadConfigurationProviderIssue)
                .run()
                .expect("No warnings.")
    }

    @Test
    fun testBadConfigurationProviderDetector_failure() {
        lint().files(
                WORK_MANAGER_CONFIGURATION_INTERFACE,
                ANDROID_APP_CLASS,
                OTHER_CLASS_IMPLEMENTS_CONFIGURATION_PROVIDER)
                .allowMissingSdk()
                .issues(BadConfigurationProviderIssue)
                .run()
                .expect(
                        """
                        project0: Error: Only an android.app.Application can implement androidx.work.Configuration.Provider [BadConfigurationProviderId]
                        1 errors, 0 warnings
                        """.trimIndent()
                )
    }

    @Test
    fun testLogWtfDetector() {
        /* ktlint-disable max-line-length */
        val expected = "No warnings.".trimIndent()
        /* ktlint-enable max-line-length */

        lint().files(
                ANDROID_LOG_JAVA,
                LOG_WTF_JAVA)
                .allowMissingSdk() // The one SDK class that we need has been added manually!
                .issues(LogWtfDetector.ISSUE)
                .run()
                .expect(expected.trimIndent())
    }
}
