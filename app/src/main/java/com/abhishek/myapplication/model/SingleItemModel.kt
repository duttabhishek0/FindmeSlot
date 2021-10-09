package com.abhishek.myapplication.model


/**
 * Data class to hold the data
 *
 * @property centerName
 * @property centerAddress
 * @property centerFromTiming
 * @property centerToTiming
 * @property feeType
 * @property ageLimit
 * @property vaccineName
 * @property availableCapacity
 */
data class SingleItemModel(
    val centerName: String,
    val centerAddress: String,
    val centerFromTiming: String,
    var centerToTiming: String,
    var feeType: String,
    var ageLimit: Int,
    var vaccineName: String,
    var availableCapacity: Int
)