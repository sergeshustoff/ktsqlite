package com.sshustoff.ktsqlite.statement

class StatementWithArgumentsBuilder {
    private val sqlBuilder = StringBuilder()
    private val argsList = ArrayList<String>()

    fun newLine() {
        sqlBuilder.append("\r\n")
    }

    fun sql(text: String) {
        sqlBuilder.append(text)
    }

    fun argument(argument: String) {
        sqlBuilder.append("?")
        argsList.add(argument)
    }

    fun build() = StatementWithArguments(sqlBuilder.toString(), argsList.toTypedArray())
}