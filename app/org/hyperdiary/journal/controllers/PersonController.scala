package org.hyperdiary.journal.controllers

import org.hyperdiary.journal.models.*
import org.hyperdiary.journal.services.PersonService
import play.api.mvc.{AnyContent, BaseController, ControllerComponents, Request}

import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PersonController @Inject() (
    val controllerComponents: ControllerComponents,
    personService: PersonService
) extends BaseController {

  def view(id: String) = Action { implicit request: Request[AnyContent] =>
    personService.getPerson(id) match {
      case Some(person) =>
        val title = s"${person.givenName} ${person.surname}"
        val dates = "1926-2018"
        val dateFormat = DateTimeFormatter.ofPattern("dd MMMM YYYY")
        person.birthDate.map(birthDate => birthDate.format(dateFormat))
        Ok(
          org.hyperdiary.journal.views.html
            .person(title, dates, dateFormat, person)
        )
      case _ => NotFound("Person not found")

    }

  }

}
