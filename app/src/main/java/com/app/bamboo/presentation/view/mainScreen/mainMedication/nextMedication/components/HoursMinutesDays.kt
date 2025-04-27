package com.app.bamboo.presentation.view.mainScreen.mainMedication.nextMedication.components

fun hoursMinutesDays(
    time1: Long,
    time2: Long,
    inHours: String,
    inMinutes: String,
    and: String,
    inDays: String,
    suffix: String
): String {
    var phrase = ""
    if (time1 <= 24) {
        phrase = if (time1 == 0L) {
            String.format("$suffix %02d $inMinutes", time2)
        } else {
            String.format("$suffix %02d $inHours $and %02d $inMinutes", time1, time2)
        }
    } else {
        val days = time1 / 24
        val hoursInDays = time1 % 24
        phrase = String.format("$suffix %02d $inDays $and %02d $inHours", days, hoursInDays)
    }
    return phrase
}