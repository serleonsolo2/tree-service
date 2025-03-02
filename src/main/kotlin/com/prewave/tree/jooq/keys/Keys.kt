/*
 * This file is generated by jOOQ.
 */
package com.prewave.tree.jooq.keys


import com.prewave.tree.jooq.tables.Edge
import com.prewave.tree.jooq.tables.records.EdgeRecord

import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val EDGE_PKEY: UniqueKey<EdgeRecord> = Internal.createUniqueKey(Edge.EDGE, DSL.name("edge_pkey"), arrayOf(Edge.EDGE.FROM_ID, Edge.EDGE.TO_ID), true)
