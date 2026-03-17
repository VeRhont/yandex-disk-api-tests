import models.DiskInfoResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DiskInfoTests : BaseTest() {

    @Test
    fun `should return disk info`() {
        val response = diskApi.getDiskInfo()
        val disk = response.`as`(DiskInfoResponse::class.java)

        assertThat(response.statusCode).isEqualTo(200)
        assertThat(disk.totalSpace).isGreaterThan(0)
    }

    @Test
    fun `should validate disk space consistency`() {
        val disk = diskApi.getDiskInfo().`as`(DiskInfoResponse::class.java)

        assertThat(disk.usedSpace).isLessThanOrEqualTo(disk.totalSpace)
        assertThat(disk.trashSize).isLessThanOrEqualTo(disk.usedSpace)
    }
}
