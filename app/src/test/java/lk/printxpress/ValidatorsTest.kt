package lk.printxpress

import lk.printxpress.utils.SecurityUtils
import lk.printxpress.utils.Validators
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidatorsTest {

    @Test
    fun testNameValidation() {
        assertTrue(Validators.isValidName("Test Customer"))
        assertFalse(Validators.isValidName(""))
        assertFalse(Validators.isValidName("A"))
    }

    @Test
    fun testEmailValidation() {
        assertTrue(Validators.isValidEmail("testcustomer@example.com"))
        assertTrue(Validators.isValidEmail("ruzaik@domain.lk"))
        assertFalse(Validators.isValidEmail("invalid-email"))
        assertFalse(Validators.isValidEmail("missing@domain"))
        assertFalse(Validators.isValidEmail(""))
    }

    @Test
    fun testSriLankanPhoneValidation() {
        assertTrue(Validators.isValidPhone("0771234567"))
        assertTrue(Validators.isValidPhone("+94771234567"))
        assertTrue(Validators.isValidPhone("0719876543"))
        assertFalse(Validators.isValidPhone("12345"))
        assertFalse(Validators.isValidPhone("abc"))
        assertFalse(Validators.isValidPhone("0112508899")) // Landline, mobile 07 required
    }

    @Test
    fun testPasswordValidation() {
        assertTrue(Validators.isValidPassword("TestPassword123"))
        assertFalse(Validators.isValidPassword("short"))
        assertFalse(Validators.isValidPassword("allletters"))
        assertFalse(Validators.isValidPassword("12345678"))
    }

    @Test
    fun testQuantityValidation() {
        assertTrue(Validators.isValidQuantity(100))
        assertTrue(Validators.isValidQuantity(1))
        assertFalse(Validators.isValidQuantity(0))
        assertFalse(Validators.isValidQuantity(-10))
    }

    @Test
    fun testSupportedFileTypes() {
        assertTrue(Validators.isSupportedFileType("poster.pdf"))
        assertTrue(Validators.isSupportedFileType("artwork.png"))
        assertTrue(Validators.isSupportedFileType("logo.jpg"))
        assertTrue(Validators.isSupportedFileType("vector.ai"))
        assertFalse(Validators.isSupportedFileType("virus.exe"))
        assertFalse(Validators.isSupportedFileType("notes.txt"))
    }

    @Test
    fun testPasswordHashingSecurity() {
        val raw = "TestPassword123"
        val hash = SecurityUtils.hashPassword(raw)
        assertNotEquals(raw, hash)
        assertTrue(SecurityUtils.verifyPassword(raw, hash))
        assertFalse(SecurityUtils.verifyPassword("WrongPassword", hash))
    }
}
