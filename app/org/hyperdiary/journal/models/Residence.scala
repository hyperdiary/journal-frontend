package org.hyperdiary.journal.models

import org.apache.jena.rdf.model.Model
import org.apache.jena.vocabulary.{ DCTerms, RDF }
import org.hyperdiary.journal.vocabulary.{ DBpedia, HyperDiary }

import scala.jdk.CollectionConverters.*
import scala.util.{ Failure, Success }

case class Residence(locationUri: String, date: Option[String], startDate: Option[String], endDate: Option[String])
object Residence extends RdfResource {

  def fromModel(model: Model): Option[Residence] = {
    val maybeResidenceResource = model
      .listSubjectsWithProperty(RDF.`type`, HyperDiary.Residence)
      .asScala
      .toList
      .headOption
    maybeResidenceResource.flatMap { residenceResource =>
      val residence = for {
        locationUri <- getRequiredResource(residenceResource, DBpedia.location)
        date        <- getOptionalLiteral(residenceResource, DCTerms.date)
        startDate   <- getOptionalLiteral(residenceResource, DBpedia.startDate)
        endDate     <- getOptionalLiteral(residenceResource, DBpedia.endDate)
      } yield Residence(
        locationUri,
        date,
        startDate,
        endDate
      )
      residence match {
        case Success(value) => Some(value)
        case Failure(e)     => None // TODO log error
      }
    }
  }

}
