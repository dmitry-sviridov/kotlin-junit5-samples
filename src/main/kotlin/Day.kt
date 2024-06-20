package ru.otus

enum class Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

fun Day.isWeekend() = this == Day.SATURDAY || this == Day.SUNDAY
