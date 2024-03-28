package org.hyperdiary.journal.models

import org.apache.jena.rdf.model.{Model, Property, Resource}
import org.apache.jena.sparql.vocabulary.FOAF
import org.apache.jena.vocabulary.{DCTerms, RDF, RDFS}
import org.hyperdiary.journal.vocabulary.{DBpedia, HyperDiary}

import scala.jdk.CollectionConverters.*
import scala.util.{Failure, Success, Try}

case class Person(
  identifier: String,
  givenName: String,
  familyName: String,
  gender: String,
  parentUris: List[String],
  label: Option[String] = None,
  birthDate: Option[String] = None,
  birthPlace: Option[String] = None,
  deathDate: Option[String] = None,
  deathPlace: Option[String] = None,
  siblingUris: List[String] = List.empty,
  spouseUris: List[String] = List.empty,
  childUris: List[String] = List.empty,
  education: List[String] = List.empty,
  employers: List[String] = List.empty,
  militaryUnits: List[String] = List.empty,
  residences: List[String] = List.empty
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
        parents    <- getObjectUris(personResource, DBpedia.parent)
        label      <- getOptionalLiteral(personResource, RDFS.label)
        birthDate  <- getOptionalLiteral(personResource, DBpedia.birthDate)
        birthPlace <- getOptionalResource(personResource, DBpedia.birthPlace)
        deathDate  <- getOptionalLiteral(personResource, DBpedia.deathDate)
        deathPlace <- getOptionalResource(personResource, DBpedia.deathPlace)
        siblings   <- getObjectUris(personResource, DBpedia.sibling)
        spouses    <- getObjectUris(personResource, DBpedia.spouse)
        children   <- getObjectUris(personResource, DBpedia.child)
        education <- getObjectUris(personResource, DBpedia.education)
        employers <- getObjectUris(personResource, DBpedia.employer)
        militaryUnits <- getObjectUris(personResource, DBpedia.militaryUnit)
        residences <- getObjectUris(personResource, HyperDiary.residence)
      } yield Person(
        identifier,
        givenName,
        familyName,
        gender,
        parents,
        label,
        birthDate,
        birthPlace,
        deathDate,
        deathPlace,
        siblings,
        spouses,
        children,
        education,
        employers,
        militaryUnits
      )
      person match {
        case Success(value) => Some(value)
        case Failure(e)     => None // TODO log error
      }
    }
  }

  private def getObjectUris(person: Resource, property: Property): Try[List[String]] = Try {
    person.listProperties(property).asScala.toList.map { statement =>
      statement.getObject.asResource().getURI
    }
  }

  private def getIdentifier(person: Resource): Try[String] =
    for {
      statement  <- Try(person.getRequiredProperty(DCTerms.identifier))
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

  private def getOptionalResource(person: Resource, property: Property): Try[Option[String]] = Try {
    Option(person.getProperty(property)).flatMap { statement =>
      Some(statement.getObject.asResource().getURI)
    }
  }

  private def getOptionalLiteral(person: Resource, property: Property): Try[Option[String]] = Try {
    Option(person.getProperty(property)).flatMap { literalStatement =>
      Some(literalStatement.getObject.asLiteral().getString)
    }
  }

}
