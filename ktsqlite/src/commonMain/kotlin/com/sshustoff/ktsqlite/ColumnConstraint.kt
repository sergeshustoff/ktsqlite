package com.sshustoff.ktsqlite

sealed class ColumnConstraint(
    val sqlDifinition: String
) {

    data class Reference(
        val name: String,
        val to: Column<ColumnType.Id, ColumnNullability.NOT_NULL>,
        val onDeleteCascade: Boolean
    ) : ColumnConstraint(
        "FOREIGN KEY($name) REFERENCES ${to.tableName}(${to.name}) ON DELETE ${if (onDeleteCascade) "CASCADE" else "SET NULL"}"
    )
}