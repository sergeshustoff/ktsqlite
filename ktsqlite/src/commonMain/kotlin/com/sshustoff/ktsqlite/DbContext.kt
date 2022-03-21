package com.sshustoff.ktsqlite

import com.sshustoff.ktsqlite.changes.InsertContext
import com.sshustoff.ktsqlite.changes.UpdateContext
import com.sshustoff.ktsqlite.internal.SelectionColumnsExtractor
import com.sshustoff.ktsqlite.parsing.ConvertContext
import com.sshustoff.ktsqlite.predicates.core.Predicate
import com.sshustoff.ktsqlite.selection.FullSelection
import com.sshustoff.ktsqlite.selection.JoinsSelection
import com.sshustoff.ktsqlite.selection.Selectable
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToFullSelection
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToSelectable
import com.sshustoff.ktsqlite.selection.tuples.Tables
import com.sshustoff.ktsqlite.statement.StatementExecutor
import com.sshustoff.ktsqlite.statement.StatementWithArgumentsBuilder

open class DbContext(
    private val statementExecutor: StatementExecutor
) {

    suspend fun <T> inTransaction(block: DbContext.() -> T): T {
        return statementExecutor.inTransaction {
            block.invoke(this)
        }
    }

    fun <T> inTransactionBlocking(block: DbContext.() -> T): T {
        return statementExecutor.inTransactionBlocking {
            block.invoke(this)
        }
    }

    fun create(table: Table) {
        runStatement {
            sql("CREATE TABLE ${table.tableName} (")
            newLine()
            var isFirst = true

            for (column in table.columns) {
                if (!isFirst) {
                    sql(",")
                    newLine()
                } else {
                    isFirst = false
                }

                sql("${column.name} ${columnDifinition(column)}")
            }

            for (constraint in table.columns.mapNotNull { it.constraint }) {
                sql(",")
                newLine()
                sql(constraint.sqlDifinition)
            }

            sql(");")
        }
    }

    fun addColumn(column: Column<*, *>) {
        runStatement {
            sql("ALTER TABLE ${column.tableName} ADD COLUMN ${column.name} ${columnDifinition(column)}")
        }
    }

    private fun columnDifinition(column: Column<*, *>): String {
        val nullability = when (column.nullability) {
            ColumnNullability.NULL -> "NULL"
            ColumnNullability.NOT_NULL -> "NOT NULL"
        }
        val difinition = "${column.type.sqlDifinition} $nullability"
        return if (column.type.sqlDifinitionSuffix == null) {
            difinition
        } else {
            "$difinition ${column.type.sqlDifinitionSuffix}"
        }
    }

    fun runStatement(fillStatement: StatementWithArgumentsBuilder.() -> Unit) {
        val builder = StatementWithArgumentsBuilder()
        fillStatement.invoke(builder)
        val statement = builder.build()
        statementExecutor.execute(statement.sql, statement.args)
    }

    fun <T : Table> delete(from: T, where: (T) -> Predicate? = { null }) {
        StatementWithArgumentsBuilder().apply {
            sql("DELETE FROM ${from.tableName}")
            where(from)?.let {
                newLine()
                sql("WHERE ")
                it.addToStatement(this)
            }
            sql(";")
        }.build().let { statementExecutor.execute(it.sql, it.args) }
    }

    fun <T : Table> update(
        table: T,
        where: (T) -> Predicate? = { null },
        values: UpdateContext.(T) -> Unit
    ) {
        StatementWithArgumentsBuilder().apply {
            sql("UPDATE ${table.tableName}")
            newLine()
            sql("SET ")
            UpdateContext(this).apply { values(table) }
            where(table)?.let {
                newLine()
                sql("WHERE ")
                it.addToStatement(this)
            }
            sql(";")
        }.build().let { statementExecutor.execute(it.sql, it.args) }
    }

    fun <T : Table> insert(table: T, values: InsertContext.(T) -> Unit): Long {
        return StatementWithArgumentsBuilder().apply {
            sql("INSERT INTO ${table.tableName} (")
            InsertContext().apply { values(table) }.inserts.let { inserts ->
                var isFirst = true
                for (insert in inserts) {
                    if (!isFirst) {
                        sql(",")
                    }
                    sql(insert.column.name)
                    isFirst = false
                }
                sql(")")
                newLine()
                sql("VALUES (")
                isFirst = true
                for (insert in inserts) {
                    if (!isFirst) {
                        sql(",")
                    }
                    if (insert.putInArguments) {
                        argument(insert.value)
                    } else {
                        sql(insert.value)
                    }
                    isFirst = false
                }
                sql(");")
            }
        }.build().let { statementExecutor.insert(it.sql, it.args) }
    }

    fun <T : CanConvertToSelectable> select(from: T, distinct: Boolean = false) =
        JoinsSelection(Tables.One(from), distinct, from.convertToSelectable(), emptyList())

    fun <T1, R> CanConvertToFullSelection<Tables.One<T1>>.into(
        limit: Int? = null,
        convert: ConvertContext.(T1) -> R
    ): List<R> = intoInternal(limit, Tables.convertFunReceiver(convert))

    fun <T1, T2, R> CanConvertToFullSelection<Tables.Two<T1, T2>>.into(
        limit: Int? = null,
        convert: ConvertContext.(T1, T2) -> R
    ): List<R> = intoInternal(limit, Tables.convertFunReceiver(convert))

    fun <T1, T2, T3, R> CanConvertToFullSelection<Tables.Three<T1, T2, T3>>.into(
        limit: Int? = null,
        convert: ConvertContext.(T1, T2, T3) -> R
    ): List<R> = intoInternal(limit, Tables.convertFunReceiver(convert))

    fun <T1, T2, T3, T4, R> CanConvertToFullSelection<Tables.Four<T1, T2, T3, T4>>.into(
        limit: Int? = null,
        convert: ConvertContext.(T1, T2, T3, T4) -> R
    ): List<R> = intoInternal(limit, Tables.convertFunReceiver(convert))

    fun <T1, T2, T3, T4, T5, R> CanConvertToFullSelection<Tables.Five<T1, T2, T3, T4, T5>>.into(
        limit: Int? = null,
        convert: ConvertContext.(T1, T2, T3, T4, T5) -> R
    ): List<R> = intoInternal(limit, Tables.convertFunReceiver(convert))

    fun <T1, T2, T3, T4, T5, T6, R> CanConvertToFullSelection<Tables.Six<T1, T2, T3, T4, T5, T6>>.into(
        limit: Int? = null,
        convert: ConvertContext.(T1, T2, T3, T4, T5, T6) -> R
    ): List<R> = intoInternal(limit, Tables.convertFunReceiver(convert))

    fun <R> CanConvertToFullSelection<Tables.Many>.into(
        limit: Int? = null,
        convert: ConvertContext.() -> R
    ): List<R> = intoInternal(limit, Tables.convertFunReceiver(convert))

    internal fun <T: Tables, R> CanConvertToFullSelection<T>.intoInternal(
        limit: Int? = null,
        convert: ConvertContext.(T) -> R
    ): List<R> {
        val fillSelection = convertToFullSelection()

        val requiredColumns = SelectionColumnsExtractor().apply {
            ConvertContext(this).apply { convert(fillSelection.tables) }
        }.columns

        return StatementWithArgumentsBuilder().apply {
            applySelection(fillSelection, requiredColumns, limit)
            sql(";")
        }.build().let {
            statementExecutor.select(it.sql, it.args) {
                ConvertContext(it).convert(fillSelection.tables)
            }
        }
    }

    private fun StatementWithArgumentsBuilder.applySelection(
        fullSelection: FullSelection<*>,
        requiredColumns: List<Column<*, *>>,
        limit: Int? = null
    ) {
        sql("SELECT ")

        if (fullSelection.distinct) sql("DISTINCT ")

        val allRequiredColumns = requiredColumns +
            (fullSelection.where?.findUsedColumns() ?: emptyList()) +
            fullSelection.orderBy.map { it.column } +
            fullSelection.groupBy.map { it } +
            fullSelection.joins.mapNotNull { it.on }.flatMap { it.findUsedColumns() }

        sql(allRequiredColumns.joinToString { "${it.fullName} AS ${it.alias}" })
        newLine()

        sql("FROM ")
        appendSelectableSource(this, fullSelection.originTable, allRequiredColumns)

        for (join in fullSelection.joins) {
            newLine()
            sql(if (join.left) "LEFT" else "INNER")
            sql(" JOIN ")

            appendSelectableSource(this, join.selectable, allRequiredColumns)

            sql(" ON ")
            join.on?.addToStatement(this)
        }

        fullSelection.where?.let {
            newLine()
            sql("WHERE ")
            it.addToStatement(this)
        }

        if (fullSelection.groupBy.isNotEmpty()) {
            newLine()
            sql("GROUP BY ")
            sql(fullSelection.groupBy.joinToString { it.fullName })
        }

        fullSelection.having?.let {
            newLine()
            sql("HAVING ")
            it.addToStatement(this)
        }

        if (fullSelection.orderBy.isNotEmpty()) {
            newLine()
            sql("ORDER BY ")
            sql(fullSelection.orderBy.joinToString { "${it.column.alias} ${it.type.sql}" })
        }

        limit?.let {
            newLine()
            sql("LIMIT $it")
        }
    }

    private fun appendSelectableSource(
        builder: StatementWithArgumentsBuilder,
        table: Selectable<*>,
        requiredColumns: List<Column<*, *>>
    ) {
        with(builder) {
            when (table) {
                is Selectable.FromTable -> {
                    sql(table.source.tableName)
                }

                is Selectable.FromAlias -> {
                    sql(table.source.table.tableName)
                    sql(" AS ")
                    sql(table.source.alias)
                }

                is Selectable.FromQuery -> {
                    sql("(")
                    val requiredFormQuery = requiredColumns
                        .filter { it.source == table.source }
                        .mapNotNull { it.wrappedColumn }

                    applySelection(table.source.query, requiredFormQuery)

                    sql(") AS ${table.source.alias}")
                }
            }
        }
    }
}
