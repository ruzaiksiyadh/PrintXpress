package lk.printxpress.utils

object Validators {
    fun isValidName(name: String): Boolean {
        return name.trim().length >= 2
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        return email.trim().matches(Regex(emailRegex))
    }

    /**
     * Validates Sri Lankan phone numbers:
     * Accepts: 07XXXXXXXX (10 digits) or +947XXXXXXXX or 947XXXXXXXX
     */
    fun isValidPhone(phone: String): Boolean {
        val cleaned = phone.replace("\\s|-".toRegex(), "")
        val slPhoneRegex = "^(0|\\+?94)7[0-9]{8}$"
        return cleaned.matches(Regex(slPhoneRegex))
    }

    fun isValidPassword(password: String): Boolean {
        // At least 6 characters, containing at least one letter and one number
        return password.length >= 6 &&
                password.any { it.isLetter() } &&
                password.any { it.isDigit() }
    }

    fun isValidQuantity(quantity: Int): Boolean {
        return quantity > 0
    }

    fun isSupportedFileType(fileName: String): Boolean {
        val ext = fileName.substringAfterLast('.', "").lowercase()
        return ext in listOf("pdf", "png", "jpg", "jpeg", "ai", "psd", "tiff", "eps")
    }

    fun isWithinCharacterLimit(text: String, maxChars: Int = 500): Boolean {
        return text.length <= maxChars
    }
}
