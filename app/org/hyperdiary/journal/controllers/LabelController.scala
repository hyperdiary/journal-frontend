package org.hyperdiary.journal.controllers

import org.hyperdiary.journal.services.{ LabelService, PersonService }
import play.api.mvc.{ Action, AnyContent, BaseController, ControllerComponents, MessagesActionBuilder, MessagesRequest, Request }
import org.hyperdiary.journal.forms.LabelDataFormProvider

import javax.inject.Inject

class LabelController @Inject() (
  messagesAction: MessagesActionBuilder,
  val controllerComponents: ControllerComponents,
  labelService: LabelService
) extends BaseController {

  def view(): Action[AnyContent] = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    Ok(
      org.hyperdiary.journal.views.html.labelMaker(LabelDataFormProvider())
    )

  }

  def submit(): Action[AnyContent] = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    request.body.asFormUrlEncoded.get("action").headOption match {
      case Some("add") =>
        LabelDataFormProvider()
          .bindFromRequest()
          .fold(
            formWithErrors => BadRequest(org.hyperdiary.journal.views.html.labelMaker(formWithErrors)),
            labelData => {
              labelService.createLabel(labelData.labelText, s"${labelData.targetGraph}/${labelData.targetName}")
              Ok(org.hyperdiary.journal.views.html.labelMaker(LabelDataFormProvider()))
            }
          )
      case Some("finish") => Ok(org.hyperdiary.journal.views.html.labelMaker(LabelDataFormProvider()))
      case _              => Ok(org.hyperdiary.journal.views.html.labelMaker(LabelDataFormProvider()))
    }
  }

}
