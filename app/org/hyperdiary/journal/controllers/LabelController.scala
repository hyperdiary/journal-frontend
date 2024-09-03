package org.hyperdiary.journal.controllers

import org.hyperdiary.journal.services.{ LabelService, PersonService }
import play.api.mvc.{ Action, AnyContent, BaseController, ControllerComponents, Request }
import org.hyperdiary.journal.forms.LabelDataFormProvider

import javax.inject.Inject

class LabelController @Inject() (
  val controllerComponents: ControllerComponents,
  labelService: LabelService
) extends BaseController {

  def view(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(
      org.hyperdiary.journal.views.html.labelMaker(LabelDataFormProvider())
    )

  }

  def submit(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    request.body.asFormUrlEncoded.get("action").headOption match {
      case Some("add") => ???
      case Some("finish") => ???
      case _ => ???
    }
    Ok(
      org.hyperdiary.journal.views.html.labelMaker(LabelDataFormProvider())
    )

  }

}
