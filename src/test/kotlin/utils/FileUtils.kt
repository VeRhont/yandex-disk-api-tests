package utils

import java.io.File

object FileUtils {

    fun createTempFile(content: String): File {
        val file = File.createTempFile("test", ".txt").apply {
            writeText(content)
        }
        return file
    }
}
