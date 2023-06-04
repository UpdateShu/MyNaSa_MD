package com.geekbrains.mynasa_md.model.response

data class SolarFlareResponseData (
    val flrID : String,
    val beginTime: String,
    val peakTime: String,
    val endTime: Any? = null,
    val classType: String,
    val sourceLocation: String,
    val activeRegionNum: Long,
    val link: String
) {
    override fun toString(): String {
        return "Начало вспышки: ${beginTime}\nКонец вспышки: ${endTime}\nПик: ${peakTime}\nКласс: ${classType}\nИсточник: ${sourceLocation}\nРегион: ${activeRegionNum}\n"
    }
}
