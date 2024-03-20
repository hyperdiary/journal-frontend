package org.hyperdiary.journal.models

import java.net.URI
import java.time.LocalDate

case class Father(label: String = "Unknown", uri: Option[URI] = None)
case class Mother(label: String = "Unknown", uri: Option[URI] = None)
case class Parents(mother: Mother, father: Father)
case class LabelledLink(label: String, uri: URI)
case class Person(
  givenName: String,
  surname: String,
  gender: String,
  parents: Parents,
  birthDate: Option[LocalDate] = None,
  birthPlace: Option[LabelledLink] = None,
  deathDate: Option[LocalDate] = None,
  deathPlace: Option[LabelledLink] = None,
  siblings: List[LabelledLink] = List.empty,
  children: List[LabelledLink] = List.empty,
  educationalEstablishments: List[LabelledLink] = List.empty,
  employers: List[LabelledLink] = List.empty,
  militaryUnits: List[LabelledLink] = List.empty,
  residences: List[LabelledLink] = List.empty,
  relatedImages: List[LabelledLink] = List.empty
)
