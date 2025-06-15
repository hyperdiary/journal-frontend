package org.hyperdiary.journal.models

import org.apache.jena.rdf.model.Model
import org.apache.jena.vocabulary.{ DCTerms, RDF, RDFS }
import org.hyperdiary.journal.vocabulary.DBpedia

import scala.jdk.CollectionConverters.*
import scala.util.{ Failure, Success }

case class Place(identifier: String, label: String)
object Place extends RdfResource {

  def fromModel(model: Model): Option[Place] = {
    val maybePlaceResource = model
      .listSubjectsWithProperty(RDF.`type`, DBpedia.Place)
      .asScala
      .toList
      .headOption
    maybePlaceResource.flatMap { placeResource =>
      val place = for {
        identifier <- getRequiredLiteral(placeResource, DCTerms.identifier)
        label      <- getRequiredLiteral(placeResource, RDFS.label)
      } yield Place(
        identifier,
        label
      )
      place match {
        case Success(value) => Some(value)
        case Failure(e)     => None // TODO log error
      }
    }
  }

}
