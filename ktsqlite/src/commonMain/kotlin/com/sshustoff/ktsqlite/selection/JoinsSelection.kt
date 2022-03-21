package com.sshustoff.ktsqlite.selection

import com.sshustoff.ktsqlite.asNullable
import com.sshustoff.ktsqlite.internal.Join
import com.sshustoff.ktsqlite.predicates.core.Predicate
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToGroupeableSelection
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToSelectable
import com.sshustoff.ktsqlite.selection.tuples.Tables
import kotlin.jvm.JvmName

class JoinsSelection<TablesRepresentation : Tables> internal constructor(
    val tables: TablesRepresentation,
    val distinct: Boolean,
    internal val originTable: Selectable<*>,
    internal val joins: List<Join>
) : CanConvertToGroupeableSelection<TablesRepresentation> {

    override fun convertToGroupeableSelection() = whereInternal { null }

    internal fun whereInternal(where: (TablesRepresentation) -> Predicate?) =
        ConditionalSelection(
            tables = tables,
            distinct = distinct,
            originTable = originTable,
            joins = joins,
            where = where(tables)
        )
}

fun <T1 : CanConvertToSelectable, T2 : CanConvertToSelectable> JoinsSelection<Tables.One<T1>>.join(
    with: T2,
    on: (T1, T2) -> Predicate? = { _, _ -> null }
) = joinInternal(
    this,
    with,
    with.convertToSelectable(),
    Tables.convertFun(on)
) { old, added ->
    Tables.Two(old.t1, added)
}

fun <T1, T2, T3 : CanConvertToSelectable> JoinsSelection<Tables.Two<T1, T2>>.join(
    with: T3,
    on: (T1, T2, T3) -> Predicate? = { _, _, _ -> null }
) = joinInternal(
    this,
    with,
    with.convertToSelectable(),
    Tables.convertFun(on)
) { old, added ->
    Tables.Three(old.t1, old.t2, added)
}

fun <T1, T2, T3, T4 : CanConvertToSelectable> JoinsSelection<Tables.Three<T1, T2, T3>>.join(
    with: T4,
    on: (T1, T2, T3, T4) -> Predicate? = { _, _, _, _ -> null }
) = joinInternal(
    this,
    with,
    with.convertToSelectable(),
    Tables.convertFun(on)
) { old, added ->
    Tables.Four(old.t1, old.t2, old.t3, added)
}

fun <T1, T2, T3, T4, T5 : CanConvertToSelectable> JoinsSelection<Tables.Four<T1, T2, T3, T4>>.join(
    with: T5,
    on: (T1, T2, T3, T4, T5) -> Predicate? = { _, _, _, _, _ -> null }
) = joinInternal(
    this,
    with,
    with.convertToSelectable(),
    Tables.convertFun(on)
) { old, added ->
    Tables.Five(old.t1, old.t2, old.t3, old.t4, added)
}

fun <T1, T2, T3, T4, T5, T6 : CanConvertToSelectable> JoinsSelection<Tables.Five<T1, T2, T3, T4, T5>>.join(
    with: T6,
    on: (T1, T2, T3, T4, T5, T6) -> Predicate? = { _, _, _, _, _, _ -> null }
) = joinInternal(
    this,
    with,
    with.convertToSelectable(),
    Tables.convertFun(on)
) { old, added ->
    Tables.Six(old.t1, old.t2, old.t3, old.t4, old.t5, added)
}

fun JoinsSelection<Tables.Six<*, *, *, *, *, *>>.join(
    with: CanConvertToSelectable,
    on: () -> Predicate? = { null }
) = JoinsSelection(
    Tables.Many, distinct, originTable,
    joins + Join(with.convertToSelectable(), false, on())
)

@JvmName("joinMany")
fun JoinsSelection<Tables.Many>.join(
    with: CanConvertToSelectable,
    on: () -> Predicate? = { null }
) = JoinsSelection(
    Tables.Many, distinct, originTable,
    joins + Join(with.convertToSelectable(), false, on())
)

fun <T1 : CanConvertToSelectable, T2 : CanConvertToSelectable> JoinsSelection<Tables.One<T1>>.leftJoin(
    with: T2,
    on: (T1, T2) -> Predicate? = { _, _ -> null }
) = leftJoinInternal(
    this,
    with,
    with.convertToSelectable(),
    Tables.convertFun(on),
    buildNewTables = { old, added ->
        Tables.Two(old.t1, added)
    },
    buildNewTablesNullable = { old, added ->
        Tables.Two(old.t1, added.asNullable())
    }
)

fun <T1, T2, T3 : CanConvertToSelectable> JoinsSelection<Tables.Two<T1, T2>>.leftJoin(
    with: T3,
    on: (T1, T2, T3) -> Predicate? = { _, _, _ -> null }
) = leftJoinInternal(
    this,
    with,
    with.convertToSelectable(),
    Tables.convertFun(on),
    buildNewTablesNullable = { old, added ->
        Tables.Three(old.t1, old.t2, added.asNullable())
    },
    buildNewTables = { old, added ->
        Tables.Three(old.t1, old.t2, added)
    }
)

