package com.emerson.bankapp.loginScreen

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class CredentialsValidatorTest {

    @Test
    fun `When enter correct password, expect success`() {
        val result = CredentialsValidator.isPasswordValid("Abc@1")
        assertThat(result, equalTo(true))
    }

    @Test
    fun `When enter correct password without number, expect false`() {
        val result = CredentialsValidator.isPasswordValid("Test@")
        assertThat(result, equalTo(false))
    }

    @Test
    fun `When enter correct password without special character, expect false`() {
        val result = CredentialsValidator.isPasswordValid("Test1")
        assertThat(result, equalTo(false))
    }

    @Test
    fun `When enter correct password without uppercase letter, expect false`() {
        val result = CredentialsValidator.isPasswordValid("test@1")
        assertThat(result, equalTo(false))
    }

    @Test
    fun `When enter valid cpf as user, expect success`() {
        val result = CredentialsValidator.isUserValid("123.456.789-10")
        assertThat(result, equalTo(true))
    }

    @Test
    fun `When enter invalid cpf as user, expect false`() {
        val result = CredentialsValidator.isUserValid("123.456.789-01")
        assertThat(result, equalTo(false))
    }

    @Test
    fun `When enter cpf in right format and invalid character as user, expect false`() {
        val result = CredentialsValidator.isUserValid("abc.456.789-1")
        assertThat(result, equalTo(false))
    }

    @Test
    fun `When enter valid email as user, expect success`() {
        val result = CredentialsValidator.isUserValid("ern@email.com")
        assertThat(result, equalTo(true))
    }

    @Test
    fun `When enter INvalid email as user, expect false`() {
        val result = CredentialsValidator.isUserValid("meu_email@email.")
        assertThat(result, equalTo(false))
    }
}