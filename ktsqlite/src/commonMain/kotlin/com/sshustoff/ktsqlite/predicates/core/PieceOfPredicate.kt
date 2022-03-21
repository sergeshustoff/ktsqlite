package com.sshustoff.ktsqlite.predicates.core

import com.sshustoff.ktsqlite.Column
import com.sshustoff.ktsqlite.statement.StatementWithArgumentsBuilder

interface PieceOfPredicate : CanConvertToPieceOfPredicate {

    fun addToStatement(statementBuilder: StatementWithArgumentsBuilder)
    fun findUsedColumns(): List<Column<*, *>> = emptyList()

    override fun convertToPieceOfPredicate() = this
}