fun <T1, T2, T3, T4 : CanConvertToSelectable> JoinsSelection<Tables.Three<T1, T2, T3>>.leftJoin(
    with: T4,
    on: (T1, T2, T3, T4) -> Predicate? = { _, _, _, _ -> null }
) = leftJoinInternal(
    this,
    with,
    with.convertToSelectable(),
    Tables.convertFun(on),
    buildNewTables = { old, added ->
        Tables.Four(old.t1, old.t2, old.t3, added)
    },
    buildNewTablesNullable = { old, added ->
        Tables.Four(old.t1, old.t2, old.t3, added.asNullable())
    }
)


fun <T1, T2, T3, T4, T5 : CanConvertToSelectable> JoinsSelection<Tables.Four<T1, T2, T3, T4>>.leftJoin(
    with: T5,
    on: (T1, T2, T3, T4, T5) -> Predicate? = { _, _, _, _, _ -> null }
) = leftJoinInternal(
    this,
    with,
    with.convertToSelectable(),
    Tables.convertFun(on),
    buildNewTablesNullable =  { old, added ->
        Tables.Five(old.t1, old.t2, old.t3, old.t4, added.asNullable())
    },
    buildNewTables =  { old, added ->
        Tables.Five(old.t1, old.t2, old.t3, old.t4, added)
    }
)

fun <T1, T2, T3, T4, T5, T6 : CanConvertToSelectable> JoinsSelection<Tables.Five<T1, T2, T3, T4, T5>>.leftJoin(
    with: T6,
    on: (T1, T2, T3, T4, T5, T6) -> Predicate? = { _, _, _, _, _, _ -> null }
) = leftJoinInternal(
    this,
    with,
    with.convertToSelectable(),
    Tables.convertFun(on),
    buildNewTablesNullable =  { old, added ->
        Tables.Six(old.t1, old.t2, old.t3, old.t4, old.t5, added.asNullable())
    },
    buildNewTables =  { old, added ->
        Tables.Six(old.t1, old.t2, old.t3, old.t4, old.t5, added)
    }
)

fun JoinsSelection<Tables.Six<*, *, *, *, *, *>>.leftJoin(
    with: CanConvertToSelectable,
    on: () -> Predicate? = { null }
) = JoinsSelection(
    Tables.Many, distinct, originTable,
    joins + Join(with.convertToSelectable(), true, on())
)

@JvmName("leftJoinMany")
fun JoinsSelection<Tables.Many>.leftJoin(
    with: CanConvertToSelectable,
    on: () -> Predicate? = { null }
) = JoinsSelection(
    Tables.Many, distinct, originTable,
    joins + Join(with.convertToSelectable(), true, on())
)

private fun <TOldTables: Tables, TNewTables: Tables, TNewTablesNullable: Tables, TNewTable> leftJoinInternal(
    sourceJoin: JoinsSelection<TOldTables>,
    with: TNewTable,
    selectable: Selectable<*>,
    on: (TNewTables) -> Predicate?,
    buildNewTables: (TOldTables, TNewTable) -> TNewTables,
    buildNewTablesNullable: (TOldTables, TNewTable) -> TNewTablesNullable
): JoinsSelection<TNewTablesNullable> {
    val withNullable = buildNewTablesNullable(sourceJoin.tables, with)
    val forJoinCondition = buildNewTables(sourceJoin.tables, with)
    return JoinsSelection(
        withNullable, sourceJoin.distinct, sourceJoin.originTable,
        sourceJoin.joins + Join(selectable, true, on(forJoinCondition))
    )
}

private fun <TOldTables: Tables, TNewTables: Tables, TNewTable> joinInternal(
    sourceJoin: JoinsSelection<TOldTables>,
    with: TNewTable,
    selectable: Selectable<*>,
    on: (TNewTables) -> Predicate?,
    buildNewTables: (TOldTables, TNewTable) -> TNewTables
): JoinsSelection<TNewTables> {
    val tuple = buildNewTables(sourceJoin.tables, with)
    return JoinsSelection(
        tuple, sourceJoin.distinct, sourceJoin.originTable,
        sourceJoin.joins + Join(selectable, false, on(tuple))
    )
}

fun <T> JoinsSelection<Tables.One<T>>.where(
    condition: (T) -> Predicate
) = whereInternal(Tables.convertFun(condition))

fun <T1, T2> JoinsSelection<Tables.Two<T1, T2>>.where(
    condition: (T1, T2) -> Predicate
) = whereInternal(Tables.convertFun(condition))

fun <T1, T2, T3> JoinsSelection<Tables.Three<T1, T2, T3>>.where(
    condition: (T1, T2, T3) -> Predicate
) = whereInternal(Tables.convertFun(condition))

fun <T1, T2, T3, T4> JoinsSelection<Tables.Four<T1, T2, T3, T4>>.where(
    condition: (T1, T2, T3, T4) -> Predicate
) = whereInternal(Tables.convertFun(condition))

fun <T1, T2, T3, T4, T5> JoinsSelection<Tables.Five<T1, T2, T3, T4, T5>>.where(
    condition: (T1, T2, T3, T4, T5) -> Predicate
) = whereInternal(Tables.convertFun(condition))

fun <T1, T2, T3, T4, T5, T6> JoinsSelection<Tables.Six<T1, T2, T3, T4, T5, T6>>.where(
    condition: (T1, T2, T3, T4, T5, T6) -> Predicate
) = whereInternal(Tables.convertFun(condition))

fun JoinsSelection<Tables.Many>.where(
    condition: () -> Predicate
) = whereInternal(Tables.convertFun(condition))