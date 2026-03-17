package utils

import java.util.*

object TestDataFactory {

    private const val FOLDER_PREFIX = "test-folder-"
    private const val FILE_PREFIX = "test-file-"

    fun randomFolderName(): String {
        return FOLDER_PREFIX + UUID.randomUUID()
    }

    fun randomFileName(): String {
        return FILE_PREFIX + UUID.randomUUID() + ".txt"
    }
}
