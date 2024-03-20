package org.hyperdiary.journal.services

import org.hyperdiary.journal.models.{ Father, LabelledLink, Mother, Parents, Person }
import org.hyperdiary.journal.repository.SolidRepository

import java.net.URI
import java.time.LocalDate
import javax.inject.{ Inject, Singleton }

@Singleton
class PersonService @Inject() (solidRepository: SolidRepository) {

  def getPerson(id: String): Option[Person] =
    Some(
      Person(
        "Kenneth Robert",
        "Walpole",
        "male",
        Parents(
          Mother("Louisa", Some(new URI("http://krw.localhost:3000/person/I100194545786"))),
          Father("Ernest", Some(new URI("http://krw.localhost:3000/person/I100194342016")))
        ),
        Some(LocalDate.parse("1926-08-08")),
        Some(
          LabelledLink(
            "Rochford",
            new URI("http://dbpedia.org/resource/Rochford")
          )
        ),
        Some(LocalDate.parse("2018-07-20")),
        Some(
          LabelledLink(
            "Exeter",
            new URI("http://dbpedia.org/resource/Exeter")
          )
        ),
        siblings = List(
          LabelledLink(
            "Peggy",
            new URI("http://krw.localhost:3000/person/I100194424063")
          )
        ),
        children = List(
          LabelledLink(
            "Lois",
            new URI("http://krw.localhost:3000/person/I100194342035")
          ),
          LabelledLink(
            "Peter",
            new URI("http://krw.localhost:3000/person/I100194342029")
          ),
          LabelledLink(
            "Robert",
            new URI("http://krw.localhost:3000/person/I100194341916")
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
            "1935",
            new URI("http://krw.localhost:3000/residence/0")
          ),
          LabelledLink(
            "1944",
            new URI("http://krw.localhost:3000/residence/21")
          ),
          LabelledLink(
            "1977-1987",
            new URI("http://krw.localhost:3000/residence/22")
          ),
          LabelledLink(
            "1987-2018",
            new URI("http://krw.localhost:3000/residence/23")
          )
        )
      )
    )

}
