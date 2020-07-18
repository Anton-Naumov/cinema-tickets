package com.kpk.cinematickets.wallet

import com.kpk.cinematickets.commons.BusinessException

class InsufficientClientMoney(message: String): BusinessException(message)
