package com.emerson.bankapp.loginScreen


class LoginModel
data class LoginViewModel (
    //TODO - filter to have only the needed data
    var user: String?,
    var password: String?
)

data class LoginRequest (
    var loginViewModel: LoginViewModel
)

data class LoginResponse (
    var userAccount : UserAccount,
    var error : ErrorResponse
)

data class UserAccount (
    var userId : Int?,
    var name : String?,
    var bankAccount : String?,
    var agency : String?,
    var balance : Double?
)

data class ErrorResponse (
    var code : Int?,
    var message : String?
)

sealed class LoginBusiness {

    object UserInvalid : BusinessException()
    object PasswordInvalid : BusinessException()
    object PasswordNeedSpecialCharacter : BusinessException()
    object PasswordNeedCapitalizedLetter : BusinessException()
    object PasswordNeedNumber : BusinessException()

}

abstract class BusinessException : AppException()

sealed class AppException(message: String = "") : RuntimeException(message)
