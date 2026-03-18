package utils

import java.util.*

object TestDataFactory {

    private const val FOLDER_PREFIX = "test-folder-"
    private const val FILE_PREFIX = "test-file-"

    fun getRandomFolderName(): String {
        return FOLDER_PREFIX + UUID.randomUUID()
    }

    fun getRandomFileName(): String {
        return FILE_PREFIX + UUID.randomUUID() + ".txt"
    }
}
