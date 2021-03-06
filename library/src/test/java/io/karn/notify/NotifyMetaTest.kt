package io.karn.notify

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.provider.Settings
import android.support.v4.app.NotificationCompat
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class NotifyMetaTest : NotifyTestBase() {

    @Test
    fun defaultMetadataTest() {
        val notification = Notify.with(this.context)
                .content {
                    title = "New dessert menu"
                    text = "The Cheesecake Factory has a new dessert for you to try!"
                }
                .asBuilder()
                .build()

        Assert.assertNull(notification.contentIntent)
        Assert.assertNull(notification.deleteIntent)
        Assert.assertTrue((notification.flags and NotificationCompat.FLAG_AUTO_CANCEL) != 0)
        Assert.assertNull(notification.category)
        Assert.assertEquals(NotificationCompat.PRIORITY_DEFAULT, notification.priority)
    }

    @Test
    fun modifiedMetadataTest() {
        val testClickIntent = PendingIntent.getActivity(this.context, 0, Intent(Settings.ACTION_SYNC_SETTINGS), 0)
        val testClearIntent = PendingIntent.getActivity(this.context, 0, Intent(Settings.ACTION_SETTINGS), 0)

        val testCancelOnClick = false
        val testCategory = NotificationCompat.CATEGORY_STATUS
        val testPriority = NotificationCompat.PRIORITY_MAX

        val notification = Notify.with(this.context)
                .meta {
                    clickIntent = testClickIntent
                    clearIntent = testClearIntent
                    cancelOnClick = testCancelOnClick
                    category = testCategory
                    priority = testPriority
                }
                .content {
                    title = "New dessert menu"
                    text = "The Cheesecake Factory has a new dessert for you to try!"
                }
                .asBuilder()
                .build()

        Assert.assertEquals(testClickIntent, notification.contentIntent)
        Assert.assertEquals(testClearIntent, notification.deleteIntent)
        Assert.assertEquals(testCancelOnClick, (notification.flags and NotificationCompat.FLAG_AUTO_CANCEL) != 0)
        Assert.assertEquals(testCategory, notification.category)
        Assert.assertEquals(testPriority, notification.priority)
    }
}
