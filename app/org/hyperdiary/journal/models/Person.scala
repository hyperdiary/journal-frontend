package org.hyperdiary.journal.models

import org.apache.jena.rdf.model.{Model, Resource}
import org.apache.jena.sparql.vocabulary.FOAF
import org.apache.jena.vocabulary.{DCTerms, RDF, RDFS}
import org.hyperdiary.journal.vocabulary.DBpedia

import scala.jdk.CollectionConverters.*
import scala.util.{Failure, Success, Try}

case class Person(
  identifier: String,
  givenName: String,
  familyName: String,
  gender: String,
  parentUris: List[String],
  label: Option[String] = None  
)
object Person extends RDFUtils {
  def fromModel(model: Model): Option[Person] = {
    val maybePersonResource = model
      .listSubjectsWithProperty(RDF.`type`, FOAF.Person)
      .asScala
      .toList
      .headOption
    maybePersonResource.flatMap { personResource =>
      val person = for {
        identifier <- getIdentifier(personResource)
        givenName  <- getGivenName(personResource)
        familyName <- getFamilyName(personResource)
        gender     <- getGender(personResource)
        parents    <- getParents(personResource)
        label      <- Try(getLabel(personResource))
      } yield Person(identifier, givenName, familyName, gender, parents, label)
      person match {
        case Success(value) => Some(value)
        case Failure(e)     => None // TODO log error
      }
    }
  }
  
  private def getIdentifier(person: Resource): Try[String] =
    for {
      statement <- Try(person.getRequiredProperty(DCTerms.identifier))
      identifier <- Try(statement.getObject.asLiteral().getString)
    } yield identifier

  private def getGivenName(person: Resource): Try[String] =
    for {
      statement <- Try(person.getRequiredProperty(FOAF.givenName))
      givenName <- Try(statement.getObject.asLiteral().getString)
    } yield givenName

  private def getFamilyName(person: Resource): Try[String] =
    for {
      statement  <- Try(person.getRequiredProperty(FOAF.familyName))
      familyName <- Try(statement.getObject.asLiteral().getString)
    } yield familyName

  private def getGender(person: Resource): Try[String] =
    for {
      statement <- Try(person.getRequiredProperty(FOAF.gender))
      gender    <- Try(statement.getObject.asLiteral().getString)
    } yield gender

  private def getParents(person: Resource): Try[List[String]] = Try {
    person.listProperties(DBpedia.parent).asScala.toList.map { parentStmt =>
      parentStmt.getObject.asResource().getURI
    }
  }

  private def getLabel(person: Resource): Option[String] =
    Option(person.getProperty(RDFS.label)).flatMap { labelStmt =>
      Try(labelStmt.getObject.asLiteral()) match {
        case Success(labelLit) => Some(labelLit.getString)
        case Failure(e)        => None // TODO log error not a literal
      }
    }

}
