package org.hyperdiary.journal.controllers

import org.hyperdiary.journal.services.{LabelService, PersonService}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import javax.inject.Inject

class LabelController @Inject() (
  val controllerComponents: ControllerComponents,
  labelService: LabelService
) extends BaseController {

  def view(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(
      org.hyperdiary.journal.views.html.labelMaker()
    )
    
  }

}
