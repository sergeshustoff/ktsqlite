package com.sshustoff.ktsqlite

data class Order(
    val column: Column<*, *>,
    val type: Type
) {

    enum class Type(val sql: String) {
        ASC("ASC"),
        DESC("DESC")
    }
}