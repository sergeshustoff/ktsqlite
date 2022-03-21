package com.sshustoff.ktsqlite

sealed class ColumnType(
    internal val sqlDifinition: String,
    internal val sqlDifinitionSuffix: String? = null
) {

    object Long : ColumnType("INTEGER")
    object Text : ColumnType("TEXT")
    object Float : ColumnType("FLOAT")
    object Id : ColumnType("INTEGER", sqlDifinitionSuffix = "PRIMARY KEY AUTOINCREMENT")
}