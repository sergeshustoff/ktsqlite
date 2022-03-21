package com.sshustoff.ktsqlite.selection

import com.sshustoff.ktsqlite.Column
import com.sshustoff.ktsqlite.internal.Join
import com.sshustoff.ktsqlite.predicates.core.Predicate
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToGroupeableSelection
import com.sshustoff.ktsqlite.selection.tuples.Tables

class ConditionalSelection<TablesRepresentation: Tables> internal constructor(
    internal val tables: TablesRepresentation,
    internal val distinct: Boolean,
    internal val originTable: Selectable<*>,
    internal val joins: List<Join>,
    internal val where: Predicate?
) : CanConvertToGroupeableSelection<TablesRepresentation> {

    override fun convertToGroupeableSelection() = this

    internal fun groupByInternal(groupBy: (TablesRepresentation) -> List<Column<*, *>>) =
        GroupedSelection(
            tables = tables,
            where = where,
            distinct = distinct,
            groupBy = groupBy(tables),
            originTable = originTable,
            joins = joins
        )

    internal fun groupBySingleInternal(groupBy: (TablesRepresentation) -> Column<*, *>) =
        GroupedSelection(
            tables = tables,
            where = where,
            distinct = distinct,
            groupBy = listOf(groupBy(tables)),
            originTable = originTable,
            joins = joins
        )
}

fun <T1> CanConvertToGroupeableSelection<Tables.One<T1>>.groupBy(
    groupBy: (T1) -> List<Column<*, *>>
) = convertToGroupeableSelection().groupByInternal(Tables.convertFun(groupBy))

fun <T1> CanConvertToGroupeableSelection<Tables.One<T1>>.groupBySingle(
    groupBy: (T1) -> Column<*, *>
) = convertToGroupeableSelection().groupBySingleInternal(Tables.convertFun(groupBy))

fun <T1, T2> CanConvertToGroupeableSelection<Tables.Two<T1, T2>>.groupBy(
    groupBy: (T1, T2) -> List<Column<*, *>>
) = convertToGroupeableSelection().groupByInternal(Tables.convertFun(groupBy))

fun <T1, T2> CanConvertToGroupeableSelection<Tables.Two<T1, T2>>.groupBySingle(
    groupBy: (T1, T2) -> Column<*, *>
) = convertToGroupeableSelection().groupBySingleInternal(Tables.convertFun(groupBy))

fun <T1, T2, T3> CanConvertToGroupeableSelection<Tables.Three<T1, T2, T3>>.groupBy(
    groupBy: (T1, T2, T3) -> List<Column<*, *>>
) = convertToGroupeableSelection().groupByInternal(Tables.convertFun(groupBy))

fun <T1, T2, T3> CanConvertToGroupeableSelection<Tables.Three<T1, T2, T3>>.groupBySingle(
    groupBy: (T1, T2, T3) -> Column<*, *>
) = convertToGroupeableSelection().groupBySingleInternal(Tables.convertFun(groupBy))

fun <T1, T2, T3, T4> CanConvertToGroupeableSelection<Tables.Four<T1, T2, T3, T4>>.groupBy(
    groupBy: (T1, T2, T3, T4) -> List<Column<*, *>>
) = convertToGroupeableSelection().groupByInternal(Tables.convertFun(groupBy))

fun <T1, T2, T3, T4> CanConvertToGroupeableSelection<Tables.Four<T1, T2, T3, T4>>.groupBySingle(
    groupBy: (T1, T2, T3, T4) -> Column<*, *>
) = convertToGroupeableSelection().groupBySingleInternal(Tables.convertFun(groupBy))

fun <T1, T2, T3, T4, T5> CanConvertToGroupeableSelection<Tables.Five<T1, T2, T3, T4, T5>>.groupBy(
    groupBy: (T1, T2, T3, T4, T5) -> List<Column<*, *>>
) = convertToGroupeableSelection().groupByInternal(Tables.convertFun(groupBy))

fun <T1, T2, T3, T4, T5> CanConvertToGroupeableSelection<Tables.Five<T1, T2, T3, T4, T5>>.groupBySingle(
    groupBy: (T1, T2, T3, T4, T5) -> Column<*, *>
) = convertToGroupeableSelection().groupBySingleInternal(Tables.convertFun(groupBy))

fun <T1, T2, T3, T4, T5, T6> CanConvertToGroupeableSelection<Tables.Six<T1, T2, T3, T4, T5, T6>>.groupBy(
    groupBy: (T1, T2, T3, T4, T5, T6) -> List<Column<*, *>>
) = convertToGroupeableSelection().groupByInternal(Tables.convertFun(groupBy))

fun <T1, T2, T3, T4, T5, T6> CanConvertToGroupeableSelection<Tables.Six<T1, T2, T3, T4, T5, T6>>.groupBySingle(
    groupBy: (T1, T2, T3, T4, T5, T6) -> Column<*, *>
) = convertToGroupeableSelection().groupBySingleInternal(Tables.convertFun(groupBy))

fun CanConvertToGroupeableSelection<Tables.Many>.groupBy(
    groupBy: () -> List<Column<*, *>>
) = convertToGroupeableSelection().groupByInternal(Tables.convertFun(groupBy))

fun CanConvertToGroupeableSelection<Tables.Many>.groupBySingle(
    groupBy: () -> Column<*, *>
) = convertToGroupeableSelection().groupBySingleInternal(Tables.convertFun(groupBy))