package org.hyperdiary.journal.connectors

import org.apache.jena.query.{ QueryFactory, ResultSet }
import org.apache.jena.rdf.model.RDFNode
import org.apache.jena.sparql.exec.http.QueryExecutionHTTPBuilder

import scala.jdk.CollectionConverters.*
import scala.util.{ Failure, Success, Try }

object DBpediaConnector {

  private val builder = QueryExecutionHTTPBuilder.service("https://dbpedia.org/sparql")

  def getLabel(resourceUri: String): Option[String] =
    for {
      results   <- runLabelQuery(resourceUri)
      solution  <- results.asScala.toList.headOption
      labelNode <- Option(solution.get("label"))
      label     <- getValue(labelNode)
    } yield label

  private def runLabelQuery(resourceUri: String): Option[ResultSet] = Try {
    val query = QueryFactory.create(getLabelQuery(resourceUri))
    builder.query(query)
    val queryExecution = builder.build()
    queryExecution.execSelect()
  } match {
    case Success(results) => Some(results)
    case Failure(e)       => None // TODO log error
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
