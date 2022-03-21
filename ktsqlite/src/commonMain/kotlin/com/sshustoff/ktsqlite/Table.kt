package com.sshustoff.ktsqlite

import com.sshustoff.ktsqlite.selection.Selectable
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToSelectable

open class Table(
    val tableName: String
) : CanConvertToSelectable {

    val columns: List<Column<*, *>>
        get() = columnsInternal

    private val columnsInternal = ArrayList<Column<*, *>>()

    val id = internalColumn {
        Column(
            tableName,
            idName,
            ColumnType.Id,
            ColumnNullability.NOT_NULL,
            this
        )
    }

    fun text(
        name: String
    ) = internalColumn {
        Column(
            tableName,
            name,
            ColumnType.Text,
            ColumnNullability.NOT_NULL,
            this
        )
    }

    fun textOrNull(
        name: String
    ) = internalColumn { Column(tableName, name, ColumnType.Text, ColumnNullability.NULL, this) }

    fun long(
        name: String
    ) = internalColumn {
        Column(
            tableName,
            name,
            ColumnType.Long,
            ColumnNullability.NOT_NULL,
            this
        )
    }

    fun longOrNull(
        name: String
    ) = internalColumn { Column(tableName, name, ColumnType.Long, ColumnNullability.NULL, this) }

    fun float(
        name: String
    ) = internalColumn {
        Column(
            tableName,
            name,
            ColumnType.Float,
            ColumnNullability.NOT_NULL,
            this
        )
    }

    fun floatOrNull(
        name: String
    ) = internalColumn { Column(tableName, name, ColumnType.Float, ColumnNullability.NULL, this) }

    fun reference(
        to: Table,
        name: String = "${to.tableName}_${to.id.name}"
    ) = internalReference(ColumnType.Long, ColumnNullability.NOT_NULL, to, name)

    fun referenceOrNull(
        to: Table,
        name: String = "${to.tableName}_${to.id.name}",
        onDeleteCascade: Boolean = false
    ) = internalReference(ColumnType.Long, ColumnNullability.NULL, to, name, onDeleteCascade)

    private fun <T : ColumnType, N : ColumnNullability> internalReference(
        type: T,
        nullability: N,
        to: Table,
        name: String = "${to.tableName}_${to.id.name}",
        onDeleteCascade: Boolean = nullability == ColumnNullability.NOT_NULL
    ) = internalColumn {
        Column(
            tableName = tableName,
            type = type,
            nullability = nullability,
            name = name,
            source = this,
            constraint = ColumnConstraint.Reference(
                name, to.id,
                onDeleteCascade = onDeleteCascade
            )
        )
    }

    private fun <T : ColumnType, N : ColumnNullability> internalColumn(
        create: () -> Column<T, N>
    ): Column<T, N> {
        return create().also { columnsInternal.add(it) }
    }

    override fun convertToSelectable() = Selectable.FromTable(this)

    companion object {
        private const val idName = "id"
    }
}