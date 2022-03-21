package com.sshustoff.ktsqlite.selection

import com.sshustoff.ktsqlite.Column
import com.sshustoff.ktsqlite.Order
import com.sshustoff.ktsqlite.internal.Join
import com.sshustoff.ktsqlite.predicates.core.Predicate
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToOrderableSelection
import com.sshustoff.ktsqlite.selection.tuples.Tables

class OrderableSelection<TablesRepresentation : Tables> internal constructor(
    internal val tables: TablesRepresentation,
    internal val distinct: Boolean,
    internal val originTable: Selectable<*>,
    internal val joins: List<Join>,
    internal val where: Predicate?,
    internal val groupBy: List<Column<*, *>>,
    internal val having: Predicate?
) : CanConvertToOrderableSelection<TablesRepresentation> {

    override fun convertToOrderableSelection() = this

    internal fun orderByInternal(order: (TablesRepresentation) -> List<Order>) =
        FullSelection(
            tables = tables,
            where = where,
            distinct = distinct,
            orderBy = order(tables),
            groupBy = groupBy,
            having = having,
            originTable = originTable,
            joins = joins
        )

    internal fun orderBySingleInternal(order: (TablesRepresentation) -> Order) =
        orderByInternal { listOf(order.invoke(it)) }
}


fun <T1> CanConvertToOrderableSelection<Tables.One<T1>>.orderBy(
    orderBy: (T1) -> List<Order>
) = convertToOrderableSelection().orderByInternal(Tables.convertFun(orderBy))

fun <T1> CanConvertToOrderableSelection<Tables.One<T1>>.orderBySingle(
    orderBy: (T1) -> Order
) = convertToOrderableSelection().orderBySingleInternal(Tables.convertFun(orderBy))

fun <T1, T2> CanConvertToOrderableSelection<Tables.Two<T1, T2>>.orderBy(
    orderBy: (T1, T2) -> List<Order>
) = convertToOrderableSelection().orderByInternal(Tables.convertFun(orderBy))

fun <T1, T2> CanConvertToOrderableSelection<Tables.Two<T1, T2>>.orderBySingle(
    orderBy: (T1, T2) -> Order
) = convertToOrderableSelection().orderBySingleInternal(Tables.convertFun(orderBy))

fun <T1, T2, T3> CanConvertToOrderableSelection<Tables.Three<T1, T2, T3>>.orderBy(
    orderBy: (T1, T2, T3) -> List<Order>
) = convertToOrderableSelection().orderByInternal(Tables.convertFun(orderBy))

fun <T1, T2, T3> CanConvertToOrderableSelection<Tables.Three<T1, T2, T3>>.orderBySingle(
    orderBy: (T1, T2, T3) -> Order
) = convertToOrderableSelection().orderBySingleInternal(Tables.convertFun(orderBy))

fun <T1, T2, T3, T4> CanConvertToOrderableSelection<Tables.Four<T1, T2, T3, T4>>.orderBy(
    orderBy: (T1, T2, T3, T4) -> List<Order>
) = convertToOrderableSelection().orderByInternal(Tables.convertFun(orderBy))

fun <T1, T2, T3, T4> CanConvertToOrderableSelection<Tables.Four<T1, T2, T3, T4>>.orderBySingle(
    orderBy: (T1, T2, T3, T4) -> Order
) = convertToOrderableSelection().orderBySingleInternal(Tables.convertFun(orderBy))

fun <T1, T2, T3, T4, T5> CanConvertToOrderableSelection<Tables.Five<T1, T2, T3, T4, T5>>.orderBy(
    orderBy: (T1, T2, T3, T4, T5) -> List<Order>
) = convertToOrderableSelection().orderByInternal(Tables.convertFun(orderBy))

fun <T1, T2, T3, T4, T5> CanConvertToOrderableSelection<Tables.Five<T1, T2, T3, T4, T5>>.orderBySingle(
    orderBy: (T1, T2, T3, T4, T5) -> Order
) = convertToOrderableSelection().orderBySingleInternal(Tables.convertFun(orderBy))

fun <T1, T2, T3, T4, T5, T6> CanConvertToOrderableSelection<Tables.Six<T1, T2, T3, T4, T5, T6>>.orderBy(
    orderBy: (T1, T2, T3, T4, T5, T6) -> List<Order>
) = convertToOrderableSelection().orderByInternal(Tables.convertFun(orderBy))

fun <T1, T2, T3, T4, T5, T6> CanConvertToOrderableSelection<Tables.Six<T1, T2, T3, T4, T5, T6>>.orderBySingle(
    orderBy: (T1, T2, T3, T4, T5, T6) -> Order
) = convertToOrderableSelection().orderBySingleInternal(Tables.convertFun(orderBy))

fun CanConvertToOrderableSelection<Tables.Many>.orderBy(
    orderBy: () -> List<Order>
) = convertToOrderableSelection().orderByInternal(Tables.convertFun(orderBy))

fun CanConvertToOrderableSelection<Tables.Many>.orderBySingle(
    orderBy: () -> Order
) = convertToOrderableSelection().orderBySingleInternal(Tables.convertFun(orderBy))