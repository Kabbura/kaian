package com.narbase.kabbura.kaian.data.enums

import com.narbase.kabbura.kaian.data.columntypes.EnumPersistenceName
import com.narbase.kabbura.kaian.dto.common.EnumDtoName

/*
 * Copyright 2017-2020 Narbase technologies and contributors. Use of this source code is governed by the MIT License.
 */

enum class Gender(override val persistenceName: String, override val dtoName: String) : EnumPersistenceName,
    EnumDtoName {
    Male("Male", "Male"),
    Female("Female", "Female")
}