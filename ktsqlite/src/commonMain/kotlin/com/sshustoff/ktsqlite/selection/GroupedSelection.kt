package com.sshustoff.ktsqlite.selection

import com.sshustoff.ktsqlite.Column
import com.sshustoff.ktsqlite.internal.Join
import com.sshustoff.ktsqlite.predicates.core.Predicate
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToHavingableSelection
import com.sshustoff.ktsqlite.selection.tuples.Tables

class GroupedSelection<TablesRepresentation: Tables> internal constructor(
    internal val tables: TablesRepresentation,
    internal val distinct: Boolean,
    internal val originTable: Selectable<*>,
    internal val joins: List<Join>,
    internal val where: Predicate?,
    internal val groupBy: List<Column<*, *>>
) : CanConvertToHavingableSelection<TablesRepresentation> {

    override fun convertToHavingableSelection() = this

    internal fun havingInternal(having: (TablesRepresentation) -> Predicate?) =
        OrderableSelection(
            tables = tables,
            distinct = distinct,
            originTable = originTable,
            joins = joins,
            where = where,
            groupBy = groupBy,
            having = having(tables)
        )
}

fun <T> CanConvertToHavingableSelection<Tables.One<T>>.having(
    condition: (T) -> Predicate
) = convertToHavingableSelection().havingInternal(Tables.convertFun(condition))

fun <T1, T2> CanConvertToHavingableSelection<Tables.Two<T1, T2>>.having(
    condition: (T1, T2) -> Predicate
) = convertToHavingableSelection().havingInternal(Tables.convertFun(condition))

fun <T1, T2, T3> CanConvertToHavingableSelection<Tables.Three<T1, T2, T3>>.having(
    condition: (T1, T2, T3) -> Predicate
) = convertToHavingableSelection().havingInternal(Tables.convertFun(condition))

fun <T1, T2, T3, T4> CanConvertToHavingableSelection<Tables.Four<T1, T2, T3, T4>>.having(
    condition: (T1, T2, T3, T4) -> Predicate
) = convertToHavingableSelection().havingInternal(Tables.convertFun(condition))

fun <T1, T2, T3, T4, T5> CanConvertToHavingableSelection<Tables.Five<T1, T2, T3, T4, T5>>.having(
    condition: (T1, T2, T3, T4, T5) -> Predicate
) = convertToHavingableSelection().havingInternal(Tables.convertFun(condition))

fun <T1, T2, T3, T4, T5, T6> CanConvertToHavingableSelection<Tables.Six<T1, T2, T3, T4, T5, T6>>.having(
    condition: (T1, T2, T3, T4, T5, T6) -> Predicate
) = convertToHavingableSelection().havingInternal(Tables.convertFun(condition))

fun CanConvertToHavingableSelection<Tables.Many>.having(
    condition: () -> Predicate
) = convertToHavingableSelection().havingInternal(Tables.convertFun(condition))