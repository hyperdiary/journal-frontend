package org.hyperdiary.journal.services

import org.hyperdiary.journal.connectors.DBpediaConnector
import org.hyperdiary.journal.models.*
import org.hyperdiary.journal.repository.SolidRepository
import org.hyperdiary.journal.vocabulary.PersonalKnowledgeGraph

import java.net.URI
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.{ Inject, Singleton }

@Singleton
class PersonService @Inject (pkg: PersonalKnowledgeGraph)(solidRepository: SolidRepository) extends BaseService {

  // private val solidPersonBaseUri = s"$cssPodUri/person"

  def getPerson(personId: String): Option[PersonHtml] = {
    val personUri = s"${pkg.personBaseUri}$personId"
    for {
      person <- solidRepository.getPerson(personUri)
    } yield PersonHtml(
      person.givenName,
      person.familyName,
      person.gender,
      getParents(person.parentUris),
      getEventDate(person.birthDate),
      getLabelledLink(person.birthPlace),
      getEventDate(person.deathDate),
      getLabelledLink(person.deathPlace),
      getPersonLinks(person.siblingUris),
      getPersonLinks(person.spouseUris),
      getPersonLinks(person.childUris),
      getLabelledLinks(person.education),
      getLabelledLinks(person.employers),
      getLabelledLinks(person.militaryUnits),
      getResidenceLinks(person.residences)
    )
  }

  private def getParents(parentsUris: List[String]): Parents = {
    val parents = parentsUris.flatMap(parentUri => solidRepository.getPerson(updateUri(parentUri, pkg.baseUri)))
    Parents(getMother(parents), getFather(parents))
  }

  private def getMother(parents: List[Person]): Mother =
    parents.find(_.gender == "female") match {
      case Some(mother) =>
        Mother(mother.label.getOrElse(mother.givenName), Some(new URI(s"$frontendBaseUri/person/${mother.identifier}")))
      case _ => Mother()
    }

  private def getFather(parents: List[Person]): Father =
    parents.find(_.gender == "male") match {
      case Some(father) =>
        Father(father.label.getOrElse(father.givenName), Some(new URI(s"$frontendBaseUri/person/${father.identifier}")))
      case _ => Father()
    }

  private def getPersonLinks(personUris: List[String]): List[LabelledLink] = {
    val people = personUris.map(personUri => solidRepository.getPerson(updateUri(personUri, pkg.baseUri)))
    people.flatten.map { person =>
      LabelledLink(
        person.label.getOrElse(person.givenName),
        new URI(s"$frontendBaseUri/person/${person.identifier}")
      )
    }
  }

  private def getResidenceLinks(residenceUris: List[String]): List[LabelledLink] =
    for {
      residence <- getResidences(residenceUris)
      place     <- solidRepository.getPlace(updateUri(residence.locationUri, pkg.baseUri))
    } yield LabelledLink(getResidenceLabel(residence, place), new URI(s"$frontendBaseUri/place/${place.identifier}"))

  private def getResidenceLabel(residence: Residence, place: Place): String = {
    val placeLabel = place.label
    if (residence.date.nonEmpty) {
      s"$placeLabel (${residence.date})" // FIXME - this is an Option
    } else {
      placeLabel
    }
  }

  private def getResidences(residenceUris: List[String]): List[Residence] =
    for {
      residenceUri <- residenceUris
      residence    <- solidRepository.getResidence(updateUri(residenceUri, pkg.baseUri))
    } yield residence

  private def getEventDate(maybeEventDate: Option[String]): Option[Either[String, LocalDate]] = {
    val datePattern = "(18|19|20)[0-9]{2}-[01][0-9]-[0123][0-9]".r
    maybeEventDate.flatMap { dateString =>
      datePattern.findFirstIn(dateString) match {
        case Some(date) => Some(Right(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)))
        case None       => Some(Left(dateString))
      }
    }
  }

  private def getLabelledLinks(uriList: List[String]): List[LabelledLink] =
    uriList.flatMap(uri => getLabelledLink(Some(uri)))

  private def getLabelledLink(maybeUri: Option[String]): Option[LabelledLink] =
    for {
      uri   <- maybeUri
      label <- DBpediaConnector.getLabel(uri)
    } yield LabelledLink(label, new URI(uri))

  def getExamplePerson: PersonHtml =
    PersonHtml(
      "Kenneth Robert",
      "Walpole",
      "male",
      Parents(
        Mother("Louisa", Some(new URI("http://localhost:9000/person/I100194545786"))),
        Father("Ernest", Some(new URI("http://localhost:9000/person/I100194342016")))
      ),
      Some(Right(LocalDate.parse("1926-08-08"))),
      Some(
        LabelledLink(
          "Rochford",
          new URI("http://dbpedia.org/resource/Rochford")
        )
      ),
      Some(Right(LocalDate.parse("2018-07-20"))),
      Some(
        LabelledLink(
          "Exeter",
          new URI("http://dbpedia.org/resource/Exeter")
        )
      ),
      siblings = List(
        LabelledLink(
          "Peggy",
          new URI("http://localhost:9000/person/I100194424063")
        )
      ),
      spouses = List(
        LabelledLink(
          "Irene",
          new URI("http://localhost:9000/person/I100194341956")
        )
      ),
      children = List(
        LabelledLink(
          "Lois",
          new URI("http://localhost:9000/person/I100194342035")
        ),
        LabelledLink(
          "Peter",
          new URI("http://localhost:9000/person/I100194342029")
        ),
        LabelledLink(
          "Robert",
          new URI("http://localhost:9000/person/I100194341916")
        )
      ),
      educationalEstablishments = List(
        LabelledLink(
          "Manchester Grammar School",
          new URI("http://dbpedia.org/resource/Manchester_Grammar_School")
        ),
        LabelledLink(
          "Ridgewood High School (New Jersey)",
          new URI("http://dbpedia.org/resource/Ridgewood_High_School_(New_Jersey)")
        ),
        LabelledLink(
          "Ramsey High School (New Jersey)",
          new URI("http://dbpedia.org/resource/Ramsey_High_School_(New_Jersey)")
        ),
        LabelledLink(
          "Staff College, Camberley",
          new URI("http://dbpedia.org/resource/Staff_College,_Camberley")
        ),
        LabelledLink(
          "Henley Business School",
          new URI("http://dbpedia.org/resource/Henley_Business_School")
        )
      ),
      employers = List(
        LabelledLink(
          "Royal Navy",
          new URI("http://dbpedia.org/resource/Royal_Navy")
        ),
        LabelledLink(
          "British Army",
          new URI("http://dbpedia.org/resource/British_Army")
        )
      ),
      militaryUnits = List(
        LabelledLink(
          "HMS Birmingham",
          new URI("http://dbpedia.org/resource/HMS_Birmingham_(C19)")
        ),
        LabelledLink(
          "Royal Artillery",
          new URI("http://dbpedia.org/resource/Royal_Artillery")
        )
      ),
      residences = List(
        LabelledLink(
          "Rosslyn Avenue (1935)",
          new URI("http://localhost:9000/place/R0")
        ),
        LabelledLink(
          "Church Road (1939,1944)",
          new URI("http://localhost:9000/place/R1")
        ),
        LabelledLink(
          "Dornaford Bungalow (1977-1987)",
          new URI("http://localhost:9000/place/R22")
        ),
        LabelledLink(
          "Oak Ridge (1987-2018)",
          new URI("http://localhost:9000/place/R24")
        )
      )
    )

}
