package com.sshustoff.ktsqlite

import com.sshustoff.ktsqlite.internal.ColumnInPredicate
import com.sshustoff.ktsqlite.predicates.core.CanConvertToPieceOfPredicate

class Column<T : ColumnType, N : ColumnNullability> internal constructor(
    val tableName: String,
    val name: String,
    internal val type: T,
    internal val nullability: N,
    internal val source: Any,
    internal val wrappedColumn: Column<*, *>? = null,
    internal val constraint: ColumnConstraint? = null,
    internal val fullName: String = "$tableName.$name",
    val alias: String = "${tableName}_$name",
    internal val args: List<String> = emptyList()
) : CanConvertToPieceOfPredicate {

    //TODO: make extensions for typed columns
    val count by lazy {
        aggregate("COUNT")
    }

    val max by lazy {
        aggregate("MAX")
    }

    val min by lazy {
        aggregate("MIN")
    }

    val sum by lazy {
        aggregate("SUM")
    }

    val asc: Order
        get() = Order(this, Order.Type.ASC)

    val desc: Order
        get() = Order(this, Order.Type.DESC)

    private fun aggregate(functionName: String) = Column(
        tableName = tableName,
        name = "${name}_${functionName.toLowerCase()}",
        type = ColumnType.Long,
        nullability = ColumnNullability.NULL,
        alias = "${tableName}_${name}_${functionName.toLowerCase()}",
        fullName = "$functionName($fullName)",
        source = source,
        wrappedColumn = this
    )

    override fun convertToPieceOfPredicate() = ColumnInPredicate(this)
}