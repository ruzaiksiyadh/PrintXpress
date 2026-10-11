package lk.printxpress.data.model

enum class OrderStatus(val displayName: String, val stepIndex: Int) {
    CONFIRMED("Confirmed", 0),
    PROCESSING("Processing", 1),
    PRINTING("Printing", 2),
    READY_FOR_PICKUP("Ready for Pickup", 3),
    COMPLETED("Completed", 4),
    CANCELLED("Cancelled", -1);

    fun canCancel(): Boolean {
        return this == CONFIRMED || this == PROCESSING
    }

    fun canReschedule(): Boolean {
        return this == CONFIRMED || this == PROCESSING
    }

    companion object {
        fun fromString(value: String): OrderStatus {
            return try {
                valueOf(value.uppercase())
            } catch (e: Exception) {
                CONFIRMED
            }
        }
    }
}
