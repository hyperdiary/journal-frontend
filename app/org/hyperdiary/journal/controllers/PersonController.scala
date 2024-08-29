package org.hyperdiary.journal.controllers

import org.hyperdiary.journal.models.*
import org.hyperdiary.journal.services.PersonService
import play.api.mvc.{ Action, AnyContent, BaseController, ControllerComponents, Request }

import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PersonController @Inject() (
  val controllerComponents: ControllerComponents,
  personService: PersonService
) extends BaseController {

  def view(id: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    if (id == "example") {
      val person = personService.getExamplePerson
      val title = s"${person.givenName} ${person.surname}"
      val dates = "1926-2018"
      val dateFormat = DateTimeFormatter.ofPattern("dd MMMM YYYY")
      Ok(
        org.hyperdiary.journal.views.html
          .person(title, dates, dateFormat, person)
      )
    } else {
      personService.getPerson(id) match {
        case Some(person) =>
          val title = s"${person.givenName} ${person.surname}"
          val dates = "1926-2018"
          val dateFormat = DateTimeFormatter.ofPattern("dd MMMM YYYY")
          Ok(
            org.hyperdiary.journal.views.html
              .person(title, dates, dateFormat, person)
          )
        case _ => NotFound("Person not found")
      }
    }
  }
}
