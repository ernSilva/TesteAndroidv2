package com.emerson.bankapp.commons.extensions

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

val ViewModel.ioScope: CoroutineScope
    get() = CoroutineScope(Dispatchers.IO)