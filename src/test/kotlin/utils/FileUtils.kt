@file:Suppress("DEPRECATION")

package utils

object FileUtils {

    private const val PREFIX = "test"
    private const val SUFFIX = ".txt"

    fun createTempFile(content: String) =
        createTempFile(PREFIX, SUFFIX).apply {
            writeText(content)
        }
}
