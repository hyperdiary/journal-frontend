package org.hyperdiary.journal.controllers

import play.api.*
import play.api.mvc.*

import javax.inject.*

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class JournalController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(org.hyperdiary.journal.views.html.index())
  }

  def view(id: String) = Action { implicit request: Request[AnyContent] =>
    Ok(org.hyperdiary.journal.views.html.journal())
  }

  def viewEntry(id: String, entryId: String) = Action { implicit request: Request[AnyContent] =>
    Ok(org.hyperdiary.journal.views.html.entry())
  }

}
