package org.hyperdiary.journal.models

import org.apache.jena.rdf.model.{ Property, Resource }
import org.apache.jena.vocabulary.DCTerms

import scala.util.Try

trait RdfResource {

  def getRequiredLiteral(resource: Resource, property: Property): Try[String] =
    for {
      statement  <- Try(resource.getRequiredProperty(property))
      identifier <- Try(statement.getObject.asLiteral().getString)
    } yield identifier

  def getRequiredResource(resource: Resource, property: Property): Try[String] =
    for {
      statement  <- Try(resource.getRequiredProperty(property))
      identifier <- Try(statement.getObject.asResource().getURI)
    } yield identifier

}