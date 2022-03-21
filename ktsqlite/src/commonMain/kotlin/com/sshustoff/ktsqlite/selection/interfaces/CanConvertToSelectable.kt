package com.sshustoff.ktsqlite.selection.interfaces

import com.sshustoff.ktsqlite.selection.Selectable

interface CanConvertToSelectable {

    fun convertToSelectable(): Selectable<*>
}