package com.sshustoff.ktsqlite.parsing

import com.sshustoff.ktsqlite.Column

/*
 * ************************************************
 * SqlResultParser
 * Date: 04.03.2018
 * ------------------------------------------------
 * Copyright (C) SPB TV AG 2007-2018 - All Rights Reserved
 * Author: Serge Shustov
 * ************************************************
 */
interface SqlResultParser {

    fun getLong(column: Column<*, *>): Long?

    fun getFloat(column: Column<*, *>): Float?

    fun getText(column: Column<*, *>): String?
}