package org.hyperdiary.journal.models

import org.apache.jena.rdf.model.Model
import org.apache.jena.vocabulary.{ DCTerms, RDF }
import org.hyperdiary.journal.vocabulary.{ DBpedia, HyperDiary }

import scala.jdk.CollectionConverters.*
import scala.util.{ Failure, Success }

case class Residence(identifier: String, date: String, locationUri: String)
object Residence extends RdfResource {

  def fromModel(model: Model): Option[Residence] = {
    val maybeResidenceResource = model
      .listSubjectsWithProperty(RDF.`type`, HyperDiary.Residence)
      .asScala
      .toList
      .headOption
    maybeResidenceResource.flatMap { residenceResource =>
      val residence = for {
        identifier  <- getRequiredLiteral(residenceResource, DCTerms.identifier)
        date        <- getRequiredLiteral(residenceResource, DCTerms.date)
        locationUri <- getRequiredResource(residenceResource, DBpedia.location)
      } yield Residence(
        identifier,
        date,
        locationUri
      )
      residence match {
        case Success(value) => Some(value)
        case Failure(e)     => None // TODO log error
      }
    }
  }

}
