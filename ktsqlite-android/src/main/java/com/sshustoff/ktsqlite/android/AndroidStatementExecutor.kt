package com.sshustoff.ktsqlite.android

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.sshustoff.ktsqlite.parsing.SqlResultParser
import com.sshustoff.ktsqlite.statement.StatementExecutor
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class AndroidStatementExecutor(
    private val writable: () -> SQLiteDatabase,
    private val readable: () -> SQLiteDatabase = writable,
    private val closeAfterExecute: Boolean = false,
    private val debug: Boolean = false
) : StatementExecutor {

    override fun <R> select(
        sql: String,
        args: Array<String>,
        itemParser: (SqlResultParser) -> R
    ): List<R> {
        return readable().useAndCloseIfNeeded {
            if (debug) {
                Log.d("ktsqlite", "sql:\n\r$sql")
            }
            it.rawQuery(sql, args).use { cursor ->
                ArrayList<R>().apply {
                    while (cursor.moveToNext()) {
                        add(itemParser(CursorParser(cursor)))
                    }

                    if (debug) {
                        Log.d("ktsqlite", "select result count: ${this.size}")
                    }
                }
            }
        }
    }

    override fun insert(sql: String, args: Array<String>): Long {
        return writable().useAndCloseIfNeeded {
            if (debug) {
                Log.d("ktsqlite", "sql:\n\r$sql")
            }
            it.compileStatement(sql).apply {
                bindAllArgsAsStrings(args)
            }.executeInsert()
        }
    }

    override fun execute(sql: String, args: Array<String>) {
        if (debug) {
            Log.d("ktsqlite", "sql:\n\r$sql")
        }
        writable().useAndCloseIfNeeded {
            it.execSQL(sql, args)
        }
    }

    override suspend fun <T> inTransaction(block: () -> T): T {
        return withContext(transactionDispatcher) {
            inTransactionBlocking(block)
        }
    }

    override fun <T> inTransactionBlocking(block: () -> T): T {
        val writable = writable()
        writable.beginTransactionNonExclusive()
        return try {
            block.invoke()
                .also {
                    writable.setTransactionSuccessful()
                }
        } finally {
            writable.endTransaction()
        }
    }

    private fun <R> SQLiteDatabase.useAndCloseIfNeeded(run: (SQLiteDatabase) -> R): R {
        return if (closeAfterExecute) {
            use(run)
        } else {
            run(this)
        }
    }

    companion object {
        private val transactionDispatcher =
            Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    }
}
