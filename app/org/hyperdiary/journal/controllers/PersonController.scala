package org.hyperdiary.journal.controllers

import org.hyperdiary.journal.services.JournalService
import play.api.mvc.{AnyContent, BaseController, ControllerComponents, Request}

import javax.inject.Inject

class PersonController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def view(id: String) = Action { implicit request: Request[AnyContent] =>
    Ok(org.hyperdiary.journal.views.html.person())
  }

}
