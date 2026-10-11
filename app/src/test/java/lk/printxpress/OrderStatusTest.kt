package lk.printxpress

import lk.printxpress.data.model.OrderStatus
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OrderStatusTest {

    @Test
    fun testCancellationAllowedBeforePrinting() {
        // T15: Cancel order before printing -> Allowed
        assertTrue(OrderStatus.CONFIRMED.canCancel())
        assertTrue(OrderStatus.PROCESSING.canCancel())
    }

    @Test
    fun testCancellationPreventedDuringOrAfterPrinting() {
        // T16: Cancel order during/after printing -> Prevented
        assertFalse(OrderStatus.PRINTING.canCancel())
        assertFalse(OrderStatus.READY_FOR_PICKUP.canCancel())
        assertFalse(OrderStatus.COMPLETED.canCancel())
        assertFalse(OrderStatus.CANCELLED.canCancel())
    }

    @Test
    fun testRescheduleAllowedBeforePrinting() {
        assertTrue(OrderStatus.CONFIRMED.canReschedule())
        assertTrue(OrderStatus.PROCESSING.canReschedule())
        assertFalse(OrderStatus.PRINTING.canReschedule())
        assertFalse(OrderStatus.COMPLETED.canReschedule())
    }
}
