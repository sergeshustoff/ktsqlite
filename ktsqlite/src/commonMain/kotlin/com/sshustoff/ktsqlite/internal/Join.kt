package com.sshustoff.ktsqlite.internal

import com.sshustoff.ktsqlite.predicates.core.Predicate
import com.sshustoff.ktsqlite.selection.Selectable

internal class Join(
    val selectable: Selectable<*>,
    val left: Boolean,
    val on: Predicate?
)