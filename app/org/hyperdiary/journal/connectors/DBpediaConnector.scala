package org.hyperdiary.journal.connectors

import org.apache.jena.query.QueryFactory
import org.apache.jena.rdf.model.RDFNode
import org.apache.jena.sparql.exec.http.QueryExecutionHTTPBuilder

import scala.jdk.CollectionConverters.*
import scala.util.{ Failure, Success, Try }

object DBpediaConnector {

  private val builder = QueryExecutionHTTPBuilder.service("https://dbpedia.org/sparql")

  def getLabel(resourceUri: String): Option[String] = {
    val query = QueryFactory.create(getLabelQuery(resourceUri))
    builder.query(query)
    val queryExecution = builder.build()
    val results = queryExecution.execSelect()
    val querySolution = results.asScala.toList.headOption
    for {
      solution  <- querySolution
      labelNode <- Option(solution.get("label"))
      label     <- getValue(labelNode)
    } yield label
  }

  private def getValue(node: RDFNode): Option[String] =
    Try(node.asLiteral()) match {
      case Success(lab) => Some(lab.getString)
      case Failure(e)   => None // TODO log an error
    }

  private def getLabelQuery(resourceUri: String): String =
    s"""
       |PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
       |SELECT ?label WHERE { <$resourceUri> rdfs:label ?label . FILTER ( langMatches(lang(?label), \"en\") ) }
       |""".stripMargin

}
