package com.sshustoff.ktsqlite

sealed class ColumnNullability {

    object NULL : ColumnNullability()
    object NOT_NULL : ColumnNullability()
}