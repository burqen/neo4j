/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.compiler.v2_3.commands

import org.neo4j.cypher.internal.compiler.v2_3._
import org.neo4j.cypher.internal.compiler.v2_3.commands.expressions.{Identifier, PathImpl, SizeFunction}
import org.neo4j.cypher.internal.compiler.v2_3.pipes.QueryStateHelper
import org.neo4j.cypher.internal.frontend.v2_3.CypherTypeException
import org.neo4j.cypher.internal.frontend.v2_3.test_helpers.CypherFunSuite
import org.neo4j.graphdb.{Node, Relationship}

class SizeFunctionTest extends CypherFunSuite {

  test("size can be used on collections") {
    //given
    val l = Seq("it", "was", "the")
    val m = ExecutionContext.from("l" -> l)
    val sizeFunction = SizeFunction(Identifier("l"))

    //when
    val result = sizeFunction.apply(m)(QueryStateHelper.empty)

    //then
    result should equal(3)
  }

  test("size can be used on strings") {
    //given
    val s = "it was the"
    val m = ExecutionContext.from("s" -> s)
    val sizeFunction = SizeFunction(Identifier("s"))

    //when
    val result = sizeFunction.apply(m)(QueryStateHelper.empty)

    //then
    result should equal(10)
  }

  test("size cannot be used on paths") {
    //given
    val p = new PathImpl(mock[Node], mock[Relationship], mock[Node])
    val m = ExecutionContext.from("p" -> p)
    val sizeFunction = SizeFunction(Identifier("p"))

    //when/then
    intercept[CypherTypeException](sizeFunction.apply(m)(QueryStateHelper.empty))
  }
}
